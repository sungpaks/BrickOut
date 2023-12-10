import java.awt.Color;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class SoundPlayerPanel extends JPanel {
	Clip clip = null;
	SoundPlayerPanel() {
		try {
			clip = AudioSystem.getClip();
			//1. 클립 만들기
			URL url = getClass().getResource("loop.wav");
			AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			clip.open(audio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JButton play = new JButton("play");
		play.addActionListener((e)-> {
			clip.setFramePosition(0);
			clip.start();
		});
		JButton stop = new JButton("stop");
		stop.addActionListener((e)-> {
			clip.stop();
		});
		clip.addLineListener((e)->{
			if (e.getType() == LineEvent.Type.START) this.setBackground(Color.green);
			else if (e.getType() == LineEvent.Type.STOP) this.setBackground(Color.yellow); 
		});
		
		add(play);
		add(stop);
	}
}

public class SoundPlayer extends JFrame{
	SoundPlayer() {
		setSize(300,100);
		setTitle("sound player");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new SoundPlayerPanel());
		setVisible(true);
	}
	public static void main(String[] args) {
		new SoundPlayer();
	}

}
