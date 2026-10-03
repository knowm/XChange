package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

public class GateioErrorLabels {
  public static int convert(String input) {
    switch (input) {
      case "INSUFFICIENT_AVAILABLE" -> {
        return 1;
      }
      case "INVALID_PARAM_VALUE" -> {
        return 2; // Your order size .... is too small. The minimum is .....
      }
      default -> {
        return -1; // Unknown error
      }
    }
  }
}
