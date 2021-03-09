package info.bitrich.xchangestream.service.netty;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
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
  private final Subject<State> stateSubject = BehaviorSubject.create(); // remembers the last state

  public State getState() {
    return state.get();
  }

  void setState(State newState) {
    if (newState != state.getAndSet(newState)) // returns old state value
    {
      this.stateSubject.onNext(newState);
    }
  }

  public Observable<State> stateObservable() {
    return stateSubject.share(); // stateSubject can never emit an error
  }
}
