package game.entity;

import game.GameComponent;
import game.State;
import game.level.Checker;

public class HungryFish extends Fish {

	public Fish target;
	public int tickTime;
	public double viewRadius = 60.0;
	public int followTime;
	public int nextFollowTime = (2 + random.nextInt(3)) * 60;
	private double xTarget;
	private double yTarget;
	private int blindTime;
	
	public HungryFish(double x, double y) {
		super(x, y);
		this.scale = 2;
		this.sprite = 2;
		this.minSwimSpeed = 0.015;
		this.maxSwimSpeed = 0.02;
		this.health = 48880;
	}
	
	public void tick() {
		super.tick();
		
		this.tickTime++;
		if(this.target == null && this.tickTime % 60 == 0) {
			this.target = (Fish)this.level.findTarget(this, new Checker() {
				public boolean check(Entity owner, Entity e) {
					return e instanceof Fish && owner.scale > e.scale && e.distanceTo(owner) < owner.scale * HungryFish.this.viewRadius;
				}
			});
		}
		
		if(this.blindTime > 0) this.blindTime--;
		
		if(this.target != null) {
			this.followTime++;
			if(this.target.removed || this.followTime > this.nextFollowTime) {
				this.target = null;
				this.followTime = 0;
				return;
			}
						
			if(this.blindTime == 0) {
				this.xTarget = this.target.x;
				this.yTarget = this.target.y;
				
				if(Math.abs(this.xTarget - this.target.xo) > GameComponent.WIDTH / 2) {
					if(this.target.xa > 0) this.xTarget = GameComponent.WIDTH + this.target.x;
					if(this.target.xa < 0) this.xTarget = this.target.x - GameComponent.WIDTH;
					
					this.blindTime = 20;
				}
				
				if(Math.abs(this.yTarget - this.target.yo) > GameComponent.HEIGHT / 2) {
					if(this.target.ya > 0) this.yTarget = GameComponent.HEIGHT + this.target.y;
					if(this.target.ya < 0) this.yTarget = this.target.y - GameComponent.HEIGHT;
					
					this.blindTime = 20;
				}
			}
			
			double xd = this.xTarget - this.x;
			double yd = this.yTarget - this.y;
			double dd = Math.sqrt(xd * xd + yd * yd);
			
			if(dd < this.viewRadius / 6.0 * this.scale) {
				this.target.hurt(this, this.xa, this.ya);
				this.followTime = 0;
				this.nextFollowTime = (2 + random.nextInt(3)) * 60;
			}
			
			double angle = Math.atan2(yd, xd) + Math.PI / 2;
			double x0 = this.x + Math.cos(angle) * 8.0;
			double y0 = this.y + Math.sin(angle) * 8.0;
			double x1 = this.x + Math.cos(angle) * 16.0;
			double y1 = this.y + Math.sin(angle) * 16.0;
		
			double xx = x0 + (x1 - this.x);
			double yy = y0 + (y1 - this.y);
			this.rot = Math.atan2(this.yTarget - yy, this.xTarget - xx) + Math.PI / 2;
		}
	}

	public void eat(Entity e) {
		State.eaten++;
		
		if(this.scale <= 3) {
			this.scale += State.eaten * 0.005 * e.scale;
			this.scale = Math.min(this.scale, 3.0);
			this.minSwimSpeed = this.scale * 0.01;
		}
	}
	
	public double getDamage() { return this.scale * this.minSwimSpeed * 100; }

}