import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Tank {
	private final static int TANK_WIDTH = 60;
	private final static int TANK_HEIGHT = 60;
	private final static int TANK_SPEED_X = 20;
	private final static int TANK_SPEED_Y = 20;
	private boolean bL, bD, bR, bU;
	private boolean moving = true;
	enum Direction{U,RU,R,RD,D,LD,L,LU};
	public static Random random = new Random();
	private int step = 0;
	
	private int x;
	private int y;
	private String resourseID;
	private Direction direction;
	private boolean good;
	private int life;
	TankClient tc;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public Tank(int x, int y, boolean good, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.good = good;
		this.tc = tc;
	}

	public Tank(int x, int y, boolean good, int life, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.good = good;
		this.life = life;
		this.tc = tc;
	}

	public Tank(int x, int y, Direction direction, boolean good, int life,
			TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.good = good;
		this.life = life;
		this.tc = tc;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getResourseID() {
		return resourseID;
	}
	public void setResourseID(String resourseID) {
		this.resourseID = resourseID;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public void draw(Graphics g) {
		move();
		Color c = g.getColor();
		if(this.good){
			g.setColor(Color.white);
			g.drawRect(x, y-20, this.TANK_WIDTH, 10);
			g.setColor(Color.RED);
			g.fillRect(x, y-19, TANK_WIDTH*this.life/5, 8);
		}
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		g.setColor(c);
	}
	
	public void KeyPress(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
	}
	
	public void KeyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_CONTROL:
			attack();
			break;
		case KeyEvent.VK_A:
			superAttack();
			break;
		case KeyEvent.VK_F2:
			reLive();
			break;
		case KeyEvent.VK_F3:
			tc.init();
			break;
		}
	}
	
	void move() {
		locateDirection();
		if(!moving) return;
		int oldX = x;
		int oldY = y;
		switch(direction) {
		case L:
			x -= TANK_SPEED_X;
			break;
		case LD:
			x -= TANK_SPEED_X;
			y += TANK_SPEED_Y;
			break;
		case D:
			y += TANK_SPEED_Y;
			break;
		case RD:
			x += TANK_SPEED_X;
			y += TANK_SPEED_Y;
			break;
		case R:
			x += TANK_SPEED_X;
			break;
		case RU:
			x += TANK_SPEED_X;
			y -= TANK_SPEED_Y;
			break;
		case U:
			y -= TANK_SPEED_Y;
			break;
		case LU:
			x -= TANK_SPEED_X;
			y -= TANK_SPEED_Y;
			break;
		}
		
		if(x<0) x = 0;
		else if(x>TankClient.WINDOW_WIDTH-this.TANK_WIDTH) x = TankClient.WINDOW_WIDTH-this.TANK_WIDTH;
		if(y<0) y = 0;
		else if(y>TankClient.WINDOW_HEIGHT-this.TANK_HEIGHT) y = TankClient.WINDOW_HEIGHT-this.TANK_HEIGHT;
		//避免撞墙
		for(Wall w:tc.getWalls()) {
			if(this.getRect().intersects(w.getRect())) stay(oldX,oldY);
		}
		//避免坦克相撞
		for(Tank t:tc.getEnemies()) {
			if(this == t) continue;
			if(this.getRect().intersects(t.getRect())) stay(oldX,oldY);
		}
		if(this != tc.getMyTank() && this.getRect().intersects(tc.getMyTank().getRect())) stay(oldX,oldY); 
	}
	
	void stay(int oldX, int oldY) {
		x = oldX;
		y = oldY;
	}
	
	void locateDirection() {
		moving = true;
		if(good) {
			if(bL && !bD && !bR && !bU) direction = Direction.L;
			else if(bL && bD && !bR && !bU) direction = Direction.LD;
			else if(!bL && bD && !bR && !bU) direction = Direction.D;
			else if(!bL && bD && bR && !bU) direction = Direction.RD;
			else if(!bL && !bD && bR && !bU) direction = Direction.R;
			else if(!bL && !bD && bR && bU) direction = Direction.RU;
			else if(!bL && !bD && !bR && bU) direction = Direction.U;
			else if(bL && !bD && !bR && bU) direction = Direction.LU;
			else if(!bL && !bD && !bR && !bU) moving = false;
		}
		else {
			if(step == 0) {
				step = random.nextInt(13) + 3;
				//敌方坦克改变方向
				Direction[] directions = Direction.values();
				int rn = random.nextInt(9);
				if(rn == 8) moving = false;
				else this.direction = directions[rn];
			}
			
			step --;
			
			//敌方坦克有一定几率开炮
			int fire = random.nextInt(4);
			if(fire == 0) this.attack();
		}
	}
	
	public Missile attack() {
		Missile missile = new Missile(this.x+this.TANK_WIDTH/2-Missile.MISSILE_SIZE/2,
				this.y+TANK_HEIGHT/2-Missile.MISSILE_SIZE/2,
				this.direction,
				this.tc);
		missile.setGood(this.good);
		missile.setVisiable(true);
		if(tc != null) tc.getMissiles().add(missile);
		return missile;
	}
	
	public void superAttack() {
		Direction[] directions = Direction.values();
		for(int i=0;i<directions.length;i++) {
			Missile m = attack();
			m.setDirection(directions[i]);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,this.TANK_WIDTH,TANK_HEIGHT);
	}
	
	public void eat(Blood blood) {
		if(!good) return;
		if(blood.isLive() && this.getRect().intersects(blood.getRect())) {
			this.life = 5;
			blood.setLive(false);
		}
	}
	
	void reLive() {
		if(life != 0) return;
		life = 5;
	}
}
