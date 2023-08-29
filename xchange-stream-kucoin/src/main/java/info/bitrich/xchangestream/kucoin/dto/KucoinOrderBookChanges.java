package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.ToString;

@ToString
public class KucoinOrderBookChanges {

    @JsonProperty("asks")
    public List<List<String>> asks;

    @JsonProperty("bids")
    public List<List<String>> bids;
}
