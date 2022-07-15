package org.knowm.xchange.ascendex.dto.marketdata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Package:org.knowm.xchange.ascendex.dto.marketdata
 * Description:
 *
 * @date:2022/7/14 18:07
 * @author:wodepig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexProductBaseDto {
    private String	symbol;
    private BigDecimal	minNotional;
    private BigDecimal	maxNotional;
    private BigDecimal	tickSize;
    private BigDecimal	lotSize;
    private AscendexProductDto.AscendexProductCommissionType	commissionType;
    private BigDecimal	commissionReserveRate;
}
