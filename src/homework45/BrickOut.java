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
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


abstract class MyObject {
	boolean isDead;
	abstract public void draw(Graphics g);
	public void update(double dt) {}
	public void resolve(MyObject o) {}
	public void resolve(int w, int h) {}
	public boolean isIn(MyBall o) {return false;}
	public void setDead() {isDead = true;}
}
class MyBrick extends MyObject {
	double x, y, width, height;
	double alpha;
	Color color;
	MyBrick(double _x, double _y, double w, double h) {
		isDead = false;
		alpha = 1.0;
		x = _x;
		y = _y;
		width = w-10;
		height = h-10;
		color = new Color(128,0,128);;
	}
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
class MyPlate extends MyBrick {
	double vx = 0, vy = 0; //좌우로만 움직인다
	MyPlate(double _x, double _y, double w, double h) {
		super(_x,_y,w,h);
		color = new Color(150,75,0);
	}
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
	@Override public void resolve(int w, int h) {
		if (x+width >= w) x = w - width;
		if (x <= 0) x = 0;
	}
	public void setDirectionRight() {vx = 500;}
	public void setDirectionLeft() {vx = -500;}
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
	public static double SCALA_VELOCITY = 300;
	double x, y, r;
	double vx, vy;
	double prevX, prevY;
	MyBall(double _x, double _y) {
		x = _x;
		y = _y;
		prevX = x;
		prevY = y;
		r = 5;
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
		g.setColor(Color.white);
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
		if (y >= BrickOut.FRAME_HEIGHT) {setDead(); return;}
		if (o instanceof MyPlate) {
			MyPlate p = (MyPlate)o;
			if (p.isIn(this) == false) return;
			double scalaSpeed = Math.sqrt(vx*vx + vy*vy);
			double degree = Math.toDegrees(Math.atan2(p.height, (x - (p.x + p.width/2))/2));
			
			vx = scalaSpeed*Math.cos(Math.toRadians(degree));
			vy = -Math.abs(scalaSpeed*Math.sin(Math.toRadians(degree)));
			//vy = -SCALA_VELOCITY; 
			//vx = (x - (p.x + p.width/2)) / (p.width / 2) * SCALA_VELOCITY;
			if (prevY < p.y - r) {y = p.y-r;}
			if (prevX < p.x - r) {x = p.x-r;}
			if (prevX > p.x + p.width + r) {x = p.x+p.width+r;}
			//부딪히면 vy는 무조건 양수, vx는 중앙에서의 거리에 따라.. 
			return;
		}
		if (o instanceof MyBrick) {
			MyBrick b = (MyBrick)o;
			if (b.isIn(this) == false) return;
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
								scalaSpeed*Math.cos(Math.toRadians(degree-30)), 
								scalaSpeed*Math.sin(Math.toRadians(degree-30))
								));
		newBalls.add(new MyBall(x, 
								y, 
								SCALA_VELOCITY*Math.cos(Math.toRadians(degree+30)), 
								SCALA_VELOCITY*Math.sin(Math.toRadians(degree+30))
								));
	}
}

abstract class GamePanel extends JPanel implements Runnable, KeyListener{
	private Thread t;
	private static int SCORE;
	private static int HIGH_SCORE = 0;
	GamePanel() {
		t = new Thread(this);
		//t.start();
		System.out.println("started");
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		GradientPaint gp = new GradientPaint(getWidth()/2, 0, Color.black, getWidth()/2, getHeight(), new Color(112,128,144));
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(gp);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
	synchronized public void interruptThread() {notify();}
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
}
class GameOverPanel extends GamePanel {
	Thread flickerThread = null;
	GameOverPanel() {
		super();
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
		super();
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
		super();
		balls = new LinkedList<>();
		bricks = new LinkedList<>();
		bricks.add(new MyWall(0,0,GAME_ORIGIN_X,h));
		bricks.add(new MyWall(w - GAME_ORIGIN_X,0,GAME_ORIGIN_X,h));
		bricks.add(new MyWall(0,0,w,GAME_ORIGIN_Y));
		plate = new MyPlate(GAME_ORIGIN_X + BRICK_AREA_WIDTH/2 - 75, 700, 150, 30);
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
				if (Math.random() > 0.3) bricks.add(new MyBrick(
						i*BRICK_AREA_WIDTH/(stageLevel*3) + GAME_ORIGIN_X,
						j*BRICK_AREA_HEIGHT/(stageLevel*3) + GAME_ORIGIN_Y,
						BRICK_AREA_WIDTH/(stageLevel*3),
						BRICK_AREA_HEIGHT/(stageLevel*3)
				));
				else bricks.add(new MySpecialBrick(
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
		MyBall.SCALA_VELOCITY *= 1.1;
		System.out.println("new Stage");
		balls.clear();
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		setBricks();
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
					plate.resolve((int)(GAME_ORIGIN_X + BRICK_AREA_WIDTH),(int)BRICK_AREA_WIDTH); //resolve
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
						}
						//노란 블록이 깨진 경우, 새로운 공 생성
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
			
			if (balls.isEmpty()) {
				MyBall.SCALA_VELOCITY = 300;
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
		if(e.getKeyCode()==KeyEvent.VK_SPACE) getThread().interrupt();
		if(e.getKeyCode() == KeyEvent.VK_LEFT) isLeftPressing = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) isRightPressing = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) isLeftPressing = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) isRightPressing = false;
	}
	public void keyTyped(KeyEvent e) {}
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
		} catch (InterruptedException e) {
			System.out.println("interrupted in Main");
		}
	}
	@Override
	public void run() {
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
		} catch (InterruptedException e) {
			System.out.println("interrupted in GameThread");
		}
		curScene = (curScene+1)%3;
		run();
	}
	public static void main(String[] args) {
		new BrickOut();
	}
}
