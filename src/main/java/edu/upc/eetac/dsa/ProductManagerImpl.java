package edu.upc.eetac.dsa;

import org.apache.log4j.Logger;

import java.util.*;

public class ProductManagerImpl implements ProductManager{
    //Basic ProductManager values
    private static ProductManager instance;
    private List<Order> orderList;
    private HashMap<String , Product> mapProduct;
    private HashMap<String , Client> mapClient;
    private static Logger logger = Logger.getLogger(ProductManagerImpl.class);

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
            Logger.info("List of products by price in descending order: " + result.toString());
            return result; //200 OK PETITION
        }
        else {
            return null; //404 (Empty Table)
            Logger.warn(" is empty");
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
            Logger.info("List of products by price in descending order: " + result.toString());
            return result; //200 OK PETITION
        }
        else {
            return null; //404 (Empty Table)
            Logger.warn(" is empty");
        }
    }

    @Override
    public String addClient(Client c) {
        logger.info("new client " + c);
        this.mapClient.put(c.getId(),c);
        logger.info("new client added");
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
        logger.info("getClient("+id+")");
        if(c!=null) { logger.info("getClient(" + id + "): " + c); }
        else{ logger.warn("not found " + id);}
        return c;
    }

    @Override
    public void addProduct(Product p) {
        logger.info("new product " + p);
        this.mapProduct.put(p.getName(),p);
        logger.info("new product added");
    }

    @Override
    public void addProduct(String name, Double price) {
        Product p = new Product(name, price);
        this.addProduct(p);
    }

    @Override
    public String addOrder(Order o) {
        logger.info("new order " + o);
        this.orderList.add(o);
        logger.info("new order added");
        return o.getId();
    }

    @Override
    public Order getOrder(String id){
        int index = orderList.size();
        for(int i=0;i<orderList.size();i++){
            if(orderList.get(i).getId()==id){
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
    //Logger.info("getServerOrders("+id+")");
        Client c = this.mapClient.get(id);
        List<Order> result = new LinkedList<Order>();
        if(c!=null) {
            for (Order o : c.getMyOrders()) {
                if (o.isServed()) {
                    logger.info("served order from(" + id + "): " + o);
                    result.add(o);
                }
            }
            if (result.isEmpty()){
                logger.info("is empty ");
            }
        }
        else{
            logger.info("user not found ");
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
            logger.warn("Product or order not found ");
            return null;
        }
        return o;

    }

    @Override
    public Order addProductToOrder(Product p, Order o) {
        logger.info("Product added to order");
        o.setOrder(p);
        return o;
    }

    @Override
    public void myOrderIsReady(String id) {
        Order order = this.getOrder(id);
        if(order!=null){
            order.setComplete(true);
            this.mapClient.get(order.getClient()).setOrder(order);
            logger.info("Order ready to be served");
        }
        else{
            logger.warn("Order not found");
        }

    }

    @Override
    public List<Order> serveOrder() {
        int index = this.orderList.size()-1;
        while(index>0 && this.orderList.get(index).isComplete()){
            index= index-1;
        }
        if(index>=0) {
            this.orderList.get(index).setServed(true);
            Double bill = 0.0;
            for (Product p : this.orderList.get(index).getListProducts()) {
                bill = bill + p.getPrice();
                p.setSold(p.getSold() + 1);
            }
            this.orderList.get(index).setPrice(bill);
            this.mapClient.get(orderList.get(index).getClient()).getOrder(orderList.get(index).getId());
            return orderList;
        }
        return orderList;

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
