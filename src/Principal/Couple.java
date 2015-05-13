package Principal;

public class Couple {
	public Couple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private int x, y;
}
