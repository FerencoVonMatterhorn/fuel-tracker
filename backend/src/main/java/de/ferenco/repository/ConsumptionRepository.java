package de.ferenco.repository;

import de.ferenco.model.Consumption;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsumptionRepository implements ReactivePanacheMongoRepository<Consumption> {

  public Uni<Consumption> findConsumptionByCar(String car) {
    return find("car", car).firstResult();
  }
}
