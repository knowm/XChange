package info.bitrich.xchangestream.deribit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.deribit.dto.response.DeribitTradeNotification.TradeData;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.deribit.v2.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.dto.Order;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class DeribitTradeNotification extends DeribitWsNotification<List<TradeData>> {

  @Data
  @Builder
  @Jacksonized
  public static class TradeData {

    @JsonProperty("amount")
    BigDecimal amount;

    @JsonProperty("block_rfq_id")
    Integer blockRfqId;

    @JsonProperty("block_trade_id")
    String blockTradeId;

    @JsonProperty("block_trade_leg_count")
    Integer blockTradeLegCount;

    @JsonProperty("combo_id")
    String comboId;

    @JsonProperty("combo_trade_id")
    String comboTradeId;

    @JsonProperty("contracts")
    BigDecimal contracts;

    /** direction, buy or sell */
    @JsonProperty("direction")
    @JsonDeserialize(converter = StringToOrderTypeConverter.class)
    Order.OrderType orderSide;

    @JsonProperty("index_price")
    BigDecimal indexPrice;

    @JsonProperty("instrument_name")
    String instrumentName;

    @JsonProperty("iv")
    BigDecimal iv;

    @JsonProperty("liquidation")
    String liquidation;

    @JsonProperty("mark_price")
    BigDecimal markPrice;

    @JsonProperty("price")
    BigDecimal price;

    @JsonProperty("tick_direction")
    Integer tickDirection;

    @JsonProperty("timestamp")
    Instant timestamp;

    @JsonProperty("trade_id")
    String tradeId;

    @JsonProperty("trade_seq")
    Integer tradeSeq;
  }
}
