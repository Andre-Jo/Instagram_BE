package com.insta.instagram.service;

import com.insta.instagram.dto.UserDto;
import com.insta.instagram.exceptions.UserException;
import com.insta.instagram.entity.User;
import com.insta.instagram.repository.UserRepository;
import com.insta.instagram.security.JwtTokenClaims;
import com.insta.instagram.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public User registerUser(User user) throws UserException {

        Optional<User> isEmailExits = userRepository.findByEmail(user.getEmail());

        if (isEmailExits.isPresent()) {
            throw new UserException("Email is Already Exist");
        }

        Optional<User> isUsernameExist = userRepository.findByUsername(user.getUsername());

        if (isUsernameExist.isPresent()) {
            throw new UserException("Username is Already Taken...");
        }

        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null || user.getName() == null) {
            throw new UserException("All fields are required");
        }

        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("User not exist with id : " + userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {

        token = token.substring(7);

        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);

        String email = jwtTokenClaims.getUsername();

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("invalid token...");

    }

    @Override
    public User findUserByUsername(String username) throws UserException {

        Optional<User> opt = userRepository.findByUsername(username);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new UserException("User Not exist with userName : " + username);
    }

    @Override
    public String followUser(Integer resUserId, Integer followUserId) throws UserException {

        User reqUser = findUserById(resUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        follower.setUserImage(reqUser.getImage());

        UserDto following = new UserDto();

        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());
        following.setUserImage(followUser.getImage());

        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You Are Following " + followUser.getUsername();
    }

    @Override
    public String unFollowUser(Integer resUserId, Integer followUserId) throws UserException {
        User reqUser = findUserById(resUserId);
        User followUser = findUserById(followUserId);

        UserDto follower = new UserDto();

        follower.setEmail(reqUser.getEmail());
        follower.setId(reqUser.getId());
        follower.setName(reqUser.getName());
        follower.setUsername(reqUser.getUsername());
        follower.setUserImage(reqUser.getImage());

        UserDto following = new UserDto();

        following.setEmail(followUser.getEmail());
        following.setId(followUser.getId());
        following.setName(followUser.getName());
        following.setUsername(followUser.getUsername());
        following.setUserImage(followUser.getImage());

        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);

        userRepository.save(followUser);
        userRepository.save(reqUser);

        return "You have Unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(userIds);

        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);

        if (users.size() == 0) {
            throw new UserException("User Not Found.");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {

        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }

        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }

        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }

        if (updatedUser.getWebsite() != null) {
            existingUser.setWebsite(updatedUser.getWebsite());
        }

        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }

        if (updatedUser.getId().equals(existingUser.getId())) {
            return userRepository.save(existingUser);
        }

        throw new UserException("You Can't Update This User");
    }
}
