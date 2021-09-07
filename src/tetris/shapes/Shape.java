package tetris.shapes;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import tetris.enums.DIRECTION;

public abstract class Shape{
	protected final List<Rectangle>rectangles;
	public final int blockSizePixels;
	public Shape(final int blockSizePixels){
		rectangles=new ArrayList<>();
		this.blockSizePixels=blockSizePixels;
	}
	public abstract void draw(final GraphicsContext gc);
	public abstract void translate(final int horPixels,final int verPixels);
	public abstract void translate(final DIRECTION direction);
	public abstract void move(final int horPixels,final int verPixels);
	public List<Rectangle> getRectangles(){
		return rectangles;
	}
}
