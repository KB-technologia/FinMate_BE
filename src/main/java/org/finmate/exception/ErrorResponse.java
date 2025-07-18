package org.finmate.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@ApiModel(description = "에러 응답 DTO")
public class ErrorResponse {
    @ApiModelProperty(value = "에러 유형", example = "Bad Request")
    String error;

    @ApiModelProperty(value = "에러 메시지", example = "Missing required parameter: id1")
    String message;

    @ApiModelProperty(value = "요청 경로", example = "/api/product/404")
    String path;

    @ApiModelProperty(value = "에러 발생 시각", example = "2025-07-18T12:34:56")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime timestamp;
}
