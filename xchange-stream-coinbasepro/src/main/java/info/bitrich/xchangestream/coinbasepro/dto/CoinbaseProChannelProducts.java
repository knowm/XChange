package info.bitrich.xchangestream.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinbaseProChannelProducts {
    private final String name;
    private final String[] product_ids;

    public CoinbaseProChannelProducts(
            @JsonProperty("name") String name,
            @JsonProperty("product_ids") String[] product_ids
    ) {
        this.name = name;
        this.product_ids = product_ids;
    }

    public String getName() {
        return name;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoinbaseProChannelProducts{");
        sb.append("name='").append(name).append('\'');
        sb.append(", product_ids='").append(product_ids).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
