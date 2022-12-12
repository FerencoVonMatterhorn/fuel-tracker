package de.ferenco;

import de.ferenco.model.Consumption;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsumptionResourceTest {

    public void initialiseConsumptionEndpoint() {
        Consumption c = new Consumption();
        c.car = "VW UP";
        c.consumptionPer100km = 5.2f;
        c.date = new Date();
        c.drivenKilometers = 625.0f;
        c.litersConsumed = 32.7f;
        c.tireType = "Winterreifen";

        given()
                .body(c)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("consumptions")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(1)
    public void testNonInitialisedConsumptionEndpoint() {
        given()
                .when().get("/consumptions")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void testInitialisedConsumptionEndpoint() {
        Consumption c = new Consumption();
        c.car = "VW UP";
        c.consumptionPer100km = 5.2f;
        c.date = new Date();
        c.drivenKilometers = 625.0f;
        c.litersConsumed = 32.7f;
        c.tireType = "Winterreifen";

        given()
                .body(c)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when().post("consumptions")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(3)
    public void testGetConsumptions() {
        given()
                .when().get("/consumptions")
                .then()
                .statusCode(200)
                .body("car[0]", is("VW UP"));
    }
}