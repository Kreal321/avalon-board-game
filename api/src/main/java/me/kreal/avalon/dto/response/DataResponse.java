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

    public static DataResponse error(String message) {
        return DataResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static DataResponse success(String message) {
        return DataResponse.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static DataResponse success(Object data) {
        return DataResponse.builder()
                .success(false)
                .data(data)
                .build();
    }

    public static DataResponse success(String message, Object data) {
        return DataResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public DataResponse token(String token) {

        this.token = token;
        return this;

    }

    public DataResponse data(Object data) {

        this.data = data;
        return this;

    }

}
