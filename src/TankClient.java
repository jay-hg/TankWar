import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class TankClient extends Frame {
	public final static int WINDOW_HEIGHT = 600;
	public final static int WINDOW_WIDTH = 800;
	private final static int WINDOW_START_POSITION_X = 100;
	private final static int WINDOW_START_POSITION_Y = 80;
	private final static String TITLE = "TankWar";
	
	private Tank myTank = new Tank(450,550,true,5,this);
	private List<Tank> enemies = new LinkedList<Tank>();
	private List<Missile> missiles = new LinkedList<Missile>();
	private List<Exploder> exploders = new LinkedList<Exploder>();
	private List<Wall> walls = new LinkedList<Wall>();
	private Blood blood = new Blood();
	private Image offScreenImage = null;
	
	public List<Missile> getMissiles() {
		return missiles;
	}

	public void setMissiles(List<Missile> missiles) {
		this.missiles = missiles;
	}

	public List<Exploder> getExploders() {
		return exploders;
	}

	public void setExploders(List<Exploder> exploders) {
		this.exploders = exploders;
	}

	public Tank getMyTank() {
		return myTank;
	}

	public void setMyTank(Tank myTank) {
		this.myTank = myTank;
	}

	public List<Tank> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Tank> enemies) {
		this.enemies = enemies;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}

	@Override
	public void paint(Graphics g) {
		//子弹
		for(ListIterator<Missile> iterator = missiles.listIterator();iterator.hasNext();) {
			Missile missile = iterator.next();
			//子弹打坦克和墙
			for(Wall w:walls) {
				missile.hitWall(w);
			}
			for(ListIterator<Tank> it = enemies.listIterator();it.hasNext();) {
				Tank enemy = it.next();
				missile.hitTank(enemy);
			}
			missile.hitTank(myTank);
			if(missile != null) {
				if(missile.isVisiable()) missile.draw(g);
				else iterator.remove();
			}
		}
		//血块
		blood.draw(g);
		//我方坦克
		if(myTank.getLife() != 0) {
			myTank.eat(blood);
			myTank.draw(g);
		}
		//敌方坦克
		for(ListIterator<Tank> iterator = enemies.listIterator();iterator.hasNext();) {
			Tank enemy = iterator.next();
			if(enemy.getLife() != 0) enemy.draw(g);
			else iterator.remove();
		}
		//爆炸效果
		for(ListIterator<Exploder> iterator = (ListIterator<Exploder>) exploders.iterator();iterator.hasNext();) {
			Exploder exploder = iterator.next();
			if(exploder != null) {
				if(exploder.isLive()) exploder.draw(g);
				else iterator.remove();
			}
		}
		//墙
		for(Wall w:walls) {
			w.draw(g);
		}
	}

	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) offScreenImage = createImage(WINDOW_WIDTH,WINDOW_HEIGHT);
		
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		paint(gOffScreen);
		gOffScreen.setColor(c);
		g.drawImage(offScreenImage, 0 , 0, null);
	}



	public void launchFrame() {
		this.setLocation(WINDOW_START_POSITION_X, WINDOW_START_POSITION_Y);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle(TITLE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				myTank.KeyPress(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				myTank.KeyReleased(e);
			}
			
		});
		this.setResizable(false);
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.init();
		for(int i=0;i<2;i++) {
			Wall w = new Wall(25+i*225,150+i*50,300,50);
			tankClient.walls.add(w);
		}
		tankClient.launchFrame();
	}
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void init() {
		for(int i=0;i<5;i++) {
			Tank t = new Tank(50+i*100,250,Tank.Direction.D,false,1,this);
			this.enemies.add(t);
		}
	}
}
