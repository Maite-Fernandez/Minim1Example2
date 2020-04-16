package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;

import java.util.*;

public class ProductManagerImpl implements ProductManager{
    //Basic ProductManager values
    private static ProductManager instance;
    private List<Order> orderList;
    private HashMap<String , Product> mapProduct;
    private HashMap<String , Client> mapClient;
    private static Logger log = Logger.getLogger(ProductManagerImpl.class);

    //Private Constructor for Singleton
    private ProductManagerImpl(){
        this.orderList = new LinkedList<Order>();
        this.mapProduct = new HashMap<String, Product>();
        this.mapClient = new HashMap<String, Client>();
    }
    //Singleton implementation for the instance of the ProductManager
    public static ProductManager getInstance(){
        if(instance == null) {
            instance = new ProductManagerImpl();
        }
        return instance;
    }
    @Override
    public List<Product> getProductsSortedByPrice() {
        //Map of Products is not empty
        if(this.mapProduct != null) {
            List<Product> result = new LinkedList<Product>(mapProduct.values());

            Collections.sort(result, new Comparator<Product>() {
                @Override
                public int compare(Product p1, Product p2) {
                    //Compare prices in ascending order
                    return p1.getPrice().compareTo(p2.getPrice());
                }
            });
            log.info("List of products by price in descending order: " + result.toString());
            return result; //200 OK PETITION
        }
        else {
            log.warn("The list of products is empty");
            return null; //404 (Empty Table)

        }
    }

    @Override
    public List<Product> getProductsSortedBySells() {
        //Map of Products is not empty
        if(this.mapProduct != null) {
            List<Product> result = new LinkedList<Product>(mapProduct.values());
            class SortbySells implements Comparator<Product>{
                public int compare(Product p1, Product p2) {
                    //Compare prices in descending order
                    return p1.getSold() - p2.getSold();
                }
            }
            Comparator c = Collections.reverseOrder(new SortbySells());
            Collections.sort(result, c);
            log.info("List of products by price in descending order: " + result.toString());
            return result; //200 OK PETITION
        }
        else {
            log.warn("The list of products is empty");
            return null; //404 (Empty Table)

        }
    }

    @Override
    public String addClient(Client c) {
        log.info("New client " + c);
        this.mapClient.put(c.getId(),c);
        log.info("New client added");
        return c.getId();

    }

    @Override
    public String addClient(String name, String surname) {
        Client c = new Client(name,surname);
        this.addClient(c);
        return c.getId();
    }

    @Override
    public Client getClient(String id){
        Client c = this.mapClient.get(id);
        log.info("Get Client("+id+")");
        if(c!=null) { log.info("Get Client(" + id + "): " + c); }
        else{
            log.error("Not found " + id);
            return null;
        }
        return c;
    }

    @Override
    public List<Client> getClients() {
        return new LinkedList<Client>(mapClient.values());
    }

    @Override
    public void addProduct(Product p) {
        log.info("Add product " + p);
        this.mapProduct.put(p.getName(),p);
        log.info("New product added");
    }

    @Override
    public void addProduct(String name, Double price) {
        Product p = new Product(name, price);
        this.addProduct(p);
    }

    @Override
    public String addOrder(Order o) {
        log.info("Add order " + o);
        this.orderList.add(o);
        log.info("New order added");
        return o.getId();
    }

    @Override
    public Order getOrder(String id){
        int index = orderList.size();
        for(int i=0;i<orderList.size();i++){
            if(orderList.get(i).getId().equals(id)){
                index = i;
            }
        }
        if(index!=orderList.size()){
            return orderList.get(index);
        }
        else{
            return null;
        }
    }

    @Override
    public List<Order> getServedOrders(String id) {
        log.info("Get Server Orders ("+id+")");
        Client c = this.mapClient.get(id);
        List<Order> result = new LinkedList<Order>();
        if(c!=null) {
            for (Order o : c.getMyOrders()) {
                if (o.isServed()) {
                    log.info("Served order from (" + id + "): " + o);
                    result.add(o);
                }
            }
            if (result.isEmpty()){
                log.info("No served orders");
            }
        }
        else{
            log.info("User not found ");
            return null;
        }
        return result;
    }

    @Override
    public Order addProductToOrder(String name, String orderId) {
        Order o = this.getOrder(orderId);
        if(o!=null){
            Product p = this.mapProduct.get(name);
            if(p!=null) {
                this.addProductToOrder(p,o);
            }
        }
        else {
            log.warn("Product or order not found ");
            return null;
        }
        return o;

    }

    @Override
    public Order addProductToOrder(Product p, Order o) {
        log.info("Product added to order");
        o.setOrder(p);
        return o;
    }

    @Override
    public Order myOrderIsReady(String id) {
        int index = orderList.size();
        for(int i=0;i<orderList.size();i++){
            if(orderList.get(i).getId().equals(id)){
                index = i;
            }
        }
        if(index!=orderList.size()){
            Order order = orderList.get(index);
            this.orderList.get(index).setComplete(true);
            this.mapClient.get(orderList.get(index).getClient()).setOrder(order);
            log.info("Order ready to be served" + order);
            return order;
        }
        else{
            log.warn("Order not found");
            return null;
        }

    }

    @Override
    public Order serveOrder() {
        log.info("Serve Order");
        int index = this.orderList.size()-1;
        boolean found = false;
        Order order=null;
        Double bill = 0.0;
        while(index>=0 && (!found)){
            order=orderList.get(index);
            if(order.isComplete() && (!order.isServed())){
                found=true;
            }
            else{
                index= index-1;
            }
        }
        if(found) {
            this.orderList.get(index).setServed(true);
            for (Product p : order.getListProducts()) {
                bill = bill + p.getPrice();
                p.setSold(p.getSold() + 1);
            }
            this.orderList.get(index).setPrice(bill);
            log.info("Order served:"+ order);
            return orderList.get(index);
        }
        log.warn("No orders ready to be served.");
        return null;

    }

    @Override
    public int getClientsNumber(){
        return this.mapClient.size();
    }

    @Override
    public void clearResources() {
        this.orderList.clear();
        this.mapClient.clear();
        this.mapProduct.clear();
    }
}
