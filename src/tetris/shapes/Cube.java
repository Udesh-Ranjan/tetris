package tetris.shapes;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.util.stream.Stream;
import java.util.Objects;

public class Cube extends Shape{
	private int blockLength=50;
	private int topHor=0,topVer=0;
	private Color color=Color.CYAN;
	public Cube(){
		super();
		initialize();
	}
	public Cube(final int BLOCK_LENGTH){
		super();
		blockLength=BLOCK_LENGTH;
		initialize();
	}
	//should be called only one during initialization
	private void initialize(){
		Rectangle topLeft=new Rectangle(Double.valueOf(topHor),Double.valueOf(topVer),
				Double.valueOf(blockLength),Double.valueOf(blockLength));
		Rectangle topRight=new Rectangle(Double.valueOf(topHor+blockLength),Double.valueOf(topVer),
				Double.valueOf(blockLength),Double.valueOf(blockLength));
		Rectangle bottomLeft=new Rectangle(Double.valueOf(topHor),Double.valueOf(topVer+blockLength),
				Double.valueOf(blockLength),Double.valueOf(blockLength));
		Rectangle bottomRight=new Rectangle(Double.valueOf(topHor+blockLength),Double.valueOf(topVer+blockLength),
				Double.valueOf(blockLength),Double.valueOf(blockLength));
		rectangles.addAll(Stream.of(topLeft,topRight,bottomLeft,bottomRight).toList());
	}
	@Override
	public void move(final int hor,final int ver){
		final int horShift=topHor-hor;
		final int verShift=topVer-ver;
		rectangles.stream()
			.forEach(rectangle->{
				rectangle.setX(rectangle.getX()+horShift);
				rectangle.setY(rectangle.getY()+verShift);
			});
	}
	@Override
	public void translate(final int hor,final int ver){
		rectangles.stream()
			.forEach(rectangle->{
				rectangle.setX(rectangle.getX()+hor);
				rectangle.setY(rectangle.getY()+ver);
			});
	}
	@Override
	public void drawShape(final GraphicsContext gc){
		Objects.requireNonNull(gc,"GraphicsContext must not be null");
		gc.setFill(color);
		rectangles.stream()
			.forEach(rectangle->{
				gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
			});
	}

}
