package com.xeiam.xchange.service;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;

import com.xeiam.xchange.streaming.socketio.SocketIOException;

public class RunnableSocketIOEventProducerTest {

  private BlockingQueue<ExchangeEvent> queue;
  private RunnableSocketIOEventProducer testObject;

  @Before
  public void onSetUp() {

    queue = new ArrayBlockingQueue<ExchangeEvent>(5);

    testObject = new RunnableSocketIOEventProducer(queue);

  }

  @Test
  public void testOnConnect() throws Exception {

    testObject.onConnect();

    ExchangeEvent event = queue.take();

    assertEquals(event.getEventType(), ExchangeEventType.CONNECT);

  }

  @Test
  public void testOnDisconnect() throws Exception {

    testObject.onDisconnect();

    ExchangeEvent event = queue.take();

    assertEquals(event.getEventType(), ExchangeEventType.DISCONNECT);

  }

  @Test
  public void testOnMessage() throws Exception {

    testObject.onMessage("test", null);

    ExchangeEvent event = queue.take();

    assertEquals(event.getEventType(), ExchangeEventType.MESSAGE);

  }

  // @Test
  // public void testOnJsonMessage() throws Exception {
  //
  // JSONObject jsonObject = new JSONObject();
  //
  // testObject.onMessage(jsonObject, null);
  //
  // ExchangeEvent event = queue.take();
  //
  // assertEquals(event.getEventType(), ExchangeEventType.JSON_MESSAGE);
  //
  // }

  @Test
  public void testOn() throws Exception {

    testObject.on("event", null);

    ExchangeEvent event = queue.take();

    assertEquals(event.getEventType(), ExchangeEventType.EVENT);

  }

  @Test
  public void testOnError() throws Exception {

    SocketIOException exception = new SocketIOException("Failure");

    testObject.onError(exception);

    ExchangeEvent event = queue.take();

    assertEquals(event.getEventType(), ExchangeEventType.ERROR);

  }

}
