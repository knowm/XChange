package org.knowm.xchange.cryptonit2.dto.account;

import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;

public class SepaWithdrawParams extends DefaultWithdrawFundsParams {

  public final int methodId = 8;
  public final SepaParams params;

  public SepaWithdrawParams(
      Currency currency, BigDecimal amount, String firstname, String lastname, String iban) {
    super(null, currency, amount);
    this.params = new SepaParams(firstname, lastname, iban);
  }

  public class SepaParams {
    public SepaParams(String firstname, String lastname, String iban) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.iban = iban;
    }

    public final String firstname;
    public final String lastname;
    public final String iban;

    public String toJson() {
      return "{\"firstname\": \""
          + firstname
          + "\", \"lastname\": \""
          + lastname
          + "\", \"iban\": \""
          + iban
          + "\"}";
    }
  }
}
