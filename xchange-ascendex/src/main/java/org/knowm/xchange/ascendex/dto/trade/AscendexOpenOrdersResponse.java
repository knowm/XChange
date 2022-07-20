package org.knowm.xchange.ascendex.dto.trade;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;
import org.knowm.xchange.ascendex.dto.enums.AscendexSide;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexOpenOrdersResponse {

  private BigDecimal avgPx;

  private BigDecimal cumFee;

  private BigDecimal cumFilledQty;

  private String errorCode;

  private String feeAsset;

  private Date lastExecTime;

  private String orderId;

  private BigDecimal orderQty;

  private AscendexOrderType orderType;

  private BigDecimal price;

  private Long seqNum;

  private AscendexSide side;

  private String status;

  private BigDecimal stopPrice;

  private String symbol;

  private String execInst;

}
