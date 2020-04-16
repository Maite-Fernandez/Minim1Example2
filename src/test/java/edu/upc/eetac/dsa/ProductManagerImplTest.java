package edu.upc.eetac.dsa;
import edu.upc.eetac.dsa.*;
import org.apache.log4j.Logger;
//Junit 4.13
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ProductManagerImplTest {
    // THE QUICK REMINDER: Remember to name the test class public smh
    //Log4j Logger initialization
    private static Logger log = Logger.getLogger(ProductManagerImplTest.class);
    //ProductManager
    public ProductManager manager = null;
    //Data structures
    Client client;
    String clientId;
    String orderId;

    List<Order> orderList;

    //SetUp
    @Before
    public void setUp() {
        //Configuring Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        log.debug("Debug Test Message!");
        log.info("Info Test Message!");
        log.warn("Warning Test Message!");
        log.error("Error Test Message!");

        //Instancing ProductManager Implementation
        manager = ProductManagerImpl.getInstance();

        //Initializing Test Client
        clientId = this.manager.addClient("Maite", "Fernandez");
        client = this.manager.getClient(clientId);

        //Adding Products
        this.manager.addProduct("Toast", 1.5);
        this.manager.addProduct("Coffee", 1.0);
        this.manager.addProduct("Sandwich", 4.5);

        //Initializing a Test Order
        orderId = this.manager.addOrder(new Order(clientId));
    }

    //Tests
    @Test
    //Test to make an order
    public void addOrder() {
        //Adding Product to the Order
        this.manager.addProductToOrder("Toast", orderId);
        this.manager.addProductToOrder("Coffee", orderId);
        this.manager.addProductToOrder("Sandwich", orderId);
        Assert.assertEquals(3, this.manager.getOrder(orderId).getListProducts().size());

        //Finish Order
        this.manager.myOrderIsReady(orderId);
        Assert.assertTrue(this.manager.getOrder(orderId).isComplete());
    }

    @Test
    //Test to serve an order
    public void serveOrder() {
        //Adding Product to the Order
        this.manager.addProductToOrder("Toast", orderId);
        this.manager.addProductToOrder("Coffee", orderId);
        this.manager.addProductToOrder("Sandwich", orderId);
        Assert.assertEquals(3, this.manager.getOrder(orderId).getListProducts().size());

        //Finish Order
        this.manager.myOrderIsReady(orderId);
        Assert.assertTrue(this.manager.getOrder(orderId).isComplete());

        //Serve Order
        this.manager.serveOrder();
        Assert.assertTrue(this.manager.getOrder(orderId).isServed());

    }

    //Teardown
    @After
    public void tearDown() {
        this.manager.clearResources();
    }
}
