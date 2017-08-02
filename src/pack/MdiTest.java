package pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class MdiTest extends JFrame implements ActionListener {
	JMenuItem mnuNew, mnuExit;
	JInternalFrame childWindow;
	JDesktopPane desktopPane = new JDesktopPane();

	public MdiTest() {
		setTitle("Mid Test");

		JMenuBar mbar = new JMenuBar();
		JMenu mnuFile = new JMenu("파일");
		mnuNew = new JMenuItem("새창");
		mnuExit = new JMenuItem("종료");
		mnuFile.add(mnuNew);
		mnuFile.add(mnuExit);
		mbar.add(mnuFile);
		setJMenuBar(mbar);

		mnuNew.addActionListener(this);
		mnuExit.addActionListener(this);

		getContentPane().setBackground(Color.GRAY);

		setBounds(200, 200, 400, 300);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("새창")) {
			this.getContentPane().add(desktopPane);
			createListen();
			desktopPane.add(childWindow);
			childWindow.setLocation(10, 10);
			childWindow.show();
		} else if (e.getActionCommand().equals("종료")) {
			System.exit(0);

		}

	}

	public void createListen() {
		childWindow = new JInternalFrame("자식창", true, true, true,true);
		childWindow.getContentPane().setLayout(new BorderLayout());
		childWindow.setSize(300, 200);
		childWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		new MdiTest();
	}

}
