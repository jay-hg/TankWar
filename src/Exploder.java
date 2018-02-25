import java.awt.Color;
import java.awt.Graphics;


public class Exploder {
	private int x;
	private int y;
	private boolean live = true;
	private TankClient tc;
	int step;
	int diameter[] = {3,7,18,29,40,63,57,38,16,5};
	
	
	public Exploder(int x, int y, boolean live) {
		super();
		this.x = x;
		this.y = y;
		this.live = live;
	}

	public Exploder(int x, int y, TankClient tc) {
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

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public void draw(Graphics g) {
		if(!live) return;
		if(step == diameter.length) {
			step = 0;
			live = false;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step ++;
	}
}
