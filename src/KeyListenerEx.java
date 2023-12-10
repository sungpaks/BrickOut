import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class MyMyPanel extends JPanel {
	MyMyPanel() {
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("Java~", 30,30);
		g.drawString("is Fun", 50, 50);
	}
}

public class KeyListenerEx extends JFrame{
	KeyListenerEx () {
		setSize(400,400);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new MyMyPanel());
		setVisible(true);
	}
	public static void main(String[] args) {
		new KeyListenerEx();
	}

}
