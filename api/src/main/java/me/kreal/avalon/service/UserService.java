package me.kreal.avalon.service;

import me.kreal.avalon.dao.UserDao;
import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.request.UserRequest;
import me.kreal.avalon.dto.response.DataResponse;
import me.kreal.avalon.security.AuthUserDetail;
import me.kreal.avalon.security.JwtProvider;
import me.kreal.avalon.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        return this.userDao.findUserByUsername(authUserDetail.getUsername()).get();
    }

    @Transactional
    public DataResponse createNewPlayer(UserRequest request) {

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Email is required.")
                    .build();
        }

        User u = UserMapper.convertToEntity(request);

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

        return DataResponse.builder()
                .success(true)
                .message("Creation succeeded.")
                .data(UserMapper.convertToDTO(u))
                .token(jwtProvider.createToken(u))
                .build();

    }

    @Transactional
    public DataResponse findUserByLoginRequest(UserRequest request) {

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()){
            return DataResponse.builder()
                    .success(false)
                    .message("Password is empty.")
                    .build();
        }

        User u = UserMapper.convertToEntity(request);

        Optional<User> userOptional = this.findUserByUsername(u.getUsername());

        if (!userOptional.isPresent()) {
            return DataResponse.builder()
                    .success(false)
                    .message("Cannot find user.")
                    .build();
        }

        if (!userOptional.get().getOneTimePassword().equals(u.getOneTimePassword())) {
            return DataResponse.builder()
                    .success(false)
                    .message("Password is incorrect.")
                    .build();
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
