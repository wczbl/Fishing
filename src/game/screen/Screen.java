package game.screen;

import game.Game;
import game.InputHandler;
import game.gfx.Bitmap;

public abstract class Screen {

	protected Game game;
	
	public void init(Game game) { this.game = game; }

	public abstract void destroy();
	public abstract void tick(InputHandler input);
	public abstract void render(Bitmap screen, double delta);
	
}