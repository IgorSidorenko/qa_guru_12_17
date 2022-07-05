package in.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @Test
    void createUser() {
        String body = "{\"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void listRequest() {
        given()
                .get("https://reqres.in" + "/api/unknown")
                .then()
                .statusCode(200)
                .log().all()
                .body("total", is(Integer.valueOf(12)))
                .body("total_pages", is(Integer.valueOf(2)))
                .body("page", is(Integer.valueOf(1)));

    }

    @Test
    void deleteRequest() {
        given()
                .delete("https://reqres.in" + "/api/users/4")
                .then()
                .statusCode(204);

    }

    @Test
    void updateRequest() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .body(body)
                .contentType(JSON)
                .put("https://reqres.in" + "/api/users/2")
                .then()
                .statusCode(200)
                .log().status()
                .log().body()
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));

    }

    @Test
    void loginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\" }";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
