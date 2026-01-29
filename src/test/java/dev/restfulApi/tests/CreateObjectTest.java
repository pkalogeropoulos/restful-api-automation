package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.dtos.ObjectRequest;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static org.testng.Assert.*;

public class CreateObjectTest extends TestBase {

    @Test
    public void create_valid_shouldReturn200_andEchoFields_andCreatedAt() {
        ObjectRequest req = ObjectRequest.builder()
                .name("Apple iPad Air")
                .data(Map.of("Generation", "4th", "Price", "519.99", "Capacity", "256 GB")).build();

        Response res = api.createObject(req);
        assertEquals(res.statusCode(), 200);

        ObjectResponse created = res.as(ObjectResponse.class);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(created.getId());
        softAssert.assertEquals(created.getName(), req.getName());
        softAssert.assertNotNull(created.getCreatedAt());
        softAssert.assertNotNull(created.getData());
        softAssert.assertEquals(String.valueOf(created.getData().get("Generation")), "4th");
        softAssert.assertAll();
    }

    @Test
    public void create_invalidJson_shouldReturn400_or415() {
        Response res = api.createObject("{\"name\": \"Bad\", \"data\": ");
        int sc = res.statusCode();
        assertTrue(sc == 400, "Expected client error, got: " + sc);
    }
}
