package zerkinisoft.application.domain.record;

import java.math.BigDecimal;

public class Order extends CSVRecord {

    private final int quantity;
    private final String product;
    private final BigDecimal price;
    private final String currency;

    public Order(int quantity, String product, BigDecimal price, String currency) {
        this.quantity = quantity;
        this.product = product;
        this.price = price;
        this.currency = currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
