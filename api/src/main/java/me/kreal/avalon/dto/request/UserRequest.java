package me.kreal.avalon.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Username can not be empty. ")
    private String username;

    private String preferredName;

    private String email;

    private String password;

}
