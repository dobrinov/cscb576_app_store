package client.customer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.IAppStoreServer;
import server.ServerState;

public class Application extends JFrame {
	
	private ScheduledExecutorService serverStateChecker;

	private JPanel contentPane;
	private JScrollPane appTableScrollPane;
	private JTable appTable;
	private DefaultTableModel appTableModel;
	String APP_TABLE_COLUMN_TITLES[] = {"Name", "Genre", "Description"};
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
			getApplicationList();
			newLblServerState = "The server is running.";
			break;
		case STOPPED:
			newLblServerState = "The server is stopped.";
			break;
		case PAUSED:
			newLblServerState = "The server is paused.";
			break;
		case UNKNOWN:
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
		setResizable(false);
		setTitle("CSCBB576 Application store");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		appTableModel = new DefaultTableModel(APP_TABLE_COLUMN_TITLES,0);
		appTable = new JTable(appTableModel);
		appTable.setFillsViewportHeight(true);
		appTable.setColumnSelectionAllowed(true);
		appTable.setSurrendersFocusOnKeystroke(true);
		appTable.setEnabled(false);

		sl_contentPane.putConstraint(SpringLayout.NORTH, appTable, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, appTable, -10, SpringLayout.EAST, contentPane);
		appTableScrollPane = new JScrollPane(appTable);
		sl_contentPane.putConstraint(SpringLayout.NORTH, appTableScrollPane, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, appTableScrollPane, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, appTableScrollPane, 0, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, appTableScrollPane, 0, SpringLayout.EAST, contentPane);
		contentPane.add(appTableScrollPane);
		
		lblServerState = new JLabel("Loading server state ...");
		sl_contentPane.putConstraint(SpringLayout.WEST, appTable, 0, SpringLayout.WEST, lblServerState);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, appTable, -6, SpringLayout.NORTH, lblServerState);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblServerState, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblServerState, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(lblServerState);
	}

	private void getApplicationList(){
		try {
			String applications[][] = appStoreServer.getApplicationList();

			appTableModel.setRowCount(applications.length);

			for(int i=0; i<applications.length; i++){
				for(int j=0; j<applications[0].length; j++){
					appTable.setValueAt(applications[i][j],i,j);
				}
			}

			appTableModel.fireTableDataChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
