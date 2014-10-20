package com.xeiam.xchange.bitbay.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.bitbay.dto.BitbayBaseResponse;

import java.util.Map;

/**
 * Created by Yar.kh on 10/10/14.
 */
public class BitbayAccount extends BitbayBaseResponse {

    Map<String, BitbayBalance> balances;
    Map<String, String> addresses;

    public BitbayAccount(@JsonProperty("code") String code, @JsonProperty("message")  String message, @JsonProperty("success") String success,
                         @JsonProperty("balances") Map<String, BitbayBalance> balances,
                         @JsonProperty("addresses") Map<String, String> addresses) {
        super(code, message, success);

        this.balances = balances;
        this.addresses = addresses;
    }

    public Map<String, BitbayBalance> getBalances() {
        return balances;
    }

    public Map<String, String> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        return "BitbayAccount{" +
                "balances=" + balances +
                ", addresses=" + addresses +
                '}';
    }
}
