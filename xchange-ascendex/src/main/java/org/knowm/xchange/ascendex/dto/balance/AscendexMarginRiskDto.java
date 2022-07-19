package org.knowm.xchange.ascendex.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 19:57
 * @author:wodepig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexMarginRiskDto {
    private Integer	accountMaxLeverage;
    private BigDecimal availableBalanceInUSDT;
    private BigDecimal	totalBalanceInUSDT;
    private BigDecimal	totalBorrowedInUSDT;
    private BigDecimal	totalInterestInUSDT;
    private BigDecimal	netBalanceInUSDT;
    private BigDecimal	pointsBalance;
    private Integer	currentLeverage;
    private BigDecimal	cushion;

}
