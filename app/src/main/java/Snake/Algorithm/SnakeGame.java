package Snake.Algorithm;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SnakeGame extends Application {

    private int width = 100;
    private int height = 100;
    private long then = System.nanoTime();
    private long second = 1000000000;
    private int spd = 5;
    private boolean isRunning = false;
    private Field gameField;
    private Scene gameScene;
    private Scene scoreScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        gameField = new Field(width, height);

        HBox scoreBox = new HBox(10);
        Label scoreLabel = new Label("Score: " + gameField.getCscore().getScore());
        scoreLabel.setFont(Font.font("helvetica", 16));
        Button scoreBtn = new Button("Highscore");
        scoreBtn.setFocusTraversable(false);
        scoreBtn.setOnAction(e -> primaryStage.setScene(scoreScene));
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().addAll(scoreLabel, scoreBtn);

        HBox controlBox = new HBox(10);
        controlBox.setAlignment(Pos.CENTER);

        VBox spacebarBox = new VBox(10);
        Text spacebarText = new Text();
        spacebarText.setText("Play/Pause");
        spacebarText.setFont(Font.font("helvetica", 16));
        ImageView spacebarIcon = new ImageView("spacebar.jpeg");
        spacebarIcon.setFitWidth(80);
        spacebarIcon.setFitHeight(40);
        spacebarBox.setAlignment(Pos.CENTER);
        spacebarBox.getChildren().addAll(spacebarIcon, spacebarText);

        VBox arrowBox = new VBox(10);
        Text arrowText = new Text();
        arrowText.setText("Movement");
        arrowText.setFont(Font.font("helvetica", 16));
        ImageView arrowIcon = new ImageView("arrow.jpeg");
        arrowIcon.setFitWidth(80);
        arrowIcon.setFitHeight(40);
        arrowBox.setAlignment(Pos.CENTER);
        arrowBox.getChildren().addAll(arrowIcon, arrowText);

        VBox shiftBox = new VBox(10);
        Text shiftText = new Text();
        shiftText.setText("Boost");
        shiftText.setFont(Font.font("helvetica", 16));
        ImageView shiftIcon = new ImageView("shift.png");
        shiftIcon.setFitWidth(80);
        shiftIcon.setFitHeight(40);
        shiftBox.setAlignment(Pos.CENTER);
        shiftBox.getChildren().addAll(shiftIcon, shiftText);

        Label pauseText = new Label("PAUSED");
        pauseText.setTextFill(Color.GRAY);
        pauseText.setFont(Font.font("helvetica", 32));
        pauseText.layoutXProperty().bind(gameField.widthProperty().subtract(pauseText.widthProperty()).divide(2));
        pauseText.layoutYProperty().bind(gameField.heightProperty().subtract(pauseText.heightProperty()).divide(2));
        gameField.addSnake(new Snake(Snake.initL, gameField));
        gameField.getChildren().add(pauseText);

        // highscore scene
        Label HScoreLabel = new Label("HIGHSCORE");
        HScoreLabel.setFont(Font.font("helvetica", 32));
        Button backToGame = new Button("back");
        backToGame.setOnAction(e -> primaryStage.setScene(gameScene));
        HBox scoreSceneHeader = new HBox(10);
        scoreSceneHeader.getChildren().addAll(backToGame, HScoreLabel);
        HBox.setMargin(backToGame, new Insets(10, 100, 0, 10));

        gameField.getCscore().sortHighScore();
        Label scoreList = new Label(gameField.getCscore().readScore());
        scoreList.setFont(Font.font("helvetica", 16));
        VBox scoreTable = new VBox(10);
        scoreTable.setAlignment(Pos.TOP_CENTER);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                if (now - then > second / spd) {
                    if (isRunning == true) {
                        gameField.update();
                        then = now;
                        scoreLabel.setText("Score: " + gameField.getCscore().getScore());
                        if (gameField.snake.isDead()) {
                            isRunning = false;
                            stop();
                            Alert al = new Alert(AlertType.INFORMATION);
                            al.setHeaderText("GAME OVER");
                            al.setContentText("Your Score Is " + gameField.getCscore().getScore());

                            // savescore ke txt tiap mati
                            try {
                                gameField.getCscore().saveScore();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            // update isi txt tiap mati
                            try {
                                gameField.getCscore().sortHighScore();
                                scoreList.setText(gameField.getCscore().readScore());
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            gameField.getCscore().resetScore();
                            Platform.runLater(al::showAndWait);

                            al.setOnHidden(e -> {
                                root.getChildren().clear();
                                gameField = new Field(width, height);
                                gameField.addSnake(new Snake(Snake.initL, gameField));
                                pauseText.setVisible(true);
                                gameField.getChildren().add(pauseText);
                                scoreLabel.setText("Score: 0");
                                root.getChildren().addAll(scoreBox, gameField, controlBox);
                                start();
                            });
                        }
                    }

                }
            }
        };
        timer.start();

        controlBox.getChildren().addAll(spacebarBox, arrowBox, shiftBox);
        root.getChildren().addAll(scoreBox, gameField, controlBox);
        gameScene = new Scene(root);

        scoreTable.getChildren().addAll(scoreSceneHeader, scoreList);
        scoreTable.setMinSize(gameField.getW() * Block.blockSize, gameField.getH() * Block.blockSize);
        scoreScene = new Scene(scoreTable);

        // game logic game event
        gameScene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.UP) && gameField.snake.getDirection() != MyDirection.DOWN) {
                gameField.snake.setDirection(MyDirection.UP);
            }
            if (e.getCode().equals(KeyCode.DOWN) && gameField.snake.getDirection() != MyDirection.UP) {
                gameField.snake.setDirection(MyDirection.DOWN);
            }
            if (e.getCode().equals(KeyCode.LEFT) && gameField.snake.getDirection() != MyDirection.RIGHT) {
                gameField.snake.setDirection(MyDirection.LEFT);
            }
            if (e.getCode().equals(KeyCode.RIGHT) && gameField.snake.getDirection() != MyDirection.LEFT) {
                gameField.snake.setDirection(MyDirection.RIGHT);
            }
            if (e.getCode().equals(KeyCode.SPACE)) {
                isRunning = !isRunning;
                pauseText.setVisible(!isRunning);
            }
            if (e.getCode().equals(KeyCode.SHIFT)) {
                spd = 20;
            }
        });

        gameScene.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.SHIFT)) {
                spd = 5;
            }
        });

        primaryStage.setTitle("Game Ular wuswus");
        primaryStage.setResizable(false);
        primaryStage.setScene(gameScene);
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.show();

    }
}
