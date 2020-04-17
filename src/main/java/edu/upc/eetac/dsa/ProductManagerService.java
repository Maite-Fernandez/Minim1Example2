package edu.upc.eetac.dsa;

//Swagger Imports
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//Log4j imports
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/ProductManager", description = "Endpoint to Product Manager Service")
@Path("/ProductManager")
public class ProductManagerService {

    static final Logger logger = Logger.getLogger(ProductManagerService.class);
    private ProductManager manager;
    public ProductManagerService(){
        //Configuring Log4j, location of the log4j.properties file and must always be inside the src folder
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        this.manager = ProductManagerImpl.getInstance();
        if (this.manager.getClientsNumber() == 0) {

            //Adding Clients
            String userId1 = this.manager.addClient("Maite","Fernandez");
            String userId2 = this.manager.addClient("Toni","Oller");
            String userId3 = this.manager.addClient("Juan","Lopez");

            //Adding Products
            this.manager.addProduct("Toast", 1.5);
            this.manager.addProduct("Coffee", 1.0);
            this.manager.addProduct("Sandwich", 4.5);
            this.manager.addProduct("Beer", 2.0);
            this.manager.addProduct("Water", 0.5);
            this.manager.addProduct("Paella", 10.5);
            this.manager.addProduct("Coke", 1.5);
            this.manager.addProduct("Pizza", 6.5);
            this.manager.addProduct("Burger", 5.5);

        }
    }
    //When multiple GET, PUT, POSTS & DELETE EXIST on the same SERVICE, path must be aggregated
    //Clients list
    @GET
    @ApiOperation(value = "Get all Clients", notes = "Retrieves the list of Clients")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Client.class, responseContainer="List"),
    })
    @Path("/listClients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<Client> clients = this.manager.getClients();

        GenericEntity<List<Client>> entity = new GenericEntity<List<Client>>(clients) {};
        return Response.status(201).entity(entity).build()  ;
    }
    //Add a product to an order
    @PUT
    @ApiOperation(value = "Add a new client", notes = "Creates a new client given a name and a surname")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Name or Surname not valid"),
    })
    @Path("/addClient/{name}/{surname}")
    public Response addClient(@PathParam("name") String name,@PathParam("surname") String surname ) {
        if(name.isEmpty()||surname.isEmpty()) return Response.status(404).build();
        String clientId = this.manager.addClient(name,surname);
        return Response.status(201).entity(this.manager.getClient(clientId)).build();
    }

    //Orders of a user
    @GET
    @ApiOperation(value = "Get all orders of a user", notes = "Retrieves the list of orders")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Order.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Client not found")
    })
    @Path("/orderList/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderList(@PathParam("clientId") String clientId ) {
        Client client = this.manager.getClient(clientId);
        if(client==null) return Response.status(404).build();
        List<Order> orders = client.getMyOrders();
        GenericEntity<List<Order>> entity = new GenericEntity<List<Order>>(orders) {};
        return Response.status(201).entity(entity).build()  ;
    }

    //Product list sorted by sells
    @GET
    @ApiOperation(value = "Get products sorted by sells", notes = "Retrieves the list of products sorted by sells")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/listProducts/BySells")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySells() {

        List<Product> products = this.manager.getProductsSortedBySells();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;
    }

    //Product list sorted by price
    @GET
    @ApiOperation(value = "Get products sorted by price", notes = "Retrieves the list of products sorted by price")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Product.class, responseContainer="List"),
    })
    @Path("/listProducts/ByPrice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {

        List<Product> products = this.manager.getProductsSortedByPrice();

        GenericEntity<List<Product>> entity = new GenericEntity<List<Product>>(products) {};
        return Response.status(201).entity(entity).build()  ;
    }

    //New Order
    @POST
    @ApiOperation(value = "Create a new order", notes = "Adds a new order and returns the order id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Order.class),
            @ApiResponse(code = 404, message = "Client not found")
    })
    @Path("/newOrder/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newUser( @PathParam("clientId") String clientId ) {
        Client client = this.manager.getClient(clientId);
        if (client==null)  return Response.status(404).build();
        Order order = new Order(clientId);
        String orderId = this.manager.addOrder(order);
        return Response.status(201).entity(this.manager.getOrder(orderId)).build();
    }


    //Add a product to an order
    @PUT
    @ApiOperation(value = "Add products to order", notes = "Edits an existing order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Order or product not found"),
    })
    @Path("/addProductToOrder/{orderId}/{productName}")
    public Response addProductToOrder(@PathParam("orderId") String orderId,@PathParam("productName") String product ) {

        Order order = this.manager.addProductToOrder(product,orderId);
        if (order == null) return Response.status(404).build();
        return Response.status(201).entity(order).build();
    }

    //Finish an order
    @PUT
    @ApiOperation(value = "My order is ready", notes = "It finishes an existing order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Order not found"),
    })
    @Path("/orderReady/{orderId}")
    public Response orderReady(@PathParam("orderId") String orderId) {

        Order order = this.manager.myOrderIsReady(orderId);
        if (order == null) return Response.status(404).build();
        return Response.status(201).entity(order).build();
    }

    //Serve an order
    @PUT
    @ApiOperation(value = "Serve an order", notes = "It serves the first ready order in the list")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "No orders ready to serve")
    })

    @Path("/serveOrder")
    public Response serveOrder() {

        Order orderServed = this.manager.serveOrder();
        if (orderServed == null) return Response.status(404).build();
        return Response.status(201).entity(orderServed).build();
    }

}
