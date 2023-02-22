package de.ferenco.consumptions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import de.ferenco.model.Consumption;
import de.ferenco.repository.ConsumptionRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ConsumptionControllerTest {

  @Inject ConsumptionRepository consumptionRepository;

  @AfterEach
  public void cleanUp() {
    this.consumptionRepository.deleteAll().await().indefinitely();
  }

  @Test
  @DisplayName("Test - When Calling POST - /api/consumptions user should create resource - 201")
  public void consumptionCanBeCreated() {
    Consumption testConsumption =
        new Consumption(
            new ObjectId(), LocalDateTime.now(), "Honda", 500.0f, 40.0f, "Sommerreifen");

    given()
        .when()
        .body(testConsumption)
        .contentType(ContentType.JSON)
        .post("/api/consumptions")
        .then()
        .statusCode(200);
  }
}
