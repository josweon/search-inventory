package com.pluralsight;

import java.util.ArrayList;

public class StoreApp {

    public static void main(String[] args) {
        ArrayList<Product> inventory = getInventory();
        System.out.println("We carry the following inventory: ");

        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            System.out.printf("id: %d %s - Price: $%.2f%n",
                    p.getId(), p.getName(), p.getPrice());
        }
    }

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<Product>();

        inventory.add(new Product(211, "Apple", 1.99));
        inventory.add(new Product(222, "Pear", 3.99));
        inventory.add(new Product(217, "Milk", 8.50));
        inventory.add(new Product(281, "Bread", 4.75));
        inventory.add(new Product(215, "Rice (1/2 pound)", 11.99));

    return inventory;
    }
}
