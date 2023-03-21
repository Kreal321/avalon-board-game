package me.kreal.avalon.service;

import me.kreal.avalon.dao.UserDao;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.request.UserRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.JwtProvider;
import me.kreal.avalon.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserDao userDao, JwtProvider jwtProvider) {
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
    }

    public Optional<User> findUserByUsername(String username) {
        return this.userDao.findUserByUsername(username);
    }

    @Transactional
    public DataResponse createNewPlayer(UserRequest request) {

        User u = UserMapper.convertToEntity(request);

        if (this.findUserByUsername(u.getUsername()).isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Username is existed.")
                    .build();
        }

        if (u.getPreferredName() == null || u.getPreferredName().trim().isEmpty()) {
            u.setPreferredName(u.getUsername());
        }

        this.userDao.save(u);

        return DataResponse.builder()
                .success(true)
                .message("Creation succeeded.")
                .data(UserMapper.convertToDTO(u))
                .token(jwtProvider.createToken(u))
                .build();

    }

    @Transactional
    public DataResponse findUserByLoginRequest(UserRequest request) {

        User u = UserMapper.convertToEntity(request);

        Optional<User> userOptional = this.findUserByUsername(u.getUsername());

        if (!userOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Cannot find user.")
                    .build();
        }

        return DataResponse.builder()
                .success(true)
                .message("Login succeeded.")
                .data(UserMapper.convertToDTO(userOptional.get()))
                .token(jwtProvider.createToken(userOptional.get()))
                .build();

    }


}
