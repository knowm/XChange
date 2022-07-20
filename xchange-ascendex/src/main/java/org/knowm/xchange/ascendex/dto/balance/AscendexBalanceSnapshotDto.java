package org.knowm.xchange.ascendex.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 21:13
 * @author:wodepig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexBalanceSnapshotDto {


    private Meta meta;

    private List<Balance> balance;

    @Data
    public static class Meta {

        private String ac;
        private String accountId;
        private Long sn;
        private Long balanceTime;

    }

    @Data
    public static class Balance {

        private String asset;
        private BigDecimal totalBalance;

    }
}
