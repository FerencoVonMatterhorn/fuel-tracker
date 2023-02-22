package de.ferenco.consumptions;

import static org.assertj.core.api.Assertions.assertThat;

import de.ferenco.model.Consumption;
import de.ferenco.repository.ConsumptionRepository;
import io.quarkus.test.junit.QuarkusTest;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ConsumptionRepositoryTest {
  private final Duration TEST_TIMEOUT = Duration.ofSeconds(5);

  @Inject ConsumptionRepository consumptionRepository;

  @AfterEach
  public void cleanUp() {
    consumptionRepository.deleteAll().await().indefinitely();
  }

  @Test
  public void findConsumptionByCarShouldReturnResult() {
    Consumption expected =
        new Consumption(
            new ObjectId(), LocalDateTime.now(), "Honda", 500.0f, 40.0f, "Sommerreifen");

    consumptionRepository.persist(expected).await().atMost(TEST_TIMEOUT);

    Consumption actual =
        consumptionRepository.findConsumptionByCar("Honda").await().atMost(TEST_TIMEOUT);

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFields("id", "createdAt")
        .isEqualTo(expected);
  }
}
