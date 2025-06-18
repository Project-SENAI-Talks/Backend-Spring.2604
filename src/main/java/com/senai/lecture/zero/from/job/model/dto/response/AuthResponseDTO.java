package com.senai.lecture.zero.from.job.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Authentication Response", description = "Authentication response")
public class AuthResponseDTO {

    @Schema(name = "accessToken", example = " eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    private String accessToken;
    @Schema(name = "Bearer", example = "Bearer")
    private String tokenType = "Bearer";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
