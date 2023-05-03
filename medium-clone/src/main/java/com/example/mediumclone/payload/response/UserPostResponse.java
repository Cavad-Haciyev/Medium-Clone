package com.example.mediumclone.payload.response;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPostResponse {

  private UUID id;
  private String userName;
  private String biography;
  @ToString.Exclude
  private List<PostUserResponse> userPosts;
  @ToString.Exclude
  private List<FollowingResponse> followings;
}
