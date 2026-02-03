package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.base.TestDataFactory;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class UpdateObjectPatchTest extends TestBase {

    private ObjectResponse created;

    @BeforeClass
    public void initialize() {
        created = TestDataFactory.createObject(api, sampleRequest());
    }

    @Test
    public void patch_existing_shouldReturn200_andUpdatedAt_andChangeSubset() {

        Map<String, Object> patchBody = Map.of(
                "name", "Updated Name PATCH",
                "data", Map.of("Price", "111.11")
        );

        Response res = api.updateObjectPatch(created.getId(), patchBody);
        assertEquals(res.statusCode(), 200);

        ObjectResponse updated = res.as(ObjectResponse.class);
        assertEquals(updated.getId(), created.getId());
        assertEquals(updated.getName(), "Updated Name PATCH");
        assertNotNull(updated.getUpdatedAt());
        assertEquals(String.valueOf(updated.getData().get("Price")), "111.11");
    }

    @Test
    public void patch_missing_shouldReturn404() {
        Response res = api.updateObjectPatch(TestDataFactory.definitelyMissingId(), Map.of("name", "x"));
        assertEquals(res.statusCode(), 404);

        String message = res.jsonPath().getString("error");
        assertNotNull(message);
    }

    @Test
    public void patch_invalidBody_shouldReturn400() {
        Response res = api.updateObjectPatch(created.getId(), "{\"data\": ");
        int sc = res.statusCode();
        assertTrue(sc == 400, "Expected client error, got: " + sc);
    }
}

