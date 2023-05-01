package game.entity.cuttle;

import game.entity.Entity;
import game.entity.HungryFish;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Checker;

public class CuttlePart extends Entity {

	public int tickTime;
	private double viewRadius;
	private HungryFish target;
	
	public CuttlePart(double x, double y) {
		this.x = x;
		this.y = y;
		this.sprite = 3;
		this.r = 8.0;
		this.scale = 1;
		this.viewRadius = this.r * 3.0;
	}
	
	public void tick() {
		this.tickTime++;
		
		if(this.target == null) {
			this.target = (HungryFish)this.level.findTarget(this, new Checker() {
				public boolean check(Entity owner, Entity e) {
					return e instanceof HungryFish && e.distanceTo(owner) < CuttlePart.this.viewRadius;
				}
			});
		}
		
		if(this.target != null) {
			if(this.target.distanceTo(this) > this.viewRadius) {
				this.target = null;
				return;
			}
			
			double xa = this.xo - this.x;
			double ya = this.yo - this.y;
			
			double speed = random.nextGaussian() * random.nextGaussian() * 0.04 * getDamage() * 0.01;
			this.target.hurt(this, xa * speed, ya * speed);
		}
	}
	
	public void render(Bitmap screen, double delta) {
		if(this.removed) return;
		
		if(this.sprite >= 0) {
			screen.scaleDraw(Art.sprites[this.sprite].rotate(this.rot * 2.0), (int)(this.x - this.r), (int)(this.y - this.r), 0x46EA00, (int)this.scale);
		}
	}
	
	public void setRot(double rr) {}
	public double getDamage() { return 15.0; }

}