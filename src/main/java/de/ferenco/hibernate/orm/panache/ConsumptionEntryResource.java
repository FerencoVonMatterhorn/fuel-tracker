package de.ferenco.hibernate.orm.panache;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Date;
import java.util.List;

@Path("/consumptions")

@ApplicationScoped
public class ConsumptionEntryResource {

    @GET
    public Uni<List<ConsumptionEntry>> get() {
        return ConsumptionEntry.listAll(Sort.by("date"));
    }

    @GET
    @Path("/{id}")
    public Uni<ConsumptionEntry> getSingle(String id) {
        return ConsumptionEntry.findById(id);
    }


    @POST
    public Uni<Response> create() {
        //TODO: replace this hack x) with real handling of new consumptionEntries
        ConsumptionEntry cEntry = new ConsumptionEntry();
        cEntry.date = new Date();
        cEntry.litersConsumed = 32.7f;
        cEntry.drivenKilometers = 625.0f;
        cEntry.consumptionPer100km = (cEntry.litersConsumed / cEntry.drivenKilometers) * 100;
        cEntry.car = "VW UP";
        cEntry.tireType = "Winterreifen";


        // We want to write to a database, so we need a (async) transaction -> Panache.withTransaction and then call the persist method when we receive the transaction.
        // The transaction method returns a Uni = result of the creation in the database.
        // Once the creation completes we create a 201 CREATED response.
        // RESTEasy automatically reads the request body as JSON and create the consumptionEntry instance.
        return Panache.<ConsumptionEntry>withTransaction(cEntry::persist)
                .onItem().transform(inserted -> Response.created(URI.create("/consumptions" + inserted.id)).build());
    }
}
