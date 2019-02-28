package org.knowm.xchange.lykke.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeWallet {

    private String assetId;
    private double balance;
    private double reserved;

    public LykkeWallet(
            @JsonProperty("AssetId") String assetId,
            @JsonProperty("Balance") double balance,
            @JsonProperty("Reserved") double reserved) {
        this.assetId = assetId;
        this.balance = balance;
        this.reserved = reserved;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getReserved() {
        return reserved;
    }

    public void setReserved(double reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "assetId='" + assetId + '\'' +
                ", balance=" + balance +
                ", reserved=" + reserved +
                '}';
    }
}
