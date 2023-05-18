package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.List;

@ToString
public class KucoinOrderBookChanges {

    @JsonProperty("asks")
    public List<List<String>> asks;

    @JsonProperty("bids")
    public List<List<String>> bids;
}
