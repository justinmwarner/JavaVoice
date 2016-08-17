import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Tooltip {
	Tooltip(String msg, final String from, final int to) {
		String message = msg;
		String header = "New message from " + from + "!";
		final JFrame frame = new JFrame();
		frame.setSize(300, 125);
		frame.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel headingLabel = new JLabel(header);
		// headingLabel.setIcon(headingIcon); // --- use image icon you want to
		// be as heading image.
		headingLabel.setOpaque(false);
		frame.add(headingLabel, constraints);
		constraints.gridx++;
		constraints.weightx = 0f;
		constraints.weighty = 0f;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		JButton closesButton = new JButton(new AbstractAction("x") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				frame.dispose();


			}
		});

		closesButton.setMargin(new Insets(1, 4, 1, 4));
		closesButton.setFocusable(false);
		frame.add(closesButton, constraints);
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.weightx = 1.0f;
		constraints.weighty = 1.0f;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.fill = GridBagConstraints.BOTH;
		JLabel messageLabel = new JLabel("<HtMl>" + message);
		frame.add(messageLabel, constraints);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();// size
																		// of
																		// the
																		// screen
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(
				frame.getGraphicsConfiguration());// height of the task bar
		frame.setLocation(scrSize.width - frame.getWidth(), scrSize.height
				- toolHeight.bottom - frame.getHeight());
		// frame.setOpacity((float) .5);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(to); // time after which pop up will be
										// disappeared.
					frame.dispose();
					// If this isn't closed in time, send a OS version
					// notification to appear in the notification menu.
					try {
						Runtime.getRuntime().exec(
								"notify-send -t 1 \"You got a message from "
										+ from + "!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

	}
}