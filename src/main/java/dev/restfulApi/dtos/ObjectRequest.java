package dev.restfulApi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ObjectRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private Map<String, Object> data;
}
