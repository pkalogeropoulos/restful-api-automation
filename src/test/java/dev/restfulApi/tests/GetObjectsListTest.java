package dev.restfulApi.tests;

import dev.restfulApi.base.TestBase;
import dev.restfulApi.dtos.ObjectResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

public class GetObjectsListTest extends TestBase {

    @Test
    public void listAll_shouldReturn200_andJsonArray() {
        Response res = api.listAllObjects();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.statusCode(), 200);
        softAssert.assertEquals(res.contentType(), "application/json");

        List<ObjectResponse> list = Arrays.asList(res.as(ObjectResponse[].class));
        softAssert.assertFalse(list.isEmpty(), "Array size should be >= 0");
        softAssert.assertAll();
    }

    @Test
    public void listAll_itemsShouldHaveBasicFields_whenPresent() {
        Response res = api.listAllObjects();
        assertEquals(res.statusCode(), 200);

        List<?> list = res.jsonPath().getList("$");
        assertNotNull(list);

        if (!list.isEmpty()) {
            Object id = res.jsonPath().get("[0].id");
            assertNotNull(id, "First item id should not be null");
            // name/data can be null depending on data in system; just sanity check type-ish
            Object name = res.jsonPath().get("[0].name");
            Object data = res.jsonPath().get("[0].data");
            // no strict assertion on name/data existence; API data varies
        }
    }
}

