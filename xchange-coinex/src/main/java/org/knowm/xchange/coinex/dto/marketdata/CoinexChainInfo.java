package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
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
  CoinexAsset asset;

  @JsonProperty("chains")
  List<CoinexChain> chains;

  @Data
  @Builder
  @Jacksonized
  public static class CoinexAsset {

    @JsonProperty("ccy")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency currency;

    @JsonProperty("deposit_enabled")
    private Boolean depositEnabled;

    @JsonProperty("withdraw_enabled")
    private Boolean withdrawEnabled;

    @JsonProperty("inter_transfer_enabled")
    private Boolean transferEnabled;

    @JsonProperty("is_st")
    private Boolean isSt;
  }

  @Data
  @Builder
  @Jacksonized
  public static class CoinexChain {

    @JsonProperty("chain")
    private String name;

    @JsonProperty("min_deposit_amount")
    private BigDecimal minDepositAmount;

    @JsonProperty("min_withdraw_amount")
    private BigDecimal minWitdrawAmount;

    @JsonProperty("deposit_enabled")
    private Boolean depositEnabled;

    @JsonProperty("withdraw_enabled")
    private Boolean withdrawEnabled;

    @JsonProperty("deposit_delay_minutes")
    private BigDecimal depositDelayMinutes;

    @JsonProperty("safe_confirmations")
    private Integer safeConfirmations;

    @JsonProperty("irreversible_confirmations")
    private Integer irreversibleConfirmations;

    @JsonProperty("deflation_rate")
    private BigDecimal deflationRate;

    @JsonProperty("withdrawal_fee")
    private BigDecimal withdrawalFee;

    @JsonProperty("withdrawal_precision")
    private Integer withdrawalPrecision;

    @JsonProperty("memo")
    private String memo;

    @JsonProperty("is_memo_required_for_deposit")
    private Boolean isMemoRequiredForDeposit;

    @JsonProperty("explorer_asset_url")
    private URI explorerAssetUrl;
  }
}
