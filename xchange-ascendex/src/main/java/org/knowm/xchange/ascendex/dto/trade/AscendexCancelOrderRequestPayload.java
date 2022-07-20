package org.knowm.xchange.ascendex.dto.trade;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;

@Data
@AllArgsConstructor
public class AscendexCancelOrderRequestPayload {
  private  String orderId;


  private  String symbol;


  private  Long time;
  @JsonIgnore
  private AccountCategory accountCategory;

  public void setSymbol(String symbol) {
    this.symbol = symbol==null?null:symbol.toUpperCase();
  }
}
