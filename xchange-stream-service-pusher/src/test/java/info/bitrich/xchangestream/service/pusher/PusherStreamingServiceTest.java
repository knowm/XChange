package info.bitrich.xchangestream.service.pusher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.connection.websocket.WebSocketConnection;
import info.bitrich.xchangestream.service.exception.NotConnectedException;
import io.reactivex.observers.TestObserver;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PusherStreamingServiceTest {
  @Mock private Pusher pusher;

  @Mock private WebSocketConnection connection;

  @Mock private Channel channel;

  private PusherStreamingService streamingService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    streamingService = new PusherStreamingService(pusher);
  }

  @Test
  public void testConnect() throws Exception {
    // Capture listener from connect method.
    ArgumentCaptor<ConnectionEventListener> listener =
        ArgumentCaptor.forClass(ConnectionEventListener.class);
    TestObserver<Void> test = streamingService.connect().test();

    // Verify the connect on pusher has been called.
    verify(pusher).connect(listener.capture(), any());

    // Mock connection state change.
    listener
        .getValue()
        .onConnectionStateChange(
            new ConnectionStateChange(ConnectionState.CONNECTING, ConnectionState.CONNECTED));

    // Connect should complete.
    test.assertComplete();
  }

  @Test
  public void testSubscribeChannel() throws Exception {
    when(pusher.getConnection()).thenReturn(connection);
    when(connection.getState()).thenReturn(ConnectionState.CONNECTED);
    when(pusher.subscribe(any())).thenReturn(channel);

    // Subscribe to the channel
    TestObserver<String> test =
        streamingService.subscribeChannel("channelName", "eventName").test();

    // There are no errors and stream is not terminated.
    test.assertNoErrors();
    test.assertNotTerminated();
    verify(pusher).subscribe("channelName");

    ArgumentCaptor<SubscriptionEventListener> subscription =
        ArgumentCaptor.forClass(SubscriptionEventListener.class);
    // Verify binding to the channel.
    verify(channel).bind(anyString(), subscription.capture());

    // Send data to the observable
    Map<String, Object> pusherEventMap = new HashMap<>();
    pusherEventMap.put("data", "dataObject");
    subscription.getValue().onEvent(new PusherEvent(pusherEventMap));
    test.assertValue("dataObject");
  }

  @Test
  public void testSubscribeChannelNotConnected() throws Exception {
    when(pusher.getConnection()).thenReturn(connection);
    when(connection.getState()).thenReturn(ConnectionState.DISCONNECTED);

    // Subscribe to the channel
    TestObserver<String> test =
        streamingService.subscribeChannel("channelName", "eventName").test();

    // There are no errors and stream is not terminated.
    test.assertError(NotConnectedException.class);
    verify(pusher, never()).subscribe("channelName");
  }
}
