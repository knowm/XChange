package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.enigma.dto.BaseResponse;

@Getter
@Setter
public final class EnigmaOrderSubmission extends BaseResponse {

  @JsonProperty("order_id")
  private int id;

  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("product_name")
  private String productName;

  @JsonProperty("side")
  private String side;

  @JsonProperty("sent_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date sent;

  @JsonProperty("quantity")
  private BigDecimal quantity;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("nominal")
  private BigDecimal nominal;

  @JsonProperty("result")
  private boolean result;

  @JsonProperty("infra")
  private String infrastructure;

  @JsonProperty("message")
  private String message;

  @JsonProperty("user_name")
  private String userName;
}
