package zerkinisoft.application.adapter.processor;

import zerkinisoft.application.adapter.loader.CSVLoader;
import zerkinisoft.application.adapter.printer.ValueCalculatedPrinter;
import zerkinisoft.application.domain.record.Order;
import zerkinisoft.application.domain.handler.OrderHandler;
import zerkinisoft.application.domain.record.ValueCalculatedOrder;

import java.util.List;

public class OrderCSVProcessor implements CSVProcessor {

    private final OrderHandler<ValueCalculatedOrder> orderHandler;
    private final CSVLoader<Order> orderLoader;
    private final ValueCalculatedPrinter valueCalculatedPrinter;

    public OrderCSVProcessor(OrderHandler<ValueCalculatedOrder> orderHandler,
                             CSVLoader<Order> orderLoader,
                             ValueCalculatedPrinter valueCalculatedPrinter) {
        this.orderHandler = orderHandler;
        this.orderLoader = orderLoader;
        this.valueCalculatedPrinter = valueCalculatedPrinter;
    }

    @Override
    public void processCsv(String[] args) {
        List<Order> orders = orderLoader.loadFromCSV(args);
        valueCalculatedPrinter.printCSV(orderHandler.handleOrders(orders));
    }

}
