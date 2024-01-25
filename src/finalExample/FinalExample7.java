package finalExample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class examplePanel7 extends JPanel implements Runnable, MouseListener, MouseMotionListener{
	int time = 0;
	JLabel l = null;
	double centerXRate = 0.5, centerYRate = 0.5, radiusRate = 0.3;
	boolean isDigitClockMode = false;
	boolean isSelected = false;
	int prevX, prevY;
	examplePanel7() {
		l = new JLabel("00:00:00");
		l.setFont(new Font("Arial", Font.BOLD, 48));
		JButton hb = new JButton("Hour");
		JButton mb = new JButton("Minute");
		JButton sb = new JButton("Second");
		JButton db = new JButton("Digit Clock");
		add(hb);
		add(mb);
		add(sb);
		add(db);
		add(l);
		TextField htf = new TextField();
		TextField mtf = new TextField();
		TextField stf = new TextField();
		htf.addActionListener((e)->{
			int hIn = Integer.parseInt(htf.getText());
			int h = time/3600%24;
			int m = time/60%60;
			int s = time%60;
			h = hIn;
			time = h*3600 + m*60 + s;
			updateTime();
			htf.setText("");
		});
		mtf.addActionListener((e)->{
			int mIn = Integer.parseInt(mtf.getText());
			int h = time/3600%24;
			int m = time/60%60;
			int s = time%60;
			m = mIn;
			time = h*3600 + m*60 + s;
			updateTime();
			mtf.setText("");
		});
		stf.addActionListener((e)->{
			int sIn = Integer.parseInt(stf.getText());
			int h = time/3600%24;
			int m = time/60%60;
			int s = time%60;
			s = sIn;
			time = h*3600 + m*60 + s;
			updateTime();
			stf.setText("");
		});
		add(htf);add(mtf);add(stf);
		hb.addActionListener((e)->{time+=3600;updateTime();repaint();});
		mb.addActionListener((e)->{time+=60;updateTime();repaint();});
		sb.addActionListener((e)->{time++;updateTime();repaint();});
		db.addActionListener((e)->{isDigitClockMode = !isDigitClockMode; repaint();});
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int centerX = (int)(centerXRate * getWidth());
		int centerY = (int)(centerYRate * getHeight());
		int radius = (int)(radiusRate * (getWidth() < getHeight() ? getWidth() : getHeight()));
		g.clearRect(0, 0, getWidth(), getHeight());
		if(isDigitClockMode) {
			((Graphics2D)g).setStroke(new BasicStroke(2));
			for(int i=0;i<72;i++) {
				double dx = Math.cos(Math.toRadians(i*5));
				double dy = Math.sin(Math.toRadians(i*5));
				double rate = 0.9;
				if (i%6==0) rate = 0.8;
				g.drawLine((int)(centerX+rate*radius*dx), (int)(centerY+rate*radius*dy), (int)(centerX+radius*dx), (int)(centerY+radius*dy));
			}
			g.drawOval(centerX-radius, centerY-radius, 2*radius, 2*radius);
			double h = time/3600%24;
			double m = time/60%60;
			double s = time%60;
			double sDegree = 6*s-90;
			double mDegree = 6*(m+s/60)-90;
			double hDegree = 30*(h+m/60)-90;
			g.setColor(Color.red);
			g.drawLine(centerX, centerY, centerX + (int)(radius*0.9*Math.cos(Math.toRadians(sDegree))), centerY + (int)(0.9*radius*Math.sin(Math.toRadians(sDegree))));
			g.setColor(Color.green);
			g.drawLine(centerX, centerY, centerX + (int)(radius*0.8*Math.cos(Math.toRadians(mDegree))), centerY + (int)(0.8*radius*Math.sin(Math.toRadians(mDegree))));
			g.setColor(Color.blue);
			g.drawLine(centerX, centerY, centerX + (int)(radius*0.7*Math.cos(Math.toRadians(hDegree))), centerY + (int)(0.7*radius*Math.sin(Math.toRadians(hDegree))));
		}
		l.setVisible(!isDigitClockMode);
	}
	private void updateTime() {
		int h = time/3600%24;
		int m = time/60%60;
		int s = time%60;
		l.setText(String.format("%02d", h)+":"+String.format("%02d", m)+":"+String.format("%02d", s));
		repaint();
	}
	@Override
	public void run() {
		while(true) {
			time++;
			updateTime();
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if (isSelected) {
			int x = e.getX();
			int y = e.getY();
			double dx = x-prevX;
			double dy = y-prevY;
			centerXRate += dx/getWidth();
			centerYRate += dy/getHeight();
			prevX = x;
			prevY = y;
		}
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		isSelected = false;
		if (isDigitClockMode && e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int centerX = (int)(centerXRate * getWidth());
			int centerY = (int)(centerYRate * getHeight());
			int radius = (int)(radiusRate * (getWidth() < getHeight() ? getWidth() : getHeight()));
			int DistSquare = (x-centerX)*(x-centerX)+(y-centerY)*(y-centerY);
			if(DistSquare <= radius*radius) {
				isSelected = true;
				prevX = x;
				prevY = y;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDragged(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

public class FinalExample7 extends JFrame {
	examplePanel7 p;
	FinalExample7() {
		setSize(500,500);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new examplePanel7();
		add(p);
		Thread t = new Thread(p);
		t.start();
		setVisible(true);
		try {
			t.join();
		} catch (InterruptedException e) {
			
		}
	}
	public static void main(String[] args) {
		new FinalExample7();
	}

}
