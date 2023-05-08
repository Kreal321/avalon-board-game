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

    @Pattern(regexp = "[0-9]{4}", message = "The user number must consist of four digits. ")
    private String userHash;

    private String preferredName;

    private String email;

    private String password;

    public String getFullUsername() {
        return this.username + "#" + this.userHash;
    }

}
