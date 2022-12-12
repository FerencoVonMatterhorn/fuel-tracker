package de.ferenco.resource;

import de.ferenco.model.Consumption;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.*;

@Path("/consumptions")
@ApplicationScoped
public class ConsumptionResource {

    @GET
    public Uni<List<Consumption>> get() {
        return Consumption.listAll(Sort.by("date"));
    }

    @GET
    @Path("/{id}")
    public Uni<Consumption> getSingle(String id) {
        return Consumption.findById(id);
    }

    @POST
    public Uni<Response> create(Consumption consumptionEntry) {
        if (consumptionEntry == null || consumptionEntry.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        // We want to write to a database, so we need a (async) transaction -> Panache.withTransaction and then call the persist method when we receive the transaction.
        // Once the creation completes we create a 201 CREATED response.
        // RESTEasy automatically reads the request body as JSON and create the consumptionEntry instance.
        return Panache.<Consumption>withTransaction(consumptionEntry::persist).replaceWith(Response.ok(consumptionEntry).status(CREATED)::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id) {
        return Panache.withTransaction(() -> Consumption.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build());
    }
}
