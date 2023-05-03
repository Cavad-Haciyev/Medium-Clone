package com.example.mediumclone.payload.request;

public record ChangePassword(
    String password,
    String confirmPassword
) {

}
