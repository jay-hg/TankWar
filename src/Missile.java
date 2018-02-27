import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile {
	public final static int MISSILE_SPEED = 30;
	public final static int MISSILE_SIZE = 30;
	private int x;
	private int y;
	private boolean visiable;
	private Tank.Direction direction;
	TankClient tc;
	private boolean good;
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
	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}
	public Missile(int x, int y, Tank.Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Missile(int x, int y, Tank.Direction direction, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.tc = tc;
	}
	public void draw(Graphics g) {
		move();
		Color c = g.getColor();
		if(good) g.setColor(Color.WHITE);
		else g.setColor(Color.GRAY);
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
		
		if(x<0-MISSILE_SIZE || x>TankClient.WINDOW_WIDTH ||
				y<0-MISSILE_SIZE || y>TankClient.WINDOW_HEIGHT) 
			this.visiable = false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,this.MISSILE_SIZE,MISSILE_SIZE);
	}
	
	public void hitTank(Tank tank) {
		if(this.good) {
			if(tank.isGood()) return;
			if(tank.getLife() == 0) return;
			if(this.getRect().intersects(tank.getRect())) {
				this.visiable = false;
				tank.setLife(0);
				Exploder e = new Exploder(tank.getX(),tank.getY(),tc);
				tc.getExploders().add(e);
			}
		} else {
			if(!tank.isGood()) return;
			if(tank.getLife() == 0) return;
			if(this.getRect().intersects(tank.getRect())) {
				tank.setLife(tank.getLife()-1);
				this.visiable = false;
				if(tank.getLife() == 0) {
					Exploder e = new Exploder(tank.getX(),tank.getY(),tc);
					tc.getExploders().add(e);
				}
			}
		}
	}
	
	public void hitWall(Wall wall) {
		if(this.getRect().intersects(wall.getRect())) {
			this.visiable = false;
		}
	}
}
