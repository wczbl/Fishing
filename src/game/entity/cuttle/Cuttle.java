package game.entity.cuttle;

import game.level.Level;
import game.particle.BubbleParticle;

public class Cuttle extends CuttlePart {

	public Cuttle(double x, double y) { super(x, y); }
	
	public void tick() {
		super.tick();
		
		double xa = this.x - this.xo;
		double ya = this.y - this.yo;
		double dd = Math.sqrt(xa * xa + ya * ya);
		
		xa /= dd;
		ya /= dd;
		
		if(Math.random() < 0.02) {
			this.level.ps.add(new BubbleParticle(this.x + random.nextInt(30) - 15.0, this.y + random.nextInt(30) - 15.0, xa, ya));
		}
		
		this.xo = this.x;
		this.yo = this.y;
	}
	
	public static CuttlePart buildCuttle(Level level, double x, double y, int parts, double baseRot) {
		CuttlePart prev = new CuttlePart(x, y);
		level.add(prev);
		for(int i = 0; i < parts; i++) {
			prev = addPart(level, x, y, prev, baseRot);
			prev.tickTime += i * (random.nextInt(10) + 5);
		}
		
		return prev;
	}
	
	public static CuttlePart addPart(Level level, double x, double y, CuttlePart prev, double baseRot) {
		prev = new CuttleTentacle(baseRot, x, y, prev);
		level.add(prev);
		return prev;
	}
	
}