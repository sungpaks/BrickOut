package finalExample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class examplePanel9 extends JPanel implements MouseMotionListener, Runnable{
	int centerX, centerY;
	int r = 10;
	int dr = 1;
	examplePanel9() {
		addMouseMotionListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(20+(r*5), 20+(r*5), 20+(r*5)));
		g.fillOval(centerX-r, centerY-r, 2*r, 2*r);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		centerX = e.getX();
		centerY = e.getY();
		repaint();
	}
	@Override
	public void run() {
		while(true) {
			r+=dr;
			if (r >= 40 || r <= 10) dr = -dr;
			repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}

public class FinalExample9 extends JFrame {
	FinalExample9() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		examplePanel9 p = new examplePanel9();
		add(p);
		setVisible(true);
		Thread t = new Thread(p);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			return;
		}
	}
	public static void main(String[] args) {
		new FinalExample9();
	}

}
