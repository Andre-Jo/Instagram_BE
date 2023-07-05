package com.insta.instagram.entity;


import com.insta.instagram.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name="stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name = "user_id")),
            @AttributeOverride(name="email", column = @Column(name = "user_email"))
    })
    private UserDto user;

    @NotNull
    private String image;
    private String caption;
    private LocalDateTime timetamp;

}
