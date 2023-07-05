package com.insta.instagram.entity;

import com.insta.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String name;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String image;

    private String password;

    @Embedded
    @ElementCollection
    private Set<UserDto> follower = new HashSet<>();

    @Embedded
    @ElementCollection
    private Set<UserDto> following = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Story> stories = new ArrayList<>();

//    북마크
    @ManyToMany
    private List<Post> savedPost = new ArrayList<>();

}
