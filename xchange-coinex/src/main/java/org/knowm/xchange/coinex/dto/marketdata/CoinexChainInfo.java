package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.net.URI;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.coinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class CoinexChainInfo {

    @JsonProperty("asset")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency currency;

    @JsonProperty("chain")
    private String chainName;

    @JsonProperty("withdrawal_precision")
    private Integer withdrawalPrecision;

    @JsonProperty("can_deposit")
    private Boolean depositEnabled;

    @JsonProperty("can_withdraw")
    private Boolean withdrawEnabled;

    @JsonProperty("deposit_least_amount")
    private BigDecimal minDepositAmount;

    @JsonProperty("withdraw_least_amount")
    private BigDecimal minWitdrawAmount;

    @JsonProperty("withdraw_tx_fee")
    private BigDecimal witdrawFeeAmount;

    @JsonProperty("explorer_asset_url")
    private URI explorerAssetUrl;

}
