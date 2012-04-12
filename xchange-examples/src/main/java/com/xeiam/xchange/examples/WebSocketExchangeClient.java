package com.xeiam.xchange.examples;

import com.xeiam.xchange.streaming.websocket.Draft;
import com.xeiam.xchange.streaming.websocket.HandshakeData;
import com.xeiam.xchange.streaming.websocket.WebSocket;
import com.xeiam.xchange.streaming.websocket.WebSocketClient;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_10;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_17;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_75;
import com.xeiam.xchange.streaming.websocket.drafts.Draft_76;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p>A simple WebSocketExchangeClient implementation for an exchange client</p>
 * <h3>How to use it</h3>
 * <p>Simply run this up through main() after running {@link WebSocketExchangeServer}. Multiple instances add to the fun.</p>
 */
public class WebSocketExchangeClient extends JFrame implements ActionListener {

	private final JTextField uriField;
	private final JButton connect;
	private final JButton close;
	private final JTextArea ta;
	private final JTextField chatField;
	private final JComboBox draft;
	private WebSocketClient cc;

  public static void main( String[] args ) {
    WebSocket.DEBUG = true;
    String location;
    if( args.length != 0 ) {
      location = args[ 0 ];
    } else {
      location = "ws://localhost:8887";
    }
    new WebSocketExchangeClient( location );
  }

  public WebSocketExchangeClient(String defaultlocation) {
		super( "WebSocket Exchange Client" );
		Container c = getContentPane();
		GridLayout layout = new GridLayout();
		layout.setColumns( 1 );
		layout.setRows( 6 );
		c.setLayout( layout );

		Draft[] drafts = { new Draft_10(), new Draft_17(), new Draft_76(), new Draft_75() };
		draft = new JComboBox( drafts );
		c.add( draft );

		uriField = new JTextField();
		uriField.setText( defaultlocation );
		c.add( uriField );

		connect = new JButton( "Connect" );
		connect.addActionListener( this );
		c.add( connect );

		close = new JButton( "Close" );
		close.addActionListener( this );
		close.setEnabled( false );
		c.add( close );

		JScrollPane scroll = new JScrollPane();
		ta = new JTextArea();
		scroll.setViewportView( ta );
		c.add( scroll );

		chatField = new JTextField();
		chatField.setText( "" );
		chatField.addActionListener( this );
		c.add( chatField );

		java.awt.Dimension d = new java.awt.Dimension( 600, 500 );
		setPreferredSize( d );
		setSize( d );

		addWindowListener( new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing( WindowEvent e ) {
				if( cc != null ) {
					cc.close();
				}
				dispose();
			}
		} );

		setLocationRelativeTo( null );
		setVisible( true );
	}

	public void actionPerformed( ActionEvent e ) {

		if( e.getSource() == chatField ) {
			if( cc != null ) {
				try {
					cc.send( chatField.getText() );
					chatField.setText( "" );
					chatField.requestFocus();
				} catch ( InterruptedException ex ) {
					ex.printStackTrace();
				}
			}

		} else if( e.getSource() == connect ) {
			try {
				cc = new WebSocketClient( new URI( uriField.getText() ), (Draft) draft.getSelectedItem() ) {

					public void onMessage( String message ) {
						ta.append( "got: " + message + "\n" );
						ta.setCaretPosition( ta.getDocument().getLength() );
					}

					public void onOpen( HandshakeData handshake ) {
						ta.append( "You are connected to ExchangeServer: " + getURI() + "\n" );
						ta.setCaretPosition( ta.getDocument().getLength() );
					}

					public void onClose( int code, String reason, boolean remote ) {
						ta.append( "You have been disconnected from: " + getURI() + "; Code: " + code + " " + reason + "\n" );
						ta.setCaretPosition( ta.getDocument().getLength() );
						connect.setEnabled( true );
						uriField.setEditable( true );
						draft.setEditable( true );
						close.setEnabled( false );
					}

					public void onError( Exception ex ) {
						ta.append( "Exception occurred ...\n" + ex + "\n" );
						ta.setCaretPosition( ta.getDocument().getLength() );
						ex.printStackTrace();
						connect.setEnabled( true );
						uriField.setEditable( true );
						draft.setEditable( true );
						close.setEnabled( false );
					}
				};

				close.setEnabled( true );
				connect.setEnabled( false );
				uriField.setEditable( false );
				draft.setEditable( false );
				cc.connect();
			} catch ( URISyntaxException ex ) {
				ta.append( uriField.getText() + " is not a valid WebSocket URI\n" );
			}
		} else if( e.getSource() == close ) {
			try {
				cc.close();
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
	}

}