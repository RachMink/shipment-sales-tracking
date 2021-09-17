/**
 * @name: Rachel Minkowitz
 * @Since: 06-20-2021
 * @Version: 2.0
 * @Description: Professor Lowenthal, Homework 3
 * XYZ Widget Store Order Processing using Linked List
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Homework3 {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("data.txt"));
        PrintStream output = new PrintStream(new File("output.txt"));
        ALinkyList newList = new ALinkyList();
        double totalPrice;
        double promotion = 0.0;
        int numCustomers = 0;

        while (sc.hasNext()) {
            String type = sc.next();

            // RECIEVED SHIPMENT

            if (type.equals("R")) {

                // create a Shipment object using the info in the scanner

                Shipment ship = Shipment.read(sc);

                // add this shipment to the end of the list

                newList.insertNodeAtRear(ship);

                // print the info of the shipment

                totalPrice = ship.getAmount() * ship.getPrice();
                output.printf("%d Widgets Received at: $%.2f each%n", ship.getAmount(), ship.getPrice());
                output.printf("TOTAL PRICE: $%.2f%n%n", totalPrice);
            }

            // SALE

            else if (type.equals("S")) {
                int amountInSale = sc.nextInt();
                totalPrice = 0.0;

                if (newList.isEmpty()) {
                    output.println("Remainder of " + amountInSale
                            + " widgets non-existant . Sorry. Please wait for next order \n\n");
                }

                // OPTION 1: if the amount of widgets being bought is the EXACT amount of
                // Widgets in the first node

                if (amountInSale == newList.peek().getAmount()) {
                    totalPrice = totalPrice + amountInSale * (newList.peek().getPrice() * 1.3);
                    output.printf("%2d Widgets sold %n%2d at $%2.2f each%n", amountInSale, amountInSale,
                            (newList.peek().getPrice() * 1.30));

                    // checks if this customer deserves a promotional discount

                    if (numCustomers != 0) {
                        output.printf("\t\tTotal sale: $%.2f%n%n", (totalPrice - (totalPrice * promotion)));
                        numCustomers--;
                    } else {
                        output.printf("\t\tTotal sale: $%.2f%n%n", totalPrice);
                    }

                    // removes the first node because we just sold all of its widgets

                    newList.removeNodeFromFront();
                }

                // OPTION 2: if the amount in the sale is LESS than the amount in the first node

                else if (amountInSale < newList.peek().getAmount()) {

                    output.printf("%2d Widgets sold %n%2d at $%2.2f each%n", amountInSale, amountInSale,
                            newList.peek().getPrice() * 1.3);
                    totalPrice = totalPrice + amountInSale * (newList.peek().getPrice() * 1.3);

                    if (numCustomers != 0) {
                        output.printf("\t\tTotal sale: $%.2f%n%n", (totalPrice - (totalPrice * promotion)));
                        numCustomers--;
                    } else {
                        output.printf("\t\tTotal sale: $%.2f%n%n", totalPrice);
                    }

                    // instead of removing the first node- because we still have more widgets to
                    // sell from it, we modify the amount dataField in the first node - by using the .modifyHead()
                    // method which resets the amount to the Amount originally in the head minus the amount
                    // we just sold.

                    int newAmount = newList.peek().getAmount() - amountInSale;
                    newList.modifyHead(newAmount);

                }

                // OPTION 3: the amount in the sale is GREATER than the amount of widgets in the
                // first node

                else if (amountInSale > newList.peek().getAmount()) {
                    output.println(amountInSale + " Widgets sold");

                    // while there are still more widgets to be sold in this sale- haven't given the
                    // customer all the widgets they requested, and I still have more widgets in-stock

                    while (amountInSale != 0 && !newList.isEmpty()) {

                        if (amountInSale > newList.peek().getAmount()) {
                            output.printf("%2d at $%2.2f each \t Sales: $%4.2f%n", newList.peek().getAmount(),
                                    newList.peek().getPrice() * 1.30,
                                    newList.peek().getAmount() * (newList.peek().getPrice() * 1.30));
                            totalPrice = totalPrice + newList.peek().getAmount() * (newList.peek().getPrice() * 1.30);

                            // reset the amountInSale so this amount reflects the difference between what we
                            // started with and how much we sold from this widget.

                            amountInSale = amountInSale - newList.peek().getAmount();

                            // because we've sold all the widgets in this node, remove it so we can go to
                            // the next node to compelete the order

                            newList.removeNodeFromFront();
                        }

                        // the next node may have more widgets than the last node- may be more than the
                        // amount in sale

                        else if (newList.peek().getAmount() > amountInSale) {
                            output.printf("%2d at $%2.2f each \t Sales: $%4.2f%n", amountInSale,
                                    newList.peek().getPrice() * 1.3, amountInSale * (newList.peek().getPrice() * 1.30));
                            totalPrice = totalPrice + amountInSale * (newList.peek().getPrice() * 1.3);

                            // we modify the head because the head has a remainding number of widgets not
                            // sold

                            int newNumber = newList.peek().getAmount() - amountInSale;
                            newList.modifyHead(newNumber);

                            // since this widget stored more widgets than the amountInSale - the sale was
                            // finished in this statement, therefore the amountInSale = 0
                            
                            amountInSale = 0;
                        }

                        // this statement is not used in this program, but could hypothetically happen

                        else if (newList.peek().getAmount() == amountInSale) {
                            totalPrice = totalPrice + amountInSale * (newList.peek().getPrice() * 1.3);
                            output.printf("%2d at $%2.2f each \t Sales: $%4.2f%n", amountInSale,
                                    (newList.peek().getPrice() * 1.30), totalPrice);
                            newList.removeNodeFromFront();
                            amountInSale = 0;
                        }

                    }

                    if (newList.isEmpty() && amountInSale != 0) {
                        output.println("Sorry, remainder of " + amountInSale + " Widgets unavailable.");
                    }

                    // checks if the customer is part of the promotion

                    if (numCustomers != 0) {
                        output.printf("\t\tTotal sale: $%.2f%n%n", (totalPrice - (totalPrice * promotion)));
                        numCustomers--;
                    } else {
                        output.printf("\t\tTotal sale: $%.2f%n%n", totalPrice);
                    }
                }
            }

            // PROMOTION

            else if (type.equals("P")) {
                int promotionalAmount = sc.nextInt();

                // sets numCustomers to 2 because in the Sales section we will check if the
                // numCustomers is more than 0
                // aka the customer is part of the next 2 Customers desserving the promo

                numCustomers = 2;
                promotion = (double) promotionalAmount / 100;
                output.println("next 2 customers will receive a " + promotionalAmount + "% discount" + "\n");
            }
        }

        // checks for remainding Widgets that weren't sold

        while (!newList.isEmpty()) {
            output.printf("There are %2d left over widgets in stock bought at $%2.2f each %n",
                    newList.peek().getAmount(), newList.peek().getPrice());
            newList.removeNodeFromFront();
        }

    }
}
