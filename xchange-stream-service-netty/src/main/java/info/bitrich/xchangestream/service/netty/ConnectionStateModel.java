package info.bitrich.xchangestream.service.netty;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * The simplest Connection state model possible (maybe someone wants to add authenticated,
 * connecting, etc...)
 */
public final class ConnectionStateModel {

  public enum State {
    closed,
    open
  }

  private volatile State state = State.closed;  // start with a closed state
  private final Subject<State> stateSubject = BehaviorSubject.create();  // remembers the last state

  public State getState() {
    return state;
  }

  void setState(State newState) {
    if (this.state != newState) {  // reason why state is volatile
      this.state = newState;  // no need to synchronize as this assigment is the switch
      this.stateSubject.onNext(newState); // newState is pm the stack (so need to sync)
    }
  }

  public Observable<State> stateObservable() {
    return stateSubject.share();  // stateSubject can never emit an error
  }
}
