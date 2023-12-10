import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class EventPractice3Panel extends JPanel implements KeyListener, MouseListener{
	String str = "Hello Java!";
	JLabel l;
	int x = 50;
	int y = 50;
	boolean backSpace = false;
	
	int x1=0;int y1=0; int x2=0; int y2=0;
	EventPractice3Panel() {
		this.setLayout(null);
		l = new JLabel(str);
		l.setSize(100,30);
		l.setLocation(x,y);
		add(l);

		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		
		addMouseListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.magenta);
		Font f = new Font("궁서체", Font.PLAIN, 30);
		g.setFont(f);
		g.drawString("세종대학교", 50, 30);
		g.setColor(Color.blue);
		g.drawRect(50, 50, 200, 200);
		g.setColor(Color.black);
		g.drawOval(50, 50, 200, 200);
		g.setColor(Color.red);
		g.drawLine(50,50, 250, 250);
		
		int[]x = {300,300,370};
		int[]y = {50,150,100};
		g.fillPolygon(x,y,3);
		
		g.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (!backSpace) {
			str += e.getKeyChar();
		}
		l.setText(str);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		backSpace = false;
		boolean changePos = false;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_BACK_SPACE:
			backSpace = true;
			str = str.substring(0,str.length()-1);
			break;
		case KeyEvent.VK_LEFT: x-=10; changePos=true; break;
		case KeyEvent.VK_RIGHT: x+=10; changePos=true; break;
		case KeyEvent.VK_UP : y-=10; changePos=true; break;
		case KeyEvent.VK_DOWN : y+=10; changePos=true; break;
		default : 
		}
		if (changePos) l.setLocation(x, y);;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		x1=e.getX();
		y1=e.getY();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		for(int i=0;i<100;i++) {
			x2=e.getX() + i;
			y2=e.getY() + i;
			this.repaint();
		}
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

public class EventPractice3 extends JFrame{
	EventPractice3() {
		setSize(400,300);
		setTitle("EventPractice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new EventPractice3Panel());
		setVisible(true);
	}
	public static void main(String[] args) {
		new EventPractice3();
	}

}
