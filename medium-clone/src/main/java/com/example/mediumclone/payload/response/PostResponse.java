package com.example.mediumclone.payload.response;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

  private UUID id;
  private String tittle;
  private String description;
  private Boolean activate;
  private UserPostDto user;
  private List<CommentPostResponse> comments;
  private String imageName;
  private List<UUID> postLikes;

}
