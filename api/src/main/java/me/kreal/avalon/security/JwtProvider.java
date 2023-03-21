package me.kreal.avalon.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.kreal.avalon.domain.Game;
import me.kreal.avalon.domain.Player;
import me.kreal.avalon.domain.Record;
import me.kreal.avalon.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JwtProvider {

    @Value("$security.jwt.token.key")
    private String key;

    public String createToken(AuthUserDetail userDetails){

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("gameId", userDetails.getGameId());
        claims.put("playerId", userDetails.getPlayerId());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String createToken(User u){

        Claims claims = Jwts.claims().setSubject(u.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String createToken(Record r){

        Claims claims = Jwts.claims().setSubject(r.getUser().getUsername());
        claims.put("gameId", r.getGame().getGameId());
        claims.put("playerId", r.getPlayerId());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Optional<UserDetails> resolveToken(HttpServletRequest request){
        String prefixedToken = request.getHeader("Authorization"); // extract token value by key "Authorization"
        try {
            String token = prefixedToken.substring(7); // remove the prefix "Bearer "

            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

            String username = claims.getSubject();

            // convert the permission list to a list of GrantedAuthority
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("player"));

            if (claims.get("gameId") == null || claims.get("playerId") == null) {
                return Optional.of(AuthUserDetail.builder()
                        .username(username)
                        .authorities(authorities)
                        .build());
            }

            //return a userDetail object with the permissions the user has
            return Optional.of(AuthUserDetail.builder()
                    .username(username)
                    .authorities(authorities)
                    .gameId(((Number) claims.get("gameId")).longValue())
                    .playerId(((Number) claims.get("playerId")).longValue())
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
