package zerkinisoft.application.adapter;

import org.javamoney.moneta.Money;
import zerkinisoft.application.domain.CurrencyConverterPort;

import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;

public class CurrencyConverterAdapter implements CurrencyConverterPort {

    private static final String CHF_CURRENCY_CODE = "CHF";
    private final CurrencyConversion conversionCHF;

    public CurrencyConverterAdapter() {
        conversionCHF = MonetaryConversions.getConversion(CHF_CURRENCY_CODE);;
    }

    @Override
    public BigDecimal convertToCHF(BigDecimal amount, String currency) {
        Money sourceMoney = Money.of(amount, currency);
        return sourceMoney.with(conversionCHF).getNumberStripped();
    }
}
