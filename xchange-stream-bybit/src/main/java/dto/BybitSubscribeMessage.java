package dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BybitSubscribeMessage {
  private final String op;
  private final List<String> args;
}
