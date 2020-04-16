package edu.upc.eetac.dsa;

import java.util.LinkedList;
import java.util.List;

public class Client {

    //Basic client values
    private String name;
    private String surname;
    private String id;

    //Client orders
    private List<Order> myOrders=null;

    //Public constructor to initialize user
    public Client(String name, String surname){
        this.name = name;
        this.surname = surname;
        this.id = RandomUtils.getId();
        this.myOrders= new LinkedList<Order>();
    }

    //Empty constructor for de API REST
    public Client(){}

    //Getters and Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) { this.surname = surname; }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getOrdersNumber(){ return myOrders.size(); }
    public void setMyOrders(List<Order> list){
        this.myOrders = list;
    }
    public List<Order> getMyOrders(){
        return this.myOrders;
    }

    //Adds an order to the clients list
    public void setOrder(Order order){ this.myOrders.add(order); }
    public Order getOrder(String orderId){
        int index = myOrders.size();
        for(int i=0;i<myOrders.size();i++){
            if(myOrders.get(i).getId()==id){
                index = i;
            }
        }
        return this.myOrders.get(index);
    }



    //Returns in string format the client
    @Override
    public String toString(){
        return "Client [name=" + this.getName() + ", surname=" + this.getSurname() +"]" ; }
}
