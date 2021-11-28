package a_Introductory;

public class Vector2D {
	public Integer x, y;
	
	Vector2D(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	/* Construct Vector2D from two points */
	Vector2D(Point p1, Point p2) {
		Point p3 = p1.sub(p2);
		this.x = p3.x;
		this.y = p3.y;
	}
	
	public int dotProduct(Vector2D v) {
		return (x * v.x) + (y * v.y);
	}
	
	public boolean isOrthogonalTo(Vector2D v) {
		return (dotProduct(v) == 0);
	}
}
