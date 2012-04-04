import com.xeiam.xchange.service.marketdata.streaming.MarketDataEvent;
import com.xeiam.xchange.service.marketdata.streaming.MarketDataListener;
import com.xeiam.xchange.service.marketdata.streaming.SocketStreamingMarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;

/**
 * <p>Streaming market data client to provide the following to XChange:</p>
 * <ul>
 * <li>Demonstration of connection to exchange server using direct socket and displaying events</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class StreamingMarketDataClient extends JFrame implements MarketDataListener, ActionListener {

  private final Logger log = LoggerFactory.getLogger(StreamingMarketDataClient.class);

  private static final long serialVersionUID = -6056260699201258657L;

  private final JTextField uriField;
  private final JButton connect;
  private final JButton close;
  private final JTextArea ta;
  private SocketStreamingMarketDataService socketClient = null;

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
    new StreamingMarketDataClient("http://intersango.com:1337");

  }

  public StreamingMarketDataClient(String rawUri) {
    super("Direct Socket Streaming Exchange Client");
    Container c = getContentPane();
    GridLayout layout = new GridLayout();
    layout.setColumns(1);
    layout.setRows(5);
    c.setLayout(layout);

    uriField = new JTextField();
    uriField.setText(rawUri);
    c.add(uriField);

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

    Dimension d = new Dimension(300, 400);
    setPreferredSize(d);
    setSize(d);

    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (socketClient != null) {
          socketClient.disconnect();
        }
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
      uriField.setEditable(false);

      socketClient = new SocketStreamingMarketDataService(uriField.getText(), 1337);
    } else if (e.getSource() == close) {
      socketClient.disconnect();
    }
  }

  @Override
  public void onUpdate(MarketDataEvent event) {
    ta.append(event.getRawData().toString() + "\n");
    ta.setCaretPosition(ta.getDocument().getLength());

  }
}
