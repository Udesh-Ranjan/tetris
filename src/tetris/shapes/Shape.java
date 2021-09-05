package tetris.shapes;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
public abstract class Shape{
	protected final List<Rectangle>rectangles;
	public Shape(){
		rectangles=new ArrayList<>();
	}
	public abstract void drawShape(final GraphicsContext gc);
	public abstract void translate(final int horPixels,final int verPixels);
	public abstract void move(final int horPixels,final int verPixels);
	public List<Rectangle> getRectangles(){
		return rectangles;
	}
}
