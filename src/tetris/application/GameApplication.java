package tetris.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCombination;

import tetris.util.EnvironmentConstants;

import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.Group;
import javafx.scene.effect.ColorInput;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.AnchorPane;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import javafx.geometry.Pos;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.LongProperty;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import tetris.animation.AnimationClass;
import tetris.shapes.Shape;
import tetris.shapes.Cube;

public class GameApplication extends Application{
	private Thread thread;
	private volatile boolean running;
	AnimationTimer animationTimer=new AnimationClass();
	private void startGame(){
		System.out.println("Game started");
		//running=true;
		//thread=new Thread(new Game());
		//thread.start();
		animationTimer.start();
	}
	private void stopGame(){
		System.out.println("Game stopped");
		//running=false;
		animationTimer.stop();
	}
	List<Integer>sizeWindows;
	@Override
	public void start(Stage stage){
		//StackPane stackPane=new StackPane();
		System.out.println("SCREEN_WIDTH : "+EnvironmentConstants.SCREEN_WIDTH);
		System.out.println("SCREEN_HEIGHT : "+EnvironmentConstants.SCREEN_HEIGHT);
		sizeWindows=EnvironmentConstants.computeEnvironSize();
		Rectangle r = new Rectangle(25,25,250,250);
		Rectangle rect=new Rectangle(0,0,100,100);
		int leftSize=sizeWindows.get(0);
		int centerSize=sizeWindows.get(1);
		int rightSize=sizeWindows.get(2);
		final int blockSizePixels=centerSize/EnvironmentConstants.HORIZONTAL_BLOCKS;
		System.out.println("Size : "+sizeWindows);
		System.out.println("blockSizePixels : "+blockSizePixels);
		//HBox hbox=new HBox();
		//TilePane hbox=new TilePane();
		AnchorPane anchorPane=new AnchorPane();
		anchorPane.setMinHeight(EnvironmentConstants.SCREEN_HEIGHT);
		anchorPane.setMinWidth(EnvironmentConstants.SCREEN_WIDTH);
		//hbox.setTileAlignment(Pos.CENTER_RIGHT);
		//hbox.setPadding(new Insets(1));
		Pane left,center,right,centerTop;
		left=new Pane();
		//left.prefWidth(leftSize);
		//left.prefHeight(400);
		left.setMinSize(leftSize,EnvironmentConstants.SCREEN_HEIGHT);
		left.setStyle("-fx-background-color: grey;");
		//left.getChildren().add(r);
		center=new Pane();
		ColorInput ci = new ColorInput(center.getLayoutX(),
				center.getLayoutY(),
				center.getLayoutBounds().getWidth(),
				center.getLayoutBounds().getHeight(),
				Color.RED);
		//center.prefWidth(centerSize);
		//center.prefHeight(400);
		//center.setEffect(ci);
		center.setMinSize(centerSize,EnvironmentConstants.VERTICAL_BLOCKS*blockSizePixels);
		center.setStyle("-fx-background-color: black;");
		r.setFill(Color.YELLOW);
		Canvas canvas=new Canvas(centerSize,EnvironmentConstants.SCREEN_HEIGHT);
		GraphicsContext gc=canvas.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.fillRect(0,75,100,100);
		center.getChildren().add(canvas);
		//center.getChildren().add(r);
		right=new Pane();
		right.setMinSize(rightSize,EnvironmentConstants.SCREEN_HEIGHT/*-(EnvironmentConstants.VERTICAL_BLOCKS*blockSizePixels)*/);
		//right.prefWidth(rightSize);
		//right.prefHeight(400);
		right.setStyle("-fx-background-color: purple;");
		centerTop=new Pane();
		centerTop.setMinSize(centerSize,EnvironmentConstants.SCREEN_HEIGHT-EnvironmentConstants.VERTICAL_BLOCKS*blockSizePixels);
		centerTop.setStyle("-fx-background-color:red;");
		//right.getChildren().add(r);
		//hbox.getChildren().addAll(left,center,right);
		//hbox.getStyleClass().add("jfx-decorator-buttons-container");
		/*hbox.setBackground(new Background(new BackgroundFill(Color.BLACK,
		  CornerRadii.EMPTY,
		  Insets.EMPTY)));*/
		anchorPane.setLeftAnchor(left,0.0);
		anchorPane.setLeftAnchor(centerTop,Double.valueOf(leftSize));
		anchorPane.setTopAnchor(centerTop,0.0);
		anchorPane.setLeftAnchor(centerTop,Double.valueOf(leftSize));
		anchorPane.setLeftAnchor(right,Double.valueOf(leftSize+centerSize));
		anchorPane.setTopAnchor(center,Double.valueOf(EnvironmentConstants.SCREEN_HEIGHT-EnvironmentConstants.VERTICAL_BLOCKS*blockSizePixels));
		anchorPane.setLeftAnchor(center,Double.valueOf(leftSize));
		anchorPane.getChildren().addAll(left,center,centerTop,right);
		Scene scene=new Scene(anchorPane,EnvironmentConstants.SCREEN_WIDTH,EnvironmentConstants.SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("Tetris Game");
		stage.setFullScreen(true);
		//stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue != null && !newValue.booleanValue())
					stage.setFullScreen(true);
			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent winEvent){
				stopGame();
				System.out.println("Exiting");
				System.exit(0);
			}
		});
		stage.show();
		startGame();
	}
	/*public class Game implements Runnable{
	  @Override
	  public void run(){
	  try{
	  while(running){
	  Thread.sleep(100);
	  }
	  }catch(final InterruptedException exception){
	  exception.printStackTrace();
	  }
	  }
	  }*/
	//TODO Make in seperate file
	//AnimationClass
	/*public class AnimationClass extends AnimationTimer{
	  private final LongProperty lastUpdateTime;
	  public AnimationClass(){
	  lastUpdateTime=new SimpleLongProperty(0);	
	  }
	  @Override
	  public void handle(long nano){
	  if(lastUpdateTime.get()>0){
	//TODO
	final long elapsedTime=nano-lastUpdateTime.get();
	  }
	  lastUpdateTime.set(nano);	
	  }
	}
	*/
	////////////main//////////
	public static void main(String ...$){
		launch($);
	}
}
