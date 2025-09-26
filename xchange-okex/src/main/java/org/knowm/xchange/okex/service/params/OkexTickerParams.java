/** */
package org.knowm.xchange.okex.service.params;

import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * @author leeyazhou
 */
@Setter
@Getter
public class OkexTickerParams implements Params {
  private String instType;
  private String uly;
  private String instFamily;
}
