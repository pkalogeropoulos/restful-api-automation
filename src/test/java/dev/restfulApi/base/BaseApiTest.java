package dev.restfulApi.base;

import dev.restfulApi.client.ApiClient;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaseApiTest {

    protected static RequestSpecification spec;

    @BeforeClass
    public void setUp() {

    }

    @Test
    public void test() {
        Response r = new ApiClient().listAllObjects();
        System.out.println();
    }
}
