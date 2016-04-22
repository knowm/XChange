package org.knowm.xchange.huobi.dto.streaming.request;

/**
 * Request that contain {@link #symbolId}.
 */
public abstract class AbstractSymbolIdRequest extends Request {

  private final String symbolId;

  public AbstractSymbolIdRequest(int version, String msgType, String symbolId) {
    super(version, msgType);
    this.symbolId = symbolId;
  }

  public String getSymbolId() {
    return symbolId;
  }

}
