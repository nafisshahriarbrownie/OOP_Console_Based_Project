
package BillingSystem;
import java.util.*;


//Main System Class
public class RestaurantBillingSystem {
 private static Scanner sc = new Scanner(System.in);
 private static List<MenuItem> menu = new ArrayList<>();
 private static int nextId = 12; 

 public static void main(String[] args) {
  
     menu.add(new Food(1, "Kichuri", 25)); 
     menu.add(new Food(2, "Dim Bhaji", 20)); 
     menu.add(new Food(3, "Porota", 10)); 
     menu.add(new Food(4, "Daal Bhaji", 15)); 
     menu.add(new Food(5, "Shingara", 8)); 
     menu.add(new Food(6, "Daal Puri", 10)); 
     menu.add(new Drink(7, "Cha", 15)); 
     menu.add(new Drink(8, "Coffee", 25));    
     menu.add(new Food(9, "Dim Bhat", 60)); 
     menu.add(new Food(10, "Murgi Bhat", 100)); 
     menu.add(new Food(11, "Biriyani", 60));

     System.out.println("** Welcome to 7 Bhai Chompa Restaurant Billing System **");

     System.out.print("Enter role (admin/customer): ");
     String role = sc.nextLine().toLowerCase();

     if (role.equals("admin")) {
         if (adminLogin()) { 
             adminMenu();
         } else {
             System.out.println("Access denied!");
         }
     } else {
         customerMenu();
     }
 }


 private static boolean adminLogin() {
     System.out.print("Enter admin password: ");
     String password = sc.nextLine();
     return password.equals("admin123"); 
 }


 private static void adminMenu() {
	    while (true) {
	        System.out.println("\n--- Admin Menu ---");
	        System.out.println("1. View Menu");
	        System.out.println("2. Add Item");
	        System.out.println("3. Remove Item");
	        System.out.println("4. View Saved Bills"); 
	        System.out.println("0. Logout");
	        System.out.print("Choose option: "); 
	        
	        String input = sc.nextLine(); 
	        int choice; 
	        try {
	            choice = Integer.parseInt(input);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input! Please enter a number.");
	            continue; // go back to menu
	        }

	        switch (choice) {
	            case 1 -> viewMenu();
	            case 2 -> addItem();
	            case 3 -> removeItem();
	            case 4 -> viewSavedBills();
	            case 0 -> {
	                System.out.println("Logging out...");
	                return;
	            }
	            default -> System.out.println("Invalid choice!");
	        }
	    }
	}

 private static void addItem() {
     System.out.print("Enter item name: ");
     String name = sc.nextLine();
     System.out.print("Enter price: ");
     double price = sc.nextDouble();
     sc.nextLine();

     System.out.print("Enter category (food/drink): ");
     String category = sc.nextLine().toLowerCase();

     MenuItem item;
     if (category.equals("food")) {
         item = new Food(nextId++, name, price);
     } else {
         item = new Drink(nextId++, name, price);
     }
     menu.add(item);
     System.out.println("Item added successfully!");
 }

 private static void removeItem() {
     viewMenu();
     System.out.print("Enter item ID to remove: ");
     int id = sc.nextInt();
     sc.nextLine();

     menu.removeIf(item -> item.getId() == id);
     System.out.println("Item removed successfully!");
 }

private static void viewSavedBills() {
  try (Scanner fileScanner = new Scanner(new java.io.File("bills.txt"))) {
      System.out.println("\n--- All Saved Bills ---");
      boolean hasBills = false;
      while (fileScanner.hasNextLine()) {
          System.out.println(fileScanner.nextLine());
          hasBills = true;
      }
      if (!hasBills) {
          System.out.println("No bills found.");
      }
  } catch (Exception e) {
      System.out.println("No bills found or error reading file: " + e.getMessage());
  }
}

 // CUSTOMER MENU
 private static void customerMenu() {
     System.out.print("Enter your name: ");
     String name = sc.nextLine();
     System.out.print("Enter your phone: ");
     String phone = sc.nextLine();

     Bill bill = new Bill(name, phone);

     while (true) {
         System.out.println("\n--- Customer Menu ---");
         viewMenu();
         System.out.println("0. Finish and Print Bill");
         System.out.print("Enter item ID: ");

         int id = sc.nextInt();
         if (id == 0) break;

         System.out.print("Enter quantity: ");
         int qty = sc.nextInt();

         // Find item
         MenuItem selectedItem = null;
         for (MenuItem item : menu) {
             if (item.getId() == id) {
                 selectedItem = item;
                 break;
             }
         }

         if (selectedItem != null) {
             bill.addItem(new OrderItem(selectedItem, qty));
             System.out.println(qty + " x " + selectedItem.getName() + " added.");
         } else {
             System.out.println("Invalid item ID!");
         }
     }
     bill.printBill();
 }

 private static void viewMenu() {
     System.out.println("\n------ MENU ------");
     for (MenuItem item : menu) {
         System.out.println(item.toString()); 
     }
 }
}