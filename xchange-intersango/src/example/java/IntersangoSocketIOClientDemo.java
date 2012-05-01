import com.xeiam.xchange.streaming.socketio.IOAcknowledge;
import com.xeiam.xchange.streaming.socketio.IOCallback;
import com.xeiam.xchange.streaming.socketio.SocketIO;
import com.xeiam.xchange.streaming.socketio.SocketIOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;

/**
 * <p>Socket IO client to provide the following to XChange:</p>
 * <ul>
 * <li>Demonstration of connection to Intersango exchange server over socket IO</li>
 * </ul>
 *
 *
 * TODO Talk to Intersango about their SocketIO implementation - it's not handshaking correctly
 */
public class IntersangoSocketIOClientDemo extends JFrame implements IOCallback, ActionListener {

  private final Logger log = LoggerFactory.getLogger(IntersangoSocketIOClientDemo.class);

  private static final long serialVersionUID = -6056260699201258657L;

  private final JTextField uriField;
  private final JButton connect;
  private final JButton close;
  private final JTextArea ta;
  private final JTextField messageField;
  private SocketIO socketClient = null;

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
    IntersangoSocketIOClientDemo client = new IntersangoSocketIOClientDemo("http://intersango.com:1337");

    // TODO HTTPS handshake working for MtGox demo
    // new SocketIO("https://socketio.mtgox.com/mtgox",client);
    // TODO HTTP handshake working for internal exchange demo
    // new SocketIO("http://localhost:8887",client);

  }

  public IntersangoSocketIOClientDemo(String rawUri) {
    super("SocketIO Exchange Client");
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

    messageField = new JTextField();
    messageField.setText("");
    messageField.addActionListener(this);
    c.add(messageField);

    java.awt.Dimension d = new java.awt.Dimension(300, 400);
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

    if (e.getSource() == messageField) {
      if (socketClient != null) {
        socketClient.send(messageField.getText());
        messageField.setText("");
        messageField.requestFocus();
      }

    } else if (e.getSource() == connect) {
      try {

        close.setEnabled(true);
        connect.setEnabled(false);
        uriField.setEditable(false);
        socketClient = new SocketIO(uriField.getText(), this);
      } catch (MalformedURLException e1) {
        e1.printStackTrace();
      }
    } else if (e.getSource() == close) {
      socketClient.disconnect();
    }
  }

  public void onError(Exception ex) {
    ta.append("Exception occurred ...\n" + ex + "\n");
    ta.setCaretPosition(ta.getDocument().getLength());
    ex.printStackTrace();
    connect.setEnabled(true);
    uriField.setEditable(true);
    close.setEnabled(false);
  }

  @Override
  public void onDisconnect() {
    log.debug("Disconnected");
    ta.append("You have been disconnected\n");
    ta.setCaretPosition(ta.getDocument().getLength());
    connect.setEnabled(true);
    uriField.setEditable(true);
    close.setEnabled(false);
  }

  @Override
  public void onConnect() {
    log.debug("Connected");
    ta.append("You are connected\n");
    ta.setCaretPosition(ta.getDocument().getLength());

  }

  @Override
  public void onMessage(String data, IOAcknowledge ack) {
    log.debug("Message: " + data);
    ta.append("Received: " + data + "\n");
    ta.setCaretPosition(ta.getDocument().getLength());
  }

  @Override
  public void onMessage(JSONObject json, IOAcknowledge ack) {
    try {
      JSONObject ticker = (JSONObject) json.get("ticker");
      if (ticker != null) {
        JSONObject last = (JSONObject) ticker.get("last");
        if (last != null) {
          String display = (String) last.get("display");
          ta.append(display.toString() + "\n");
          ta.setCaretPosition(ta.getDocument().getLength());
        }
      }
    } catch (JSONException e) {
      // Ignore (probably an "op")
    }
  }

  @Override
  public void on(String event, IOAcknowledge ack, Object... args) {
    log.debug("Event: " + event);
  }

  @Override
  public void onError(SocketIOException socketIOException) {
    log.debug("Error: " + socketIOException.getMessage());
  }
}
