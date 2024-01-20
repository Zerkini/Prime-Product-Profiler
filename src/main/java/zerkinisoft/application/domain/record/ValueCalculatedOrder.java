package zerkinisoft.application.domain.record;

import java.math.BigDecimal;
import java.util.Locale;

public class ValueCalculatedOrder extends CSVRecord {

    private final String product;
    private final int totalQuantity;
    private final String currency;
    private final BigDecimal value;

    public ValueCalculatedOrder(String product, int totalQuantity, String currency, BigDecimal value) {
        this.product = product;
        this.totalQuantity = totalQuantity;
        this.currency = currency;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s,%d,%s,%.2f",product, totalQuantity, currency, value);
    }

    public String getProduct() {
        return product;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }
}
