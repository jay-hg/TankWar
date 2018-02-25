import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;


public class Tank {
	private final static int TANK_WIDTH = 60;
	private final static int TANK_HEIGHT = 60;
	private final static int TANK_SPEED_X = 5;
	private final static int TANK_SPEED_Y = 5;
	private boolean bL, bD, bR, bU;
	private boolean moving = true;
	enum Direction{U,RU,R,RD,D,LD,L,LU,STOP};
	
	private int x;
	private int y;
	private String resourseID;
	private Direction direction;
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
		g.setColor(Color.RED);
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
		}
	}
	
	void move() {
		locateDirection();
		if(!moving) return;
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
	}
	
	void locateDirection() {
		moving = true;
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
	
	public Missile attack() {
		Missile missile = new Missile(this.x+this.TANK_WIDTH/2-Missile.MISSILE_SIZE/2,
				this.y+TANK_HEIGHT/2-Missile.MISSILE_SIZE/2,
				this.direction);
		missile.setVisiable(true);
		if(tc != null) tc.getMissiles().add(missile);
		return missile;
	}
}
