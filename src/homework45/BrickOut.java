package homework45;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


abstract class MyObject {
	boolean isDead;
	Clip effectClip;
	abstract public void draw(Graphics g);
	public void update(double dt) {}
	public void resolve(MyObject o) {}
	public boolean isIn(MyBall o) {return false;}
	public void setDead() {isDead = true;}
	public void setClip(String path) {
		try {
			effectClip = AudioSystem.getClip();
			URL effectSound1 = getClass().getClassLoader().getResource(path);
			AudioInputStream effectStream1 = AudioSystem.getAudioInputStream(effectSound1);
			effectClip.open(effectStream1);
		} catch (Exception e) {}
	}
	public void playEffectClip() {
		if (effectClip == null) return;
		effectClip.setFramePosition(0);
		Thread t = new Thread(()->effectClip.start());
		t.start();
	}
}
class MyBrick extends MyObject {
	protected double x, y, width, height;
	double alpha;
	Color color;
	MyBrick(double _x, double _y, double w, double h) {
		isDead = false;
		alpha = 1.0;
		x = _x;
		y = _y;
		width = w-10;
		height = h-10;
		color = new Color(128,0,128);
		setClip("chip_kick_4.wav");
	}
	public void setWidth(double w) {width = w;}
	public void setHeight(double h) {height = h;}
	public double getWidth() {return width;}
	public double getHeight() {return height;}
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		GradientPaint gp = new GradientPaint(
				(int)(x+width/2), 
				(int)y,
				new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)(255*alpha)), 
				(int)(x+width/2), 
				(int)(y+height), 
				new Color(128, 128, 128, (int) (255 * alpha))
				);
		g2.setPaint(gp);
		g2.fill(new Rectangle2D.Double(x,y,width,height));
		g2.setStroke(new BasicStroke(5));
		//g.setColor(new Color(0,0,0,(int) (255 * alpha)));
		gp = new GradientPaint(
				(int)(x+width/2), 
				(int)y,
				new Color(color.getRed(),color.getGreen(),color.getBlue(), (int)(255*alpha)), 
				(int)(x+width/2), 
				(int)(y+height),
				new Color(0,0,0, (int) (255 * alpha))
				);
		g2.setPaint(gp);
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	@Override public void update(double dt) {
		if (isDead) alpha -= dt*2;
		if (alpha < 0) alpha = 0;
	}
	@Override public boolean isIn(MyBall o) {
		double xmin = x-o.r;
		double ymin = y-o.r;
		double xmax = x+width+o.r;
		double ymax = y+height+o.r;
		if (o.x>xmin && o.x<xmax && o.y>ymin && o.y<ymax) return true;
		return false;
	}
}
class MySpecialBrick extends MyBrick {
	MySpecialBrick(double _x, double _y, double w, double h) {
		super(_x,_y,w,h);
		color = Color.yellow;
	}
}
class MyBlueBrick extends MyBrick {
	MyBlueBrick(double _x, double _y, double w, double h) {
		super(_x,_y,w,h);
		color = Color.blue;
	}
}
class MyRedBrick extends MyBrick {
	MyRedBrick(double _x, double _y, double w, double h) {
		super(_x,_y,w,h);
		color = Color.red;
	}
}
class MyPlate extends MyBrick {
	double vx = 0, vy = 0; //좌우로만 움직인다
	double curVelocity = 0;
	public static final double MAX_WIDTH = 400;
	public static final double MIN_WIDTH = 50;
	public static final double ORIGIN_WIDTH = 150;
	public static final double ORIGIN_HEIGHT = 30;
	public static final double MAX_VX = 1000;
	public static final double MIN_VX = 100;
	public static final double ORIGIN_VX = 500;
	MyPlate(double _x, double _y, double w, double h) {
		super(_x,_y,w,h);
		color = new Color(150,75,0);
		curVelocity = ORIGIN_VX;
		setClip("chip_fx_9.wav");
	}
	public void setVX(double _vx) {curVelocity = _vx;}
	public double getVX() {return curVelocity;}
	@Override public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
		GradientPaint gp = new GradientPaint(
				(int)(x+width/2), 
				(int)y,
				new Color(248,124,86,(int)(255*alpha)), 
				(int)(x+width/2), 
				(int)(y+height), 
				new Color(0,0,0, (int) (255 * alpha))
				);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.setPaint(gp);
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	@Override public void update(double dt) {
		x += vx*dt;
		y += vy*dt;
	}
	@Override public void resolve(MyObject o) {
		if (o instanceof MyWall) {
			MyWall wall = (MyWall)o;
			if (wall.x <= x && x <= wall.x + wall.width) {
				x = wall.x + wall.width;
			}
			else if (wall.x <= x + width && x + width <= wall.x + wall.width) {
				x = wall.x - width;
			}
		}
	}
	public void setDirectionRight() {vx = curVelocity;}
	public void setDirectionLeft() {vx = -curVelocity;}
	public void setDirectionBoth() {vx = 0;}	
}
class MyWall extends MyBrick {
	MyWall(double _x, double _y, double w, double h) {
		super(_x,_y,w,h);
	}
	@Override public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		GradientPaint gp = new GradientPaint((int)(x+width/2), (int)y, Color.DARK_GRAY, (int)(x+width/2), (int)(y+height), Color.black);
		g2.setPaint(gp);
		g2.fill(new Rectangle2D.Double(x,y,width,height));
		g2.setStroke(new BasicStroke(4));
	}
	@Override public void setDead() {isDead = false;}
}
class MyBall extends MyObject {
	public static double SCALA_VELOCITY = 400;
	double x, y, r;
	double vx, vy;
	double prevX, prevY;
	Color color;
	MyBall(double _x, double _y) {
		x = _x;
		y = _y;
		prevX = x;
		prevY = y;
		r = 5;
		color = Color.white;
		vx = SCALA_VELOCITY*Math.cos(Math.toRadians(45));
		vy = -SCALA_VELOCITY*Math.sin(Math.toRadians(45));
	}
	MyBall(double _x, double _y, double _vx, double _vy) {
		this(_x, _y);
		vx = _vx;
		vy = _vy;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(x-r), (int)(y-r), 2*(int)r, 2*(int)r);
	}
	@Override
	public void update(double dt) {
		prevX = x;
		prevY = y;
		x += vx*dt;
		y += vy*dt;
	}
	@Override
	public void resolve(MyObject o) {
		if (y >= BrickOut.FRAME_HEIGHT || x <= 0 || x >= BrickOut.FRAME_WIDTH) {setDead(); return;}
		if (o instanceof MyPlate) {
			MyPlate p = (MyPlate)o;
			if (p.isIn(this) == false) return;
			p.playEffectClip();
			double scalaSpeed = Math.sqrt(vx*vx + vy*vy);
			double degree = Math.toDegrees(Math.atan2(p.height, (x - (p.x + p.width/2))/2));
			
			vx = scalaSpeed*Math.cos(Math.toRadians(degree));
			vy = -Math.abs(scalaSpeed*Math.sin(Math.toRadians(degree)));
			if (prevY < p.y - r) {y = p.y-r;}
			if (prevX < p.x - r) {x = p.x-r;}
			if (prevX > p.x + p.width + r) {x = p.x+p.width+r;}
			//부딪히면 vy는 무조건 양수, vx는 중앙에서의 거리에 따라.. 
			return;
		}
		if (o instanceof MyBrick) {
			MyBrick b = (MyBrick)o;
			if (b.isIn(this) == false) return;
			b.playEffectClip();
			//벽이 직각임을 가정
			double xmin = b.x-r;
			double ymin = b.y-r;
			double xmax = b.x+b.width+r;
			double ymax = b.y+b.height+r;
			if (prevX < xmin) {vx = -vx;x = xmin;} //경계 왼쪽 
			if (prevX > xmax) {vx = -vx;x = xmax;} //경계 오른쪽
			if (prevY < ymin) {vy = -vy;y = ymin;} //경계 위쪽
			if (prevY > ymax) {vy = -vy;y = ymax;} //경계 아래쪽
			b.setDead();
		}
	}
	public void specialBrickHit(LinkedList<MyBall>newBalls) {
		double scalaSpeed = Math.sqrt(vx*vx + vy*vy);
		double degree = Math.toDegrees(Math.atan2(vy, vx));
		newBalls.add(new MyBall(x, 
								y, 
								scalaSpeed*Math.cos(Math.toRadians(degree-20)), 
								scalaSpeed*Math.sin(Math.toRadians(degree-20))
								));
		newBalls.add(new MyBall(x, 
								y, 
								SCALA_VELOCITY*Math.cos(Math.toRadians(degree+20)), 
								SCALA_VELOCITY*Math.sin(Math.toRadians(degree+20))
								));
	}
	public void setRadius(double _r) {r = _r;}
	public void setVX(double _vx) {vx = _vx;}
	public void setVY(double _vy) {vy = _vy;}
	public void setColor(Color c) {color = c;}
}
interface ItemEffect {
	abstract void applyItem(MyItem item, MyPlate plate);
}
abstract class MyItem extends MyBall implements ItemEffect{
	//double r;
	URL url;
	BufferedImage img;
	int w = 40;
	MyItem(double _x, double _y, String path) {
		super(_x,_y);
		setRadius(10);
		setVX(0);
		setVY(Math.random()*100 + 100);
		setImage(path);
	}
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(img,(int)(x-w), (int)(y-r), (int)(2*w), (int)(2*r), null);
	}
	public void resolve(MyObject o) {
		if (y >= BrickOut.FRAME_HEIGHT) {setDead(); return;}
		if (o instanceof MyPlate) {
			MyPlate p = (MyPlate)o;
			if (p.x <= x+w && x-w <= p.x + p.width && p.y <= y && y <= p.y + p.height) {
				applyItem(this, p);
				setDead();
			}
		}
	}
	public void setImage(String path) {
		url  = getClass().getClassLoader().getResource(path);
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			return;
		}
	}
	public BufferedImage getImage() {return img;}
}
class MyExpandItem extends MyItem implements ItemEffect {
	MyExpandItem(double _x, double _y) {
		super(_x,_y, "Expand.png");
	}
	@Override
	public void applyItem(MyItem item, MyPlate plate) {
		plate.setWidth(plate.getWidth() + 20);
		if (plate.getWidth() > MyPlate.MAX_WIDTH) plate.setWidth(MyPlate.MAX_WIDTH);
	}
}
class MyReduceItem extends MyItem implements ItemEffect {
	MyReduceItem(double _x, double _y) {
		super(_x,_y, "Reduce.png");
	}
	@Override
	public void applyItem(MyItem item, MyPlate plate) {
		plate.setWidth(plate.getWidth() - 40);
		if (plate.getWidth() < MyPlate.MIN_WIDTH) plate.setWidth(MyPlate.MIN_WIDTH);
	}
}
class MySpeedUpItem extends MyItem implements ItemEffect {
	MySpeedUpItem(double _x, double _y) {
		super(_x,_y , "SpeedUp.png");
	}
	@Override
	public void applyItem(MyItem item, MyPlate plate) {
		plate.setVX(plate.getVX() + 50);
		if (plate.getVX() > MyPlate.MAX_VX) plate.setVX(MyPlate.MAX_VX);
	}
}
class MySpeedDownItem extends MyItem implements ItemEffect {
	MySpeedDownItem(double _x, double _y) {
		super(_x,_y,"SpeedDown.png");
	}
	@Override
	public void applyItem(MyItem item, MyPlate plate) {
		plate.setVX(plate.getVX() - 50);
		if (plate.getVX() < MyPlate.MIN_VX) plate.setVX(MyPlate.MIN_VX);
	}
}

abstract class GamePanel extends JPanel implements Runnable, KeyListener{
	private Thread t;
	private static int SCORE;
	private static int HIGH_SCORE = 0;
	protected Clip bgmClip = null;
	GamePanel(String path) {
		t = new Thread(this);
		initBGMClip(path);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GradientPaint gp = new GradientPaint(getWidth()/2, 0, Color.black, getWidth()/2, getHeight(), new Color(112,128,144));
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(gp);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
	synchronized public void interruptThread() {
		if (bgmClip != null && bgmClip.isRunning()) bgmClip.stop();
		notify();
	}
	public Thread getThread() {return t;}
	public void startThread() {t.start();}
	public static int getScore() {return SCORE;}
	public static void setScore(int score) {SCORE = score; HIGH_SCORE = Math.max(HIGH_SCORE, score);}
	public static int getHighScore() {return HIGH_SCORE;}
	public JLabel getShadow(JLabel l) {
		JLabel result = new JLabel(l.getText());
		result.setFont(l.getFont());
		result.setForeground(Color.black);
		result.setLocation(l.getX()+2, l.getY()+2);
		result.setSize(l.getSize());
		result.setHorizontalAlignment(l.getHorizontalAlignment());
		return result;
	}
	public void initBGMClip(String path) {
		try {
			bgmClip = AudioSystem.getClip();
			URL bgmURL = getClass().getClassLoader().getResource(path);
			AudioInputStream bgmStream = AudioSystem.getAudioInputStream(bgmURL);
			bgmClip.open(bgmStream);
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {}
	}
}
class GameOverPanel extends GamePanel {
	Thread flickerThread = null;
	GameOverPanel() {
		super("game_over.wav");
		setLayout(null);
		JLabel gameOverLabel = new JLabel("GAMEOVER");
		gameOverLabel.setFont(new Font("Impact", Font.BOLD, 72));
		gameOverLabel.setForeground(Color.red);
		gameOverLabel.setLocation(0, 200);
		gameOverLabel.setSize(BrickOut.FRAME_WIDTH,100);
		gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel gameScoreLabel = new JLabel("Your Score : " + Integer.toString(getScore()));
		gameScoreLabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		gameScoreLabel.setForeground(Color.LIGHT_GRAY);
		gameScoreLabel.setLocation(0, 450);
		gameScoreLabel.setSize(BrickOut.FRAME_WIDTH,100);
		gameScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel highScoreLabel = new JLabel("High Score : " + Integer.toString(getHighScore()));
		highScoreLabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		highScoreLabel.setForeground(Color.LIGHT_GRAY);
		highScoreLabel.setLocation(0, 480);
		highScoreLabel.setSize(BrickOut.FRAME_WIDTH,100);
		highScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel pressSpaceLabel = new JLabel("PRESS SPACEBAR!!");
		pressSpaceLabel.setFont(new Font("Serif", Font.BOLD, 36));
		pressSpaceLabel.setForeground(Color.red);
		pressSpaceLabel.setLocation(0, 600);
		pressSpaceLabel.setSize(BrickOut.FRAME_WIDTH,100);
		pressSpaceLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel pressSpaceShadow = getShadow(pressSpaceLabel);
		add(highScoreLabel);
		add(getShadow(highScoreLabel));
		add(gameScoreLabel);
		add(getShadow(gameScoreLabel));
		add(gameOverLabel);
		add(pressSpaceLabel);
		add(pressSpaceShadow);
		flickerThread = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(100);
					pressSpaceLabel.setVisible(!(pressSpaceLabel.isVisible()));
					pressSpaceShadow.setVisible(!(pressSpaceShadow.isVisible()));
				} catch (InterruptedException e) {
					return;
				}
			}
		});
		flickerThread.start();
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		startThread();
	}
	@Override
	synchronized public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) interruptThread();
	}
	@Override
	synchronized public void run() {
		try {
			wait();
		} catch (InterruptedException e) {
			return;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	@Override public void interruptThread() {
		super.interruptThread();
		if (flickerThread != null) flickerThread.interrupt();
	}
}

class GameStartPanel extends GamePanel {
	Thread flickerThread = null;
	GameStartPanel() {
		super("HappyLevel.wav");
		setScore(0);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		JLabel pressSpaceLabel = new JLabel("PRESS SPACEBAR TO START");
		pressSpaceLabel.setFont(new Font("Serif", Font.BOLD, 30));
		pressSpaceLabel.setForeground(Color.red);
		pressSpaceLabel.setLocation(0, 600);
		pressSpaceLabel.setSize(BrickOut.FRAME_WIDTH,100);
		pressSpaceLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel gameTitleLabel = new JLabel("BRICK OUT");
		gameTitleLabel.setForeground(Color.white);
		gameTitleLabel.setFont(new Font("Impact", Font.BOLD, 96));
		gameTitleLabel.setLocation(0,300);
		gameTitleLabel.setSize(BrickOut.FRAME_WIDTH,100);
		gameTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel homeworkLabel = new JLabel("Java Programming Homework #5");
		homeworkLabel.setForeground(Color.white);
		homeworkLabel.setFont(new Font("Arial", Font.ITALIC, 24));
		homeworkLabel.setLocation(0,100);
		homeworkLabel.setSize(BrickOut.FRAME_WIDTH,100);
		homeworkLabel.setHorizontalAlignment(JLabel.CENTER);
		JLabel pressSpaceShadow = getShadow(pressSpaceLabel);
		add(gameTitleLabel);
		add(pressSpaceLabel);
		add(homeworkLabel);
		add(pressSpaceShadow);
		flickerThread = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(200);
					pressSpaceLabel.setVisible(!(pressSpaceLabel.isVisible()));
					pressSpaceShadow.setVisible(!(pressSpaceShadow.isVisible()));
				} catch (InterruptedException e) {
					return;
				}
			}
		});
		flickerThread.start();
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		startThread();
	}
	@Override
	synchronized public void run() {
		try {
			wait();
		} catch (InterruptedException e) {
			return;
		}
	}
	@Override synchronized public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) interruptThread();
	}
	@Override public void interruptThread() {
		super.interruptThread();
		if (flickerThread != null) flickerThread.interrupt();
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}

class GamePlayPanel extends GamePanel {
	MyPlate plate = null;
	boolean isLeftPressing, isRightPressing;
	LinkedList<MyBall>balls = null;
	LinkedList<MyBrick>bricks = null;
	double stageLevel = 1.0;
	static final double GAME_ORIGIN_X = 25;
	static final double GAME_ORIGIN_Y = 25;
	static final double BRICK_AREA_WIDTH = BrickOut.FRAME_WIDTH - 2 *GAME_ORIGIN_X;
	static final double BRICK_AREA_HEIGHT = BrickOut.FRAME_HEIGHT/2;
	GamePlayPanel(double w, double h) {
		super("FranticLevel.wav");
		balls = new LinkedList<>();
		bricks = new LinkedList<>();
		bricks.add(new MyWall(0,0,GAME_ORIGIN_X,h));
		bricks.add(new MyWall(w - GAME_ORIGIN_X,0,GAME_ORIGIN_X,h));
		bricks.add(new MyWall(0,0,w,GAME_ORIGIN_Y));
		plate = new MyPlate(GAME_ORIGIN_X + BRICK_AREA_WIDTH/2 - 75, 700, MyPlate.ORIGIN_WIDTH, MyPlate.ORIGIN_HEIGHT);
		setBricks();
		isLeftPressing = false;
		isRightPressing = false;
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
		startThread();
	}
	synchronized private void setBricks() {
		
		for(int i=0;i<stageLevel*3;i++) {
			for(int j=0;j<stageLevel*3;j++) {
				double rand = Math.random();
				if (rand > 0.4) bricks.add(new MyBrick(
						i*BRICK_AREA_WIDTH/(stageLevel*3) + GAME_ORIGIN_X,
						j*BRICK_AREA_HEIGHT/(stageLevel*3) + GAME_ORIGIN_Y,
						BRICK_AREA_WIDTH/(stageLevel*3),
						BRICK_AREA_HEIGHT/(stageLevel*3)
				));
				else if (rand > 0.2) bricks.add(new MySpecialBrick(
						i*BRICK_AREA_WIDTH/(stageLevel*3) + GAME_ORIGIN_X,
						j*BRICK_AREA_HEIGHT/(stageLevel*3) + GAME_ORIGIN_Y,
						BRICK_AREA_WIDTH/(stageLevel*3),
						BRICK_AREA_HEIGHT/(stageLevel*3)
				));
				else if (rand > 0.1) bricks.add(new MyBlueBrick(
						i*BRICK_AREA_WIDTH/(stageLevel*3) + GAME_ORIGIN_X,
						j*BRICK_AREA_HEIGHT/(stageLevel*3) + GAME_ORIGIN_Y,
						BRICK_AREA_WIDTH/(stageLevel*3),
						BRICK_AREA_HEIGHT/(stageLevel*3)
				));
				else bricks.add(new MyRedBrick(
						i*BRICK_AREA_WIDTH/(stageLevel*3) + GAME_ORIGIN_X,
						j*BRICK_AREA_HEIGHT/(stageLevel*3) + GAME_ORIGIN_Y,
						BRICK_AREA_WIDTH/(stageLevel*3),
						BRICK_AREA_HEIGHT/(stageLevel*3)
				));
			}
		}
		balls.add(new MyBall(GAME_ORIGIN_X + BRICK_AREA_WIDTH/2, 650));
	}
	synchronized private void setNewStage() {
		stageLevel++;
		MyBall.SCALA_VELOCITY *= 1.2;
		for(var b : bricks) b.alpha = 0;
		balls.clear();
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		setBricks();
		plate.setWidth((plate.getWidth() + MyPlate.ORIGIN_WIDTH)/2);
		plate.setVX((plate.getVX() + MyPlate.ORIGIN_VX)/2);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		plate.draw(g);
		for(var b : balls) b.draw(g);
		for(var b : bricks) b.draw(g);
	}
	@Override
	public void run() {
		while(true) {
			synchronized(this) {
				if (isLeftPressing || isRightPressing) { //plate를 움직여야 할 때,
					if (isLeftPressing && isRightPressing) plate.setDirectionBoth();
					else if (isLeftPressing) plate.setDirectionLeft();
					else plate.setDirectionRight();
					plate.update(0.016); //update
					plate.resolve(bricks.get(0));
					plate.resolve(bricks.get(1));
					//plate.resolve((int)(GAME_ORIGIN_X + BRICK_AREA_WIDTH),(int)BRICK_AREA_WIDTH); //resolve
				}
			}
			//update
			for(var b : balls) {
				b.update(0.016);
			}
			for(var b : bricks) {
				b.update(0.016);
			}
			//resolve
			synchronized(this) {
				LinkedList<MyBall> newBalls = new LinkedList<>();
				for(var b : balls) {
					for(var brick : bricks) {
						if (brick.isDead) continue;
						b.resolve(brick);
						if (brick.isDead) {
							GamePanel.setScore(GamePanel.getScore() + 10);
							if (brick instanceof MySpecialBrick) b.specialBrickHit(newBalls);
							if (brick instanceof MyBlueBrick) {
								if (Math.random() > 0.5) newBalls.add(new MySpeedUpItem((brick.x + brick.width / 2), (brick.y + brick.height / 2)));
								else newBalls.add(new MyExpandItem((brick.x + brick.width / 2), (brick.y + brick.height / 2)));
							}
							if (brick instanceof MyRedBrick) {
								if(Math.random() > 0.5) newBalls.add(new MySpeedDownItem((brick.x + brick.width / 2), (brick.y + brick.height / 2)));
								else newBalls.add(new MyReduceItem((brick.x + brick.width / 2), (brick.y + brick.height / 2)));
							}
						}
						//노란 블록이 깨진 경우, 새로운 공 생성
						//아이템 블록이 깨진 경우, 아이템 생성
					}
				}
				balls.addAll(newBalls);
				newBalls.clear();
			}
			for(var b : balls) b.resolve(plate); //공과 plate
			synchronized(this) {
				if (balls != null) {
					var it = balls.iterator(); //공 죽었는지 확인
					while(it.hasNext()) {
						var o = it.next();
						if (o.isDead) it.remove();
					}
				}
			}
			synchronized(this) {
				if (bricks != null) {
					var it = bricks.iterator(); //벽돌 죽었는지 확인
					while(it.hasNext()) {
						var o = it.next();
						if (o.isDead && o.alpha <= 0) it.remove();
					}
				}
			}
			synchronized(this) {
				int cnt = 0;
				for(var b : bricks) {
					if (b.isDead) cnt++;
				}
				if (bricks.size() - cnt == 3) setNewStage();
			} //if (bricks.size() == 3) setNewStage(); //벽만 남은 경우, 다음 스테이지
			
			if (isGameOver()) {
				MyBall.SCALA_VELOCITY = 400;
				if (bgmClip != null && bgmClip.isRunning()) bgmClip.stop();
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
				getThread().interrupt();
			} //종료
			//render
			repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			MyBall.SCALA_VELOCITY = 400;
			if (bgmClip != null && bgmClip.isRunning()) bgmClip.stop();
			getThread().interrupt();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) isLeftPressing = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) isRightPressing = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) isLeftPressing = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) isRightPressing = false;
	}
	public void keyTyped(KeyEvent e) {}
	public boolean isGameOver() {
		for(var b : balls) {
			if(b instanceof MyItem == false) return false;
		}
		return true;
	}
}

public class BrickOut extends JFrame implements Runnable{
	GamePanel p;
	int curScene=0;
	static final int FRAME_WIDTH = 800;
	static final int FRAME_HEIGHT = 800;
	BrickOut() {
		setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setResizable(false);
		setTitle("벽돌깨기");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		Thread t = new Thread(this);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {}
	}
	@Override
	public void run() {
		while(true) {
			switch(curScene) {
			case 0 : 
				p = new GameStartPanel(); break;
			case 1 :
				p = new GamePlayPanel(getWidth(),getHeight()); break;
			case 2:
				p = new GameOverPanel(); break;
			default : 
				p = new GameStartPanel(); break;
			}
			add(p);
			p.requestFocus(); //이렇게 해야만 넘어가네..
			setVisible(true);
			try {
				p.getThread().join();
			} catch (InterruptedException e) {return;}
			curScene = (curScene+1)%3;
		}
	}
	public static void main(String[] args) {
		new BrickOut();
	}
}
