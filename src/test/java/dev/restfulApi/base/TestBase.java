package dev.restfulApi.base;

import dev.restfulApi.client.ApiClient;
import dev.restfulApi.dtos.ObjectRequest;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import org.testng.annotations.BeforeClass;

import java.util.Map;

public abstract class TestBase {

    protected ApiClient api;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));

        api = new ApiClient();
    }

    public static ObjectRequest sampleRequest() {
        ObjectRequest objReq = new ObjectRequest();
        objReq.setName("Test Device");
        objReq.setData(Map.of(
                        "Price", "123.45",
                        "Capacity", "256 GB",
                        "Generation", "TestGen"
                ));
        return objReq;
    }
}


