interface Engine{

    /*
        Interface for main engine
     */

    Food food = new Food();     //final public food
    Snake snake = new Snake();  //final public snake

    void setTimer();            //set animation timer
    void setKeys();             //setOnKeyPressed
    void setFood();             //set Food color and position
}