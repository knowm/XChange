package org.knowm.xchange.coincheck.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.utils.jackson.ISODateDeserializer;

@Value
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoincheckTrade {
  @JsonProperty long id;
  @JsonProperty BigDecimal amount;
  @JsonProperty BigDecimal rate;
  @JsonProperty String pair;
  @JsonProperty String orderType;

  @JsonProperty
  @JsonDeserialize(using = ISODateDeserializer.class)
  Date createdAt;
}
