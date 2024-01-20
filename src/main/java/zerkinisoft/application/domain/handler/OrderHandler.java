package zerkinisoft.application.domain.handler;

import zerkinisoft.application.domain.record.CSVRecord;
import zerkinisoft.application.domain.record.Order;

import java.util.List;

public interface OrderHandler<T extends CSVRecord> {

    List<T> handleOrders(List<Order> orders);

}
