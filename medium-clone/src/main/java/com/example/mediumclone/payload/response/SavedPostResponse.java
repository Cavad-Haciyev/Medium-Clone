package com.example.mediumclone.payload.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedPostResponse {

  private UUID id;
  private String imageName;
  private String imageType;
  private byte[] image;
  private String tittle;
  private String description;
  private Boolean activate;
  private UUID userId;

}
