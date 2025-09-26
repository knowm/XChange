package dto.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.dto.Order.IOrderFlags;

@AllArgsConstructor
@Getter
public class BybitOrderFlag implements IOrderFlags {
  private String rejectReason;
}
