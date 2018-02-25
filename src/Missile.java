import java.awt.Color;
import java.awt.Graphics;


public class Missile {
	public final static int MISSILE_SPEED = 10;
	public final static int MISSILE_SIZE = 30;
	private int x;
	private int y;
	private boolean visiable;
	private Tank.Direction direction;
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
	public Tank.Direction getDirection() {
		return direction;
	}
	public void setDirection(Tank.Direction direction) {
		this.direction = direction;
	}
	public boolean isVisiable() {
		return visiable;
	}
	public void setVisiable(boolean visiable) {
		this.visiable = visiable;
	}
	public Missile(int x, int y, Tank.Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public void draw(Graphics g) {
		move();
		clearMissile();
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		g.fillOval(x, y, MISSILE_SIZE, MISSILE_SIZE);
		g.setColor(c);
	}
	
	void move() {
		switch(direction) {
		case L:
			x -= MISSILE_SPEED;
			break;
		case LD:
			x -= MISSILE_SPEED;
			y += MISSILE_SPEED;
			break;
		case D:
			y += MISSILE_SPEED;
			break;
		case RD:
			x += MISSILE_SPEED;
			y += MISSILE_SPEED;
			break;
		case R:
			x += MISSILE_SPEED;
			break;
		case RU:
			x += MISSILE_SPEED;
			y -= MISSILE_SPEED;
			break;
		case U:
			y -= MISSILE_SPEED;
			break;
		case LU:
			x -= MISSILE_SPEED;
			y -= MISSILE_SPEED;
			break;
		}
	}
	
	void clearMissile() {
		if(x<0-MISSILE_SIZE || x>TankClient.WINDOW_WIDTH ||
				y<0-MISSILE_SIZE || y>TankClient.WINDOW_HEIGHT) 
			this.visiable = false;
	}
}
