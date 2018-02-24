import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	private final static int TANK_WIDTH = 60;
	private final static int TANK_HEIGHT = 60;
	private final static int TANK_SPEED_X = 5;
	private final static int TANK_SPEED_Y = 5;
	private boolean bL, bD, bR, bU;
	enum Direction{U,RU,R,RD,D,LD,L,LU,STOP};
	
	private int x;
	private int y;
	private String resourseID;
	private Direction direction;
	private int life;
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
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
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
		}
	}
	
	void move() {
		locateDirection();
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
		case STOP:
			break;
		}
	}
	
	void locateDirection() {
		if(bL && !bD && !bR && !bU) direction = Direction.L;
		else if(bL && bD && !bR && !bU) direction = Direction.LD;
		else if(!bL && bD && !bR && !bU) direction = Direction.D;
		else if(!bL && bD && bR && !bU) direction = Direction.RD;
		else if(!bL && !bD && bR && !bU) direction = Direction.R;
		else if(!bL && !bD && bR && bU) direction = Direction.RU;
		else if(!bL && !bD && !bR && bU) direction = Direction.U;
		else if(bL && !bD && !bR && bU) direction = Direction.LU;
		else if(!bL && !bD && !bR && !bU) direction = Direction.STOP;
	}
}
