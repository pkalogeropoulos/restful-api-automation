package dev.restfulApi.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Objects;

public class ApiClient {

    public static final String BASE_URI = "https://api.restful-api.dev";
    private static final String OBJECTS_PATH = "/objects";

    private final RequestSpecification baseRequest;

    public ApiClient() {
        this(RestAssured.given());
    }

    /**
     * Allows injecting a preconfigured RequestSpecification (e.g., with auth, proxies, logging, timeouts).
     */
    public ApiClient(RequestSpecification requestSpec) {
        Objects.requireNonNull(requestSpec, "requestSpec must not be null");
        this.baseRequest = requestSpec
                .baseUri(BASE_URI)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    /**
     * GET List of all objects
     * GET https://api.restful-api.dev/objects
     */
    public Response listAllObjects() {
        return baseRequest
                .when()
                .get(OBJECTS_PATH)
                .then()
                .extract()
                .response();
    }

    /**
     * GET List of objects by ids
     * GET https://api.restful-api.dev/objects?id=1&id=2
     */
    public Response getObjectsByIds(List<String> ids) {
        Objects.requireNonNull(ids, "ids must not be null");

        RequestSpecification req = baseRequest;
        for (String id : ids) {
            req = req.queryParam("id", id);
        }

        return req
                .when()
                .get(OBJECTS_PATH)
                .then()
                .extract()
                .response();
    }

    /**
     * GET Single object
     * GET https://api.restful-api.dev/objects/{id}
     */
    public Response getObject(String id) {
        Objects.requireNonNull(id, "id must not be null");

        return baseRequest
                .pathParam("id", id)
                .when()
                .get(OBJECTS_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    /**
     * POST Add object
     * POST https://api.restful-api.dev/objects
     *
     * body example (as Map / POJO / JSON string):
     * { "name": "Apple iPad Air", "data": { "Generation": "4th", "Price": "519.99", "Capacity": "256 GB" } }
     */
    public Response createObject(Object body) {
        Objects.requireNonNull(body, "body must not be null");

        return baseRequest
                .body(body)
                .when()
                .post(OBJECTS_PATH)
                .then()
                .extract()
                .response();
    }

    /**
     * PUT Update object (full update)
     * PUT https://api.restful-api.dev/objects/{id}
     */
    public Response updateObjectPut(String id, Object body) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(body, "body must not be null");

        return baseRequest
                .pathParam("id", id)
                .body(body)
                .when()
                .put(OBJECTS_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    /**
     * PATCH Partially update object
     * PATCH https://api.restful-api.dev/objects/{id}
     */
    public Response updateObjectPatch(String id, Object body) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(body, "body must not be null");

        return baseRequest
                .pathParam("id", id)
                .body(body)
                .when()
                .patch(OBJECTS_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    /**
     * DELETE Delete object
     * DELETE https://api.restful-api.dev/objects/{id}
     */
    public Response deleteObject(String id) {
        Objects.requireNonNull(id, "id must not be null");

        return baseRequest
                .pathParam("id", id)
                .when()
                .delete(OBJECTS_PATH + "/{id}")
                .then()
                .extract()
                .response();
    }

    /**
     * Optional: quick helper to enable full request/response logging per call.
     */
    public ApiClient withLogging() {
        RequestSpecification loggingSpec = this.baseRequest
                .log().all();

        return new ApiClient(loggingSpec);
    }
}

