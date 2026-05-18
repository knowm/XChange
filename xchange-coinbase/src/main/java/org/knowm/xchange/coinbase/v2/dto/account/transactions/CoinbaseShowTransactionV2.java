package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;

@ToString
@Getter
public class CoinbaseShowTransactionV2 {
    private final String id;
    private final String type;
    private final String status;
    private final CoinbaseAmount amount;
    private final CoinbaseAmount nativeAmount;
    private final String description;
    private final String createdAt;
    private final String resource;
    private final String resourcePath;
    private final CoinbaseAdvancedTradeFill advancedTradeFill;
    private final CoinbaseTransactionV2NetworkField network;
    private final CoinbaseTransactionV2ToField to;
    private final CoinbaseTransactionV2Field from;
    private final boolean cancelable;
    private final String idem;
    private final CoinbaseTransactionV2Expand buy;
    private final CoinbaseTransactionV2Expand sell;
    private final CoinbaseTrade trade;

    public CoinbaseShowTransactionV2(
            @JsonProperty("id") String id,
            @JsonProperty("type") String type,
            @JsonProperty("status") String status,
            @JsonProperty("amount") CoinbaseAmount amount,
            @JsonProperty("native_amount") CoinbaseAmount nativeAmount,
            @JsonProperty("description") String description,
            @JsonProperty("created_at") String createdAt,
            @JsonProperty("resource") String resource,
            @JsonProperty("resource_path") String resourcePath,
            @JsonProperty("advanced_trade_fill") CoinbaseAdvancedTradeFill advancedTradeFill,
            @JsonProperty("network") CoinbaseTransactionV2NetworkField network,
            @JsonProperty("to") CoinbaseTransactionV2ToField to,
            @JsonProperty("from") CoinbaseTransactionV2Field from,
            @JsonProperty("cancelable") boolean cancelable,
            @JsonProperty("idem") String idem,
            @JsonProperty("buy") CoinbaseTransactionV2Expand buy,
            @JsonProperty("sell") CoinbaseTransactionV2Expand sell,
            @JsonProperty("trade") CoinbaseTrade trade
            )
    {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.nativeAmount = nativeAmount;
        this.description = description;
        this.createdAt = createdAt;
        this.resource = resource;
        this.resourcePath = resourcePath;
        this.advancedTradeFill = advancedTradeFill;
        this.network = network;
        this.to = to;
        this.from = from;
        this.idem = idem;
        this.buy = buy;
        this.sell = sell;
        this.trade = trade;
        this.cancelable = cancelable;
    }

}
