package org.knowm.xchange.ascendex.dto.balance;

import lombok.Data;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;
import org.knowm.xchange.ascendex.dto.trade.AscendexPlaceOrderRequestPayload;

import java.math.BigDecimal;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.balance
 * Description:
 *
 * @date:2022/7/18 11:02
 * @author:wodepig
 */
@Data
public class AscendexOrderAndBalanceDetailDto {
    private Meta meta;
    private List<Order> order;
    private List<Balance> balance;

    @Data
    public static class Meta {

        private String ac;
        private String accountId;

    }



    @Data
    public static class Order {

        private List<DataDetail> data;
        private String liquidityInd;
        private String orderId;
        private AscendexOrderType orderType;
        private String side;
        private Long sn;
        private Long transactTime;

    }

    @Data
    public static class DataDetail {
        private String asset;
        private BigDecimal curBalance;
        private String dataType;
        private BigDecimal deltaQty;

    }

    @Data
    public static class Balance {

        private List<DataDetail> data;
        private String eventType;
        private Long sn;
        private Long transactTime;

    }
}
