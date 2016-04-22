package org.knowm.xchange.anx.v2.dto.trade.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class ANXTradeLag {

  private final String qid;
  private final String stamp;
  private final Integer age;

  /**
   * Constructor
   * 
   * @param qid
   * @param stamp
   * @param age
   */
  public ANXTradeLag(@JsonProperty("qid") String qid, @JsonProperty("stamp") String stamp, @JsonProperty("age") Integer age) {

    this.qid = qid;
    this.stamp = stamp;
    this.age = age;
  }

  public String getQid() {

    return qid;
  }

  public String getStamp() {

    return stamp;
  }

  public Integer getAge() {

    return age;
  }

  @Override
  public String toString() {

    return "ANXTradeLag{" + "qid='" + qid + '\'' + ", stamp='" + stamp + '\'' + ", age=" + age + '}';
  }

  public String toStringShort() {

    return "ANXTradeLag= " + (double) age / 1000000 + " s";
  }
}
