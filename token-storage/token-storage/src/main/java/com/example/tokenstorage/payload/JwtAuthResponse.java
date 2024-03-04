package com.example.tokenstorage.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtAuthResponse {
    private String accessToken;
    private String token;
}
