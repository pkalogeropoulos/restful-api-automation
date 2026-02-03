package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.base.TestDataFactory;
import dev.restfulApi.dtos.ObjectRequest;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;

public class UpdateObjectPutTest extends TestBase {

    @Test
    public void put_existing_shouldReturn200_andUpdatedAt_andReplaceFields() {
        ObjectResponse created = TestDataFactory.createObject(api, sampleRequest());

        ObjectRequest update = ObjectRequest.builder()
                .name("Updated Name PUT")
                .data(Map.of("Price", "999.99", "Color", "Black")).build();

        Response res = api.updateObjectPut(created.getId(), update);
        assertEquals(res.statusCode(), 200);

        ObjectResponse updated = res.as(ObjectResponse.class);
        assertEquals(updated.getId(), created.getId());
        assertEquals(updated.getName(), update.getName());
        assertNotNull(updated.getUpdatedAt(), "updatedAt should be present after PUT");
        assertEquals(String.valueOf(updated.getData().get("Color")), "Black");
    }

    @Test
    public void put_missing_shouldReturn404() {
        ObjectRequest update = sampleRequest();
        update.setName("Does not matter");

        Response res = api.updateObjectPut(TestDataFactory.definitelyMissingId(), update);
        assertEquals(res.statusCode(), 404);
    }

    @Test
    public void put_invalidBody_shouldReturn400() {
        ObjectResponse created = api.getObject("7").as(ObjectResponse.class);//TestDataFactory.createObject(api, sampleRequest());

        Response res = api.updateObjectPut(created.getId(), "{\"name\": ");
        int sc = res.statusCode();
        assertTrue(sc == 400, "Expected client error, got: " + sc);
    }
}

