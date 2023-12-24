package Snake.Algorithm;

import java.util.ArrayList;
import java.lang.Math;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Field extends Pane{
    private  int w;
    private  int h;
    private Food f;

    private Score cscore = new Score();

    ArrayList<Block> blocks = new ArrayList<Block>();
    Snake snake;

    public Field(int width, int height){
        w = width;
        h = height;

        setMinSize(w * Block.blockSize, h * Block.blockSize);
        setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        generateFood();
    }

    public void addSnake(Snake s){
        this.snake = s;
        for(Block b:s.getBlocks()){
            addBlock(b);
        }
    }

    public void update(){
        for(Block b:blocks){
            b.update();
        }

        if(isEaten(f)){
            // score++;
            cscore.increaseScore();
            generateFood();
            //nambah panjang (add block baru)
            addNewBlock();
        }
    }

    private void addBlock(Block b){
        getChildren().add(b);
        blocks.add(b);
    }

    public void addNewBlock(){
        Block b = new Block(snake.getTail().getOldPosX(), snake.getTail().getOldPosY(), snake.getTail(), this);
        snake.setTail(b);
        addBlock(b);
    }

    public void generateFood() {
        int randomX = (int) (Math.random() * w);
        int randomY = (int) (Math.random() * h);

        Food food = new Food(randomX, randomY);
        getChildren().add(food);
        getChildren().remove(f);
        f = food;

    }

    public boolean isEaten(Food f){
        if(f == null){
            return false;
        }
        return f.getPosX() == snake.getHead().getPosX() && f.getPosY() == snake.getHead().getPosY();
    } 

    public int getW(){
        return w;
    }

    public int getH(){
        return h;
    }

    public Score getCscore() {
        return this.cscore;
    }

    // public int getScore() {
    //     return this.score;
    // }
}
