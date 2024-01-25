package finalExample;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class examplePanel10 extends JPanel implements MouseMotionListener, MouseWheelListener{
	int x, y;
	int width = 100;
	examplePanel10() {
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		URL url = getClass().getClassLoader().getResource("lena.jpg");
		BufferedImage img;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			return;
		}
		//ImageIcon imageIcon = new ImageIcon(img);
		//g2.clipRect(100, 100, 200, 200);
		g2.drawImage(img,0,0,null);
		g2.drawImage(img, img.getWidth(), 0, img.getWidth()+300, 300, x-width/2,y-width/2,x+width/2,y+width/2,null);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.red);
		g2.drawRect(x-width/2, y-width/2, width, width);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		repaint();
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() > 0) width+=5;
		else width-=5;
		repaint();
	}
}

public class FinalExample10 extends JFrame{
	FinalExample10() {
		setSize(800,600);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new examplePanel10());
		setVisible(true);
	}
	public static void main(String[] args) {
		new FinalExample10();
	}

}
