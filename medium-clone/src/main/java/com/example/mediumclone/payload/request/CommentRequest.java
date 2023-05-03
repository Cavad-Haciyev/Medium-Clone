package com.example.mediumclone.payload.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;

public record CommentRequest(
    UUID postId,
    @NotNull
    String description

) {

}
