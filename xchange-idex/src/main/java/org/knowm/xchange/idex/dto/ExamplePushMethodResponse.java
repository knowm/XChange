package org.knowm.xchange.idex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.knowm.xchange.idex.annotations.ApiModelProperty;

public class ExamplePushMethodResponse {

  private String topic = "";
  private ExamplePushMethodResponseMessage message;

  /** */
  public ExamplePushMethodResponse topic(String topic) {
    this.topic = topic;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("topic")
  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  /** */
  public ExamplePushMethodResponse message(ExamplePushMethodResponseMessage message) {
    this.message = message;
    return this;
  }

  @ApiModelProperty("")
  @JsonProperty("message")
  public ExamplePushMethodResponseMessage getMessage() {
    return message;
  }

  public void setMessage(ExamplePushMethodResponseMessage message) {
    this.message = message;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExamplePushMethodResponse examplePushMethodResponse = (ExamplePushMethodResponse) o;
    return Objects.equals(topic, examplePushMethodResponse.topic)
        && Objects.equals(message, examplePushMethodResponse.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(topic, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExamplePushMethodResponse {\n");

    sb.append("    topic: ").append(toIndentedString(topic)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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
