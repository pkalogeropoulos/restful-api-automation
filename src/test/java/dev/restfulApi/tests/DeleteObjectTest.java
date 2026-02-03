package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.base.TestDataFactory;
import dev.restfulApi.dtos.DeleteResponse;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DeleteObjectTest extends TestBase {

    @Test
    public void delete_existing_shouldReturn200_andMessage_andThenGetIs404() {
        ObjectResponse created = TestDataFactory.createObject(api, sampleRequest());

        Response del = api.deleteObject(created.getId());
        assertEquals(del.statusCode(), 200);

        DeleteResponse deleteResponse = del.as(DeleteResponse.class);
        assertNotNull(deleteResponse.getMessage());

        String msgLower = deleteResponse.getMessage().toLowerCase();
        assertTrue(msgLower.contains("deleted") || msgLower.contains("success"),
                "Message should indicate deletion/success");

        Response getAfter = api.getObject(created.getId());
        assertEquals(getAfter.statusCode(), 404);
    }

    @Test
    public void delete_missing_shouldReturn404() {
        Response res = api.deleteObject(TestDataFactory.definitelyMissingId());
        assertEquals(res.statusCode(), 404);

        String message = res.jsonPath().getString("error");
        assertNotNull(message);
    }
}
