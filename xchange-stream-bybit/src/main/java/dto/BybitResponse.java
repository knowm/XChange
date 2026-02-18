package dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class BybitResponse<T> {
  private String topic;
  private String type;
  private long cs;
  private long ts;
  private T data;
}
