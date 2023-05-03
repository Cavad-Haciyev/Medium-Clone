package com.example.mediumclone.payload.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {

  private UUID id;
  private String userName;
  private String email;
  private String biography;
  private Boolean activate;

}
