package game.gfx;

public class Font {

	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      0123456789.,!?'\"/\\%-+=()[]<>:;  abcdefghijklmnopqrstuvwxyz      ";
	
	public static void draw(String text, Bitmap screen, int xp, int yp, int col) {
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			
			int xx = ix % 32;
			int yy = ix / 32;
			screen.draw(Art.font[xx + yy * 32], xp + i * 6, yp, col);
		}
	}
	
	public static void scaleDraw(String text, Bitmap screen, int xp, int yp, int col, int scale) {
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			
			int xx = ix % 32;
			int yy = ix / 32;
			screen.scaleDraw(Art.font[xx + yy * 32], xp + i * 6 * scale, yp, col, scale);
		}
	}
	
}