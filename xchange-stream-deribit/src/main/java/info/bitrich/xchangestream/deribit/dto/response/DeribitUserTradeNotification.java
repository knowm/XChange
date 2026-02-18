package info.bitrich.xchangestream.deribit.dto.response;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.deribit.dto.response.DeribitUserTradeNotification.UserTradeData;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.deribit.v2.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.dto.Order;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class DeribitUserTradeNotification extends DeribitWsNotification<List<UserTradeData>> {

  @Data
  @Builder
  @Jacksonized
  public static class UserTradeData {

    @JsonProperty("trade_id")
    String tradeId;

    @JsonProperty("tick_direction")
    Integer tickDirection;

    @JsonProperty("fee_currency")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    Currency feeCurrency;

    @JsonProperty("api")
    Boolean api;

    @JsonProperty("advanced")
    String advancedType;

    @JsonProperty("order_id")
    String orderId;

    @JsonProperty("liquidity")
    String liquidity;

    @JsonProperty("post_only")
    String postOnly;

    /** direction, buy or sell */
    @JsonProperty("direction")
    @JsonDeserialize(converter = StringToOrderTypeConverter.class)
    Order.OrderType orderSide;

    @JsonProperty("contracts")
    BigDecimal contracts;

    @JsonProperty("mmp")
    Boolean mmp;

    @JsonProperty("fee")
    BigDecimal fee;

    @JsonProperty("quote_id")
    String quoteId;

    @JsonProperty("index_price")
    BigDecimal indexPrice;

    @JsonProperty("label")
    String label;

    @JsonProperty("block_trade_id")
    String blockTradeId;

    @JsonProperty("price")
    BigDecimal price;

    @JsonProperty("combo_id")
    String comboId;

    @JsonProperty("matching_id")
    String matchingId;

    @JsonProperty("order_type")
    OrderType orderType;

    @JsonProperty("trade_allocations")
    List<TradeAllocation> tradeAllocations;

    @JsonProperty("profit_loss")
    BigDecimal pnl;

    @JsonProperty("timestamp")
    Instant timestamp;

    @JsonProperty("iv")
    BigDecimal iv;

    @JsonProperty("state")
    String state;

    @JsonProperty("underlying_price")
    BigDecimal underlyingPrice;

    @JsonProperty("block_rfq_quote_id")
    Integer blockRfqQuoteId;

    @JsonProperty("quote_set_id")
    String quoteSetId;

    @JsonProperty("mark_price")
    BigDecimal markPrice;

    @JsonProperty("block_rfq_id")
    Integer blockRfqId;

    @JsonProperty("combo_trade_id")
    String comboTradeId;

    @JsonProperty("reduce_only")
    String reduceOnly;

    @JsonProperty("amount")
    BigDecimal amount;

    @JsonProperty("liquidation")
    String liquidation;

    @JsonProperty("trade_seq")
    Integer tradeSeq;

    @JsonProperty("risk_reducing")
    Boolean riskReducing;

    @JsonProperty("instrument_name")
    String instrumentName;

    public enum OrderType {
      @JsonProperty("limit")
      LIMIT,

      @JsonProperty("market")
      MARKET,

      @JsonProperty("liquidation")
      LIQUIDATION,

      @JsonEnumDefaultValue
      UNKNOWN
    }

    @Data
    @Builder
    @Jacksonized
    public static class TradeAllocation {
      @JsonProperty("amount")
      BigDecimal amount;

      @JsonProperty("client_info")
      ClientInfo clientInfo;

      @JsonProperty("fee")
      BigDecimal fee;

      @JsonProperty("user_id")
      String userId;
    }

    @Data
    @Builder
    @Jacksonized
    public static class ClientInfo {
      @JsonProperty("client_id")
      Integer clientId;

      @JsonProperty("client_link_id")
      Integer clientLinkId;

      @JsonProperty("name")
      String name;
    }
  }
}
