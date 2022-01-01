import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;


public class Frame extends BorderPane{

    Board board;
    Pane scorePane;
    Text scoreText;

    Frame(){
        makeBoard();
        makeLogo();
        makeScore();
    }

    public void makeLogo(){
        int width=800;
        int height=100;

        Pane logo = new Pane();
        logo.setPrefSize(width, height);
        logo.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Rectangle border1 = new Rectangle(0, height-10,width, 10);
        border1.setFill(Color.WHITE);
        logo.getChildren().add(border1);

        Text snake = new Text("Snake");;
        snake.setStyle("-fx-font: 36px Arial; -fx-fill: rgba(0,255,0, 1.0);");
        snake.setX(334); snake.setY(55);

        Text fx = new Text("FX");
        fx.setFill(Color.RED);
        fx.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 36));
        fx.setX(435); fx.setY(55);

        logo.getChildren().add(snake);
        logo.getChildren().add(fx);

        this.setTop(logo);
    }

    private void makeBoard(){
        board = new Board(600, 600);
        board.setTimer();
        board.setFrame(this);
        this.setCenter(board);
    }

    private void makeScore(){
        int width=200;
        int height=600;

        scorePane = new Pane();
        scorePane.setPrefSize(width, height);
        scorePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Rectangle border1 = new Rectangle(0, 0,10, height);
        border1.setFill(Color.WHITE);
        scorePane.getChildren().add(border1);

        setInformation();

        this.setRight(scorePane);
    }

    private void setInformation(){
        Text text = new Text("Score:");
        text.setFont(new Font(35));
        text.setFill(Color.WHITE);
        text.setY(130);
        text.setX(50);
        scorePane.getChildren().add(text);

        scoreText = new Text("0");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(30));
        scoreText.setY(180);
        scoreText.setX(70);
        scorePane.getChildren().add(scoreText);

        Text color = new Text("Food:");
        color.setFont(new Font(35));
        color.setFill(Color.WHITE);
        color.setY(260);
        color.setX(55);
        scorePane.getChildren().add(color);

        Rectangle rec = new Rectangle(50,50, board.food.getColor());
        rec.setY(290);
        rec.setX(75);
        scorePane.getChildren().add(rec);
    }

    public void setScore(int score){
        scoreText = new Text(String.valueOf(score));
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(25));
        scoreText.setY(180);
        scoreText.setX(70);
        scorePane.getChildren().set(2, scoreText);
    }

    public void setColor(Color color){
        Rectangle rec = new Rectangle(50,50, color);
        rec.setY(290);
        rec.setX(75);
        scorePane.getChildren().set(4, rec);
    }

}
