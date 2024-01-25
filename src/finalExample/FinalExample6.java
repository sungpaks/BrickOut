package finalExample;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class examPanel6 extends JPanel {
	TextField tf = null;
	TextArea ta = null;
	JLabel l = null;
	examPanel6() {
		this.setLayout(new BorderLayout());
		tf = new TextField("");
		ta = new TextArea("");
		l = new JLabel("type something");
		add(BorderLayout.NORTH, tf);
		add(BorderLayout.CENTER, ta);
		tf.addActionListener((e)->{
			ta.setText(ta.getText() + tf.getText() + "\n");
			tf.setText("");
		});
		add(BorderLayout.SOUTH, l);
	}
}

public class FinalExample6 extends JFrame{
	FinalExample6() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new examPanel6());
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new FinalExample6();
	}

}
