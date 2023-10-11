package info.bitrich.xchangestream.bybit.dto;

import java.util.List;
import lombok.Data;

@Data
public class BybitStreamingDto {

  private Op op;

  List<Object> args;

  public BybitStreamingDto(Op op, List<Object> args) {
    this.op = op;
    this.args = args;
  }

  public enum Op {
    auth,
    subscribe,
    unsubscribe
  }
}
