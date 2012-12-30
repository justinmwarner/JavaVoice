import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class StartupScreen {

	private JFrame frame;
	private JTextField textField;
	private JLabel lblEmail;
	private JTextField tfEmail;
	private JLabel lblPass;
	private JTextField tfPass;
	private JCheckBox cbSP;
	private JCheckBox cbAL;
	private JButton btnQuit;
	private JButton btnLogin;
	public boolean isLogin;

	/**
	 * Create the application.
	 */
	public StartupScreen() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(4, 3, 20, 40));

		lblEmail = new JLabel("Email");
		frame.getContentPane().add(lblEmail);

		tfEmail = new JTextField();
		frame.getContentPane().add(tfEmail);
		tfEmail.setColumns(10);

		lblPass = new JLabel("Password");
		frame.getContentPane().add(lblPass);

		tfPass = new JTextField();
		frame.getContentPane().add(tfPass);
		tfPass.setColumns(10);

		cbSP = new JCheckBox("Save Password");
		frame.getContentPane().add(cbSP);

		cbAL = new JCheckBox("Auto-Login");
		frame.getContentPane().add(cbAL);

		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// Exit program.
				System.exit(0);
			}
		});
		frame.getContentPane().add(btnQuit);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Save settings from checkboxes and login.
				if ((tfEmail.getText().indexOf("@") < tfEmail.getText()
						.indexOf(".com")) || tfPass.getText().equals("")) {
					isLogin = true;
					frame.dispose();
				} else {
					Tooltip tt = new Tooltip(
							"Invalid info!",
							"You need to enter a valid password and a password.",
							10000);
				}
			}
		});
		frame.getContentPane().add(btnLogin);

	}

	public User getInfo() {
		while (!isLogin) {
			// Wait for login push.
			System.out.println("Waiting....");
		}
		return new User(tfEmail.getText(), tfPass.getText());
	}

}
