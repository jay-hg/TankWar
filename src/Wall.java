import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Wall {
	private int x;
	private int y;
	private int width;
	private int height;
	public Wall(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,width,height);
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}
}
