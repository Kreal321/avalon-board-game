package me.kreal.avalon.util;

import me.kreal.avalon.domain.User;
import me.kreal.avalon.dto.UserDTO;
import me.kreal.avalon.dto.request.UserRequest;

public class UserMapper {

    public static User convertToEntity(UserRequest request) {
        return User.builder()
                .username(request.getUsername() + "#" + request.getUserHash())
                .realName(request.getRealName())
                .preferredName(request.getPreferredName())
                .build();
    }

    public static UserDTO convertToDTO(User user) {
        String[] array = user.getUsername().split("#", 2);

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(array[0])
                .userHash(array[1])
                .realName(user.getRealName())
                .preferredName(user.getPreferredName())
                .build();
    }

}
