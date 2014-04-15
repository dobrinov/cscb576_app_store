package client.administrator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.IAppStoreServer;
import server.ServerOperationResult;
import server.ServerState;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Application extends JFrame {

	private ScheduledExecutorService serverStateChecker;

	private JPanel contentPane;
	private JButton btnStart;
	private JButton btnPause;
	private JButton btnStop;
	private JLabel lblServerState;

	private Registry registry;
	private ServerState serverState;
	private IAppStoreServer appStoreServer;

	public Application() {
		initializeRmi();
		initializeUi();
		initializeServerStateChecker();
	}

	public void setServerState(ServerState serverState) {
		String newLblServerState = "Unknown";
		
		switch (serverState) {
		case STARTED:
			btnStart.setEnabled(false);
			btnStop.setEnabled(true);
			btnPause.setEnabled(true);
			newLblServerState = "The server is running.";
			break;
		case STOPPED:
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			btnPause.setEnabled(false);
			newLblServerState = "The server is stopped.";
			break;
		case PAUSED:
			btnStart.setEnabled(true);
			btnStop.setEnabled(true);
			btnPause.setEnabled(false);
			newLblServerState = "The server is paused.";
			break;
		case UNKNOWN:
			btnStart.setEnabled(false);
			btnStop.setEnabled(false);
			btnPause.setEnabled(false);
			newLblServerState = "Cannot obtain server state.";
			break;
		default:
			break;
		}

		 this.lblServerState.setText(newLblServerState);
	}

	private void initializeServerStateChecker() {
		serverStateChecker = Executors.newSingleThreadScheduledExecutor();
		serverStateChecker.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					serverState = appStoreServer.status();
					setServerState(serverState);
				} catch (Exception e) {
					setServerState(ServerState.UNKNOWN);
					e.printStackTrace();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

	private void initializeRmi() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String name = "AppStore";
			registry = LocateRegistry.getRegistry("localhost");
			appStoreServer = (IAppStoreServer) registry.lookup(name);
		} catch (Exception e) {
			System.err.println("AppServer exception:");
			e.printStackTrace();
		}
	}

	private void initializeUi() {
		setTitle("AppStore Administration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startServer();
			}
		});
		btnStart.setToolTipText("Start the server");
		btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pauseServer();
			}
		});
		btnPause.setToolTipText("Pause the server");
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}
		});
		btnStop.setToolTipText("Stop the server");
		lblServerState = new JLabel("Loading server state ...");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				btnStart,
																				GroupLayout.PREFERRED_SIZE,
																				115,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				btnPause,
																				GroupLayout.PREFERRED_SIZE,
																				115,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				btnStop,
																				GroupLayout.PREFERRED_SIZE,
																				115,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lblServerState))
										.addContainerGap(11, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				btnStart,
																				GroupLayout.PREFERRED_SIZE,
																				117,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				btnPause,
																				GroupLayout.PREFERRED_SIZE,
																				117,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																btnStop,
																GroupLayout.PREFERRED_SIZE,
																117,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(lblServerState)
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
	
	public void startServer(){
		try {
			ServerOperationResult response = appStoreServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopServer(){
		try {
			ServerOperationResult response = appStoreServer.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pauseServer(){
		try {
			ServerOperationResult response = appStoreServer.pause();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
