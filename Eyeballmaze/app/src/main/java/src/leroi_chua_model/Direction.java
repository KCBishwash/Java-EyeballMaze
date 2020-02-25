package src.leroi_chua_model;

public enum Direction {
	UP (0,-1),
	DOWN (0,1),
	LEFT (-1,0),
	RIGHT (1,0);
	
	private final int xAdjust;
	private final int yAdjust;

	
	private Direction (int x, int y) {
		this.xAdjust = x;
		this.yAdjust = y;
	}
		
	public String toString(){
		String s =  xAdjust + " " + yAdjust;
		return s;
	}
	public int[] getLevelList(){
		int[] direction = new int[2];
		direction[0] = xAdjust;
		direction[1] = yAdjust;
		return direction;
	}
}
