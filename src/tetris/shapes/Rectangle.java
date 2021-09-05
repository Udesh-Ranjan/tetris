package tetris.shapes;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Rectangle{
	private final javafx.scene.shape.Rectangle rectangle;
	private Color fillColor,strokeColor;
	public Rectangle(final javafx.scene.shape.Rectangle rectangle){
		this.rectangle=rectangle;
		fillColor=Color.WHITE;
		strokeColor=Color.BLACK;
	}
	public Rectangle(final double x,final double y,final double width,final double height){
		rectangle=new javafx.scene.shape.Rectangle(x,y,width,height);
		fillColor=Color.WHITE;
		strokeColor=Color.BLACK;
	}

	public void setFillColor(final Color color){
		fillColor=color;
	}
	public void setStrokeColor(final Color color){
		strokeColor=color;
	}
	public Color getFillColor(){
		return fillColor;
	}
	public Color getStrokeColor(){
		return strokeColor;
	}
	public void drawRectangle(final GraphicsContext gc){
		gc.setFill(fillColor);
		gc.setStroke(strokeColor);
		gc.fillRect(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
	}
	public void moveRectangle(final double hor,final double ver){
		setX(hor);
		setY(ver);
	}
	public void translateRectangle(final int x,final int y){
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
}


