package org.knowm.xchange.ascendex.dto.wallet;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.wallet
 * Description:
 *
 * @date:2022/7/19 9:20
 * @author:wodepig
 */
@Data
public class AscendexWalletTransactionHistoryDto {
    private List<Detail> data;
    private boolean	hasNext;
    private Integer	page;
    private Integer	pageSize;

    @Data
    public static class DestAddress{

        private String	address;

    }
    @Data
    public static class Detail{

        private String	asset;
        private BigDecimal amount;
        /**
         * the commission charged by the exchange
         */
        private BigDecimal	commission;
        private DestAddress	destAddress;
        private String	networkTransactionId;
        /**
         * the minimun number of confirmations for the transaction to be viewed as confirmed.

         */
        private Integer	numConfirmations;
        /**
         * current number of confirmations
         */
        private Integer	numConfirmed;
        private String	requestId;
        private String	status;
        private Long	time;
        private String	transactionType;

    }
}
