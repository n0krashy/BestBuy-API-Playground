package stores;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import models.Store;
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

public class GetStoreTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void createRequestSpec() {
        requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3030").build();
    }

    @Test(dataProvider = "dataProvFunc")
    public void getStoreById(Store store) {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/testdata/get.store.json"));
        given()
                .spec(requestSpec)
                .pathParam("storeId", store.getId())
                .when()
                .get("/stores/{storeId}")
                .then()
                .statusCode(200)
                // asserts that bodies are identical
                .body("", equalTo(expectedJson.getMap("")));
    }

    @Test(dataProvider = "dataProvFunc")
    public void canGetStoreByIdAndSelectOnlyNameAndId(Store store) {
        given()
                .spec(requestSpec)
                .pathParam("storeId", store.getId())
                .when()
                .get("/stores/{storeId}?$select[]=name&$select[]=hours")
                .then()
                .statusCode(200)
                .and().assertThat().body("id", equalTo(store.getId()))
                .and().assertThat().body("name", equalTo(store.getName()))
                .and().assertThat().body("hours", equalTo(store.getHours()));
        // Assert that the response body only contains the store ID, name, and hours.
        expect().body("", hasSize(3));
    }

    @Test
    public void returns404ForBadStoreId() {
        given().spec(requestSpec).get("/stores/1").then().statusCode(404);
    }

    @DataProvider
    public Object[][] dataProvFunc() throws IOException {
        FileReader fileReader = new FileReader("src/test/resources/testdata/get.store.json");
        ObjectMapper mapper = new ObjectMapper();
        Store myStore = mapper.readValue(fileReader, Store.class);
        return new Object[][]{{myStore}};
    }
}
