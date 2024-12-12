package org.knowm.xchange.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultCancelOrderParamId implements CancelOrderByIdParams {

  private String orderId;

}
