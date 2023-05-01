package game.entity;

import java.util.Random;

import game.GameComponent;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Renderable;
import game.level.Level;

public class Entity implements Renderable {

	protected static final Random random = new Random();
	public double xo;
	public double yo;
	public double x;
	public double y;
	public double xa;
	public double ya;
	public double rot;
	public double rotA;
	public double rotSpeed;
	public double scale = 1.0;
	public double r = 16;
	public Level level;
	public int sprite;
	public boolean removed;
	public int col = random.nextInt();
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		
		move();
		this.xa *= 0.7;
		this.ya *= 0.7;
		
		this.rot += this.rotA;
		this.rotA *= 0.7;
		
		if(this.x < 0) this.x = GameComponent.WIDTH - 1;
		if(this.y < 0) this.y = GameComponent.HEIGHT - 1;
		
		this.x %= GameComponent.WIDTH;
		this.y %= GameComponent.HEIGHT;
	}
	
	public void render(Bitmap screen, double delta) {
		if(this.removed) return;
		
		double x = this.xo + (this.x - this.xo) * delta;
		double y = this.yo + (this.y - this.yo) * delta;
			
		screen.scaleDraw(Art.sprites[this.sprite].rotate(this.rot), (int)(x - this.r), (int)(y - this.r), this.col, (int)this.scale);
	}
	
	public void move() {
		int xSteps = (int)(Math.sqrt(this.xa * this.xa) + 100);
		for(int i = xSteps; i >= 0; i--) {
			if(isFree(this.x + this.xa * i / xSteps, 0)) {
				this.x += this.xa * i / xSteps;
				continue;
			}
			
			this.xa = 0.0;
		}

		int ySteps = (int)(Math.sqrt(this.ya * this.ya) + 100);
		for(int i = ySteps; i >= 0; i--) {
			if(isFree(0, this.y + this.ya * i / ySteps)) {
				this.y += this.ya * i / ySteps;
				continue;
			}
			
			this.ya = 0.0;
		}
	}
	
	public boolean isFree(double xxa, double yya) { return true; }
	
	public double distanceTo(Entity e) {
		double xx = this.x - e.x;
		double yy = this.y - e.y;
		return Math.sqrt(xx * xx + yy * yy);
	}
	
	public double getDamage() { return 0; }
	public void eat(Entity e) {}
	
}