package com.example.mediumclone.payload.request;

import javax.validation.constraints.NotNull;

public record PostRequest(
    @NotNull
    String tittle,
    @NotNull
    String description,
    byte[] image
) {

}
