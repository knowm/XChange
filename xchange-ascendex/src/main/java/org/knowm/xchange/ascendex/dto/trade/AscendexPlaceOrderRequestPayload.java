package org.knowm.xchange.ascendex.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;
import org.knowm.xchange.ascendex.dto.enums.AscendexRespInst;
import org.knowm.xchange.ascendex.dto.enums.AscendexSide;
import org.knowm.xchange.ascendex.dto.enums.AscendexTimeInForce;

import java.util.Date;

@Data
@AllArgsConstructor
public class AscendexPlaceOrderRequestPayload {

  private String symbol;

  private Long time;

  private String orderQty;

  private AscendexOrderType orderType;

  private AscendexSide side;

  @JsonIgnore private String id;

  private String orderPrice;

  @JsonIgnore private String stopPrice;
  /**
   * 只挂单
   */
  private boolean postOnly;

  // GTC or OIC, default GTC
  @JsonIgnore private  AscendexTimeInForce timeInForce;

  private  AscendexRespInst respInst;

  public AscendexPlaceOrderRequestPayload(String symbol,  String orderQty,String orderPrice ,AscendexOrderType orderType, AscendexSide side) {
    this.symbol = symbol;
    this.time = new Date().getTime();
    this.orderPrice = orderPrice;
    this.orderQty = orderQty;
    this.orderType = orderType;
    this.side = side;
  }
}
