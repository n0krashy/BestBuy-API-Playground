package utils;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class MyUtils {
    public static void assertValidationErrors(Response response, List<String> requiredFields) {
        List<String> validationErrors = response.jsonPath().getList("errors");
        Assert.assertEquals(requiredFields.size(), validationErrors.size());

        for (String requiredField : requiredFields) {
            Assert.assertTrue(validationErrors.contains("should have required property '" + requiredField + "'"));
        }
    }
}
