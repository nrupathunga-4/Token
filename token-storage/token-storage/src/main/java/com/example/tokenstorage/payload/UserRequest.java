package com.example.tokenstorage.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {

    private Long id;
    private String email;
    private String password;
    private String role;

}
