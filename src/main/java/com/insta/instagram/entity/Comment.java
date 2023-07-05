package com.insta.instagram.entity;

import com.insta.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name = "user_id")),
            @AttributeOverride(name="email", column = @Column(name = "user_email"))
    })
    private UserDto user;

    private String content;

    @Embedded
    @ElementCollection
    private Set<UserDto> likedByUsers = new HashSet<>();

    private LocalDateTime createdAt;

}
