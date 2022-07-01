package org.knowm.xchange.bittrex.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BittrexOrder {

  private String id;
  private String marketSymbol;
  private String direction;
  private String type;
  private BigDecimal quantity;
  private BigDecimal limit;
  private BigDecimal ceiling;
  private String timeInForce;
  private String clientOrderId;
  private BigDecimal fillQuantity;
  private BigDecimal commission;
  private BigDecimal proceeds;
  private String status;
  private Date createdAt;
  private Date updatedAt;
  private Date closedAt;
  private OrderToCancel orderToCancel;
}
