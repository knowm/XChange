package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountResponse {

    private String currency;

    private BigDecimal balance;

    private BigDecimal holds;

    private BigDecimal available;
}
