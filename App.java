import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner scanner = new Scanner(System.in);

    public static void run() {
        // load data dari file
        Item.loadItemsFromFile("items.data");

        // menu program
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clearScreen();
                    addCustomer();
                    break;
                case 2:
                    clearScreen();
                    placeOrder();
                    break;
                case 3:
                    clearScreen();
                    Customer.displayCustomerData();
                    showOrders();
                    break;
                case 4:
                clearScreen();
                    Customer.displayCustomerData();
                    break;
                case 5:
                clearScreen();
                System.out.println("Menu Pembayaran\n");
                    processPayment();
                    break;
                case 6:
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (choice != 0);
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Tambah Pelanggan");
        System.out.println("2. Buat Pesanan");
        System.out.println("3. Lihat Pesanan");
        System.out.println("4. Lihat Pelanggan");
        System.out.println("5. Proses Pembayaran");
        System.out.println("6. Keluar");
        System.out.print("Masukkan pilihanmu: ");
    }

    private static void addCustomer() {
        System.out.print("Masukkan nama pelanggan: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan alamat pelanggan: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, address);
        customer.addCustomer(customer);
        clearScreen();
        System.out.println("Pelanggan berhasil ditambahkan.");
    }

    private static void placeOrder() {
        Customer.displayCustomerData();
        System.out.print("Masukkan nama pelanggan: ");
        String customerName = scanner.nextLine();
        Customer customer = Customer.findCustomerByName(customerName);
        if (customer != null) {
            Order order = new Order(customer);
            int itemId, quantity;
            char choice;

            do {
                Item.displayItemsAsTable();
                System.out.print("Masukkan ID item: ");
                itemId = scanner.nextInt();
                System.out.print("Masukkan jumlah: ");
                quantity = scanner.nextInt();
                scanner.nextLine();

                order.addOrder(itemId, quantity);

                System.out.print("Tambah item lain? (y/n): ");
                choice = scanner.nextLine().toLowerCase().charAt(0);
                clearScreen();
            } while (choice == 'y');

            order.addOrderToCustomer();
            clearScreen();
            System.out.println("Pesanan berhasil dibuat.");
        } else {
            System.out.println("Pelanggan tidak ditemukan.");
        }
    }

    private static void showOrders() {
        System.out.print("Masukkan nama pelanggan untuk menampilkan pesanan: ");
        String customerNameToDisplay = scanner.nextLine();
        Customer customerToDisplay = Customer.findCustomerByName(customerNameToDisplay);
        clearScreen();
        if (customerToDisplay != null) {
            List<Order> orders = customerToDisplay.getOrders();
            if (!orders.isEmpty()) {
                for (Order order : orders) {
                    order.showOrder(customerNameToDisplay);
                    System.out.println();
                }
            } else {
                System.out.println("Tidak ada pesanan untuk " + customerNameToDisplay);
            }
        } else {
            System.out.println("Pelanggan tidak ditemukan.");
        }
    }

    private static void processPayment() {
        System.out.print("Masukkan nama anda: ");
        String customerName = scanner.nextLine();
        Customer customer = Customer.findCustomerByName(customerName);

        if (customer != null) {
            List<Order> orders = customer.getOrders();
            if (!orders.isEmpty()) {
                System.out.println("Pilih metode pembayaran:");
                System.out.println("1. Tunai");
                System.out.println("2. Cek");
                System.out.println("3. Kartu Kredit (Visa)");
                System.out.print("Masukkan pilihanmu: ");
                int paymentChoice = scanner.nextInt();
                scanner.nextLine();

                for (Order order : orders) {
                    Payment payment;
                    switch (paymentChoice) {
                        case 1:
                            System.out.print("Masukkan jumlah tunai yang dibayarkan: ");
                            int cashTendered = scanner.nextInt();
                            scanner.nextLine();
                            payment = new Cash(520000, cashTendered);
                            order.processPayment(payment);
                            break;
                        case 2:
                            clearScreen();
                            System.out.print("Masukkan nama anda cek: ");
                            String checkName = scanner.nextLine();
                            System.out.print("Masukkan ID bank: ");
                            String bankID = scanner.nextLine();
                            payment = new Check(500000, checkName, bankID);
                            order.processPayment(payment);
                            break;
                        case 3:
                            clearScreen();
                            System.out.print("Masukkan nama anda: ");
                            String creditCardName = scanner.nextLine();
                            System.out.print("Masukkan tipe kartu kredit: ");
                            String creditCardType = scanner.nextLine();
                            System.out.print("Masukkan tanggal kadaluarsa (MM/YY): ");
                            String expDate = scanner.nextLine();
                            payment = new Credit(360000, creditCardName, creditCardType, expDate);
                            order.processPayment(payment);
                            break;
                        default:
                            System.out.println("Metode pembayaran tidak valid.");
                    }
                }
            } else {
                System.out.println("Tidak ada pesanan untuk " + customerName);
            }
        } else {
            System.out.println("Pelanggan tidak ditemukan.");
        }
    }

    // untuk membersihkan terminal tp bisa sja tidak berjalan di beberapa terminl
    private static void clearScreen() {
        System.out.print("\033c");
        System.out.flush();
    }
}