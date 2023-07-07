package com.insta.instagram.service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.entity.Post;
import com.insta.instagram.entity.User;
import com.insta.instagram.exceptions.PostException;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.repository.PostRepository;
import com.insta.instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
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

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (post.getUser().getId().equals(user.getId())) {
            postRepository.deleteById(post.getId());
            return "Post Deleted Successfully";
        }

        throw new PostException("You can't delete other user's post");
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {

        List<Post> posts = postRepository.findByUserId(userId);

        if (posts.size() == 0) {
            throw new UserException("This user Dose not have any Post");
        }

        return posts;
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {

        Optional<Post> opt = postRepository.findById(postId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new PostException("Post not found with id " + postId);

    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostException, UserException {

        List<Post> posts = postRepository.findAllPostByUserIds(userIds);

        if (posts.size() == 0) {
            throw new PostException("No Post available");
        }

        return posts;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
        }

        return "Post Saved Successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }

        return "Post Remove Successfully";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().add(userDto);

        return postRepository.save(post);
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws PostException, UserException {

        Post post = findPostById(postId);

        User user = userService.findUserById(userId);

        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserImage(user.getImage());
        userDto.setUsername(user.getUsername());

        post.getLikedByUsers().remove(userDto);

        return postRepository.save(post);
    }
}
