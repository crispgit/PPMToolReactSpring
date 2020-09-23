package com.example.ppmtool.web;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=8080;
    }

    @Test
    void testRegisterUser() {

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", "metest@email.com");
        userDetails.put("fullName", "me me");
        userDetails.put("password", "pass00");
        userDetails.put("confirmPassword", "pass00");

        Response response = given().
                contentType("application/json").
                accept("application/json").
                body(userDetails).
                when().
                post("/api/users/register").
                then().
                statusCode(201).
                contentType("application/json").
                extract().
                response();

        String userId = response.jsonPath().getString("id");
        assertNotNull(userId);

        String bodyString = response.body().asString();
        try {
            JSONObject responseBodyJson = new JSONObject(bodyString);
            String name = responseBodyJson.getString("username");
            System.out.println(name);
            assertEquals("metest@email.com", name);

        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }
}