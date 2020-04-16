package edu.upc.eetac.dsa;

import java.util.LinkedList;
import java.util.List;

public class Order {
    //Basic order values
    private  String id;
    private  Double price;
    private  String client;
    private  boolean served;
    private  boolean complete;

    //Order products
    private List<Product> listProducts=null;

    //Public constructor to initialize order
    public Order(String clientId){
        this.price = 0.0;
        this.client = clientId;
        this.served = false;
        this.id= RandomUtils.getId();
        this.listProducts= new LinkedList<Product>();
    }

    //Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    public boolean isComplete() { return complete; }
    public void setComplete(boolean complete) { this.complete = complete; }
    public boolean isServed() { return served; }
    public void setServed(boolean served) { this.served = served; }


    //Swaggers Magic
    public List<Product> getListProducts() { return listProducts; }
    public void setListProducts(List<Product> listProducts) { this.listProducts = listProducts; }

    //Adds a product to the order's list
    public void setOrder(Product product){ this.listProducts.add(product); }

    //Returns order's product list
    public List<Product> getOrder(){
        return this.listProducts;
    }

    //Returns in string format the order
    @Override
    public String toString(){
        return "Order [id=" + this.getId() + ", price=" + this.getPrice() + ", complete"+ this.isComplete() + ", served=" + this.isServed() + "]" ; }
}
