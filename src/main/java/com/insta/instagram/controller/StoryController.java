package com.insta.instagram.controller;

import com.insta.instagram.entity.Story;
import com.insta.instagram.entity.User;
import com.insta.instagram.exceptions.StoryException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.service.StoryService;
import com.insta.instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Story> createStoryHandler(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        Story createdStory = storyService.createStory(story, user.getId());

        return new ResponseEntity<>(createdStory, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findAllStoryByUserIdHandler(@PathVariable("userId") Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);
        List<Story> stories = storyService.findStoryByUserId(user.getId());

        return new ResponseEntity<>(stories, HttpStatus.OK);
    }

}
