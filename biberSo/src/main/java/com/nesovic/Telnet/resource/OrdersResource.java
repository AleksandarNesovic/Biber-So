package com.nesovic.Telnet.resource;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.serializer.JsonbDeserializer;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.eclipse.yasson.internal.serializer.JsonArrayDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nesovic.Telnet.DAO.ClientsDAO;
import com.nesovic.Telnet.annotations.Secured;
import com.nesovic.Telnet.controller.OrdersController;
import com.nesovic.Telnet.model.Order;

import flexjson.JSONDeserializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("/orders")
@SwaggerDefinition(tags= {@Tag(name="/orders",description="REST Endpoints for Orders")})
public class OrdersResource {

	OrdersController controller=OrdersController.getInstance();
	ClientsDAO daoclient=new ClientsDAO();
	
	@GET
	public ArrayList<Order> getOrders(){
		return controller.selectOrders();
	}
	@GET
	@Path("/scroll")
	public ArrayList<Order> getOrdersScrollList(@QueryParam("offset") int offset){
		return controller.OrdersScrollList(offset);
	}
	@GET
	@Path("/date/{date}")
	public ArrayList<Order> getOrdersByDate(@PathParam("date") String date,@QueryParam("offset") int offset){
		return controller.ScrollOrdersByDate(offset, date);
	}
	@GET
	@Path("/period")
	public ArrayList<Order> getOrdersByPeriod(@QueryParam("offset") int offset,@QueryParam("start") String startDate,@QueryParam("end") String endDate){
		return controller.ScrollOrdersByPeriod(offset, startDate, endDate);
	}
	@GET
	@Path("/combination")
	public ArrayList<Order> getOrdersCombination(@QueryParam("offset") int offset,@QueryParam("start") String startDate,@QueryParam("end") String endDate,@QueryParam("client_id") int id){
		return controller.ScrollOrdersCombination(offset, startDate, endDate, id);
	}
	@GET
	@Path("/{id}")
	public Order getOrdersById(@PathParam("id") int id) {
		return controller.selectOrderById(id);
	}
	@POST
	@Secured
	public Response addOrder(Order g,@Context UriInfo uriInfo,@Context SecurityContext securityContext) {
		g.setClient(daoclient.selectClientsById(Integer.parseInt(securityContext.getUserPrincipal().getName())));
		Order order=controller.insertOrder(g);
		String order_id=String.valueOf(order.getOrder_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(order_id).build();
		return Response.created(uri).entity(order).build();
	}
	@POST
	@Path("/list")
	@Secured
	public Response addOrderList(ArrayList<Order> item,@Context UriInfo uriInfo,@Context SecurityContext securityContext) {
		for (Order order : item) {
	//	Order first=new JSONDeserializer<Order>().deserialize(order.toString(), Order.class);
		order.setClient(daoclient.selectClientsById(Integer.parseInt(securityContext.getUserPrincipal().getName())));
		controller.insertOrder(order);
		}
		return Response.ok().entity(item).build();
	}
	@DELETE
	@Path("/{id}")
	public Response deleteOrder(@PathParam("id") int id) {
		controller.deleteOrder(id);
		return Response.noContent().build();
	}
	@PUT
	@Path("/{id}")
	public Response updateOrder(@PathParam("id") int id,Order g,@Context UriInfo uriInfo) {
		g.setOrder_id(id);
		controller.updateOrder(g);
		String order_id=String.valueOf(g.getOrder_id());
		URI uri=uriInfo.getAbsolutePathBuilder().path(order_id).build();
		return Response.created(uri).entity(g).build();
	}
}
