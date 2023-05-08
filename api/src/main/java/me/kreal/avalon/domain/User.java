package me.kreal.avalon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "one_time_password", nullable = false)
    private String oneTimePassword;

    @Column(name = "preferred_name", nullable = false)
    private String preferredName;

    @Column(name = "last_login_ip", nullable = false)
    private String lastLoginIp;

    @Column(name = "last_login_client", nullable = false)
    private String lastLoginClient;

}
