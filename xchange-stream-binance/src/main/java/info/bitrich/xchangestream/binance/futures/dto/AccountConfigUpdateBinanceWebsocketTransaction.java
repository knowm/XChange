package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;

public class AccountConfigUpdateBinanceWebsocketTransaction
    extends BaseBinanceWebSocketTransaction {

  private final long transactionTime;
  private final AccountConfigUpdateData accountConfigUpdateData;
  private final UserAccountConfigUpdateData userAccountConfigUpdateData;

  public AccountConfigUpdateBinanceWebsocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") long transactionTime,
      @JsonProperty("ac") AccountConfigUpdateData accountConfigUpdateData,
      @JsonProperty("ai") UserAccountConfigUpdateData userAccountConfigUpdateData) {
    super(eventType, eventTime);
    this.transactionTime = transactionTime;
    this.accountConfigUpdateData = accountConfigUpdateData;
    this.userAccountConfigUpdateData = userAccountConfigUpdateData;
  }

  public long getTransactionTime() {
    return transactionTime;
  }

  public AccountConfigUpdateData getAccountConfigurationUpdateData() {
    return accountConfigUpdateData;
  }

  public UserAccountConfigUpdateData getUserAccountConfigurationUpdateData() {
    return userAccountConfigUpdateData;
  }
}
