package dev.restfulApi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteResponse {

    @JsonProperty("message")
    private String message;
}

