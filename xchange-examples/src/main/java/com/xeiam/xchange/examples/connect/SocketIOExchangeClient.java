/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.connect;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.xeiam.xchange.streaming.socketio.IOAcknowledge;
import com.xeiam.xchange.streaming.socketio.IOCallback;
import com.xeiam.xchange.streaming.socketio.SocketIO;
import com.xeiam.xchange.streaming.socketio.SocketIOException;

/**
 * <p>
 * Socket IO client to provide the following to XChange:
 * </p>
 * <ul>
 * <li>Demonstration of connection to exchange server over SocketIO</li>
 * </ul>
 * <h3>How to use it</h3>
 * <p>
 * Simply run this up through main() and click Connect. The default settings will connect to the MtGox exchange
 * </p>
 */
public class SocketIOExchangeClient extends JFrame implements IOCallback, ActionListener {

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
   * @throws MalformedURLException If something goes wrong
   * @throws InterruptedException If something goes wrong
   */
  public static void main(String[] args) throws MalformedURLException, InterruptedException {

    // Require a client to respond to events
    new SocketIOExchangeClient("http://socketio.mtgox.com/mtgox");

    // HTTPS handshake working for MtGox demo
    // new SocketIO("https://socketio.mtgox.com/mtgox",client);
    // HTTP handshake working for internal exchange demo
    // new SocketIO("http://localhost:8887",client);

  }

  /**
   * Constructor
   * 
   * @param rawUri
   */
  public SocketIOExchangeClient(String rawUri) {

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

    java.awt.Dimension d = new java.awt.Dimension(600, 500);
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

  @Override
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

        // The client creates a SocketIO connection to the host registering itself for the callbacks
        socketClient = new SocketIO(uriField.getText(), this);

      } catch (MalformedURLException e1) {
        e1.printStackTrace();
      }
    } else if (e.getSource() == close) {
      socketClient.disconnect();
    }
  }

  // public void onError(Exception ex) {
  //
  // ta.append("Exception occurred ...\n" + ex + "\n");
  // ta.setCaretPosition(ta.getDocument().getLength());
  // ex.printStackTrace();
  // connect.setEnabled(true);
  // uriField.setEditable(true);
  // close.setEnabled(false);
  // }

  @Override
  public void onDisconnect() {

    System.out.println("Disconnected");
    ta.append("You have been disconnected\n");
    ta.setCaretPosition(ta.getDocument().getLength());
    connect.setEnabled(true);
    uriField.setEditable(true);
    close.setEnabled(false);
  }

  @Override
  public void onConnect() {

    System.out.println("Connected");
    ta.append("You are connected\n");
    ta.setCaretPosition(ta.getDocument().getLength());

  }

  @Override
  public void onMessage(String data, IOAcknowledge ack) {

    System.out.println("Message: " + data);
    ta.append("Received: " + data + "\n");
    ta.setCaretPosition(ta.getDocument().getLength());
  }

  // @Override
  // public void onJSONMessage(JSONObject json, IOAcknowledge ack) {
  //
  // try {
  // JSONObject ticker = (JSONObject) json.get("ticker");
  // if (ticker != null) {
  // JSONObject last = (JSONObject) ticker.get("last");
  // if (last != null) {
  // String display = (String) last.get("display");
  // ta.append(display.toString() + "\n");
  // ta.setCaretPosition(ta.getDocument().getLength());
  // }
  // }
  // } catch (JSONException e) {
  // // Ignore (probably an "op")
  // }
  // }

  @Override
  public void onJSONMessage(String jsonString, IOAcknowledge ack) {

    // TODO Auto-generated method stub

  }

  @Override
  public void on(String event, IOAcknowledge ack, Object... args) {

    System.out.println("Event: " + event);
  }

  @Override
  public void onError(SocketIOException socketIOException) {

    System.out.println("Error: " + socketIOException.getMessage());
  }

}
