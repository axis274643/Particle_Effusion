package fun;

public class ParticleMethods extends Circle{

	public static double getDensity(int x1, int y1, int x2, int y2) {
		int width = x2-x1;
		int height = y2-y1;
		double area = width * height;
		return particlesInside(x1, y1, x2, y2)* particleSize * particleSize/area;
	}
	
	//assumed x1,y1 is the top left corner
	public static int particlesInside(int x1, int y1, int x2, int y2) {
		int count = 0;
		for(Circle c: allCircles) {
			if(c.position[0] <= x2 && c.position[0] >= x1) {
				if(c.position[1] <= y2 && c.position[1] >= y1) {
					count += 1;
				}
			}
		}
		return(count);
	}
}
