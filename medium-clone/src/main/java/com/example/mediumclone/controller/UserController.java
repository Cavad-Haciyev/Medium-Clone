package com.example.mediumclone.controller;

import com.example.mediumclone.core.configs.JwtResponse;
import com.example.mediumclone.core.configs.JwtUtil;
import com.example.mediumclone.payload.request.EditProfileRequest;
import com.example.mediumclone.payload.request.FollowingRequest;
import com.example.mediumclone.payload.request.LoginRequest;
import com.example.mediumclone.payload.request.SavedPostRequest;
import com.example.mediumclone.payload.request.UserRequest;
import com.example.mediumclone.core.responsemodel.ResponseModel;
import com.example.mediumclone.payload.response.UserRegistrationResponse;
import com.example.mediumclone.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;


  @GetMapping("/user")  //Get All Users
  public ResponseEntity<Page<Object>> getAllPost(@RequestParam Integer pageSize,
      @RequestParam Integer pageNo) {
    return ResponseModel
        .responseBuilderPage(LocalDateTime.now(),
            userService.getAllUsers(pageSize, pageNo),
            "Successfully",
            HttpStatus.OK);
  }

  @GetMapping("/{id}") //Get User With Id
  public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            userService.getUserById(id),
            "Successfully",
            HttpStatus.OK);

  }

  @GetMapping("/myProfileDetails") // Get My Profile Details
  public ResponseEntity<Object> getMyProfileDetails() {
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            userService.getMyProfileDetails(),
            "Successfully",
            HttpStatus.OK);

  }

  @PutMapping("/savePost") //Set Saved Post
  public ResponseEntity<Object> savedPost(@RequestBody SavedPostRequest request) {
    userService.savePost(request);
    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            userService.savePost(request),
            "Successfully Saved",
            HttpStatus.OK);

  }

  @GetMapping("/savedPosts") //Get Saved Posts
  public ResponseEntity<List<Object>> getSavedPost() {
    return ResponseModel
        .responseBuilderaList(LocalDateTime.now(),
            userService.getSavedPost(),
            "Successfully",
            HttpStatus.OK);
  }

  @PutMapping("/followUp") //Follow up
  public Object following(@RequestBody FollowingRequest request) {
    return ResponseModel
        .responseBuilderaList(LocalDateTime.now(),
            userService.toFollow(request),
            "Successfully Follow",
            HttpStatus.OK);
  }

  @GetMapping("/follow") //I Follow
  public ResponseEntity<List<Object>> following() {
    return ResponseModel
        .responseBuilderaList(LocalDateTime.now(),
            userService.getMyFollowers(),
            "Successfully",
            HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable UUID id) {

    return ResponseModel
        .responseBuilderaList(LocalDateTime.now(),
            userService.deleteUser(id),
            "Successfully",
            HttpStatus.OK);
  }
  @PutMapping("/updateProfile")
  public ResponseEntity<Object> updateProfile(@RequestBody EditProfileRequest request){

    return ResponseModel
        .responseBuilder(LocalDateTime.now(),
            userService.updateProfile(request),
            "Successfully",
            HttpStatus.OK);
  }

  //USER REGISTRATION
  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> register(
      @RequestBody @Valid UserRequest userRequest) {
    return ResponseEntity.ok(userService.registration(userRequest));
  }

  @GetMapping("/activate/{email}")
  public ResponseEntity<?> activateProfile(@PathVariable String email) {

    return ResponseModel
        .responseBuilderaList(LocalDateTime.now(),
            userService.activateProfile(email),
            "Successfully",
            HttpStatus.OK);
  }

  //USER LOGIN
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
    try {
      this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.username(),
              loginRequest.password()));

    } catch (UsernameNotFoundException usernameNotFoundException) {
      usernameNotFoundException.printStackTrace();
      throw new Exception("Bad Credential");
    }

    UserDetails userDetails = this.userService.loadUserByUsername(loginRequest.username());

    String token = this.jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

}
