package de.ferenco.hibernate.orm.panache;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Cacheable
public class ConsumptionEntry extends PanacheEntity {
    @Column()
    public Date date;

    @Column()
    public float litersConsumed;

    @Column
    public float drivenKilometers;

    @Column
    public float consumptionPer100km;

    @Column
    public String car;

    @Column
    public String tireType;
}
