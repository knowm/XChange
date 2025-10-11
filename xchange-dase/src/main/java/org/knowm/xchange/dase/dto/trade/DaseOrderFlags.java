package org.knowm.xchange.dase.dto.trade;

import org.knowm.xchange.dto.Order.IOrderFlags;

/** DASE-specific order flags. */
public enum DaseOrderFlags implements IOrderFlags {
  /** Post-only limit order flag. */
  POST_ONLY
}
