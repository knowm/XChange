package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BybitKline {

  String startTime;
  String openPrice;
  String highPrice;
  String lowPrice;
  String closePrice;
  String volume;
  String turnover;
}
