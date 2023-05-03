package com.example.mediumclone.service;

import com.example.mediumclone.core.EmailService;
import com.example.mediumclone.exception.PostNotFoundException;
import com.example.mediumclone.exception.UserNotFoundException;
import com.example.mediumclone.model.Post;
import com.example.mediumclone.model.User;
import com.example.mediumclone.payload.mapper.PostMapper;
import com.example.mediumclone.payload.mapper.UserMapper;
import com.example.mediumclone.payload.request.EditProfileRequest;
import com.example.mediumclone.payload.request.FollowingRequest;
import com.example.mediumclone.payload.request.SavedPostRequest;
import com.example.mediumclone.payload.request.UserRequest;
import com.example.mediumclone.payload.response.FollowingResponse;
import com.example.mediumclone.payload.response.SavedPostResponse;
import com.example.mediumclone.payload.response.UserDetailsResponse;
import com.example.mediumclone.payload.response.UserPostResponse;
import com.example.mediumclone.payload.response.UserRegistrationResponse;
import com.example.mediumclone.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final PostService postService;
  private final UserRepository userRepository;
  private final EmailService service;

  public UserService(@Lazy PostService postService, UserRepository userRepository,
      EmailService service) {
    this.postService = postService;
    this.userRepository = userRepository;
    this.service = service;
  }


  public Page<UserPostResponse> getAllUsers(int pageSize, int pageNo) {
    Pageable pageable = PageRequest.of(pageSize, pageNo);
    Page<User> allPost = userRepository.getAllUser(pageable);
    return allPost.map(UserMapper.MAPPER::mapToUserPostResponse);

  }


  public UserPostResponse getUserById(UUID id) {
    User user = userRepository
        .getUserById(id)
        .orElseThrow(UserNotFoundException::new);
    return UserMapper.MAPPER.mapToUserPostResponse(user);

  }

  public UserPostResponse getUserByName(String userName) {
    User user = userRepository
        .getUserByUserName(userName)
        .orElseThrow(UserNotFoundException::new);
    return UserMapper.MAPPER.mapToUserPostResponse(user);
  }


  public SavedPostResponse savePost(SavedPostRequest request) {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository
        .getUserByUserName(name)
        .orElseThrow(UserNotFoundException::new);
    Post post = postService.getPostByIdForComment(request.postId());
    user.getSavedPost().add(post);
    userRepository.save(user);
    return PostMapper.MAPPER.mapToSavedPostResponse(post);
  }

  public List<SavedPostResponse> getSavedPost() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository
        .getUserByUserName(name)
        .orElseThrow(UserNotFoundException::new);

    return user.getSavedPost()
        .stream()
        .map(PostMapper.MAPPER::mapToSavedPostResponse)
        .collect(Collectors.toList());
  }

  public FollowingResponse toFollow(FollowingRequest request) {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository
        .getUserByUserName(name)
        .orElseThrow(UserNotFoundException::new);
    User user1 = userRepository
        .getUserById(request.userId())
        .orElseThrow(UserNotFoundException::new);
    user.getFollowings().add(user1);
    userRepository.save(user);

    return UserMapper.MAPPER.mapToFollowing(user1);
  }

  public List<FollowingResponse> getMyFollowers() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository
        .getUserByUserName(name)
        .orElseThrow(UserNotFoundException::new);

    return user.getFollowings()
        .stream()
        .map(UserMapper.MAPPER::mapToFollowing)
        .toList();


  }


  public UserDetailsResponse getMyProfileDetails() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository
        .getUserByUserName(name)
        .orElseThrow(UserNotFoundException::new);
    return UserMapper.MAPPER.mapToUserDetails(user);
  }


  public String deleteUser(UUID id) {
    User user = userRepository.getUserById(id)
        .orElseThrow(() -> new PostNotFoundException());

    userRepository.softDelete(user.getId());
    return "Successfully Deleted";

  }
  public String updateProfile(EditProfileRequest request){
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository
        .getUserByUserName(name)
        .orElseThrow(UserNotFoundException::new);
    user.setUserName(request.userName());
    user.setEmail(request.email());
    user.setBiography(request.biography());
    userRepository.save(user);
    return "Successfully Update Profile";
  }


  //USER REGISTRATION
  public UserRegistrationResponse registration(UserRequest request) {

    User user = UserMapper.MAPPER.mapToEntity(request);
    user.setActivate(false);
    User save = userRepository.save(user);
    String link = "http://localhost:8000/v1/users/activate/" + user.getEmail();
    service.sendSimpleEmail(user.getEmail(),
        link,
        "Activate Link");
    return UserMapper.MAPPER.mapToRegistrationResponse(save);
  }

  public String activateProfile(String email) {
    User user = userRepository
        .findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
    user.setActivate(true);
    userRepository.save(user);
    return "Successfully Activate Profile";
  }


  public User forgotPassword(String email) {
    User user = userRepository
        .findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
    return user;
  }


  //USER LOGIN
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username);
    if (user == null) {
      throw new UserNotFoundException();
    } else {
    }
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles()
        .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
    return new org.springframework.security.core.userdetails.User(user.getUserName(),
        user.getPassword(), getAuthority(user));
  }

  private Set<SimpleGrantedAuthority> getAuthority(User user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    });
    return authorities;
  }

}
