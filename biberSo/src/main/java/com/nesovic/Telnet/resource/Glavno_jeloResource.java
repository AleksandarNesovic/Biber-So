package com.nesovic.Telnet.resource;

import java.net.URI;


import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.nesovic.Telnet.controller.MainMealController;
import com.nesovic.Telnet.model.Main_meal;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/mainmeal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("/Glavno jelo")
@SwaggerDefinition(tags= {@Tag(name="/Glavno jelo",description="REST Endpoints for Glavno jelo")})
public class Glavno_jeloResource {

	MainMealController controller=new MainMealController();
	
	@GET
	public ArrayList<Main_meal> getJela(){
		return controller.getInstance().selectMeal();
	}
	@GET
	@Path("/{id}")
	public Main_meal getJeloById(@PathParam("id") int id) {
		return controller.getInstance().selectMealById(id);
	}
	@POST
	public Response addJelo(Main_meal g,@Context UriInfo uriInfo) {
		Main_meal glavnoJelo=controller.getInstance().insertMeal(g);
		String idJela=String.valueOf(glavnoJelo.getMain_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(idJela).build();
		return Response.created(uri).entity(glavnoJelo).build();
	}
	@DELETE
	@Path("/{id}")
	public Response deleteJelo(@PathParam("id") int id) {
		controller.getInstance().deleteMeal(id);
		return Response.noContent().build();
	}
	@PUT
	@Path("/{id}")
	public Response updateJelo(@PathParam("id") int id,Main_meal g,@Context UriInfo uriInfo) {
		g.setMain_id(id);
		controller.getInstance().updateMeal(g);
		String idJela=String.valueOf(g.getMain_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(idJela).build();
		return Response.created(uri).entity(g).build();
	}
}
