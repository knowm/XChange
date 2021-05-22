package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrenciesResponse {

    private String currency;

    private String name;

    private String fullName;

    private BigDecimal precision;

    private String withdrawalMinSize;

    private String withdrawalMinFee;

    private boolean isWithdrawEnabled;

    private boolean isDepositEnabled;

    private boolean isMarginEnabled;

    private boolean isDebitEnabled;

}
