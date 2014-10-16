package com.xeiam.xchange.cryptonit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yar.kh on 03/10/14.
 */
public class CryptonitFunds {

    private final Map<String, CryptonitFund> funds = new HashMap<String, CryptonitFund>();

    public Collection<CryptonitFund> getFunds() {

        return funds.values();
    }

    public CryptonitFund getFund(long id) {

        return funds.get(String.valueOf(id));
    }

    @JsonAnySetter
    public void addFund(String id, CryptonitFund fund) {

        funds.put(id, fund);
    }
}
