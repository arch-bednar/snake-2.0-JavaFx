import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Food extends Vector{

    /*
        Food class based on vector
     */

    private Color color; //food color
    private double pixels = 10; //each bound of food

    Food(){
        setRandomColor(); //set random color
        setPosition(400, 400);  //set position
    }

    //set random color
    public void setRandomColor(){
        Random generator = new Random();
        Color clr = null;
        int seed= generator.nextInt(11);

        if(seed==0){
            clr=Color.RED;
        }else if(seed==1){
            clr=Color.YELLOW;
        }else if(seed==2){
            clr=Color.PINK;
        }else if(seed==3){
            clr=Color.ORANGE;
        }else if(seed==4){
            clr=Color.BLUE;
        }else if(seed==5){
            clr=Color.BROWN;
        }else if(seed==6){
            clr=Color.CYAN;
        }else if(seed==7){
            clr=Color.DARKSALMON;
        }else if(seed==8){
            clr=Color.INDIGO;
        }else if(seed==9){
            clr=Color.SNOW;
        }else if(seed==10){
            clr=Color.LIME;
        }

        //this.color=clr;
        setColor(clr);
    }

    //set Color
    public void setColor(Color color){
        this.color=color;
    }

    //get color
    public Color getColor(){
        return color;
    }

    //draw food with GraphicsContext from canvas
    public void drawFood(GraphicsContext gc){
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), pixels, pixels);
    }

    //clear last food position
    public void clearFood(GraphicsContext gc){
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(getX(), getY(), pixels, pixels);
    }

}
