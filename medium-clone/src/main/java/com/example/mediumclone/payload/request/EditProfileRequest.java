package com.example.mediumclone.payload.request;

public record EditProfileRequest(
    String userName,
    String email,
    String biography
) {

}
