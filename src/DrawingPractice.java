import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


class TempPanel extends JPanel {
	TempPanel() {
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		URL url = getClass().getResource("flower.jpg");
		BufferedImage image;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(url);
		//g2.setClip(50,50,250,250);
		g2.clipRect(100, 100, 300, 300);
		g2.drawImage(imageIcon.getImage(), 0,0,getWidth(),getHeight(), null);
	}
}

public class DrawingPractice extends JFrame {
	DrawingPractice() {
		setSize(600,400);
		setTitle("");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new TempPanel());
		setVisible(true);
	}
	public static void main(String[] args) {
		new DrawingPractice();
	}
}
