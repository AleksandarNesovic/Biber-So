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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.nesovic.Telnet.controller.MealController;
import com.nesovic.Telnet.model.Meal;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/meal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("/meal")
@SwaggerDefinition(tags= {@Tag(name="/meal",description="REST Endpoints for Meals")})
public class MealResource {

	MealController controller=MealController.getInstance();
	
	@GET
	public ArrayList<Meal> getMeal(){
		return controller.selectMeal();
	}
	@GET
	@Path("/scroll")
	public ArrayList<Meal> getScrollMeal(@QueryParam("offset") int offset){
		return controller.scrollMeal(offset);
	}
	@GET
	@Path("/{id}")
	public Meal getMealById(@PathParam("id") int id) {
		return controller.selectMealById(id);
	}
	@GET
	@Path("/category/{id}")
	public ArrayList<Meal> getMealByCategory(@PathParam("id") int id) {
		return controller.selectMealByCategory(id);
	}
	@GET
	@Path("/category/{id}/scroll")
	public ArrayList<Meal> getScrollMealByCategory(@PathParam("id") int id, @QueryParam("offset") int offset) {
		return controller.scrollMealBycategory(id, offset);
	}
	@POST
	public Response addMeal(Meal g,@Context UriInfo uriInfo) {
		Meal glavnoJelo=controller.insertMeal(g);
		String idJela=String.valueOf(glavnoJelo.getMeal_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(idJela).build();
		return Response.created(uri).entity(glavnoJelo).build();
	}
	@DELETE
	@Path("/{id}")
	public Response deleteMeal(@PathParam("id") int id) {
		controller.deleteMeal(id);
		return Response.noContent().build();
	}
	@PUT
	@Path("/{id}")
	public Response updateMeal(@PathParam("id") int id,Meal g,@Context UriInfo uriInfo) {
		g.setMeal_id(id);
		controller.updateMeal(g);
		String idJela=String.valueOf(g.getMeal_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(idJela).build();
		return Response.created(uri).entity(g).build();
	}
}
