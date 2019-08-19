package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LgoBalanceUpdate {
    private final long batchId;
    private final String type;
    private final String channel;
    private final List<List<String>> data;

    public LgoBalanceUpdate(
            @JsonProperty("batch_id") long batchId,
            @JsonProperty("type") String type,
            @JsonProperty("channel") String channel,
            @JsonProperty("payload") List<List<String>> data) {
        this.batchId = batchId;
        this.type = type;
        this.channel = channel;
        this.data = data;
    }

    public long getBatchId() {
        return batchId;
    }

    public String getType() {
        return type;
    }

    public String getChannel() {
        return channel;
    }

    public List<List<String>> getData() {
        return data;
    }
}
