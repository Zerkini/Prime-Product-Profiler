package zerkinisoft.application.adapter.printer;

import zerkinisoft.application.domain.record.ValueCalculatedOrder;

import java.util.List;

public class ValueCalculatedPrinter implements CSVPrinter<ValueCalculatedOrder> {

    private static final String HEADER_ROW = "Product | Total Quantity | Currency | Value";

    @Override
    public void printCSV(List<ValueCalculatedOrder> orders) {
        System.out.println(HEADER_ROW);
        orders.forEach(System.out::println);
    }
}
