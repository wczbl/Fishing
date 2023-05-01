package game.entity.cuttle;

public class CuttleTentacle extends CuttlePart {

	protected double baseRot;
	protected CuttlePart child;
	
	public CuttleTentacle(double baseRot, double x, double y, CuttlePart child) {
		super(x, y);
		this.child = child;
		this.baseRot = baseRot;
	}
	
	public void tick() {
		super.tick();
		this.rot = Math.sin(this.tickTime / 42.0) * Math.cos(this.tickTime / 11.0) * 0.5;
		this.rot *= 0.9;
		double rr = this.baseRot + this.rot;
		if(this.child != null) {
			this.child.x = this.x + Math.sin(rr) * (16.0 * this.scale);
			this.child.y = this.y + Math.cos(rr) * (16.0 * this.scale);
			this.child.setRot(rr);			
		}
	}
	
	public void setRot(double rr) { this.baseRot = rr; }
	
}