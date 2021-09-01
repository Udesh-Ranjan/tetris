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

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;

public class GameApplication extends Application{
	private Thread thread;
	private volatile boolean running;
	private void startGame(){
		running=true;
		thread=new Thread(new Game());
		thread.start();
	}
	private void stopGame(){
		running=false;
	}
	@Override
	public void start(Stage stage){
		//StackPane stackPane=new StackPane();
		HBox hbox=new HBox();
		Pane left,center,right;
		left=new Pane();
		center=new Pane();
		ColorInput ci = new ColorInput(center.getLayoutX(),
				center.getLayoutY(),
				center.getLayoutBounds().getWidth(),
				center.getLayoutBounds().getHeight(),
				Color.RED);
		center.prefWidth(400);
		center.prefHeight(400);
		//center.setEffect(ci);
		center.setStyle("-fx-background-color: red;");
		Rectangle r = new Rectangle(25,25,250,250);
		r.setFill(Color.BLUE);
		center.getChildren().add(r);
		right=new Pane();
		hbox.getChildren().addAll(left,center,right);
		//hbox.getStyleClass().add("jfx-decorator-buttons-container");
		hbox.setBackground(new Background(new BackgroundFill(Color.BLACK,
						CornerRadii.EMPTY,
						Insets.EMPTY)));
		Scene scene=new Scene(hbox,500,500);
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
		System.out.println("SCREEN_WIDTH : "+EnvironmentConstants.SCREEN_WIDTH);
		System.out.println("SCREEN_HEIGHT : "+EnvironmentConstants.SCREEN_HEIGHT);
		System.out.println("Size : "+EnvironmentConstants.computeEnvironSize());
		startGame();
	}
	public static void main(String ...$){
		launch($);
	}
	public class Game implements Runnable{
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
	}
}
