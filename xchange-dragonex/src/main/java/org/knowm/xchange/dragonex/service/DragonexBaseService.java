package org.knowm.xchange.dragonex.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dragonex.DragonexExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class DragonexBaseService extends BaseExchangeService<DragonexExchange>
    implements BaseService {

  /** https://github.com/Dragonexio/OpenApi/blob/master/docs/English/0.way_of_invocation.md */
  protected static final ParamsDigest ContentSHA1 =
      restInvocation -> DragonDigest.sha1(restInvocation.getRequestBody());

  /** current date in utc as http header */
  protected static String utcNow() {

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
    // return java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(
    //    ZonedDateTime.now(ZoneOffset.UTC));
  }

  public DragonexBaseService(Exchange exchange) {
    super((DragonexExchange) exchange);
  }
}
