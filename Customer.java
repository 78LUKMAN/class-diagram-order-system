import java.util.ArrayList;
import java.util.List;

/**
 * Customer
 */
public class Customer {
    private String name;
    private String address;
    private static List<Customer> customerData = new ArrayList<>();
    private List<Order> orders;

    public List<Order> getOrders() {
        return this.orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void addCustomer(Customer customer) {
        customerData.add(customer);
    }

    public static void displayCustomerData() {
        // Lebar awal untuk kolom nama
        int maxNameWidth = "Nama".length(); 
        // Lebar awal untuk kolom alamat
        int maxAddressWidth = "Alamat".length(); 

        // Menghitung lebar maksimum untuk kolom nama dan alamat
        for (Customer customer : customerData) {
            maxNameWidth = Math.max(maxNameWidth, customer.getName().length());
            maxAddressWidth = Math.max(maxAddressWidth, customer.getAddress().length());
        }

        System.out.println("=".repeat(maxNameWidth + maxAddressWidth + 11));
        String format = "| %-" + (maxNameWidth + 2) + "s | %-" + (maxAddressWidth + 2) + "s |\n";
        System.out.printf(format, "Nama", "Alamat");
        System.out.println("=".repeat(maxNameWidth + maxAddressWidth + 11));

        // Mencetak data pelanggan
        if (!customerData.isEmpty()) {
            for (Customer customer : customerData) {
                System.out.printf(format, customer.getName(), customer.getAddress());
            }
            System.out.println("=".repeat(maxNameWidth + maxAddressWidth + 11));
        } else {
            System.out.println("tidak ada data pelanggan");
        }
    }

    public static Customer findCustomerByName(String customerName) {
        for (Customer customer : customerData) {
            if (customer.getName().equalsIgnoreCase(customerName)) {
                return customer;
            }
        }
        return null;
    }
}