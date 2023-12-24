package Snake.Algorithm;

import javafx.scene.shape.Rectangle;

public class Block extends Rectangle{
    public static int blockSize = 5;
    private MyDirection direction = MyDirection.LEFT;
    private int posX;
    private int posY;
    private int oldPosX;
    private int oldPosY;
    private Block previous;

    public Block(int x, int y, Block p, Field gameField){
        super(blockSize, blockSize);
        posX = x;
        posY = y;

        setTranslateX(posX * blockSize);
        setTranslateY(posY * blockSize);
        previous = p;
    }

    public void moveUp() {
        posY--;
        // if(posY < 0){
        //     posY = maxY - 1;
        // }
    }

    public void moveDown() {
        posY++;
        // if(posY >= maxY){
        //     posY = 0;
        // }
    }

    public void moveRight() {
        posX++;
        // if(posX >= maxX){
        //     posX = 0;
        // }
    }

    public void moveLeft() {
        posX--;
        // if(posX < 0){
        //     posX = maxX - 1;
        // }
    }

    public void update() {
        oldPosX = posX;
        oldPosY = posY;
        if (previous == null) {
            switch (direction) {
            case UP:
                moveUp();
                break;
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case DOWN:
                moveDown();
                break;
            }
        } else {
            posX = previous.oldPosX;
            posY = previous.oldPosY;
        }
        updatePosition();
    }

    public void updatePosition(){
        setTranslateX(posX * Block.blockSize);
        setTranslateY(posY * Block.blockSize);
    }

    public MyDirection getDirection() {
        return this.direction;
    }

    public void setDirection(MyDirection d) {
        this.direction = d;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getOldPosX() {
        return this.oldPosX;
    }

    public int getOldPosY() {
        return this.oldPosY;
    }
}
