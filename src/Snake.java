import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake implements Direction{

    /*
        Snake class container
     */

    private LinkedList<Vector> body = new LinkedList<>(); // linked list body with Vector objects
    private Line direction = Line.LEFT; //current direction
    private double pixels=10;   //pixels of entity
    private double tempX, tempY;    //remember last element - for grow or clear last element
    private int boundX, boundY; //bounds of board


    //creates first elements
    Snake(){
        body.add(new Vector(300, 300));
        body.add(new Vector(310, 300));
        body.add(new Vector(320, 300));
    }

    //move method
    public boolean move(){
        Vector head = body.getFirst();
        boolean death = false; //is dead
        tempX=head.getX(); tempY=head.getY(); //get head position

        //sets new location for head
        if(direction == Line.LEFT){
            head.setPosition(head.getX()-10, head.getY());
        }else if(direction == Line.RIGHT){
            head.setPosition(head.getX()+10, head.getY());
        }else if(direction == Line.DOWN){
            head.setPosition(head.getX(), head.getY()+10);
        }else if(direction == Line.UP){
            head.setPosition(head.getX(), head.getY()-10);
        }

        //set new location for others
        for(Vector v: body){

            //if first element is head then continue (already moved)
            if(v == head){
                continue;
            }else{  //else move new element
                double oldX, oldY; //declaration of old position
                oldX = v.getX(); oldY = v.getY(); //get old postion for element

                v.setPosition(tempX, tempY);    //set new position

                tempX = oldX; tempY = oldY; //remember last element to move next one

                if(death == false) //if is not dead
                    death = isDead(v);  //then check if is dead
            }
        }

        return death;
    }

    //resize snake
    public void grow(){
        body.add(new Vector(tempX, tempY));
    }

    //check if food is eaten
    public boolean isEaten(Food food){
        if(food.getX() == getHeadX() && food.getY() == getHeadY()){
            return true;
        }else{
            return false;
        }
    }

    //check if is dead
    public boolean isDead(Vector part){
        if(getHeadX() == part.getX() && getHeadY() == part.getY())
            return true;
        else if(getHeadX() == pixels || getHeadX() == getBoundX() - pixels)
            return true;
        else if(getHeadY() == pixels || getHeadY() == getBoundY() - pixels)
            return true;

        return false;
    }

    //get X of head
    public double getHeadX(){
        return body.getFirst().getX();
    }

    //get Y of head
    public double getHeadY(){
        return body.getFirst().getY();
    }

    //get direction (enum)
    public Line getDirection(){
        return direction;
    }

    //set Diretion for snake (enum)
    public void setDirection(Line direction){
        this.direction = direction;
    }

    //set board bounds
    public void setBounds(int boundX, int boundY){
        this.boundX = boundX;
        this.boundY = boundY;
    }

    //get size X of board
    public int getBoundX(){
        return boundX;
    }

    //get size Y of board
    public int getBoundY(){
        return boundY;
    }

    //draw snake with GraphicsContext from canvas
    public void drawSnake(GraphicsContext gc){
        for(Vector part: body){
            gc.setFill(Color.BLACK);
            gc.fillRect(part.getX(), part.getY(), pixels, pixels);
        }
    }

    //draw place of collision
    public void drawDeadHead(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillRect(getHeadX(), getHeadY(), pixels, pixels);
    }

    //clear last element instead refreshing whole board
    public void clearLast(GraphicsContext gc){
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(tempX, tempY, pixels, pixels);
    }

    //return body
    public final LinkedList<Vector> getBody(){
        return body;
    }
}
