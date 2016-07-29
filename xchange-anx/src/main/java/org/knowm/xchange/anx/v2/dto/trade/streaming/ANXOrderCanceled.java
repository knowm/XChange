package org.knowm.xchange.anx.v2.dto.trade.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXOrderCanceled {

  private final String oid;
  private final String qid;

  /**
   * Constructor
   * 
   * @param oid
   * @param qid
   */
  public ANXOrderCanceled(@JsonProperty("oid") String oid, @JsonProperty("qid") String qid) {

    this.oid = oid;
    this.qid = qid;
  }

  public String getOid() {

    return oid;
  }

  public String getQid() {

    return qid;
  }

  @Override
  public String toString() {

    return "ANXOrderCanceled{" + "oid='" + oid + '\'' + ", qid='" + qid + '\'' + '}';
  }
}
