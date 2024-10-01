package com.example.knightstour2023;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class HelloController {

    HelloApplication app;
    private Button stepButton;
    private Button startButton;
    private Label rowLabel;
    private Label colLable;
    private Label methodLabel;
    private TextField rowTextField;
    private TextField colTextField;
    private TextField methodTextField;
    private AnchorPane anchorPane;
    private Canvas canvas;
    private GraphicsContext gc;
    private int size = 50;
    private Font defaultFont;



    public HelloController(HelloApplication app) {
        this.app = app;
        anchorPane = new AnchorPane();
        canvas = new Canvas(600, 500);

        createGUI();
        attachListeners();

    }

    private void attachListeners() {
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleButtonClicks(actionEvent);
            }
        });

        stepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleButtonClicks(actionEvent);
            }
        });

    }

    private void handleButtonClicks(ActionEvent actionEvent) {
        if(actionEvent.getSource() == startButton) {
            String buttonText = startButton.getText();
            if(buttonText.equals("Start")) {
                Location currentLoc = app.getCurrentLoc();
                if(currentLoc == null) {
                    int row = Integer.parseInt(rowTextField.getText());
                    int col = Integer.parseInt(colTextField.getText());
                    app.setStart(new Location(row, col));
                }
                if(app.getChoice() == 0) {
                    app.setChoice(Integer.parseInt(methodTextField.getText()));
                }
                startButton.setText("Stop");
                app.setRunning(true);

            } else {
                startButton.setText("Start");
                app.setRunning(false);
            }
        }

        if(actionEvent.getSource() == stepButton) {
            String buttonText = stepButton.getText();
            if(buttonText.equals("step")) {
                Location currentLoc = app.getCurrentLoc();
                if(currentLoc == null) {
                    int row = Integer.parseInt(rowTextField.getText());
                    int col = Integer.parseInt(colTextField.getText());
                    app.setStart(new Location(row, col));
                }
                stepButton.setText("step");
                app.setRunning(true);
                app.setStep(true);
            }
        }



    }

    private void createGUI() {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, 600, 500);
        AnchorPane.setLeftAnchor(canvas, 100.0);
        AnchorPane.setTopAnchor(canvas, 100.0);
        anchorPane.getChildren().add(canvas);

        rowLabel = new Label("row");
        colLable = new Label("col");
        AnchorPane.setTopAnchor(rowLabel, 100.0);
        AnchorPane.setRightAnchor(rowLabel, 200.0);
        anchorPane.getChildren().add(rowLabel);


        colLable = new Label("col");
        AnchorPane.setTopAnchor(colLable, 130.0);
        AnchorPane.setRightAnchor(colLable, 200.0);
        anchorPane.getChildren().add(colLable);

        methodLabel = new Label("method");
        AnchorPane.setTopAnchor(methodLabel, 70.0);
        AnchorPane.setRightAnchor(methodLabel, 200.0);
        anchorPane.getChildren().add(methodLabel);

        rowTextField = new TextField();
        rowTextField.setPrefWidth(50);
        AnchorPane.setTopAnchor(rowTextField, 97.0);
        AnchorPane.setRightAnchor(rowTextField, 135.0);
        anchorPane.getChildren().add(rowTextField);

        colTextField = new TextField();
        colTextField.setPrefWidth(50);
        AnchorPane.setTopAnchor(colTextField, 127.0);
        AnchorPane.setRightAnchor(colTextField, 135.0);
        anchorPane.getChildren().add(colTextField);

        methodTextField = new TextField();
        methodTextField.setPrefWidth(50);
        AnchorPane.setTopAnchor(methodTextField, 70.0);
        AnchorPane.setRightAnchor(methodTextField, 135.0);
        anchorPane.getChildren().add(methodTextField);

        startButton = new Button("Start");
        startButton.setPrefWidth(100);
        AnchorPane.setTopAnchor(startButton, 170.0);
        AnchorPane.setRightAnchor(startButton, 140.0);
        anchorPane.getChildren().add(startButton);

        stepButton = new Button("step");
        stepButton.setPrefWidth(100);
        AnchorPane.setTopAnchor(stepButton, 210.0);
        AnchorPane.setRightAnchor(stepButton, 140.0);
        anchorPane.getChildren().add(stepButton);

    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void draw() {
        Location currentLoc = app.getCurrentLoc();
        for(int row = 0; row < HelloApplication.numRows; row++) {
            for(int col = 0; col < HelloApplication.numCols; col++) {

                if (currentLoc != null && row == currentLoc.getRow() && col == currentLoc.getCol()) {
                    drawSingleBox(col * size, row * size, 50, 2, Color.WHITE);
                } else {
                    if(app.getBoard()[row][col] >= 1) {
                        drawSingleBox(col * size, row * size, 50, 2, Color.YELLOW);
                    } else {
                        drawSingleBox(col * size, row * size, 50, 2, Color.BURLYWOOD);
                    }
                }
                if(app.getBoard()[row][col] != 0) {
                    gc.strokeText("" + app.getBoard()[row][col], col * size + 22, row * size + 28);
                }
                if(currentLoc != null) {
                    drawPossibleMoves();
                    drawCounter();
                    // drawExhaustedList(); doesnt work
                }
            }
        }
    }

    public void drawPossibleMoves() {
        ArrayList<Location> arr = app.getPossibleMoves(app.getCurrentLoc()); // store all moves
        for(int row = 0; row < HelloApplication.numRows; row++) {
            for(int col = 0; col < HelloApplication.numCols; col++) {
                for(Location locs: arr) {
                    if(row == locs.getRow() && col == locs.getCol()) {
                        drawSingleBox(col * size, row * size, 50, 2, Color.BLUEVIOLET);
                    }
                }
            }
        }
    }

    public void drawExhaustedList() {
        if(app.checkExhausted(app.getCurrentLoc()).size() == 0) {
            gc.strokeText("NULL", 500, 100);
        } else {
            gc.strokeText("" + app.checkExhausted(app.getCurrentLoc()), 500, 100);
        }
    }

    public void drawCounter() {
        Font font = new Font("Serif", 25);
        Text text = new Text(100, 50, "" + app.getCounter());
        text.setFont(font);
        text.setFill(Color.BLACK);

    }

    public void drawSingleBox(int x, int y, int size, int stroke, Paint color) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, size, size);
        gc.setFill(color);
        gc.fillRect(x + stroke, y + stroke, size-(stroke * 2), size - (stroke * 2));

    }

}