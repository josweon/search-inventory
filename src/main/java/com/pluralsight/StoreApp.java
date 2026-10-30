package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class StoreApp {

    public static void main(String[] args) {
        int option;
        ArrayList<Product> inventory = getInventory();
        System.out.println("We carry the following inventory: ");
        inventory.sort(Comparator.comparing(Product::getName));

        do {
            option = menu();

            for (int i = 0; i < inventory.size(); i++) {
                Product p = inventory.get(i);
                System.out.printf("id: %d %s - Price: $%.2f%n",
                        p.getId(), p.getName(), p.getPrice());
            }
        } while (option != 5);
    }

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> inventory = new ArrayList<Product>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/inventory.csv"));
            bufReader.readLine();

            String input;

            while ((input = bufReader.readLine()) != null) {
                String[] tokens = input.split("\\|");

                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double price = Double.parseDouble(tokens[2]);

                inventory.add(new Product(id,name,price));
            }
            bufReader.close();

        } catch (IOException e) {
            System.out.println("ERROR: Invalid IO Stream " + e.getMessage());
        }

    return inventory;
    }

    public static int menu(){
        Scanner scanner = new Scanner(System.in);
        String menuStructure = """
                What do you want to do?
                1- List all products
                2- Lookup a product by its id
                3- Find all products within a price range
                4- Add a new product
                5- Quit the application
                Enter command:\s """;
        System.out.print(menuStructure);

        String input = scanner.nextLine().trim();

        return Integer.parseInt(input);
    }

    /* HELPER FUNCTION: Adds items to inventory.csv
    public static void addToCsv(){
        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/inventory.csv"));

            bufWriter.write("id|name|price\n");
            bufWriter.write("4567|10' 2x4  (grade B)|9.99\n");
            bufWriter.write("1234|Hammer|19.49\n");
            bufWriter.write("2345|Box of nails|9.29\n");
            bufWriter.write("211|Apple|1.99\n");
            bufWriter.write("222|Pear|3.99\n");
            bufWriter.write("217|Milk|8.50\n");
            bufWriter.write("281|Bread|4.75\n");
            bufWriter.write("215|Rice (1/2) pound|11.99\n");

            bufWriter.close();

        } catch (IOException e) {
            System.out.println("ERROR: Invalid IO Stream " + e.getMessage());
        }
    }
     */
}
