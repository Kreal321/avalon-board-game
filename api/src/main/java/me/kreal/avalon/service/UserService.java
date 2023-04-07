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



}
