package org.knowm.xchange.bybit.dto.trade.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bybit.dto.BybitCategorizedPayload;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails.BybitInverseOrderDetails;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails.BybitLinearOrderDetails;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails.BybitSpotOrderDetails;
import org.knowm.xchange.bybit.dto.trade.details.inverse.BybitInverseOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.linear.BybitLinearOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.spot.BybitSpotOrderDetail;

@Data
@JsonTypeInfo(
    use = Id.NAME,
    property = "category",
    visible = true,
    defaultImpl = BybitOrderDetails.class)
@JsonSubTypes({
  @Type(value = BybitLinearOrderDetails.class, name = "linear"),
  @Type(value = BybitInverseOrderDetails.class, name = "inverse"),
  @Type(value = BybitOrderDetails.class, name = "option"),
  @Type(value = BybitSpotOrderDetails.class, name = "spot"),
})
public class BybitOrderDetails<T extends BybitOrderDetail> extends BybitCategorizedPayload<T> {

  @JsonProperty("nextPageCursor")
  String nextPageCursor;

  @Jacksonized
  @Value
  public static class BybitLinearOrderDetails extends BybitOrderDetails<BybitLinearOrderDetail> {}

  @Jacksonized
  @Value
  public static class BybitSpotOrderDetails extends BybitOrderDetails<BybitSpotOrderDetail> {}

  @Jacksonized
  @Value
  public static class BybitInverseOrderDetails extends BybitOrderDetails<BybitInverseOrderDetail> {}
}
