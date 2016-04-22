package org.knowm.xchange.huobi.dto.streaming.request;

/**
 * Request that contains {@link #symbolIdList}.
 */
public abstract class AbstractSymbolIdListRequest extends Request {

  private String[] symbolIdList;

  public AbstractSymbolIdListRequest(int version, String msgType) {
    super(version, msgType);
  }

  public String[] getSymbolIdList() {
    return symbolIdList;
  }

  public void setSymbolIdList(String[] symbolIdList) {
    this.symbolIdList = symbolIdList;
  }

}
