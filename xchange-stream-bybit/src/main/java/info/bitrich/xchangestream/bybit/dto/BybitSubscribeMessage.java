package info.bitrich.xchangestream.bybit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BybitSubscribeMessage {
  private final String op;
  private final List<String> args;
}
