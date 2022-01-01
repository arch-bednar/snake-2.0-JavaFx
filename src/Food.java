import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.util.Random;

public class Food extends Vector{

    private Color color;
    private double pixels = 10;

 /*   Food(double x, double y){
        super(x,y);
    }
*/
    Food(){
        setRandomColor();
        setPosition(400, 400);
    }

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

        this.color=clr;
    }

    public void setColor(Color color){
        this.color=color;
    }

    public Color getColor(){
        return color;
    }

    public void drawFood(GraphicsContext gc){
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), pixels, pixels);
    }

    public void clearFood(GraphicsContext gc){
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(getX(), getY(), pixels, pixels);
    }

}
