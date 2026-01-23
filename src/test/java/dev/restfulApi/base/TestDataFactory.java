package dev.restfulApi.base;

import dev.restfulApi.client.ApiClient;
import dev.restfulApi.dtos.ObjectRequest;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;

import java.util.Map;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static ObjectResponse createObject(ApiClient api, ObjectRequest req) {
        Response res = api.createObject(req);

        ObjectResponse created = res.as(ObjectResponse.class);
        return created;
    }

    public static ObjectRequest requestWithName(String name) {
        return ObjectRequest.builder()
                .name(name)
                .data(Map.of("k", "v")).build();
    }

    public static String definitelyMissingId() {
        return "999999999999";
    }
}
