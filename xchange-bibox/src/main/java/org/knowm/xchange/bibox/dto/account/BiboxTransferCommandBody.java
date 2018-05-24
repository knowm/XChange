package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BiboxTransferCommandBody {

  @JsonProperty("totp_code")
  /** google authentication code */
  public final int totpCode;

  @JsonProperty("trade_pwd")
  /** trading password */
  public final String tradePwd;

  @JsonProperty("coin_symbol")
  /** coin symbol，get from transfer/coinList，example：BTC */
  public final String coinSymbol;

  @JsonProperty
  /** withdrawal amount */
  public final BigDecimal amount;

  @JsonProperty
  /** withdrawal address */
  public final String addr;

  @JsonProperty("addr_remark")
  /** address remark */
  public final String addrRemark;

  public BiboxTransferCommandBody(
      int totpCode,
      String tradePwd,
      String coinSymbol,
      BigDecimal amount,
      String addr,
      String addrRemark) {
    super();
    this.totpCode = totpCode;
    this.tradePwd = tradePwd;
    this.coinSymbol = coinSymbol;
    this.amount = amount;
    this.addr = addr;
    this.addrRemark = addrRemark;
  }
}
