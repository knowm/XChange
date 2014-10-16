package com.xeiam.xchange.cryptonit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yar.kh on 03/10/14.
 */
public class CryptonitCoins {

    private final Map<String, CryptonitCoin> coins = new HashMap<String, CryptonitCoin>();

    public Collection<CryptonitCoin> getCoins() {

        return coins.values();
    }

    public CryptonitCoin getCoin(long id) {

        return coins.get(String.valueOf(id));
    }

    @JsonAnySetter
    public void addCoin(String id, CryptonitCoin coin) {

        coins.put(id, coin);
    }
}
