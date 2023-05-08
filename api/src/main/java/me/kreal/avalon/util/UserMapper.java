package me.kreal.avalon.util;

import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.UserDTO;
import me.kreal.avalon.dto.request.UserRequest;
import me.kreal.avalon.dto.response.UserResponse;

import javax.servlet.http.HttpServletRequest;

public class UserMapper {

    public static User convertToEntity(UserRequest userRequest, HttpServletRequest request) {
        return User.builder()
                .username(userRequest.getUsername() + "#" + userRequest.getUserHash())
                .email(userRequest.getEmail())
                .oneTimePassword(userRequest.getPassword())
                .preferredName(userRequest.getPreferredName())
                .lastLoginIp(request.getRemoteAddr())
                .lastLoginClient(request.getHeader("User-Agent"))
                .build();
    }

    public static UserDTO convertToDTO(User user) {
        String[] array = user.getUsername().split("#", 2);

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(array[0])
                .userHash(array[1])
                .email(user.getEmail())
                .oneTimePassword(user.getOneTimePassword())
                .preferredName(user.getPreferredName())
                .build();
    }

    public static UserResponse convertToResponse(User user) {
        String[] array = user.getUsername().split("#", 2);

        return UserResponse.builder()
                .userId(user.getUserId())
                .username(array[0])
                .userHash(array[1])
                .email(user.getEmail())
                .oneTimePassword(user.getOneTimePassword())
                .preferredName(user.getPreferredName())
                .build();
    }

}
