package org.knowm.xchange.bitmex.dto.trade;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;

@Data
@EqualsAndHashCode(callSuper = false)
public class BitmexCancelAll extends AbstractHttpResponseAware {
  private Date now;
  private Date cancelTime;
}
