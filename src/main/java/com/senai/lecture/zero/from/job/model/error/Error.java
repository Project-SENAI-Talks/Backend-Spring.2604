package com.senai.lecture.zero.from.job.model.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    @Schema(name = "status", example = "404", defaultValue = "500" )
    private Integer status;
    @Schema(name = "Message", example = "Not found", defaultValue = "Bad request" )
    private String message;
}
