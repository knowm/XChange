package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

public class LgoSubscription {
  public final String type;
  public final List<Channel> channels;

  public LgoSubscription(String type, Channel channels) {
    this.type = type;
    this.channels = Collections.singletonList(channels);
  }

  public static LgoSubscription subscribe(String channelName) {
    return new LgoSubscription("subscribe", new LgoSubscription.Channel(channelName));
  }

  public static LgoSubscription unsubscribe(String channelName) {
    return new LgoSubscription("unsubscribe", new LgoSubscription.Channel(channelName));
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Channel {
    public final String name;

    @JsonProperty("product_id")
    public final String productId;

    public Channel(String channelName) {
      if (channelName.contains("-")) {
        String[] strings = channelName.split("-");
        this.name = strings[0];
        this.productId = strings[1] + "-" + strings[2];
      } else {
        this.name = channelName;
        this.productId = null;
      }
    }
  }
}
