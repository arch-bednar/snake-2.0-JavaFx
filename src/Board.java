import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import java.util.LinkedList;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Board extends Canvas implements Engine, Direction{

    private final double width, height;   //bounds
    private final int pixels; //pixels
    private final GraphicsContext graphicsContext;    //graphicsContext
    private AnimationTimer animationTimer;  //animation timer
    private long lastTime=0;    //last time
    private int score=0;    //score
    private boolean dead; //if is dead flag
    private boolean flag = true; //pause flag
    private Frame frame; //frame

    Board(double width ,double height){
        super(width, height);

        graphicsContext = this.getGraphicsContext2D(); //get graphicscontext from canvas
        setFocusTraversable(true);
        this.pixels = 10;   //set pixels of entity = 10 like food or any part of snake
        this.width = width; //set width of board
        this.height = height;   //set height of board
        snake.setBounds((int) width - pixels, (int) height - pixels); //set bounds of board
        setKeys();
        draw();
    }

    //initial drawning
    public void draw(){
        refresh();
        drawSnake();
        drawFood();
        drawPause();
    }

    //draw snake
    private void drawSnake(){
        snake.drawSnake(graphicsContext);
    }

    //draw food
    private void drawFood(){
        food.drawFood(graphicsContext);
    }

    //refresh whole board (after pause)
    private void refresh(){
        for(int row=0; row<width; row=row+pixels){
            for(int col=0; col<height; col=col+pixels){
                if(row <= 10 || row >= width-pixels*2 || col <= 10 || col >= height-pixels*2){
                    graphicsContext.setFill(Color.BLACK);
                }else{
                    graphicsContext.setFill(Color.LIGHTGREEN);
                }
                graphicsContext.fillRect(row, col, pixels, pixels);
            }
        }
    }

    //set animation timer for speed of animation
    public void setTimer() {
        animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now){
                if(lastTime == 0){ //if last time = 0 then set last time to current handle time (in nanoseconds)
                    lastTime = now;
                }else{
                    //if current time - last time is greater than 1s (1_000_000_000.0 nano sec) divided by pixels (10)
                    if(now - lastTime > 1_000_000_000.0/pixels){
                        //refresh();
                        food.drawFood(graphicsContext); //draw food
                        dead = snake.move();    //move snake and check if is dead
                        snake.clearLast(graphicsContext);   //clear last position of snakes tail instead refreshing whole board

                        if(snake.isEaten(food)){    //do if when food is eaten
                            snake.grow();           //grow snake
                            setFood();              //set food location and color
                            score++;                //incrementation of score
                            frame.setScore(score);  //set score number in frame
                            frame.setColor(food.getColor());    //set food color in frame
                        }
                        snake.drawSnake(graphicsContext);   //draw snake
                        lastTime=now;   //set last time to current time

                        if(dead){   //if is dead then stop animation
                            animationTimer.stop(); //stop animation
                            snake.drawDeadHead(graphicsContext);    //draw collision
                            drawGameOver();
                        }
                    }
                }
            }
        };
    }

    //set key event when key is pressed
    public void setKeys(){
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){

                if(event.getCode() == KeyCode.LEFT){        //if left arrow then change head direction

                    if(snake.getDirection() != Line.RIGHT){ //cannot change direction to opposite direction
                        snake.setDirection(Line.LEFT);
                    }
                }else if(event.getCode() == KeyCode.RIGHT){ //if right arrow then change head direction

                    if(snake.getDirection() != Line.LEFT){  //cannot change direction to opposite direction
                        snake.setDirection(Line.RIGHT);
                    }
                }else if(event.getCode() == KeyCode.UP){    //if up arrow then change head direction

                    if(snake.getDirection() != Line.DOWN){  //cannot change direction to opposite direction
                        snake.setDirection(Line.UP);
                    }
                }else if(event.getCode() == KeyCode.DOWN){  //if down arrow then change head direction

                    if(snake.getDirection() != Line.UP){    //cannot change direction to opposite direction
                        snake.setDirection(Line.DOWN);
                    }
                }else if(event.getCode() ==  KeyCode.ESCAPE){//if esc button then pause or continue/start game
                    if(flag == false && dead == false){ //if not pause
                        animationTimer.stop(); //stop snake animation
                        drawPause();
                        flag=true;
                    }else if(flag == true && dead == false){ //if pause
                        refresh();
                        animationTimer.start(); //start snake animation
                        flag=false;
                    }
                }

                //System.out.println(snake.getDirection());
            }
        });
    }

    //draw pause text
    public void drawPause(){
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        graphicsContext.fillText("Press ESC key to start", 80, 100);
    }

    //draw game over
    public void drawGameOver(){
        graphicsContext.setFill(Color.RED);
        graphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        graphicsContext.fillText("Game Over!", 180, 100);
    }

    //set food location, color and clear last food position
    public void setFood(){
        food.clearFood(graphicsContext);
        food.setRandomColor();
        setFoodLocation();
    }

    //set food position
    private void setFoodLocation(){
        int x, y;
        Random generator = new Random();
        do{
            x = generator.nextInt((int)(width-pixels*2)/10)*10;
            y = generator.nextInt((int)(height-pixels*2)/10)*10;
        }while(!isCorrect(x,y));

        food.setPosition(x,y);
        food.setRandomColor();
    }

    //check if random food position is correct
    public boolean isCorrect(int x, int y){

        //check if new food position is not in snake body
        LinkedList<Vector> tempBody = snake.getBody();

        for(Vector c: tempBody){
            if(x==c.getX() && y == c.getY()){
                return false;
            }
        }

        //check if food position is not in board border
        if(x<=10){
            return false;
        }

        if(y<=10){
            return false;
        }

        return true;
    }

    //set Frame (BorderPane) for easy access to setScore and setFood
    public void setFrame(Frame frame){
        this.frame=frame;
    }
}
