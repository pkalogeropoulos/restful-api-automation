package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.base.TestDataFactory;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.testng.Assert.*;

public class GetObjectsByIdsTest extends TestBase {

    @Test
    public void getByIds_shouldReturn200_andContainRequestedIds() {
        ObjectResponse a = TestDataFactory.createObject(api, TestDataFactory.requestWithName("Obj A"));
        ObjectResponse b = TestDataFactory.createObject(api, TestDataFactory.requestWithName("Obj B"));

        Response res = api.getObjectsByIds(List.of(a.getId(), b.getId()));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.statusCode(), 200);

        List<String> ids = res.jsonPath().getList("id");
        softAssert.assertNotNull(ids);
        softAssert.assertTrue(ids.contains(a.getId()), "Response should contain id A");
        softAssert.assertTrue(ids.contains(b.getId()), "Response should contain id B");
        softAssert.assertAll();
    }

    @Test
    public void getByIds_withOneMissing_shouldReturn200_andReturnExistingOnlyOrErrorEntry() {
        ObjectResponse created = TestDataFactory.createObject(api, TestDataFactory.requestWithName("Obj Exists"));

        Response res = api.getObjectsByIds(List.of(created.getId(), TestDataFactory.definitelyMissingId()));
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.statusCode(), 200);

        // Most commonly returns only existing objects; keep this tolerant
        List<String> ids = res.jsonPath().getList("id");
        softAssert.assertNotNull(ids);
        softAssert.assertTrue(ids.contains(created.getId()), "Should include existing id");
        softAssert.assertAll();
    }

    @Test
    public void getByIds_withEmptyList_shouldReturn200() {
        Response res = api.getObjectsByIds(List.of());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.statusCode(), 200);
        ObjectResponse[] objects = res.as(ObjectResponse[].class);
        softAssert.assertTrue(objects.length > 0);
        softAssert.assertAll();
    }
}