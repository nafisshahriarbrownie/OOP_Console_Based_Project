package BillingSystem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Bill {
 private String customerName;
 private String customerPhone;
 private List<OrderItem> orderItems = new ArrayList<>();

 public Bill(String customerName, String customerPhone) {
     this.customerName = customerName;
     this.customerPhone = customerPhone;
 }

 public void addItem(OrderItem orderItem) {
     orderItems.add(orderItem);
 }

 public void printBill() {
     double total = 0;
     System.out.println("\n------ FINAL BILL ------");
     System.out.println("Customer: " + customerName + " (" + customerPhone + ")");
     for (OrderItem item : orderItems) {
         System.out.println(item.toString()); 
         total += item.getTotalPrice();
     }

     double discount = 0;
     if (total >= 500) {
         discount = total * 0.1;
         System.out.println("Discount (10%): -" + discount + " Taka");
     }

     System.out.println("------------------------");
     double finalAmount = total - discount;
     System.out.println("Total Amount: " + finalAmount + " Taka");
     System.out.println("Thank you for visiting 7 Bhai Chompa");

     
     saveBillToFile(finalAmount, discount);

    
     Thread t = new Thread(() -> {
         try {
             Thread.sleep(1500); 
             System.out.println("Come Again");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     });
     t.start();
 }

 private void saveBillToFile(double finalAmount, double discount) {
     try (FileWriter writer = new FileWriter("bills.txt", true)) {
         writer.write("------ BILL ------\n");
         writer.write("Customer: " + customerName + " (" + customerPhone + ")\n");
         for (OrderItem item : orderItems) {
             writer.write(item.toString() + "\n"); 
         }
         writer.write("Discount: " + discount + " Taka\n");
         writer.write("Final Amount: " + finalAmount + " Taka\n");
         writer.write("------------------------\n\n");
     } catch (IOException e) {
         System.out.println("Error saving bill to file: " + e.getMessage());
     }
 }
}