package zerkinisoft.application.domain.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerkinisoft.application.adapter.CurrencyConverterAdapter;
import zerkinisoft.application.domain.record.Order;
import zerkinisoft.application.domain.record.ValueCalculatedOrder;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderValueCalculationHandlerTest {

    public static final String USD_CURRENCY_CODE = "USD";
    public static final String EUR_CURRENCY_CODE = "EUR";
    public static final String GBP_CURRENCY_CODE = "GBP";
    public static final String CHF_CURRENCY_CODE = "CHF";

    @Mock
    private CurrencyConverterAdapter currencyConverterAdapter;

    @InjectMocks
    private OrderValueCalculationHandler orderValueCalculationHandler;

    @Test
    public void shouldHandleOrders() {
        //given
        int quantityUSD = 100;
        BigDecimal priceUSD = new BigDecimal("25.50");
        BigDecimal convertedUSD = new BigDecimal(2216);
        int quantityEUR = 2;
        BigDecimal priceEUR = new BigDecimal("12.75");
        BigDecimal convertedEUR = new BigDecimal("24.23");
        int quantityGBP = 8;
        BigDecimal priceGBP = new BigDecimal("18.99");
        BigDecimal convertedGBP = new BigDecimal("167.52");

        List<Order> orders = List.of(
                new Order(quantityUSD, "Item1", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityUSD, "Item1", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityUSD, "Item1", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityEUR, "Item2", priceEUR, EUR_CURRENCY_CODE),
                new Order(quantityGBP, "Item3", priceGBP, GBP_CURRENCY_CODE),
                new Order(quantityGBP, "Item4", priceGBP, GBP_CURRENCY_CODE),
                new Order(quantityGBP, "Item4", priceGBP, GBP_CURRENCY_CODE),
                new Order(quantityGBP, "Item5", priceGBP, GBP_CURRENCY_CODE),
                new Order(quantityUSD, "Item6", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityUSD, "Item6", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityEUR, "Item7", priceEUR, EUR_CURRENCY_CODE),
                new Order(quantityUSD, "Item6", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityUSD, "Item6", priceUSD, USD_CURRENCY_CODE),
                new Order(quantityEUR, "Item8", priceEUR, EUR_CURRENCY_CODE),
                new Order(quantityEUR, "Item9", priceEUR, EUR_CURRENCY_CODE),
                new Order(quantityEUR, "Item10", priceEUR, EUR_CURRENCY_CODE),
                new Order(quantityEUR, "Item11", priceEUR, EUR_CURRENCY_CODE),
                new Order(quantityEUR, "Item12", priceEUR, EUR_CURRENCY_CODE));

        when(currencyConverterAdapter.convertToCHF(priceUSD.multiply(BigDecimal.valueOf(quantityUSD)), USD_CURRENCY_CODE)).thenReturn(convertedUSD);
        when(currencyConverterAdapter.convertToCHF(priceEUR.multiply(BigDecimal.valueOf(quantityEUR)), EUR_CURRENCY_CODE)).thenReturn(convertedEUR);
        when(currencyConverterAdapter.convertToCHF(priceGBP.multiply(BigDecimal.valueOf(quantityGBP)), GBP_CURRENCY_CODE)).thenReturn(convertedGBP);

        //when
        List<ValueCalculatedOrder> valueCalculatedOrders = orderValueCalculationHandler.handleOrders(orders);

        //then
        assertEquals(10, valueCalculatedOrders.size());
        assertValueCalculatedOrder(valueCalculatedOrders.get(0), "Item6", quantityUSD * 4, convertedUSD.multiply(BigDecimal.valueOf(4)));
        assertValueCalculatedOrder(valueCalculatedOrders.get(1), "Item1", quantityUSD * 3, convertedUSD.multiply(BigDecimal.valueOf(3)));
        assertValueCalculatedOrder(valueCalculatedOrders.get(2), "Item4", quantityGBP * 2, convertedGBP.multiply(BigDecimal.valueOf(2)));
    }

    private void assertValueCalculatedOrder(ValueCalculatedOrder order, String product, int totalQuantity, BigDecimal value) {
        assertEquals(value, order.getValue());
        assertEquals(OrderValueCalculationHandlerTest.CHF_CURRENCY_CODE, order.getCurrency());
        assertEquals(totalQuantity, order.getTotalQuantity());
        assertEquals(product, order.getProduct());
    }

}