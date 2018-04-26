package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class ExamplePushMethodResponseMessage {

  private String type = "";
  private ExamplePushMethodResponseMessageData data;

  /** */
  public ExamplePushMethodResponseMessage type(String type) {
    this.type = type;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  /** */
  public ExamplePushMethodResponseMessage data(ExamplePushMethodResponseMessageData data) {
    this.data = data;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("data")
  public ExamplePushMethodResponseMessageData getData() {
    return data;
  }

  public void setData(ExamplePushMethodResponseMessageData data) {
    this.data = data;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExamplePushMethodResponseMessage examplePushMethodResponseMessage =
        (ExamplePushMethodResponseMessage) o;
    return Objects.equals(type, examplePushMethodResponseMessage.type)
        && Objects.equals(data, examplePushMethodResponseMessage.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExamplePushMethodResponseMessage {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
