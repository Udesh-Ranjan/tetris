package tetris.shapes;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.util.stream.Stream;
import java.util.Objects;
import tetris.enums.DIRECTION;

public class Cube extends Shape{
	private int blockSizePixels=50;
	private double topHor=0,topVer=0;
	private Color color=Color.CYAN;
	public Cube(final int blockSizePixels){
		super(blockSizePixels);
		this.blockSizePixels=blockSizePixels;
		initialize();
	}
	//should be called only one during initialization
	private void initialize(){
		rectangles.clear();
		Rectangle topLeft=new Rectangle(Double.valueOf(topHor),Double.valueOf(topVer),
				Double.valueOf(blockSizePixels),Double.valueOf(blockSizePixels));
		Rectangle topRight=new Rectangle(Double.valueOf(topHor+blockSizePixels),Double.valueOf(topVer),
				Double.valueOf(blockSizePixels),Double.valueOf(blockSizePixels));
		Rectangle bottomLeft=new Rectangle(Double.valueOf(topHor),Double.valueOf(topVer+blockSizePixels),
				Double.valueOf(blockSizePixels),Double.valueOf(blockSizePixels));
		Rectangle bottomRight=new Rectangle(Double.valueOf(topHor+blockSizePixels),Double.valueOf(topVer+blockSizePixels),
				Double.valueOf(blockSizePixels),Double.valueOf(blockSizePixels));
		rectangles.addAll(Stream.of(topLeft,topRight,bottomLeft,bottomRight).toList());
		rectangles.stream()
			.forEach(rectangle->{
				rectangle.setFill(Color.CYAN);
				rectangle.setStroke(Color.RED);
				rectangle.setStrokeWidth(2);
			});
	}
	@Override
	public void move(final double hor,final double ver){
		final double horShift=topHor-hor;
		final double verShift=topVer-ver;
		rectangles.stream()
			.forEach(rectangle->{
				/*rectangle.setX(rectangle.getX()+horShift);
				  rectangle.setY(rectangle.getY()+verShift);
				  */
				rectangle.move(hor,ver);
			});
	}
	@Override
	public void translate(final double hor,final double ver){
		rectangles.stream()
			.forEach(rectangle->{
				/*rectangle.setX(rectangle.getX()+hor);
				  rectangle.setY(rectangle.getY()+ver);
				  */
				rectangle.translate(hor,ver);
			});
	}
	@Override
	public void draw(final GraphicsContext gc){
		Objects.requireNonNull(gc,"GraphicsContext must not be null");
		gc.setFill(color);
		rectangles.stream()
			.forEach(rectangle->{
				//gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
				rectangle.draw(gc);
			});
	}
	@Override
	public void translate(final DIRECTION direction){
		Objects.requireNonNull(direction);
		double x=0,y=0;
		if(direction==DIRECTION.UP)
			y-=blockSizePixels;
		else if(direction==DIRECTION.DOWN)
			y+=blockSizePixels;
		else if(direction==DIRECTION.LEFT)
			x-=blockSizePixels;
		else if(direction==DIRECTION.RIGHT)
			x+=blockSizePixels;
		final double _x,_y;
		_x=x;
		_y=y;
		rectangles.stream()
			.forEach(rect->rect.translate(_x,_y));
	}
}
