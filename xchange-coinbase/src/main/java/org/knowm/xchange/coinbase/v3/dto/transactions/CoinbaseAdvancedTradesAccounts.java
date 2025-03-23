package org.knowm.xchange.coinbase.v3.dto.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CoinbaseAdvancedTradesAccounts {

    String uuid;
    String name;
    String currency;
    Object availableBalance;
    boolean def;
    boolean active;
    String createdAt;
    String updatedAt;
    String deletedAt;
    String type;
    boolean ready;
    Object hold;

    public CoinbaseAdvancedTradesAccounts(
            @JsonProperty("uuid") String uuid,
            @JsonProperty("name") String name,
            @JsonProperty("currency") String currency,
            @JsonProperty("available_balance") Object availableBalance,
            @JsonProperty("default") boolean def,
            @JsonProperty("active") boolean active,
            @JsonProperty("created_at") String createdAt,
            @JsonProperty("updated_at") String updatedAt,
            @JsonProperty("deleted_at") String deletedAt,
            @JsonProperty("type") String type,
            @JsonProperty("ready") boolean ready,
            @JsonProperty("hold") Object hold
            ) {

        this.uuid = uuid;
        this.name = name;
        this.currency = currency;
        this.availableBalance = availableBalance;
        this.def = def;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.type = type;
        this.ready = ready;
        this.hold = hold;
    }
}
