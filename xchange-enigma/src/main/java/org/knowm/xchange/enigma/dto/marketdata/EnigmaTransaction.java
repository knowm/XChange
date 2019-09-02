package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.enigma.dto.BaseResponse;

@Getter
@Setter
public class EnigmaTransaction extends BaseResponse {

  @JsonProperty("order_id")
  private int orderId;

  @JsonProperty("product_id")
  private int productId;

  @JsonProperty("quantity")
  private BigDecimal quantity;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("nominal")
  private BigDecimal nominal;

  @JsonProperty("sent_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date sentAt;

  @JsonProperty("side")
  private String side;

  @JsonProperty("order_type")
  private String orderType;

  @JsonProperty("order_status")
  private String orderStatus;

  @JsonProperty("product_name")
  private String productName;
}
