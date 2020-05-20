package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.knowm.xchange.enigma.dto.BaseResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnigmaExecutedQuote extends BaseResponse {
  @JsonProperty("result")
  private boolean result;

  @JsonProperty("message")
  private String message;

  @JsonProperty("rfq_client_id")
  private String rfqClientId;

  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("product_name")
  private String productName;

  @JsonProperty("side")
  private String side;

  @JsonProperty("created_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdAt;

  @JsonProperty("quantity")
  private BigDecimal quantity;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("nominal")
  private BigDecimal nominal;

  @JsonProperty("infra")
  private String infrastructure;
}
