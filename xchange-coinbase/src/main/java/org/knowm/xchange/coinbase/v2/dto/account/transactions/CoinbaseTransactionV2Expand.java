package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;

import static lombok.AccessLevel.PROTECTED;

@Getter
@FieldDefaults(level = PROTECTED)
public class CoinbaseTransactionV2Expand {

    private final String id;
    private final String paymentMethodName;
    private final CoinbaseAmount fee;
    private final CoinbaseAmount total;
    private final CoinbaseAmount subtotal;
    private final String idem;

    public CoinbaseTransactionV2Expand(
            @JsonProperty("id") String id,
            @JsonProperty("payment_method_name") String paymentMethodName,
            @JsonProperty("fee") CoinbaseAmount fee,
            @JsonProperty("total") CoinbaseAmount total,
            @JsonProperty("subtotal") CoinbaseAmount subtotal,
            @JsonProperty("idem") String idem) {
        this.id = id;
        this.paymentMethodName = paymentMethodName;
        this.fee = fee;
        this.total = total;
        this.subtotal = subtotal;
        this.idem = idem;
    }

    @Override
    public String toString() {
        return "{\"CoinbaseTransactionV2Expand\":{"
                + "\"id\":\"" + id + "\""
                + ", \"paymentMethodName\":\"" + paymentMethodName + "\""
                + ", \"fee\":" + fee
                + ", \"total\":" + total
                + ", \"subtotal\":" + subtotal
                + ", \"idem\":\"" + idem + "\""
                + "}}";
    }
}