package com.example.mediumclone.payload.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingPostResponse {

  private UUID id;
  private String tittle;
  private String description;
  private Boolean activate;
}
