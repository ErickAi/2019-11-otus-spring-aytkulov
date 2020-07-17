package ru.otus.booklibrary.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Jwt {
    private String token_type = "Bearer";
    @JsonProperty("expires_in")
    private Long expires_in;
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonIgnore
    private String scope;
    @JsonIgnore
    private String jti;
}
