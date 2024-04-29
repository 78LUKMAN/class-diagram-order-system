import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private Date date;
    private String status = "Menunggu Pembayaran";
    private Customer customer;
    private List<OrderDetail> orderDetails;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Order(Customer customer) {
        this.date = new Date();
        this.customer = customer;
        this.orderDetails = new ArrayList<>();
    }

    public void addOrder(int itemId, int quantity) {
        Item item = Item.searchById(itemId);
        if (item != null) {
            OrderDetail orderDetail = new OrderDetail(quantity, item);
            orderDetails.add(orderDetail);
        }
    }

    public void addOrderToCustomer() {
        customer.addOrder(this);
    }

    public int getTax() {
        int tax = 0;
        for (OrderDetail detail : orderDetails) {
            tax += detail.calcTax();
        }
        return tax;
    }

    public int calcSubTotal() {
        int subtotal = 0;
        for (OrderDetail detail : orderDetails) {
            subtotal += detail.calcSubTotal();
        }
        return subtotal;
    }

    public int calcTotal() {
        return getTax() + calcSubTotal();
    }

    public double calcTotalWeight() {
        double totalWeight = 0.0;
        for (OrderDetail detail : orderDetails) {
            totalWeight += detail.getQuantity() * detail.getItem().getWeight();
        }
        return totalWeight;
    }

    public String getStatus() {
        return this.status;
    }

    public String setStatus(String status) {
        return this.status = status;
    }

    public void processPayment(Payment payment) {
        int totalAmount = calcTotal();

        if (payment instanceof Cash) {
            Cash cashPayment = (Cash) payment;
            if (cashPayment.getCashTendered() >= totalAmount) {
                cashPayment.makeCashPayment(totalAmount);
                setStatus("Lunas");
            } else {
                System.out.println("Uang Tunai Tidak Cukup.");
            }
        } else if (payment instanceof Check) {
            Check checkPayment = (Check) payment;
            if (checkPayment.authorized()) {
                checkPayment.payByCheck(totalAmount);
                setStatus("Lunas");
            } else {
                System.out.println("Pembayaran cek tidak sah.");
            }
        } else if (payment instanceof Credit) {
            Credit creditPayment = (Credit) payment;
            if (creditPayment.authorized()) {
                creditPayment.payByCredit(totalAmount);
                setStatus("Lunas");
            } else {
                System.out.println("Pembayaran kredit tidak sah.");
            }
        } else {
            System.out.println("Metode Pembayaran Tidak Valid.");
        }
    }

    public void showOrder(String customerName) {
        if (customer.getName().equalsIgnoreCase(customerName)) {
            System.out.println("Order untuk " + customerName + ":");
            System.out.println("+---------------------+-----------+----------+");
            System.out.println("| Barang              | Kuantitas | Subtotal |");
            System.out.println("+---------------------+-----------+----------+");
            for (OrderDetail detail : orderDetails) {
                System.out.printf("| %-19s | %-8d | %-9d |\n",
                        detail.getItem().getName(),
                        detail.getQuantity(),
                        detail.calcSubTotal());
            }
            System.out.println("+---------------------+----------+-----------+");
            System.out.println("Tanggal: " + formatter.format(this.date));
            System.out.println("Total Berat: " + calcTotalWeight());
            System.out.println("Total Pajak: " + getTax());
            System.out.println("Sub Total: " + calcSubTotal());
            System.out.println("Total: " + calcTotal());
            System.out.println("Status Pembayaran: " + getStatus() + "\n");
        } else {
            System.out.println("Tidak ada order untuk " + customerName);
        }
    }
}
