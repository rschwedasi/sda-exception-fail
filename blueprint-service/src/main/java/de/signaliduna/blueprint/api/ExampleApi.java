package de.signaliduna.blueprint.api;

import de.signaliduna.blueprint.model.FailModel;
import de.signaliduna.blueprint.model.OkModel;
import io.swagger.annotations.Api;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api
@Path("/")
@PermitAll
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExampleApi {

  @POST
  @Path("ok")
  public Response ok(@Valid OkModel model) {
    return Response.ok().build();
  }

  @POST
  @Path("fail")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fail(@Valid FailModel model) {
    return Response.ok().build();
  }

}
