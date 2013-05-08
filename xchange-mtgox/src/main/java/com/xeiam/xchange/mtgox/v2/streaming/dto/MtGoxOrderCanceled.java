package com.xeiam.xchange.mtgox.v2.streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MtGoxOrderCanceled {

  private final String oid;
  private final String qid;

  public MtGoxOrderCanceled(@JsonProperty("oid") String oid, @JsonProperty("qid") String qid) {

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

    return "MtGoxOrderCanceled{" + "oid='" + oid + '\'' + ", qid='" + qid + '\'' + '}';
  }
}
