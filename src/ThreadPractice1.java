import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class FlickeringLabel extends JLabel implements Runnable{
	JLabel FlickeringLabel;
	FlickeringLabel(JLabel fl) {
		FlickeringLabel = fl;
	}
	@Override
	public void run() {
		while (true) {
			try {
				if(FlickeringLabel.getBackground() == Color.green) FlickeringLabel.setBackground(Color.yellow);
				else FlickeringLabel.setBackground(Color.green);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}

class ThreadPracticePanel implements Runnable {
	int time=0;
	JLabel l = new JLabel();
	ThreadPracticePanel(JLabel timeLabel) {
		l = timeLabel;
	}
	@Override
	public void run() {
		while(true) {
			l.setText(Integer.toString(time));
			time++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
}

public class ThreadPractice1 extends JFrame{
	ThreadPractice1() {
		setSize(300,100);
		setTitle("ThreadPractice1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		JLabel timeLabel = new JLabel();
		c.add(timeLabel);
		JLabel flickerLabel = new JLabel("±ôºýÇß³×¿ä");
		flickerLabel.setOpaque(true);
		c.add(flickerLabel);
		
		Thread t1 = new Thread(new ThreadPracticePanel(timeLabel));
		Thread t2 = new Thread(new FlickeringLabel(flickerLabel));
		setVisible(true);
		t1.start();
		t2.start();
		JButton timerKiller = new JButton("Timer Killer");
		timerKiller.addActionListener((e)->t1.interrupt());
		JButton flickerKiller = new JButton("Flicker Killer");
		flickerKiller.addActionListener((e)->t2.interrupt());
		add(timerKiller);
		add(flickerKiller);
	}
	
	public static void main(String[] args) {
		new ThreadPractice1();
	}
}
