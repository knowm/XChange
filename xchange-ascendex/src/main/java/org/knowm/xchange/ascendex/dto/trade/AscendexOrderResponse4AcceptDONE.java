package org.knowm.xchange.ascendex.dto.trade;

import lombok.Data;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;
import org.knowm.xchange.ascendex.dto.enums.AscendexRespInst;
import org.knowm.xchange.ascendex.dto.enums.AscendexSide;

import java.math.BigDecimal;

/**
 * Package:org.knowm.xchange.ascendex.dto.trade
 * Description:
 *
 * @date:2022/7/20 14:27
 * @author:wodepig
 */
@Data
public class AscendexOrderResponse4AcceptDONE  {
    private String	id;
    private Long	time;
    private String	symbol;
    private BigDecimal orderPrice;
    private BigDecimal	orderQty;
    private AscendexOrderType orderType;
    private AscendexSide side;
    private AscendexRespInst respInst;
}
