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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.nesovic.Telnet.DAO.ClientsDAO;
import com.nesovic.Telnet.annotations.Secured;
import com.nesovic.Telnet.controller.OrdersController;
import com.nesovic.Telnet.model.Order;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("/orders")
@SwaggerDefinition(tags= {@Tag(name="/orders",description="REST Endpoints for Orders")})
public class OrdersResource {

	OrdersController controller=new OrdersController();
	ClientsDAO daoclient=new ClientsDAO();
	
	@GET
	public ArrayList<Order> getOrders(){
		return controller.getInstance().selectOrders();
	}
	@GET
	@Path("/date/{date}")
	public ArrayList<Order> getOrdersByDate(@PathParam("date") String date){
		return controller.getInstance().selectOrdersByDate(date);
	}
	@GET
	@Path("/{id}")
	public Order getOrdersById(@PathParam("id") int id) {
		return controller.getInstance().selectOrderById(id);
	}
	@POST
	@Secured
	public Response addOrder(Order g,@Context UriInfo uriInfo,@Context SecurityContext securityContext) {
		g.setClient(daoclient.selectClientsById(Integer.parseInt(securityContext.getUserPrincipal().getName())));
		Order order=controller.getInstance().insertOrder(g);
		String order_id=String.valueOf(order.getOrder_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(order_id).build();
		return Response.created(uri).entity(order).build();
	}
	@DELETE
	@Path("/{id}")
	public Response deleteOrder(@PathParam("id") int id) {
		controller.getInstance().deleteOrder(id);
		return Response.noContent().build();
	}
	@PUT
	@Path("/{id}")
	public Response updateOrder(@PathParam("id") int id,Order g,@Context UriInfo uriInfo) {
		g.setOrder_id(id);
		controller.getInstance().updateOrder(g);
		String order_id=String.valueOf(g.getOrder_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(order_id).build();
		return Response.created(uri).entity(g).build();
	}
}
