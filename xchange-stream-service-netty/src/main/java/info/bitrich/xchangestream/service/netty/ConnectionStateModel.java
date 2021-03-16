package info.bitrich.xchangestream.service.netty;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.FlowableProcessor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The simplest Connection state model possible (maybe someone wants to add authenticated,
 * connecting, etc...)
 */
public final class ConnectionStateModel {

  public enum State {
    CLOSED,
    OPEN
  }

  private final AtomicReference<State> state =
      new AtomicReference<>(State.CLOSED); // start with a closed state
  private final FlowableProcessor<State> stateSubject = BehaviorProcessor.create(); // remembers the last state

  public State getState() {
    return state.get();
  }

  void setState(State newState) {
    if (newState != state.getAndSet(newState)) // returns old state value
    {
      this.stateSubject.onNext(newState);
    }
  }

  public Flowable<State> stateFlowable() {
    return stateSubject.publish(1).refCount(); // stateSubject can never emit an error
  }
}
