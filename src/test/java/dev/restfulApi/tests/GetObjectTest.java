package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.base.TestDataFactory;
import dev.restfulApi.dtos.ObjectRequest;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class GetObjectTest extends TestBase {

    @Test
    public void getSingle_existing_shouldReturn200_andMatchFields() {
        ObjectRequest req = sampleRequest();
        ObjectResponse created = TestDataFactory.createObject(api, req);

        Response res = api.getObject(created.getId());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.statusCode(), 200);

        ObjectResponse fetched = res.as(ObjectResponse.class);
        softAssert.assertEquals(fetched.getId(), created.getId());
        softAssert.assertEquals(fetched.getName(), req.getName());
        softAssert.assertAll();
    }

    @Test
    public void getSingle_missing_shouldReturn404_withErrorMessage() {
        String wrongId = TestDataFactory.definitelyMissingId();
        Response res = api.getObject(wrongId);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.statusCode(), 404);

        String errorMessage = res.jsonPath().getString("error");
        //cool error from backend here: Oject
        softAssert.assertEquals(errorMessage, "Oject with id=" + wrongId + " was not found.");
        softAssert.assertAll();
    }
}
