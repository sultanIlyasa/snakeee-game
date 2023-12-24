package Snake.Algorithm;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{
    private int posX;
    private int posY;

    public Food(int x, int y) {
        super(Block.blockSize, Block.blockSize);
        this.posX = x;
        this.posY = y;

        setTranslateX(posX * Block.blockSize);
        setTranslateY(posY * Block.blockSize);

        setFill(Color.GREEN);
        setStroke(Color.BLACK);
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

}
