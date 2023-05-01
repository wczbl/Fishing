package game.entity;

import game.level.Checker;
import game.particle.SpawnParticle;
import game.sound.Sound;

public class YellowBoy extends Fish {

	private HungryFish hungryFish;
	
	public YellowBoy(double x, double y) {
		super(x, y);
		this.sprite = 1;
		this.scale = 0.5 + (0.8 + random.nextDouble() * 0.7);
		this.minSwimSpeed = 0.01;
		this.health = 10;
	}
	
	public void tick() {
		super.tick();
		
		if(this.hungryFish == null) {
			this.hungryFish = (HungryFish)this.level.findTarget(this, new Checker() {				
				public boolean check(Entity owner, Entity e) {
					return e instanceof HungryFish;
				}
			});
		}
		
		if(this.hungryFish != null && this.hurtTime > 0 && Math.random() < 0.02) {
			this.level.ps.add(new SpawnParticle(this, this.x + random.nextInt(30) - 15.0, this.y + random.nextInt(30) + 15));
			Sound.spawn.setVolume(0.2f);
			Sound.spawn.play();
		}
	}

}