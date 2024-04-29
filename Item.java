import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private int id;
    private String name;
    private double weight;
    private int price;
    private static List<Item> items = new ArrayList<>();

    public Item(int id, String name, int price, double weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public static Item searchById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public static void loadItemsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].replace("\"", "").trim();
                    int price = Integer.parseInt(parts[2].trim());
                    double weight = Double.parseDouble(parts[3].trim());
                    items.add(new Item(id, name, price, weight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayItemsAsTable() {
        System.out.println("Available Items : ");
        System.out.println("+----+----------------+-------+--------+");
        System.out.println("| ID | Name           | Price | Weight |");
        System.out.println("+----+----------------+-------+--------+");
        for (Item item : items) {
            System.out.format("| %2d | %-14s | %5d | %6.2f |\n", item.getId(), item.getName(), item.getPrice(),
                    item.getWeight());
        }
        System.out.println("+----+----------------+-------+--------+\n");
    }

    @Override
    public String toString() {
        return String.format("%-14s $%-5d %.2f kg", name, price, weight);
    }
    
}
