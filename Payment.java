import java.util.Calendar;

class Payment {
    protected int amount;
    Order odr;

    public Payment(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void makePayment(float totalAmount) {
        if (this.amount >= totalAmount) {
            this.amount -= totalAmount;
            System.out.println("Pembayaran sejumlah " + totalAmount + " berhasil dilakukan.");
            System.out.println("Jumlah yang tersisa: " + this.amount);
        } else {
            System.out.println("Jumlah yang tidak mencukupi untuk melakukan pembayaran.");
        }
    }
}

class Cash extends Payment {
    private int cashTendered;

    public Cash(int amount, int cashTendered) {
        super(amount);
        this.cashTendered = cashTendered;
    }

    public int getCashTendered() {
        return cashTendered;
    }

    public void makeCashPayment(int paymentAmount) {
        System.out.println("Uang anda : " + this.amount);
        if (this.cashTendered >= paymentAmount) {
            this.amount -= paymentAmount;
            this.cashTendered -= paymentAmount;
            System.out.println("Pembayaran tunai sejumlah " + paymentAmount + " berhasil dilakukan.");
            System.out.println("Kembalian: " + this.cashTendered);
            System.out.println("Uang yang tersisa: " + this.amount);
        } else {
            System.out.println("Jumlah uang tunai yang tidak mencukupi untuk pembayaran.");
        }
    }
}

class Check extends Payment {
    private String name;
    private String bankID;

    public Check(int amount, String name, String bankID) {
        super(amount);
        this.name = name;
        this.bankID = bankID;
    }

    public void payByCheck(int paymentAmount) {
        System.out.println("Saldo anda : " + this.amount);
        if (this.authorized() == false) {
            System.out.println("Pembayaran cek tidak sah.");
        } else if (this.authorized() && paymentAmount <= getAmount()) {
            System.out.println("Pembayaran cek sejumlah " + paymentAmount + " berhasil dilakukan.");
            this.amount -= paymentAmount; // melakuka update amount
            System.out.println("Saldo tersisa: " + this.amount);
            System.out.println("Nama pada cek: " + getName());
            System.out.println("Nomor Bank: " + getBankID());
        } else {
            System.out.println("Jumlah pembayaran melebihi jumlah yang harus dibayar.");
        }
    }

    public String getName() {
        return name;
    }

    public String getBankID() {
        return bankID;
    }

    public boolean authorized() {
        String authorizedBankID = "14197";
        return this.bankID.equals(authorizedBankID);
    }
}

class Credit extends Payment {
    private String name;
    private String type;
    private String expDate;

    public Credit(int amount, String name, String type, String expDate) {
        super(amount);
        this.name = name;
        this.type = type;
        this.expDate = expDate;
    }

    public boolean payByCredit(int paymentAmount) {
        System.out.println("Saldo anda : " + this.amount);
        if (this.authorized() && paymentAmount <= getAmount()) {
            System.out.println("Pembayaran kredit sejumlah " + paymentAmount + " berhasil dilakukan.");
            this.amount -= paymentAmount;
            System.out.println("Saldo tersisa: " + this.amount);
            System.out.println("Nama Kredit: " + getName());
            System.out.println("Tipe Kredit: " + getType());
            System.out.println("Tanggal Kadaluarsa Kredit: " + getExpDate());
            return true;
        } else if (!this.authorized()) {
            System.out.println("Pembayaran kredit tidak sah.");
            return false;
        } else {
            System.out.println("Jumlah pembayaran melebihi jumlah yang harus dibayar.");
            return false;
        }
    }

    public boolean authorized() {
        boolean isCardValid = false; // semisal awalnya adalah tidak valid
        String validCreditType = "Visa"; // misal typenya Visa
        String validExpDatePattern = "^(0[1-9]|1[0-2])/([0-9]{2})$"; // formatnya mm/yy

        // mengecek apakah typnya valid
        if (this.type.equals(validCreditType)) {
            if (this.expDate.matches(validExpDatePattern)) {
                String[] expDateParts = this.expDate.split("/");
                int expMonth = Integer.parseInt(expDateParts[0]);
                int expYear = Integer.parseInt(expDateParts[1]);

                // mengambil bulan dan tahun
                Calendar cal = Calendar.getInstance();
                int currentMonth = cal.get(Calendar.MONTH) + 1;
                int currentYear = cal.get(Calendar.YEAR) % 100;

                // menghitung apakah tahun dan bulannya valid
                isCardValid = (expYear > currentYear) || (expYear == currentYear && expMonth >= currentMonth);
            } else {
                System.out.println("Hanya menerima kartu visa");
            }
        }

        return isCardValid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getExpDate() {
        return expDate;
    }
}
