import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Game extends Application{

    /*
        Main class of application
     */

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage stage){

        Frame frame = new Frame();
        Scene scene = new Scene(frame, 800, 700);

        stage.setScene(scene);
        stage.show();
    }
}
