import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;


public class Frame extends BorderPane{

    /*
        Frame inherited from BrderPane
     */

    JCanvas board;    //Canvas - main game stage
    Pane scorePane; //Pane with current score and food color
    Text scoreText; //Text with score number

    Frame(){
        makeBoard();
        makeLogo();
        makeScore();
    }

    //make logo in top of borderpane
    public void makeLogo(){
        int width=800;
        int height=100;

        //set pane for logo with preferred size and background
        Pane logo = new Pane();
        logo.setPrefSize(width, height);
        logo.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        //set border - white line
        Rectangle border1 = new Rectangle(0, height-10,width, 10);
        border1.setFill(Color.WHITE);
        logo.getChildren().add(border1);

        //set part of logo with CSS styles
        Text snake = new Text("Snake");;
        snake.setStyle("-fx-font: 36px Arial; -fx-fill: rgba(0,255,0, 1.0);");
        snake.setX(334); snake.setY(55);

        //set part of logo with Font object
        Text fx = new Text("FX");
        fx.setFill(Color.RED);
        fx.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC, 36));
        fx.setX(435); fx.setY(55);

        //set whole logo
        logo.getChildren().add(snake);
        logo.getChildren().add(fx);

        //add logo pane to Top
        this.setTop(logo);
    }

    //make Canvas - main game stage
    private void makeBoard(){
        board = new JCanvas(600, 600);
        board.setTimer();
        board.setFrame(this);
        this.setCenter(board);
    }

    //make score pane in right of borderpane
    private void makeScore(){
        int width=200;
        int height=600;

        //make pane
        scorePane = new Pane();
        scorePane.setPrefSize(width, height);
        scorePane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        //make border
        Rectangle border1 = new Rectangle(0, 0,10, height);
        border1.setFill(Color.WHITE);
        scorePane.getChildren().add(border1);

        //set information
        setInformation();

        //set on right
        this.setRight(scorePane);
    }

    //set score and food color
    private void setInformation(){

        //score text
        Text text = new Text("Score:");
        text.setFont(new Font(35));
        text.setFill(Color.WHITE);
        text.setY(130);
        text.setX(50);
        scorePane.getChildren().add(text);

        //score number
        scoreText = new Text("0");
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(30));
        scoreText.setY(180);
        scoreText.setX(70);
        scorePane.getChildren().add(scoreText);

        //set food text
        Text color = new Text("Food:");
        color.setFont(new Font(35));
        color.setFill(Color.WHITE);
        color.setY(260);
        color.setX(55);
        scorePane.getChildren().add(color);

        //set food color
        Rectangle rec = new Rectangle(50,50, board.food.getColor());
        rec.setY(290);
        rec.setX(75);
        scorePane.getChildren().add(rec);
    }

    //set score number - called from board object (Board class)
    public void setScore(int score){
        scoreText = new Text(String.valueOf(score));
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(new Font(25));
        scoreText.setY(180);
        scoreText.setX(70);
        scorePane.getChildren().set(2, scoreText);
    }

    //set food color
    public void setColor(Color color){
        Rectangle rec = new Rectangle(50,50, color);
        rec.setY(290);
        rec.setX(75);
        scorePane.getChildren().set(4, rec);
    }

}
