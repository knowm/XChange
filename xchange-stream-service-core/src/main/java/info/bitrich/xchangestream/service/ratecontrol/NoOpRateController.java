package info.bitrich.xchangestream.service.ratecontrol;

public class NoOpRateController implements RateController {

  @Override
  public void acquire() {}

  @Override
  public void throttle() {}

  @Override
  public void backoff() {}

  @Override
  public void halt() {}
}
