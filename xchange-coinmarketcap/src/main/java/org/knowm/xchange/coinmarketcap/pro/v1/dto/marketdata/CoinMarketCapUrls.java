package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class CoinMarketCapUrls {

  private List<String> website;
  private List<String> explorer;
  private List<String> sourceCode;
  private List<String> messageBoard;
  private List<Object> chat;
  private List<Object> announcement;
  private List<String> reddit;
  private List<String> twitter;

  public CoinMarketCapUrls(
      @JsonProperty("website") List<String> website,
      @JsonProperty("explorer") List<String> explorer,
      @JsonProperty("source_code") List<String> sourceCode,
      @JsonProperty("message_board") List<String> messageBoard,
      @JsonProperty("chat") List<Object> chat,
      @JsonProperty("announcement") List<Object> announcement,
      @JsonProperty("reddit") List<String> reddit,
      @JsonProperty("twitter") List<String> twitter) {
    this.website = website;
    this.explorer = explorer;
    this.sourceCode = sourceCode;
    this.messageBoard = messageBoard;
    this.chat = chat;
    this.announcement = announcement;
    this.reddit = reddit;
    this.twitter = twitter;
  }

  public List<String> getWebsite() {
    return website;
  }

  public List<String> getExplorer() {
    return explorer;
  }

  public List<String> getSourceCode() {
    return sourceCode;
  }

  public List<String> getMessageBoard() {
    return messageBoard;
  }

  public List<Object> getChat() {
    return chat;
  }

  public List<Object> getAnnouncement() {
    return announcement;
  }

  public List<String> getReddit() {
    return reddit;
  }

  public List<String> getTwitter() {
    return twitter;
  }

  @Override
  public String toString() {
    return "CoinMarketCapUrls{"
        + "website="
        + website
        + ", explorer="
        + explorer
        + ", sourceCode="
        + sourceCode
        + ", messageBoard="
        + messageBoard
        + ", chat="
        + chat
        + ", announcement="
        + announcement
        + ", reddit="
        + reddit
        + ", twitter="
        + twitter
        + '}';
  }
}
