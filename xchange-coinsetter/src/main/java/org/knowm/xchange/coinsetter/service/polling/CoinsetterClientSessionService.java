package org.knowm.xchange.coinsetter.service.polling;

import static org.knowm.xchange.coinsetter.CoinsetterExchange.ACCOUNT_UUID_KEY;
import static org.knowm.xchange.coinsetter.CoinsetterExchange.SESSION_HEARTBEAT_INTERVAL_KEY;
import static org.knowm.xchange.coinsetter.CoinsetterExchange.SESSION_HEARTBEAT_MAX_FAILURE_TIMES_KEY;
import static org.knowm.xchange.coinsetter.CoinsetterExchange.SESSION_IP_ADDRESS_KEY;
import static org.knowm.xchange.coinsetter.CoinsetterExchange.SESSION_KEY;
import static org.knowm.xchange.coinsetter.CoinsetterExchange.SESSION_LOCK_KEY;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.CoinsetterResponse;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccount;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccountList;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;

/**
 * Client session manager.
 */
public class CoinsetterClientSessionService extends CoinsetterClientSessionServiceRaw {

  private final Logger log = LoggerFactory.getLogger(CoinsetterClientSessionService.class);

  private final CoinsetterAccountServiceRaw accountServiceRaw;
  private final ReadWriteLock lock;
  private final long heartbeatInterval;
  private final int heartbeatMaxFailureTimes;

  private volatile HeartbeatThread heartbeatThread;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterClientSessionService(Exchange exchange) {

    super(exchange);

    accountServiceRaw = new CoinsetterAccountServiceRaw(exchange);
    lock = (ReadWriteLock) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(SESSION_LOCK_KEY);
    heartbeatInterval = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(SESSION_HEARTBEAT_INTERVAL_KEY);
    heartbeatMaxFailureTimes = (Integer) exchange.getExchangeSpecification()
        .getExchangeSpecificParametersItem(SESSION_HEARTBEAT_MAX_FAILURE_TIMES_KEY);
  }

  public CoinsetterClientSession getSession() throws IOException {

    CoinsetterClientSession session = readSession();

    if (session == null) {
      writeSession();

      // Read again, as we should have.
      session = readSession();
    }

    return session;
  }

  public void logout() throws IOException {
    if (heartbeatThread != null) {
      heartbeatThread.interrupt();
    }

    logout(readSession().getUuid());
  }

  private CoinsetterClientSession readSession() {

    final CoinsetterClientSession session;
    try {
      lock.readLock().lock();
      session = (CoinsetterClientSession) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(SESSION_KEY);
    } finally {
      lock.readLock().unlock();
    }
    return session;
  }

  private void writeSession() throws IOException {

    try {
      lock.writeLock().lock();
      // Got the write lock, now no one else can got read lock or write lock.

      // Check if it is updated by someone else, before we got the write lock.
      if ((CoinsetterClientSession) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(SESSION_KEY) == null) {
        // It is still null, retrieve one from server.

        final CoinsetterClientSession session = login();
        exchange.getExchangeSpecification().setExchangeSpecificParametersItem(SESSION_KEY, session);

        final String heatbeatThreadName = String.format("Coinsetter-heatbeat-%s(%s)-%s", session.getUsername(), session.getCustomerUuid(),
            session.getUuid());
        heartbeatThread = new HeartbeatThread(heatbeatThreadName, session, heartbeatInterval, heartbeatMaxFailureTimes);
        heartbeatThread.start();

        if (exchange.getExchangeSpecification().getExchangeSpecificParametersItem(ACCOUNT_UUID_KEY) == null) {
          CoinsetterAccountList accountList = accountServiceRaw.list(session.getUuid());
          CoinsetterAccount account = accountList.getAccountList()[0];
          exchange.getExchangeSpecification().setExchangeSpecificParametersItem(ACCOUNT_UUID_KEY, account.getAccountUuid());
        }
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  private CoinsetterClientSession login() throws IOException {

    String ipAddress = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(SESSION_IP_ADDRESS_KEY);
    CoinsetterClientSession session = login(exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getPassword(),
        ipAddress);
    return session;
  }

  private class HeartbeatThread extends Thread {

    private final CoinsetterClientSession session;
    private final long heatbeatInterval;
    private final int heartbeatMaxFailureTimes;

    public HeartbeatThread(String name, CoinsetterClientSession session, long heartbeatInterval, int heartbeatMaxFailureTimes) {

      super(name);
      this.session = session;
      this.heatbeatInterval = heartbeatInterval;
      this.heartbeatMaxFailureTimes = heartbeatMaxFailureTimes;
    }

    @Override
    public void run() {

      CoinsetterResponse heatbeatResponse = null;
      int tryTimes = 0;
      do {
        try {
          Thread.sleep(heatbeatInterval);
          heatbeatResponse = heartbeat(session.getUuid());
          log.trace("heatbeat: {}", heatbeatResponse);
          tryTimes = 0;
        } catch (CoinsetterException e) {
          heatbeatResponse = null;
        } catch (IOException e) {
          log.warn(e.getMessage(), e);
          tryTimes++;
        } catch (InterruptedException e) {
          log.debug("Interrupted: {}", e.getMessage());
          this.interrupt();
        }
      } while (!this.isInterrupted() && tryTimes < heartbeatMaxFailureTimes && heatbeatResponse != null
          && "SUCCESS".equals(heatbeatResponse.getRequestStatus()));

      // Session logged out or session is invalid
      log.debug("Session logged out or session is invalid.");
      try {
        lock.writeLock().lock();
        exchange.getExchangeSpecification().setExchangeSpecificParametersItem(SESSION_KEY, null);
      } finally {
        lock.writeLock().unlock();
      }
    }

  }

}
