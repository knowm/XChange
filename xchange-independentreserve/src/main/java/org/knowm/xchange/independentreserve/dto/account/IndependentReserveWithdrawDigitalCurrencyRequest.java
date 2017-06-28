package org.knowm.xchange.independentreserve.dto.account;

import java.math.BigDecimal;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

public class IndependentReserveWithdrawDigitalCurrencyRequest extends AuthAggregate {

    public IndependentReserveWithdrawDigitalCurrencyRequest(String apiKey, Long nonce, BigDecimal amount, String withdrawalAddress, String comment) {
        super(apiKey, nonce);
        this.parameters.put("amount", amount);
        this.parameters.put("withdrawalAddress", withdrawalAddress);
        this.parameters.put("comment", comment);
      }
}
