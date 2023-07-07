package com.insta.instagram.service;

import com.insta.instagram.entity.Story;
import com.insta.instagram.exceptions.StoryException;
import com.insta.instagram.exceptions.UserException;

import java.util.List;

public interface StoryService {

    public Story createStory(Story story, Integer userId) throws UserException;

    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;



}
