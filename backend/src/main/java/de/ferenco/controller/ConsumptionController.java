package de.ferenco.controller;

import de.ferenco.model.Consumption;
import de.ferenco.repository.ConsumptionRepository;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestResponse.StatusCode;

@Path("/api/consumptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsumptionController {

  @Inject ConsumptionRepository consumptionRepository;

  @Inject Logger logger;

  @GET
  @ResponseStatus(StatusCode.OK)
  @APIResponse(
      responseCode = "200",
      description = "the consumption has been found",
      content = @Content(schema = @Schema(implementation = Consumption.class)))
  public Uni<List<Consumption>> getAllConsumptions() {
    logger.info("Getting all Consumptions from ConsumptionRepository");
    return consumptionRepository.listAll();
  }

  @GET
  @ResponseStatus(StatusCode.OK)
  @APIResponse(
      responseCode = "200",
      description = "the consumption has been found",
      content = @Content(schema = @Schema(implementation = Consumption.class)))
  @Path("{id}")
  public Uni<Consumption> getConsumptionById(@PathParam("id") ObjectId id) {
    logger.info("Getting Consumption from ConsumptionRepository with ID: " + id);
    return consumptionRepository.findById(id);
  }

  @POST
  @ResponseStatus(StatusCode.CREATED)
  @APIResponse(
      responseCode = "201",
      description = "the consumption has been persisted",
      content = @Content(schema = @Schema(implementation = Consumption.class)))
  public Uni<Response> createConsumption(Consumption consumption) {
    logger.info("Creating new Consumption with ConsumptionRepository");
    return consumptionRepository.persist(consumption).map(r -> Response.ok().build());
  }
}
