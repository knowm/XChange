package com.xeiam.xchange.mtgox.v2.streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MtGoxTradeLag {

  private String qid;
  private String stamp;
  private Integer age;

  public MtGoxTradeLag(@JsonProperty("qid") String qid, @JsonProperty("stamp") String stamp, @JsonProperty("age") Integer age) {

    this.qid = qid;
    this.stamp = stamp;
    this.age = age;
  }

  public String getQid() {

    return qid;
  }

  public void setQid(String qid) {

    this.qid = qid;
  }

  public String getStamp() {

    return stamp;
  }

  public void setStamp(String stamp) {

    this.stamp = stamp;
  }

  public Integer getAge() {

    return age;
  }

  public void setAge(Integer age) {

    this.age = age;
  }

  @Override
  public String toString() {

    return "MtGoxTradeLag{" + "qid='" + qid + '\'' + ", stamp='" + stamp + '\'' + ", age=" + age + '}';
  }
}
