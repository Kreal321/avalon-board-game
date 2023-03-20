package me.kreal.avalon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse {
    private Boolean success;
    private String message;
    private Object data;
    private String token;
}
