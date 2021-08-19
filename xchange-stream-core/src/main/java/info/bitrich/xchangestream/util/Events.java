package info.bitrich.xchangestream.util;

import info.bitrich.xchangestream.service.ConnectableService;
import org.knowm.xchange.ExchangeSpecification;

public class Events {

  /**
   * Exchange-specific parameter used for providing a {@link Runnable} action to be run prior to any
   * API calls initiated during the course of maintaining a streamed connection.
   *
   * <p>While some exchanges allow bidirectional streamed communication, such that a socket can be
   * opened and then authenticated, others perform authentication by means of a separate API call
   * which can count towards API rate limits. In addition, many exchanges require API calls to
   * obtain initial snapshots for streamed data such as order books or account updates. XChange
   * requires that the developer manage rate limits themselves, but this is not possible when
   * xchange-stream has to initiate these API calls automatically. This parameter provides a means
   * for the developer to get a callback prior to these API calls, principally to apply such rate
   * limiting globally. However, in principle there are wider potential uses.
   *
   * @see ConnectableService#BEFORE_CONNECTION_HANDLER which provides the same sort of hook for
   *     socket reconnections. This also includes example usage.
   */
  public static final String BEFORE_API_CALL_HANDLER = "Before_API_Call_Event_Handler";

  /**
   * Returns the registered handler for the {@link #BEFORE_API_CALL_HANDLER} event.
   *
   * @param exchangeSpecification The exchange specification.
   * @return The handler.
   */
  public static Runnable onApiCall(ExchangeSpecification exchangeSpecification) {
    Object onApiCall =
        exchangeSpecification.getExchangeSpecificParametersItem(BEFORE_API_CALL_HANDLER);
    return onApiCall == null ? () -> {} : (Runnable) onApiCall;
  }
}
