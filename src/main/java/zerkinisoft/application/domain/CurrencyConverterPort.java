package zerkinisoft.application.domain;

import java.math.BigDecimal;

public interface CurrencyConverterPort {

    BigDecimal convertToCHF(BigDecimal amount, String currency);

}
