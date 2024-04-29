public class OrderDetail {
    private int quantity;
    private Item item;

    public Item getItem() {
        return item;
    }

    public OrderDetail(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

   public int calcSubTotal() {
       return (getQuantity() * getItem().getPrice());
   }
   
   public double calcWeight() {
       return getQuantity() * getItem().getWeight();
   }
   
   public int calcTax() {
       final int DEFAULT_TAX = 2000;
       return calcSubTotal() + DEFAULT_TAX;
   }
}
