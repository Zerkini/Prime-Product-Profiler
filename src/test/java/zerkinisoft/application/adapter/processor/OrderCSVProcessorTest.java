package zerkinisoft.application.adapter.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerkinisoft.application.adapter.CurrencyConverterAdapter;
import zerkinisoft.application.adapter.loader.OrderLoader;
import zerkinisoft.application.adapter.printer.ValueCalculatedPrinter;
import zerkinisoft.application.domain.handler.OrderValueCalculationHandler;
import zerkinisoft.application.domain.handler.OrderValueCalculationHandlerTest;
import zerkinisoft.application.domain.record.ValueCalculatedOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderCSVProcessorTest {

    @Mock
    ValueCalculatedPrinter valueCalculatedPrinter;

    @Captor
    private ArgumentCaptor<List<ValueCalculatedOrder>> captor;

    private OrderCSVProcessor orderCSVProcessor;

    @BeforeEach
    public void beforeEach() {
        orderCSVProcessor = new OrderCSVProcessor(
                new OrderValueCalculationHandler(new CurrencyConverterAdapter()),
                new OrderLoader(),
                valueCalculatedPrinter);
    }

    @Test
    public void shouldProcessCsv() {
        //given
        String[] args = {"src/test/resources/static/files/OrderCsv.csv"};

        //when
        orderCSVProcessor.processCsv(args);

        //then
        verify(valueCalculatedPrinter, times(1)).printCSV(captor.capture());
        List<ValueCalculatedOrder> capturedList = captor.getValue();
        assertValueCalculatedOrder(capturedList.getFirst(), "Item1", 100);
        assertValueCalculatedOrder(capturedList.get(1), "Item3", 8);
        assertValueCalculatedOrder(capturedList.get(2), "Item2", 2);
    }

    private void assertValueCalculatedOrder(ValueCalculatedOrder order, String product, int totalQuantity) {
        assertEquals(OrderValueCalculationHandlerTest.CHF_CURRENCY_CODE, order.getCurrency());
        assertEquals(totalQuantity, order.getTotalQuantity());
        assertEquals(product, order.getProduct());
    }

}