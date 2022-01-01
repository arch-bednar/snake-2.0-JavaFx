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

    private double width, height;
    private int pixels;
    private GraphicsContext graphicsContext;
    private AnimationTimer animationTimer;
    private long lastTime=0;
    private int score=0;
    private boolean dead;
    private boolean flag = true;
    private Frame frame;

    Board(double width ,double height){
        super(width, height);

        graphicsContext = this.getGraphicsContext2D();
        setFocusTraversable(true);
        this.pixels = 10;
        this.width = width;
        this.height = height;
        snake.setBounds((int) width - pixels, (int) height - pixels);
        setKeys();
        draw();
    }

    public void draw(){
        refresh();
        drawSnake();
        drawFood();
        drawPause();
    }

    private void drawSnake(){
        snake.drawSnake(graphicsContext);
    }

    private void drawFood(){
        food.drawFood(graphicsContext);
    }

    private void refresh(){
        for(int row=0; row<width; row=row+pixels){
            for(int col=0; col<height; col=col+pixels){
                if(row <= 10 || row >= width-pixels*2 || col <= 10 || col >= height-pixels*2){
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillRect(row, col, pixels, pixels);
                }else{
                    graphicsContext.setFill(Color.LIGHTGREEN);
                    graphicsContext.fillRect(row, col, pixels, pixels);
                }
            }
        }
    }

    public void setTimer() {
        animationTimer = new AnimationTimer(){
            @Override
            public void handle(long now){
                if(lastTime == 0){
                    lastTime = now;
                    return;
                }else{
                    if(now - lastTime > 1_000_000_000.0/pixels){
                        //refresh();
                        food.drawFood(graphicsContext);
                        dead = snake.move();
                        snake.clearLast(graphicsContext);

                        if(snake.isEaten(food)){
                            snake.grow();
                            setFood();
                            score++;
                            frame.setScore(score);
                            frame.setColor(food.getColor());
                        }
                        snake.drawSnake(graphicsContext);
                        lastTime=now;

                        if(dead){
                            animationTimer.stop();
                            snake.drawDeadHead(graphicsContext);
                        }
                    }
                }
            }
        };
    }

    public void setKeys(){
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){

                if(event.getCode() == KeyCode.LEFT){

                    if(snake.getDirection() != Line.RIGHT){
                        snake.setDirection(Line.LEFT);
                    }
                }else if(event.getCode() == KeyCode.RIGHT){

                    if(snake.getDirection() != Line.LEFT){
                        snake.setDirection(Line.RIGHT);
                    }
                }else if(event.getCode() == KeyCode.UP){

                    if(snake.getDirection() != Line.DOWN){
                        snake.setDirection(Line.UP);
                    }
                }else if(event.getCode() == KeyCode.DOWN){

                    if(snake.getDirection() != Line.UP){
                        snake.setDirection(Line.DOWN);
                    }
                }else if(event.getCode() ==  KeyCode.ESCAPE){
                    if(flag == false){
                        animationTimer.stop();
                        drawPause();
                        flag=true;
                    }else{
                        refresh();
                        animationTimer.start();
                        flag=false;
                    }

                }

                System.out.println(snake.getDirection());
            }
        });
    }

    public void drawPause(){
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        graphicsContext.fillText("Press ESC key to start", 80, 100);
    }

    public void setFood(){
        food.clearFood(graphicsContext);
        food.setRandomColor();
        setFoodLocation();
    }

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

    public boolean isCorrect(int x, int y){
        LinkedList<Vector> tempBody = snake.getBody();

        for(Vector c: tempBody){
            if(x==c.getX() && y == c.getY()){
                return false;
            }
        }

        if(x<=10){
            return false;
        }

        if(y<=10){
            return false;
        }

        return true;
    }

    public void setFrame(Frame frame){
        this.frame=frame;
    }
}
