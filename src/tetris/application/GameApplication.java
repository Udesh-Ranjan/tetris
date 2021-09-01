package tetris.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCombination;

import tetris.util.EnvironmentConstants;

public class GameApplication extends Application{
	@Override
	public void start(Stage stage){
		StackPane stackPane=new StackPane();
		Scene scene=new Scene(stackPane,500,500);
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
		stage.show();
		System.out.println("SCREEN_WIDTH : "+EnvironmentConstants.SCREEN_WIDTH);
		System.out.println("SCREEN_HEIGHT : "+EnvironmentConstants.SCREEN_HEIGHT);
	}
	public static void main(String ...$){
		launch($);
	}
}
