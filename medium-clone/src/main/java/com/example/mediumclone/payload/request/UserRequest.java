package com.example.mediumclone.payload.request;

import com.example.mediumclone.model.Role;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UserRequest(
    @NotNull
    @Length(min = 5, max = 512, message = "UserName must be between 3-20 characters")
    String userName,
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "The Email format is not correct")
    String email,
    @NotNull
    @Length(min = 8, max = 16, message = "Password must be between 3-20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}", message = "The password format is not correct")
    String password,
    String biography,
    List<Role> roles
) {

}
