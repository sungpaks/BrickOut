import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

class MyInputDialog extends JDialog {
	MoreSwingComponentPanel p = null;
	MyInputDialog(MoreSwingComponentPanel _p, JFrame owner, String title, boolean modal) {
		super (owner, title, modal);
		p = _p;
		setSize(300,200);
		setTitle("Input Dialog");
		this.setLayout(new FlowLayout());
		JTextField tf = new JTextField(10);
		tf.setText(p.label.getText());
		add(tf);
		JButton bt = new JButton("OK");
		bt.addActionListener((e)->{
			p.label.setText(tf.getText());
			setVisible(false);
		});
		add(bt);
		
		setVisible(true);
	}
	MyInputDialog(MoreSwingComponentPanel _p) {
		p = _p;
		setSize(300,200);
		setTitle("Input Dialog");
		this.setLayout(new FlowLayout());
		JTextField tf = new JTextField(10);
		tf.setText(p.label.getText());
		add(tf);
		JButton bt = new JButton("OK");
		bt.addActionListener((e)->p.label.setText(tf.getText()));
		add(bt);
		
		setVisible(true);
	}
}

class MoreSwingComponentPanel extends JPanel {
	JLabel label = null;
	MoreSwingComponentPanel() {
		label = new JLabel("세종대학교");
		add(label);
		
	}
}

public class MoreSwingComponent extends JFrame implements ActionListener{
	MoreSwingComponentPanel p = null;
	MoreSwingComponent() {
		setSize(600,500);
		setTitle("More Components");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new MoreSwingComponentPanel();
		add(p);
		
		JMenuBar mb = new JMenuBar();
		String[][] str = {
				{"File", "Load", "Save", "", "Close"}, 
				{"Edit", "Copy", "Paste", "Cut", "", "EditText"}
		};
		for(int i=0;i<str.length;i++) {
			JMenu m = null;
			for(int j=0;j<str[i].length;j++) {
				if (j == 0) m = new JMenu(str[i][j]);
				else if(str[i][j].equals("") == false) {
					JMenuItem mi = new JMenuItem(str[i][j]);
					mi.addActionListener(this);
					m.add(mi);
				}
				else m.addSeparator();
			}
			mb.add(m);
		}
		this.setJMenuBar(mb);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MoreSwingComponent();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//메뉴 이름 string 반환
		switch(e.getActionCommand()) {
		case "Load":
			JFileChooser choose = new JFileChooser();
			int ret = choose.showOpenDialog(this);
			if (ret == JFileChooser.APPROVE_OPTION) {
				p.label.setText(choose.getSelectedFile().getPath());
			}
			break;
		case "Save": 
			JFileChooser choose2 = new JFileChooser();
			int ret2 = choose2.showSaveDialog(this);
			if (ret2 == JFileChooser.APPROVE_OPTION) {
				p.label.setText(choose2.getSelectedFile().getPath());
			}
			break;
		case "Close": System.exit(-1);
		case "EditText" :
			System.out.println("begin dialog");
			String str = JOptionPane.showInputDialog("Input Dialog");
			if (str != null) p.label.setText(str);
			else {
				//int ret = JOptionPane.showConfirmDialog(this, "Confirm");
				//if (ret == JOptionPane.OK_OPTION) ;
			}
			//MyInputDialog dlg = new MyInputDialog(p, this, "Input Dialog", true);
			System.out.println("end dialog");
			break;
		}
	}
}
