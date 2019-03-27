package org.knowm.xchange.simulated;

import java.io.IOException;
import org.knowm.xchange.ExchangeSpecification;

/**
 * Listener which is called every time the {@link SimulatedExchange} performs an operation.
 *
 * <p>Pass instances to {@link ExchangeSpecification#getExchangeSpecificParametersItem(String)}
 * using the parameter name {@link SimulatedExchange#ON_OPERATION_PARAM} to have them called back.
 *
 * <p>See {@link RandomExceptionThrower} for an example implementation.
 *
 * @author Graham Crockford
 */
public interface SimulatedExchangeOperationListener {

  /**
   * Called every time
   *
   * @throws IOException
   */
  void onSimulatedExchangeOperation() throws IOException;
}
