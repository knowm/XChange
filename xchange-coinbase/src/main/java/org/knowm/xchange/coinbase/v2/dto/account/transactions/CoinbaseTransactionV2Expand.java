package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseUnitPrice;

import static lombok.AccessLevel.PROTECTED;

@Getter
@FieldDefaults(level = PROTECTED)
public class CoinbaseTransactionV2Expand {

    String id;
    String status;
    CoinbaseTransactionV2 transaction;
    String user_reference;
    String created_at;
    String updated_at;
    String resource;
    String resource_path;
    CoinbaseTransactionV2Field payment_method;
    boolean committed;
    String payout_at;
    boolean instant;
    CoinbaseAmount fee;
    CoinbaseAmount amount;
    CoinbaseAmount total;
    CoinbaseAmount subtotal;
    CoinbaseUnitPrice unit_price;
    String idem;
    String next_step;

    public CoinbaseTransactionV2Expand(
            @JsonProperty("id") String id,
            @JsonProperty("status") String status,
            @JsonProperty("transaction") CoinbaseTransactionV2 transaction,
            @JsonProperty("user_reference") String userReference,
            @JsonProperty("created_at") String createdAt,
            @JsonProperty("updated_at") String updatedAt,
            @JsonProperty("resource") String resource,
            @JsonProperty("resource_path") String resourcePath,
            @JsonProperty("payment_method") CoinbaseTransactionV2Field paymentMethod,
            @JsonProperty("committed") boolean committed,
            @JsonProperty("payout_at") String payoutAt,
            @JsonProperty("instant") boolean instant,
            @JsonProperty("fee") CoinbaseAmount fee,
            @JsonProperty("amount") CoinbaseAmount amount,
            @JsonProperty("total") CoinbaseAmount total,
            @JsonProperty("subtotal") CoinbaseAmount subtotal,
            @JsonProperty("unit_price") CoinbaseUnitPrice unitPrice,
            @JsonProperty("idem") String idem,
            @JsonProperty("next_step") String nextStep) {

        this.id = id;
        this.status = status;
        this.transaction = transaction;
        this.user_reference = userReference;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
        this.resource = resource;
        this.resource_path = resourcePath;
        this.payment_method = paymentMethod;
        this.committed = committed;
        this.payout_at = payoutAt;
        this.instant = instant;
        this.fee = fee;
        this.amount = amount;
        this.total = total;
        this.subtotal = subtotal;
        this.unit_price = unitPrice;
        this.idem = idem;
        this.next_step = nextStep;
    }
}