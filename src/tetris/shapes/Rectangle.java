package tetris.shapes;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.canvas.GraphicsContext;
import java.util.Objects;

public class Rectangle{
	private final javafx.scene.shape.Rectangle rectangle;
	
	private static final double strokeWidth=1;
	private static final Color stroke=Color.RED;
	private static final Color fill=Color.WHITE;

	private static void init(final javafx.scene.shape.Rectangle rectangle){
		Objects.requireNonNull(rectangle);
		rectangle.setFill(fill);
		rectangle.setStroke(stroke);
		rectangle.setStrokeWidth(strokeWidth);
	}
	public Rectangle(final javafx.scene.shape.Rectangle rectangle){
		Objects.requireNonNull(rectangle);
		this.rectangle=rectangle;
		init(this.rectangle);
	}
	public Rectangle(final double x,final double y,final double width,final double height){
		rectangle=new javafx.scene.shape.Rectangle(x,y,width,height);
		init(rectangle);
	}

	public void draw(final GraphicsContext gc){
		gc.setFill(rectangle.getFill());
		gc.setStroke(rectangle.getStroke());
		//gc.setStroke(rectangle.getStrokeWidth());
		gc.setLineWidth(rectangle.getStrokeWidth());
		gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
	}
	public void move(final double hor,final double ver){
		setX(hor);
		setY(ver);
	}
	public void translate(final int x,final int y){
		translateX(x);
		translateY(y);
	}
	public void setX(final double x){
		rectangle.setX(x);
	}
	public void translateX(final double x){
		rectangle.setX(getX()+x);
	}
	public void translateY(final double y){
		rectangle.setY(getY()+y);
	}
	public void setY(final double y){
		rectangle.setY(y);
	}
	public double getX(){
		return rectangle.getX();
	}
	public double getY(){
		return rectangle.getY();
	}
	public double getWidth(){
		return rectangle.getWidth();
	}
	public void setWidth(final double width){
		rectangle.setWidth(width);
	}
	public void setHeight(final double height){
		rectangle.setHeight(height);
	}
	public double getHeight(){
		return rectangle.getHeight();
	}
	public void setStroke(final Color strokeColor){
		rectangle.setStroke(strokeColor);
	}
	public void setFill(final Color fill){
		rectangle.setFill(fill);
	}
	public Color getStroke(){
		return (Color)rectangle.getStroke();
	}
	public Color getFill(){
		return (Color) rectangle.getFill();
	}
	public double getStrokeWidth(){
		return rectangle.getStrokeWidth();
	}
	public void setStrokeWidth(final double width){
		rectangle.setStrokeWidth(width);
	}
}


