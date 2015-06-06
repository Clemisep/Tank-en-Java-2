package Principal;

public class Position {
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int recX() {
		return x;
	}
	
	public int recY() {
		return y;
	}
	
	private int x, y;
}
