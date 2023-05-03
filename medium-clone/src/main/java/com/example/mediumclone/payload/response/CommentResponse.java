package com.example.mediumclone.payload.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

  private UUID id;
  private String description;
  private UUID userId;
  private PostCommentResponse post;
}
