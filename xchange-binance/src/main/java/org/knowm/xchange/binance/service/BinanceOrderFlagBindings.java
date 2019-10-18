package org.knowm.xchange.binance.service;

import java.util.Arrays;
import java.util.Collection;

import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.utils.JsonTypeContribution;

/**
 * Binds the {@link IOrderFlags} implementations provided by Binance so they
 * can be serialized/deserialized.
 */
public final class BinanceOrderFlagBindings implements JsonTypeContribution<IOrderFlags> {

  @Override
  public Class<IOrderFlags> baseType() {
    return IOrderFlags.class;
  }

  @Override
  public Collection<Class<? extends IOrderFlags>> subTypes() {
    return Arrays.asList(BinanceTradeService.ClientIdFlag.class);
  }

}
