package edu.upc.eetac.dsa;

import java.util.List;

public interface ProductManager {
    //Get the list of sorted by price in ascending order
    List<Product> getProductsSortedByPrice();

    //Get the list of sorted by sells in descending order
    List<Product> getProductsSortedBySells();

    //Add a Client
    String addClient(String name, String surname);
    String addClient(Client client);

    //Get a Client
    Client getClient(String id);

    //Add a Product
    void addProduct(String name, Double price);
    void addProduct(Product product);

    //Add an Order
    String addOrder(Order order);

    //Get an Order
    Order getOrder(String id);

    //Get information about a user's served order
    List<Order> getServedOrders(String clientId);

    //Add a product to an order
    Order addProductToOrder(String name, String orderId);
    Order addProductToOrder(Product product, Order order);

    //Finish an order and say that it is ready to be served
    void myOrderIsReady(String orderId);

    //Serve an order
    List<Order> serveOrder();

    //Get number of users
    int getClientsNumber();

    //Clear resources
    void clearResources();
}
