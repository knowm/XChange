package info.bitrich.xchangestream.huobi.dto;

public enum HuobiStepType {
  STEP0("step0"),
  STEP1("step1"),
  STEP2("step2"),
  STEP3("step3"),
  STEP4("step4"),
  STEP5("step5"),
  PERCENT10("pencent10");

  HuobiStepType(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }
}
