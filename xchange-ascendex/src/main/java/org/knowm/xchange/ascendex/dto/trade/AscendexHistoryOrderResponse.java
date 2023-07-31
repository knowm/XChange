package org.knowm.xchange.ascendex.dto.trade;

import lombok.Data;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;
import org.knowm.xchange.ascendex.dto.enums.AscendexSide;

import java.math.BigDecimal;

/**
 * Package:org.knowm.xchange.ascendex.dto.trade
 * Description:
 *
 * @date:2022/7/20 18:47
 * @author:wodepig
 */
@Data
public class AscendexHistoryOrderResponse {
    private String	orderId;
    private Long	seqNum;
    private String	accountId;
    private String	symbol;
    private AscendexOrderType orderType;
    private AscendexSide side;
    private BigDecimal	price;
    private BigDecimal	stopPrice;
    private BigDecimal	orderQty;
    private String	status;
    private Long	createTime;
    private Long	lastExecTime;
    private BigDecimal avgFillPrice;
    private BigDecimal	fillQty;
    private BigDecimal	fee;
    private String	feeAsset;
}
