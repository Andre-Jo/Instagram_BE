package com.insta.instagram.service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.entity.Post;
import com.insta.instagram.entity.User;
import com.insta.instagram.exceptions.PostException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    private UserService userService;

    @Override
    public Post createPost(Post post, Integer userId) throws UserException {

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.setUser(userDto);
        Post createdPost = postRepository.save(post);

        return createdPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        return null;
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        return null;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        return null;
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {
        return null;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {
        return null;
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {
        return null;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {
        return null;
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws PostException, UserException {
        return null;
    }
}
