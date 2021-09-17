import java.util.Scanner;

public class Shipment {
    private int amount;
    private double price;

    public Shipment(int amount, double price){
        this.amount = amount;
        this.price = price;

    }
    public void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return amount;
    }

    public double getPrice(){
        return price;
    }


    //reads in the info from the Scanner to create a new Shipment object which includes an amount and price 

    public static Shipment read(Scanner sc){
        if(sc.hasNext()){
            return new Shipment(sc.nextInt(), sc.nextDouble());
        }
        return null;
    }

}
