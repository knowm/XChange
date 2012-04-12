import com.xeiam.xchange.service.marketdata.streaming.DefaultStreamingMarketDataService;
import com.xeiam.xchange.service.marketdata.streaming.MarketDataEvent;
import com.xeiam.xchange.service.marketdata.streaming.MarketDataListener;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.util.concurrent.BlockingQueue;

/**
 * <p>Streaming market data client to provide the following to XChange:</p>
 * <ul>
 * <li>Demonstration of connection to exchange server using direct socket and displaying events</li>
 * </ul>
 * <h3>How to use it</h3>
 * <p>Simply run this up through main() and click Connect. The default settings will connect to the Intersango exchange</p>
 */
public class StreamingMarketDataClient extends JFrame implements MarketDataListener, ActionListener {

  private final Logger log = LoggerFactory.getLogger(StreamingMarketDataClient.class);

  private static final long serialVersionUID = -6056260699201258657L;

  private final JTextField hostField;
  private final JTextField portField;
  private final JButton connect;
  private final JButton close;
  private final JTextArea ta;

  /**
   * The main entry point to the demonstration
   *
   * @param args CLI arguments (ignored)
   *
   * @throws java.net.MalformedURLException If something goes wrong
   * @throws InterruptedException           If something goes wrong
   */
  public static void main(String[] args) throws MalformedURLException, InterruptedException {

    // Require a client to respond to events
    new StreamingMarketDataClient("intersango.com", 1337);

  }

  public StreamingMarketDataClient(String host, int port) {
    super("Direct Socket Streaming Exchange Client");
    Container c = getContentPane();
    GridLayout layout = new GridLayout();
    layout.setColumns(1);
    layout.setRows(5);
    c.setLayout(layout);

    hostField = new JTextField();
    hostField.setText(host);
    c.add(hostField);

    portField = new JTextField();
    portField.setText(String.valueOf(port));
    c.add(portField);

    connect = new JButton("Connect");
    connect.addActionListener(this);
    c.add(connect);

    close = new JButton("Close");
    close.addActionListener(this);
    close.setEnabled(false);
    c.add(close);

    JScrollPane scroll = new JScrollPane();
    ta = new JTextArea();
    scroll.setViewportView(ta);
    c.add(scroll);

    Dimension d = new Dimension(600, 500);
    setPreferredSize(d);
    setSize(d);

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });

    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == connect) {
      close.setEnabled(true);
      connect.setEnabled(false);
      hostField.setEditable(false);

      StreamingMarketDataService defaultClient = new DefaultStreamingMarketDataService(hostField.getText(), Integer.valueOf(portField.getText()));
    }
  }

  // TODO Fix this
  public void onUpdate(MarketDataEvent event) {
    ta.append(event.getRawData().toString() + "\n");
    ta.setCaretPosition(ta.getDocument().getLength());

  }

  @Override
  public BlockingQueue<MarketDataEvent> getMarketDataEventQueue() {
    // TODO Implement this
    return null;
  }

  @Override
  public void setMarketDataEventQueue(BlockingQueue<MarketDataEvent> marketDataEvents) {
    // TODO Implement this
  }
}
