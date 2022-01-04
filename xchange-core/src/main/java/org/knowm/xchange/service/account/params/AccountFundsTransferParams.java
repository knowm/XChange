package org.knowm.xchange.service.account.params;

import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public interface AccountFundsTransferParams {
    Currency getCurrency();
    BigDecimal getAmount();
}
