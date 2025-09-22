

package BillingSystem;

class Drink extends MenuItem {
 public Drink(int id, String name, double price) {
     super(id, name, price);
 }

 @Override
 public String getCategory() {
     return "Drink";
 }
}
