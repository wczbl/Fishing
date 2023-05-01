package game.entity;

import game.particle.BloodParticle;
import game.particle.BubbleParticle;
import game.sound.Sound;

public class Fish extends Entity {

	public int maxHurtTime;
	public int hurtTime;
	public int health;
	public double minSwimSpeed;
	public double maxSwimSpeed;
	
	public Fish(double x, double y) {
		this.x = x;
		this.y = y;
		this.r = 8.0;
		this.rot = random.nextDouble() * Math.PI * 2;
		this.scale = 0.8 + (random.nextDouble() + 0.5);
		this.minSwimSpeed = 0.002 + random.nextDouble() * 0.018;
		this.maxSwimSpeed = 0.03;
		this.maxHurtTime = 60;
		this.health = 5;
		this.rotSpeed = 0.03 + random.nextDouble() * 0.02;
	}
	
	public void tick() {
		super.tick();
		
		double xd = this.xo - this.x;
		double yd = this.yo - this.y;
		double dd = Math.sqrt(xd * xd + yd * yd);
		
		xd /= dd;
		yd /= dd;
		
		if(Math.random() < 0.007) this.level.ps.add(new BubbleParticle(this.x, this.y, xd, yd));
		if(this.health <= 0) die();
		if(this.hurtTime > 0) this.hurtTime--;
		
		double moveSpeed = this.minSwimSpeed + (this.maxSwimSpeed - this.minSwimSpeed) * this.hurtTime / (double)this.maxHurtTime;
		this.xa += Math.cos(this.rot) * moveSpeed;
		this.ya += Math.sin(this.rot) * moveSpeed;
		
		this.rotA += random.nextGaussian() * random.nextGaussian() * this.rotSpeed;
	}
	
	public void hurt(Entity e, double xxa, double yya) {
		if(this.hurtTime <= 0) {
			this.xa += xxa * 0.02;
			this.ya += yya * 0.02;
			this.health = (int)(this.health - e.getDamage());
			
			int i = 0; 
			while(i < e.getDamage()) {
				this.level.ps.add(new BloodParticle(this.x + random.nextInt(10) - 5.0, this.y + random.nextInt(10) - 5.0, -xxa, -yya));
				i++;
			}
			
			if(this.health <= 0) {
				e.eat(this);
				Sound.eat.setVolume(0.2f);
				Sound.eat.play();
			} else {
				this.hurtTime = random.nextInt(this.maxHurtTime / 2) + this.maxHurtTime / 2;
				this.rot = Math.atan2(-yya, -xxa) + Math.PI / 2;
				Sound.hurt.setVolume(0.2f);
				Sound.hurt.play();
			}
		}
	}
	
	protected void die() {
		this.removed = true;
		for(int i = 0; i < 15; i++) {
			this.level.ps.add(new BloodParticle(this.x + random.nextInt(10) - 5.0, this.y + random.nextInt(10) - 5.0));
		}
	}
	
}