package cn.edu.jnu.baiscms.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;

    private String token;

}
