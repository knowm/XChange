package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;

@ToString
@Getter
public class CoinbaseShowTransactionV2 {
    private final String id;
    private final String idem;
    private final String type;
    private final String status;
    private final CoinbaseAmount amount;
    private final CoinbaseAmount nativeAmount;
    private final String description;
    private final String createdAt;
    private final String updatedAt;
    private final String resource;
    private final String resourcePath;
    private final boolean instantExchange;
    private final CoinbaseTransactionV2Expand buy;
    private final CoinbaseTransactionV2Expand sell;
    private final CoinbaseTransactionV2Field trade;
    private final CoinbaseTransactionV2FromField from;
    private final CoinbaseTransactionV2ToField to;
    private final CoinbaseTransactionV2NetworkField network;
    private final CoinbaseTransactionV2Field application;
    private final CoinbaseTransactionDetails details;
    private final boolean hideNativeAmount;

    public CoinbaseShowTransactionV2(
            @JsonProperty("id") String id,
            @JsonProperty("idem") String idem,
            @JsonProperty("type") String type,
            @JsonProperty("status") String status,
            @JsonProperty("amount") CoinbaseAmount amount,
            @JsonProperty("native_amount") CoinbaseAmount nativeAmount,
            @JsonProperty("description") String description,
            @JsonProperty("created_at") String createdAt,
            @JsonProperty("updated_at") String updatedAt,
            @JsonProperty("resource") String resource,
            @JsonProperty("resource_path") String resourcePath,
            @JsonProperty("instant_exchange") boolean instantExchange,
            @JsonProperty("buy") CoinbaseTransactionV2Expand buy,
            @JsonProperty("sell") CoinbaseTransactionV2Expand sell,
            @JsonProperty("trade") CoinbaseTransactionV2Field trade,
            @JsonProperty("from") CoinbaseTransactionV2FromField from,
            @JsonProperty("to") CoinbaseTransactionV2ToField to,
            @JsonProperty("network") CoinbaseTransactionV2NetworkField network,
            @JsonProperty("application") CoinbaseTransactionV2Field application,
            @JsonProperty("details") CoinbaseTransactionDetails details,
            @JsonProperty("hide_native_amount") boolean hideNativeAmount) {

        this.id = id;
        this.idem = idem;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.nativeAmount = nativeAmount;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resource = resource;
        this.resourcePath = resourcePath;
        this.instantExchange = instantExchange;
        this.buy = buy;
        this.sell = sell;
        this.trade = trade;
        this.from = from;
        this.to = to;
        this.network = network;
        this.application = application;
        this.details = details;
        this.hideNativeAmount = hideNativeAmount;
    }

}
