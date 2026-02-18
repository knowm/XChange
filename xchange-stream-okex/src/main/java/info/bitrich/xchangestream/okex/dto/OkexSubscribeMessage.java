package info.bitrich.xchangestream.okex.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OkexSubscribeMessage<T> {
  private final String id;
  private final String op;
  private final List<T> args;
}
