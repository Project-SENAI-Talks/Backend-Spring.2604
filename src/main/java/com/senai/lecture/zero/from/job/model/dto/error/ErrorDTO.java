package com.senai.lecture.zero.from.job.model.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Error Response")
public class ErrorDTO {

    @Schema(name = "result", example = "ERROR")
    private String result;
    @Schema(name = "details", implementation = ErrorProperties.class)
    private ErrorProperties details;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorProperties {
        @Schema(name = "status", example = "status error code", defaultValue = "500")
        private HttpStatus status;
        @Schema(name = "Message", example = "Not found", defaultValue = "Bad request")
        private String message;
    }
}
