package com.nesovic.Telnet.resource;


import java.net.URI;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

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
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.ws.http.HTTPException;

import org.eclipse.yasson.internal.serializer.JsonArrayDeserializer;

import com.bugsnag.Bugsnag;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nesovic.Telnet.DAO.ClientsDAO;
import com.nesovic.Telnet.annotations.Secured;
import com.nesovic.Telnet.controller.OrderClosedController;
import com.nesovic.Telnet.controller.OrdersController;
import com.nesovic.Telnet.model.Clients;
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
	OrderClosedController controller2=OrderClosedController.getInstance();
	ClientsDAO daoclient=new ClientsDAO();
	//Bugsnag bugsnag = new Bugsnag("6956aff2a8ca3dd356a04806e4d2ed8d");
	
	
	@GET
	public Response getOrders(){
		return Response.ok().entity(controller.selectOrders()).build();
		
	}
	@GET
	@Path("/scroll")
	public ArrayList<Order> getOrdersScrollList(@QueryParam("offset") int offset){
		return controller.OrdersScrollList(offset);
	}
	@GET
	@Path("/reverse")
	public ArrayList<Order> getReverseOrdersScrollList(@QueryParam("offset") int offset){
		return controller.ReverseOrdersScrollList(offset);
	}
	@GET
	@Path("/date/{date}")
	public Response getOrdersByDateandOffset(@PathParam("date") String date,@QueryParam("offset") int offset){
		return Response.ok().entity(controller.ScrollOrdersByDate(offset, date).size()).entity(controller.ScrollOrdersByDate(offset, date)).build();
	}
	@GET
	@Path("/singleDate/{date}")
	public Response getOrdersByDate(@PathParam("date") String date){
		return Response.ok().entity(controller.SelectOrdersByDate(date)).build();
	}
	@GET
	@Path("/startDate/{date}")
	public ArrayList<Order> getOrdersByStartDate(@PathParam("date") String date,@QueryParam("offset") int offset){
		return controller.ScrollOrdersByStartDate(offset, date);
	}
	@GET
	@Path("/endDate/{date}")
	public ArrayList<Order> getOrdersByEndDate(@PathParam("date") String date,@QueryParam("offset") int offset){
		return controller.ScrollOrdersByEndDate(offset, date);
	}
	@GET
	@Path("/period")
	public ArrayList<Order> getOrdersByPeriod(@QueryParam("offset") int offset,@QueryParam("start") String startDate,@QueryParam("end") String endDate){
		return controller.ScrollOrdersByPeriod(offset, startDate, endDate);
	}
	@GET
	@Path("/clientAndStartDate")
	public ArrayList<Order> getOrdersByClientAndStartdate(@QueryParam("offset") int offset,@QueryParam("client_id") String idList,@QueryParam("start") String startDate){
		String[] items=idList.split(",");
		int[] id=new int[items.length];
		for (int i = 0; i < items.length; i++) {
			id[i]=Integer.parseInt(items[i]);
		}
		return controller.SelectOrdersByClientAndStartdate(offset, startDate, id);
	}
	@GET
	@Path("/clientAndEndDate")
	public ArrayList<Order> getOrdersByClientAndEnddate(@QueryParam("offset") int offset,@QueryParam("client_id") String idList,@QueryParam("end") String endDate){
		String[] items=idList.split(",");
		int[] id=new int[items.length];
		for (int i = 0; i < items.length; i++) {
			id[i]=Integer.parseInt(items[i]);
		}
		return controller.SelectOrdersByClientAndEnddate(offset, endDate, id);
	}
	@GET
	@Path("/clientAndDate")
	public ArrayList<Order> getOrdersByClientAndDate(@QueryParam("offset") int offset,@QueryParam("client_id") String idList,@QueryParam("date") String Date){
		String[] items=idList.split(",");
		int[] id=new int[items.length];
		for (int i = 0; i < items.length; i++) {
			id[i]=Integer.parseInt(items[i]);
		}
		return controller.SelectOrdersByClientAndDate(offset, Date, id);
	}
	@GET
	@Path("/combination")
	public ArrayList<Order> getOrdersCombination(@QueryParam("offset") int offset,@QueryParam("start") String startDate,@QueryParam("end") String endDate,@QueryParam("client_id") String idList){
		String[] items=idList.split(",");
		int[] id=new int[items.length];
		for (int i = 0; i < items.length; i++) {
			id[i]=Integer.parseInt(items[i]);
		}
		return controller.ScrollOrdersCombination(offset, startDate, endDate, id);
	}
	@GET
	@Path("/clients")
	public ArrayList<Order> getOrdersByClients(@QueryParam("offset") int offset,@QueryParam("id") String idList){
		String[] items=idList.split(",");
		int[] id=new int[items.length];
		for (int i = 0; i < items.length; i++) {
			id[i]=Integer.parseInt(items[i]);
		}
		return controller.SelectOrdersByClients(offset, id);
	}
	@GET
	@Path("/{id}")
	public Order getOrdersById(@PathParam("id") int id) {
		return controller.selectOrderById(id);
	}
	@GET
	@Path("/myorders")
	@Secured
	public ArrayList<Order> getMyOrders(@QueryParam("offset") int offset,@Context SecurityContext securityContext){
		int id=Integer.parseInt(securityContext.getUserPrincipal().getName());
		return controller.ScrollOrdersByClient(offset, id);
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
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		if(controller2.checkOrderClosed(datum)==false) {
			for (Order order : item) {
		//	Order first=new JSONDeserializer<Order>().deserialize(order.toString(), Order.class);
			order.setClient(daoclient.selectClientsById(Integer.parseInt(securityContext.getUserPrincipal().getName())));
			controller.insertOrder(order);
			}
			return Response.ok().entity(item).build();
		}else {
			return Response.status(Status.FORBIDDEN).entity("Order closed for today").build();
		}
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
	@PUT
	@Path("/listOforders")
	public Response updateListOfOrders(ArrayList<Order> g) {
		controller.updateListOfOrders(g);
		return Response.ok().entity(g).build();
	}
	@POST
	@Path("/orderClosed")
	public Response insertOrderClosed(@QueryParam("date") String date,@QueryParam("status") boolean status) {
		controller2.insertOrderClosed(date, status);
		return Response.ok().build();
	}
	@PUT
	@Path("/updateOrderClosed/{date}")
	public Response updateOrderClosed(@PathParam("date") String date,@QueryParam("status") boolean status) {
		controller2.updateOrderClosed(date, status);
		return Response.ok().build();
	}
}
