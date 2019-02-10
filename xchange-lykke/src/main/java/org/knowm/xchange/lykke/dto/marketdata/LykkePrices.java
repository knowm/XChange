package org.knowm.xchange.lykke.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkePrices {

    private double volume;
    private double price;

    public LykkePrices(
            @JsonProperty("Volume") double volume,
            @JsonProperty("Price") double price) {
        this.volume = volume;
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "volume=" + volume +
                ", price=" + price +
                '}';
    }
}
