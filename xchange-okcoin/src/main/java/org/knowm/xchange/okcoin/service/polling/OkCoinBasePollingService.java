package org.knowm.xchange.okcoin.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.polling.BasePollingExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class OkCoinBasePollingService extends BasePollingExchangeService implements BasePollingService {

  /** Set to true if international site should be used */
  protected final boolean useIntl;

  /**
   * Constructor
   * 
   * @param exchange
   */
  public OkCoinBasePollingService(Exchange exchange) {

    super(exchange);

    useIntl = (Boolean) exchange.getExchangeSpecification().getExchangeSpecificParameters().get("Use_Intl");

  }

  protected String createDelimitedString(String[] items) {

    StringBuilder commaDelimitedString = null;
    if (items != null) {
      for (String item : items) {
        if (commaDelimitedString == null) {
          commaDelimitedString = new StringBuilder(item);
        } else {
          commaDelimitedString.append(",").append(item);
        }
      }
    }

    return (commaDelimitedString == null) ? null : commaDelimitedString.toString();
  }
}
