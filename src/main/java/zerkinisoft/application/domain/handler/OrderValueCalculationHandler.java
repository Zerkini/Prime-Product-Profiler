package zerkinisoft.application.domain.handler;

import zerkinisoft.application.domain.CurrencyConverterPort;
import zerkinisoft.application.domain.record.Order;
import zerkinisoft.application.domain.record.ValueCalculatedOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderValueCalculationHandler implements OrderHandler<ValueCalculatedOrder> {

    private static final String CHF_CURRENCY_CODE = "CHF";
    private final CurrencyConverterPort currencyConverterPort;

    public OrderValueCalculationHandler(CurrencyConverterPort currencyConverterPort) {
        this.currencyConverterPort = currencyConverterPort;
    }

    @Override
    public List<ValueCalculatedOrder> handleOrders(List<Order> orders) {
        return orders.stream()
                .map(order -> {
                    BigDecimal valueInCHF = currencyConverterPort.convertToCHF(
                            order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())),
                            order.getCurrency()
                    );
                    return new ValueCalculatedOrder(order.getProduct(), order.getQuantity(), CHF_CURRENCY_CODE, valueInCHF);
                })
                .collect(Collectors.groupingBy(
                        ValueCalculatedOrder::getProduct,
                        Collectors.reducing(
                                (order1, order2) -> new ValueCalculatedOrder(
                                        order1.getProduct(),
                                        order1.getTotalQuantity() + order2.getTotalQuantity(),
                                        order1.getCurrency(),
                                        order1.getValue().add(order2.getValue())
                                )
                        )
                ))
                .values()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue())) // Sort in descending order
                .limit(10)
                .toList();
    }
}
