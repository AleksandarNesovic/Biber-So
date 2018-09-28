package com.nesovic.Telnet.resource;

import java.net.URI;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.json.Json;
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

import com.nesovic.Telnet.controller.ClientController;
import com.nesovic.Telnet.exception.AuthFaildException;
import com.nesovic.Telnet.jwt.LoginService;
import com.nesovic.Telnet.model.Clients;
import com.nesovic.Telnet.model.Credentials;

import javax.ws.rs.core.Response.Status;


import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/clients")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Api("/clients")
@SwaggerDefinition(tags= {@Tag(name="/clients",description="REST Endpoints for Clients")})
public class ClientResource {
	
	ClientController controller=ClientController.getInstance();
	
	@GET
	public ArrayList<Clients> getClients(){
		return controller.selectClients();
	}
	@GET
	@Path("/user/{username}")
	public ArrayList<Clients> getClientsByUsername(@PathParam("username") String username){
		return controller.selectClientsByUsername(username);
	}
	@GET
	@Path("/{id}")
	public Clients getClientById(@PathParam("id") int id) {
		return controller.selectClientById(id);
	}
	@POST
	public Response addClient(Clients c,@Context UriInfo uriInfo) {
		Clients noviKlijent=controller.insertClient(c);
		String idKlijenta=String.valueOf(noviKlijent.getClient_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(idKlijenta).build();
		return Response.created(uri).entity(noviKlijent).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteClient(@PathParam("id") int id) {
		controller.deleteClient(id);
		return Response.noContent().build();
	}
	@PUT
	@Path("/{id}")
	public Response updateClient(@PathParam("id") int id,Clients c,@Context UriInfo uriInfo) {
		c.setClient_id(id);
		controller.updateClient(c);
		String idKlijent=String.valueOf(c.getClient_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(idKlijent).build();
		return Response.created(uri).entity(c).build();
	}
	@POST
	@Path("/login")
	public Response Login(Credentials crd) {
		LoginService log=new LoginService();
		try {
			if(log.Login(crd)!="") {
				return Response.ok(log.Login(crd)).entity(Json.createValue(log.Login(crd))).build();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Resource not found");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (java.lang.NullPointerException e) {
			return Response.status(Status.UNAUTHORIZED).entity(Json.createValue("Pogresan username or password")).build();
		}catch(AuthFaildException e) {
			return Response.status(Status.UNAUTHORIZED).entity(Json.createValue("Pogresna sifra")).build();
		}
		return Response.status(Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").entity("Pogresna sifra!!!")
				.header("Access-Control-Allow-Methods", "POST").allow("OPTIONS").build();
	}
}
