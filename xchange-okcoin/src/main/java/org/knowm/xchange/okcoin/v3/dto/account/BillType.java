package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/** https://www.okex.com/docs/en/#futures-query */
@AllArgsConstructor
public enum BillType {
  open_long("1"),
  open_short("2"),
  close_long("3"),
  close_short("4"),
  transaction_fee("5"),
  transfer_in("6"),
  transfer_out("7"),
  settled_rpl("8"),
  full_liquidation_of_long("13"),
  full_liquidation_of_short("14"),
  delivery_long("15"),
  delivery_short("16"),
  settled_upl_long("17"),
  settled_upl_short("18"),
  partial_liquidation_of_short("20"),
  partial_liquidation_of_long("21");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
