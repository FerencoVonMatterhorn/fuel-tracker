package de.ferenco.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

@MongoEntity(database = "consumptionDB", collection = "consumptions")
public class Consumption {
  public ObjectId id;
  public LocalDateTime createdAt;
  public String car;
  public float drivenKm;
  public float consumedLiters;
  public String tires;

  public Consumption() {}

  public Consumption(
      ObjectId id,
      LocalDateTime createdAt,
      String car,
      float drivenKm,
      float consumedLiters,
      String tires) {
    this.id = id;
    this.createdAt = createdAt;
    this.car = car;
    this.drivenKm = drivenKm;
    this.consumedLiters = consumedLiters;
    this.tires = tires;
  }
}
