package me.kreal.avalon.service;

import me.kreal.avalon.dao.UserDao;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.MailDTO;
import me.kreal.avalon.dto.request.UserRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.security.JwtProvider;
import me.kreal.avalon.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;
    private final MailService mailService;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserService(UserDao userDao, MailService mailService, JwtProvider jwtProvider) {
        this.userDao = userDao;
        this.mailService = mailService;
        this.jwtProvider = jwtProvider;
    }

    // helper methods

    // generate one time password, 6 digits
    private String getRandomPassword() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    public Optional<User> findUserByUsername(String username) {
        return this.userDao.findUserByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return this.userDao.findUserByEmail(email);
    }

    public User findUserByAuthUserDetail(AuthUserDetail authUserDetail) {
        return this.userDao.getById(authUserDetail.getUserId()).get();
    }

    @Transactional
    public DataResponse createNewUser(UserRequest userRequest, HttpServletRequest request) {

        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Email is required.")
                    .build();
        }

        User u = UserMapper.convertToEntity(userRequest, request);

        if (this.findUserByUsername(u.getUsername()).isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Username is existed.")
                    .build();
        }

        if (this.findUserByEmail(u.getEmail()).isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Email is existed.")
                    .build();
        }

        if (u.getPreferredName() == null || u.getPreferredName().trim().isEmpty()) {
            u.setPreferredName(u.getUsername());
        }

        u.setOneTimePassword(this.getRandomPassword());

        this.userDao.save(u);

        this.mailService.sendWelcomeMail(u);

        return DataResponse.builder()
                .success(true)
                .message("Creation succeeded.")
                .data(UserMapper.convertToDTO(u))
                .token(jwtProvider.createToken(u))
                .build();

    }

    @Transactional
    public DataResponse createNewTempUser(HttpServletRequest request) {

        User u = new User();

        String username = "Player#" + new Date().getTime();

        u.setUsername(username);
        u.setPreferredName(username);
        u.setEmail(username + "@playlobby.club");
        u.setOneTimePassword(this.getRandomPassword());
        u.setLastLoginIp(request.getRemoteAddr());
        u.setLastLoginClient(request.getHeader("User-Agent"));

        this.userDao.save(u);

        return DataResponse.builder()
                .success(true)
                .message("Creation succeeded.")
                .data(UserMapper.convertToResponse(u))
                .token(jwtProvider.createToken(u))
                .build();

    }

    @Transactional
    public DataResponse findUserByLoginRequest(UserRequest userRequest, HttpServletRequest request) {

        Optional<User> userOptional = this.findUserByUsername(userRequest.getFullUsername());

        if (!userOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Cannot find user.")
                    .build();
        }

        User u = userOptional.get();

        if (!(u.getLastLoginIp().equals(request.getRemoteAddr()) && u.getLastLoginClient().equals(request.getHeader("User-Agent")))) {
            if (userRequest.getPassword() == null || userRequest.getPassword().trim().isEmpty()){
                return DataResponse.builder()
                        .success(false)
                        .message("Cannot auto login. Please enter password.")
                        .build();
            }

            if (!u.getOneTimePassword().equals(userRequest.getPassword())) {
                return DataResponse.builder()
                        .success(false)
                        .message("Password is incorrect.")
                        .build();
            }

            u.setLastLoginIp(request.getRemoteAddr());
            u.setLastLoginClient(request.getHeader("User-Agent"));
            this.userDao.update(u);
        }

        return DataResponse.builder()
                .success(true)
                .message("Login succeeded.")
                .data(UserMapper.convertToDTO(userOptional.get()))
                .token(jwtProvider.createToken(userOptional.get()))
                .build();

    }

    public DataResponse getUserMeByAuthUserDetail(AuthUserDetail authUserDetail) {

        User u = this.findUserByAuthUserDetail(authUserDetail);

        return DataResponse.builder()
                .success(true)
                .message("User found.")
                .data(UserMapper.convertToResponse(u))
                .build();

    }

    @Transactional
    public DataResponse updateUserByAuthUserDetail(AuthUserDetail authUserDetail, UserRequest request) {

        User u = this.findUserByAuthUserDetail(authUserDetail);

        String newUsername = request.getUsername() + "#" + request.getUserHash();

        if (!u.getUsername().equals(newUsername)) {
            if (this.findUserByUsername(newUsername).isPresent()) {
                return DataResponse.builder()
                        .success(false)
                        .message("New username is existed.")
                        .build();
            }
            u.setUsername(newUsername);
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty() && !u.getEmail().equals(request.getEmail())){
            if (this.findUserByEmail(request.getEmail()).isPresent()) {
                return DataResponse.builder()
                        .success(false)
                        .message("New email is existed.")
                        .build();
            }
            u.setEmail(request.getEmail());
        }

        if (request.getPreferredName() != null && !request.getPreferredName().trim().isEmpty()) {
            u.setPreferredName(request.getPreferredName());
        }

        this.userDao.save(u);

        return DataResponse.builder()
                .success(true)
                .message("User updated.")
                .data(UserMapper.convertToResponse(u))
                .token(jwtProvider.createToken(u))
                .build();

    }
}
