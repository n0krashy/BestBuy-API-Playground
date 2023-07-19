package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import models.Service;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetServiceTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030").build();
    }

    @Test(dataProvider = "dataProvFunc")
    public void getServiceById(Service service) {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/testdata/get.service.json"));
        given()
                .spec(requestSpec)
                .pathParam("serviceId", service.getId())
                .when()
                .get("/services/{serviceId}")
                .then()
                .statusCode(200)
                // asserts that bodies are identical
                .body("", equalTo(expectedJson.getMap("")));
    }

    @Test(dataProvider = "dataProvFunc")
    public void canGetServiceByIdAndSelectOnlyNameAndId(Service service) {
        given()
                .spec(requestSpec)
                .pathParam("serviceId", service.getId())
                .when()
                .get("/services/{serviceId}?$select[]=name")
                .then()
                .statusCode(200)
                .and().assertThat().body("id", equalTo(service.getId()))
                .and().assertThat().body("name", equalTo(service.getName()));
        // Assert that the response body only contains the service ID and  name
        expect().body("", hasSize(2));
    }

    @Test
    public void returns404ForBadServiceId() {
        given().spec(requestSpec).get("/services/123").then().statusCode(404);
    }

    @DataProvider
    public Object[][] dataProvFunc() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/testdata/get.service.json");
        ObjectMapper mapper = new ObjectMapper();
        Service myService = mapper.readValue(fileReader, Service.class);
        return new Object[][]{{myService}};
    }
}
