package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "alertOnLiquidations",
  "animationsEnabled",
  "announcementsLastSeen",
  "chatChannelID",
  "colorTheme",
  "currency",
  "debug",
  "disableEmails",
  "hideConfirmDialogs",
  "hideConnectionModal",
  "hideFromLeaderboard",
  "hideNameFromLeaderboard",
  "hideNotifications",
  "locale",
  "msgsSeen",
  "orderBookBinning",
  "orderBookType",
  "orderClearImmediate",
  "orderControlsPlusMinus",
  "showLocaleNumbers",
  "sounds",
  "strictIPCheck",
  "strictTimeout",
  "tickerGroup",
  "tickerPinned",
  "tradeLayout"
})
public final class BitmexAccountPreferences {

  @JsonProperty("alertOnLiquidations")
  private Boolean alertOnLiquidations;

  @JsonProperty("animationsEnabled")
  private Boolean animationsEnabled;

  @JsonProperty("announcementsLastSeen")
  private String announcementsLastSeen;

  @JsonProperty("chatChannelID")
  private Integer chatChannelID;

  @JsonProperty("colorTheme")
  private String colorTheme;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("debug")
  private Boolean debug;

  @JsonProperty("disableEmails")
  private List<String> disableEmails = null;

  @JsonProperty("hideConfirmDialogs")
  private List<String> hideConfirmDialogs = null;

  @JsonProperty("hideConnectionModal")
  private Boolean hideConnectionModal;

  @JsonProperty("hideFromLeaderboard")
  private Boolean hideFromLeaderboard;

  @JsonProperty("hideNameFromLeaderboard")
  private Boolean hideNameFromLeaderboard;

  @JsonProperty("hideNotifications")
  private List<String> hideNotifications = null;

  @JsonProperty("locale")
  private String locale;

  @JsonProperty("msgsSeen")
  private List<String> msgsSeen = null;

  @JsonProperty("orderBookBinning")
  private BitmexOrderBookBinning orderBookBinning;

  @JsonProperty("orderBookType")
  private String orderBookType;

  @JsonProperty("orderClearImmediate")
  private Boolean orderClearImmediate;

  @JsonProperty("orderControlsPlusMinus")
  private Boolean orderControlsPlusMinus;

  @JsonProperty("showLocaleNumbers")
  private Boolean showLocaleNumbers;

  @JsonProperty("sounds")
  private List<String> sounds = null;

  @JsonProperty("strictIPCheck")
  private Boolean strictIPCheck;

  @JsonProperty("strictTimeout")
  private Boolean strictTimeout;

  @JsonProperty("tickerGroup")
  private String tickerGroup;

  @JsonProperty("tickerPinned")
  private Boolean tickerPinned;

  @JsonProperty("tradeLayout")
  private String tradeLayout;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public Boolean getAlertOnLiquidations() {
    return alertOnLiquidations;
  }

  public Boolean getAnimationsEnabled() {
    return animationsEnabled;
  }

  public String getAnnouncementsLastSeen() {
    return announcementsLastSeen;
  }

  public Integer getChatChannelID() {
    return chatChannelID;
  }

  public String getColorTheme() {
    return colorTheme;
  }

  public String getCurrency() {
    return currency;
  }

  public Boolean getDebug() {
    return debug;
  }

  public List<String> getDisableEmails() {
    return disableEmails;
  }

  public List<String> getHideConfirmDialogs() {
    return hideConfirmDialogs;
  }

  public Boolean getHideConnectionModal() {
    return hideConnectionModal;
  }

  public Boolean getHideFromLeaderboard() {
    return hideFromLeaderboard;
  }

  public Boolean getHideNameFromLeaderboard() {
    return hideNameFromLeaderboard;
  }

  public List<String> getHideNotifications() {
    return hideNotifications;
  }

  public String getLocale() {
    return locale;
  }

  public List<String> getMsgsSeen() {
    return msgsSeen;
  }

  public BitmexOrderBookBinning getOrderBookBinning() {
    return orderBookBinning;
  }

  public String getOrderBookType() {
    return orderBookType;
  }

  public Boolean getOrderClearImmediate() {
    return orderClearImmediate;
  }

  public Boolean getOrderControlsPlusMinus() {
    return orderControlsPlusMinus;
  }

  public Boolean getShowLocaleNumbers() {
    return showLocaleNumbers;
  }

  public List<String> getSounds() {
    return sounds;
  }

  public Boolean getStrictIPCheck() {
    return strictIPCheck;
  }

  public Boolean getStrictTimeout() {
    return strictTimeout;
  }

  public String getTickerGroup() {
    return tickerGroup;
  }

  public Boolean getTickerPinned() {
    return tickerPinned;
  }

  public String getTradeLayout() {
    return tradeLayout;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }
}
