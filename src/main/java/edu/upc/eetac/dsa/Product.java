package edu.upc.eetac.dsa;

public class Product {
    //Basic product values
    private  String name;
    private  Double price;
    private  int sold;

    //Public constructor to initialize product
    public Product(String name, Double price){
        this.name = name;
        this.price=price;
        this.sold=0;
    }

    //Empty constructor for the API REST
    public Product(){
    }

    //Getters and setters
    public void setName(String name){this.name =name;}
    public void setPrice(Double price){this.price =price;}
    public String getName() {
        return this.name;
    }
    public Double getPrice() {return this.price;}
    public void setSold(int sold){this.sold=sold;}
    public int getSold(){return this.sold;}

    //Returns in string format the product
    @Override
    public String toString(){
        return "Product [name=" + this.getName() + ", price=" + this.getPrice() + ", sold=" +this.getSold() + "]" ;
    }
}
