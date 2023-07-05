package com.insta.instagram.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String name;
    private String userImage;
}
