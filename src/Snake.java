import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake implements Direction{

    private LinkedList<Vector> body = new LinkedList<>();
    private Line direction = Line.LEFT;
    private double pixels=10;
    private double tempX, tempY;
    private int boundX, boundY;


    Snake(){
        body.add(new Vector(300, 300));
        body.add(new Vector(310, 300));
        body.add(new Vector(320, 300));
    }

    synchronized boolean move(){
        Vector head = body.getFirst();
        boolean death = false;
        tempX=head.getX(); tempY=head.getY();

        if(direction == Line.LEFT){
            head.setPosition(head.getX()-10, head.getY());
        }else if(direction == Line.RIGHT){
            head.setPosition(head.getX()+10, head.getY());
        }else if(direction == Line.DOWN){
            head.setPosition(head.getX(), head.getY()+10);
        }else if(direction == Line.UP){
            head.setPosition(head.getX(), head.getY()-10);
        }

        for(Vector v: body){
            if(v == head){
                continue;
            }else{
                double oldX, oldY;
                oldX = v.getX(); oldY = v.getY();

                v.setPosition(tempX, tempY);

                tempX = oldX; tempY = oldY;

                if(death == false)
                    death = isDead(v);
            }
        }

        return death;
    }

    synchronized void grow(){
        body.add(new Vector(tempX, tempY));
    }

    public boolean isEaten(Food food){
        if(food.getX() == getHeadX() && food.getY() == getHeadY()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isDead(Vector part){
        if(getHeadX() == part.getX() && getHeadY() == part.getY())
            return true;
        else if(getHeadX() == pixels || getHeadX() == getBoundX() - pixels)
            return true;
        else if(getHeadY() == pixels || getHeadY() == getBoundY() - pixels)
            return true;

        return false;
    }

    public double getHeadX(){
        return body.getFirst().getX();
    }

    public double getHeadY(){
        return body.getFirst().getY();
    }

    public Line getDirection(){
        return direction;
    }

    public void setDirection(Line direction){
        this.direction = direction;
    }

    public void setBounds(int boundX, int boundY){
        this.boundX = boundX;
        this.boundY = boundY;
    }

    public int getBoundX(){
        return boundX;
    }

    public int getBoundY(){
        return boundY;
    }

    synchronized void drawSnake(GraphicsContext gc){
        for(Vector part: body){
            gc.setFill(Color.BLACK);
            gc.fillRect(part.getX(), part.getY(), pixels, pixels);
        }
    }

    public void drawDeadHead(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillRect(getHeadX(), getHeadY(), pixels, pixels);
    }

    public void clearLast(GraphicsContext gc){
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(tempX, tempY, pixels, pixels);
    }

    public final LinkedList<Vector> getBody(){
        return body;
    }
}
