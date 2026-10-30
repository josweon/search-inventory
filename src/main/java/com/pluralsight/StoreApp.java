package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class StoreApp {

    public static void main(String[] args) {
        ArrayList<Product> inventory = getInventory();
        System.out.println("--( welcome! )--");

        String option;
        do {
            option = menu();
            System.out.println("---|");
            switch (option) {
                case "1" -> listProducts();
                case "2" -> searchById(inventory);
                case "3" -> searchByPrice(inventory);
                case "4" -> addNewProduct(inventory);
                case "5" -> quit();
                default -> System.out.println("Invalid operation");
            }
            System.out.println("---|");

        } while (!option.equals("5"));
    }

    public static String menu() {
        Scanner scanner = new Scanner(System.in);
        String menuStructure = """
                What do you want to do?
                1- List all products
                2- Lookup a product by its id
                3- Find all products within a price range
                4- Add a new product
                5- Quit the application
                Enter command:\s""";
        System.out.print(menuStructure);

        // TODO: add exception for non strings
        String input = scanner.nextLine().trim();

        return input;
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

                inventory.add(new Product(id, name, price));
            }
            bufReader.close();

        } catch (IOException e) {
            System.out.println("ERROR: Invalid IO Stream " + e.getMessage());
        }

        inventory.sort(Comparator.comparing(Product::getName));
        return inventory;
    }

    public static void printProducts(ArrayList<Product> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            System.out.printf("\tid: %d %s - Price: $%.2f%n",
                    p.getId(), p.getName(), p.getPrice());
        }
    }

    public static void listProducts() {
        System.out.println("\t--Inventory--");
        printProducts(getInventory());
    }

    public static void searchById(ArrayList<Product> inventory) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\t--Search (ID)--\n\tEnter Product ID: ");
        String input = scanner.nextLine();
        boolean itemFound = false;

        int id = Integer.parseInt(input);
        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            if (id == p.getId()) {
                itemFound = true;
                System.out.printf("\tid: %d %s - Price: $%.2f%n",
                        p.getId(), p.getName(), p.getPrice());
            }
        }
        if (!itemFound) System.out.println("\tno items found!");
    }

    public static void searchByPrice(ArrayList<Product> inventory) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\t--Search (Price)--\n\tEnter price(low): $");
        String input1 = scanner.nextLine();
        System.out.print("\tEnter price(high): $");
        String input2 = scanner.nextLine();
        System.out.println("\t* * * *");

        boolean itemFound = false;
        double lowerPrice = Double.parseDouble(input1);
        double higherPrice = Double.parseDouble(input2);

        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            if (p.getPrice() >= lowerPrice && p.getPrice() <= higherPrice) {
                itemFound = true;
                System.out.printf("\tid: %d %s - Price: $%.2f%n",
                        p.getId(), p.getName(), p.getPrice());
            }
        }
        if (!itemFound) System.out.println("\tno items found!");
    }

    public static void addNewProduct(ArrayList<Product> inventory) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t--Add Product--");

        System.out.print("\tEnter ID: ");
        String input1 = scanner.nextLine().trim();
        System.out.print("\tEnter Name: ");
        String input2 = scanner.nextLine().trim();
        System.out.print("\tEnter Price: $");
        String input3 = scanner.nextLine().trim();

        int id = Integer.parseInt(input1);
        String name = input2;
        double price = Double.parseDouble(input3);

        Product newProd = new Product(id, name, price);
        inventory.add(newProd);

        System.out.print("\titem added, record transaction? (y/n): ");
        String record = scanner.nextLine().trim();

        if (record.equalsIgnoreCase("Y")) {
            try {
                BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/inventory.csv", true));
                String writeProd = String.format("%d|%s|%.2f",
                        newProd.getId(), newProd.getName(), newProd.getPrice());
                bufWriter.write(writeProd);
                inventory.remove(inventory.size() - 1);
                bufWriter.close();

            } catch (IOException e) {
                System.out.println("ERROR: Invalid IO Stream " + e.getMessage());
            }
        }
    }

    public static void quit() {
        System.out.println("--( goodbye! )--");
    }

    /* HELPER FUNCTION: Add products to csv
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
