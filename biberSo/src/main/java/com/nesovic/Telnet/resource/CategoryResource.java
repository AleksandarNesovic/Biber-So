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

import com.nesovic.Telnet.controller.CategoryController;
import com.nesovic.Telnet.model.Category;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/category")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Api("/category")
@SwaggerDefinition(tags= {@Tag(name="/category",description="REST Endpoints for Kategorije")})
public class CategoryResource {

	CategoryController controller=new CategoryController();
	
	@GET
	public ArrayList<Category> getCategory(){
		return controller.getInstance().selectCategory();
	}
	@GET
	@Path("/{id}")
	public Category getCategoryById(@PathParam("id") int id) {
		return controller.getInstance().selectCategoryById(id);
	}
	@POST
	public Response addCategory(Category c,@Context UriInfo uriInfo) {
		Category category=controller.getInstance().insertCategory(c);
		String category_id=String.valueOf(category.getCategory_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(category_id).build();
		return Response.created(uri).entity(category).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteCategory(@PathParam("id") int id) {
		controller.getInstance().deleteCategory(id);
		return Response.noContent().build();
	}
	@PUT
	@Path("/{id}")
	public Response updateCategory(@PathParam("id") int id,Category c,@Context UriInfo uriInfo) {
		c.setCategory_id(id);
		controller.getInstance().updateCategory(c);
		String category_id=String.valueOf(c.getCategory_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(category_id).build();
		return Response.created(uri).entity(c).build();
	}
}
