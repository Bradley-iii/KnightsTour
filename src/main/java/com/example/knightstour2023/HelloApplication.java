package com.example.knightstour2023;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class HelloApplication extends Application {

    private AnimationTimer animationTimer;
    public static int numRows = 10;
    public static int numCols = 10;
    private int board[][] = new int[numRows][numCols];
    private Stack<Location> stack = new Stack<>();
    private ArrayList<ArrayList<Location>> exhausted = new ArrayList<ArrayList<Location>>(130);
    private Location currentLoc;
    private boolean isRunning = false;
    private boolean step = false;
    private int counter = 1;
    private int choice = 0;


    @Override
    public void start(Stage stage) throws IOException {
        HelloController hc = new HelloController(this);
        Scene rootScene = new Scene(hc.getAnchorPane(), 1024, 768);
        stage.setTitle("Knight's tour before it x_x");
        stage.setScene(rootScene);
        Scanner myScanner = new Scanner(System.in);

        initExhaustedList();


        // Loop the program
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long l) {
                if(isRunning || step && choice != 0) {
                    if(l - lastUpdate >= 1_000_000) {

                        // run the tour;

                        // add the current Loc to the stack

                        // choose a next move

                        if(!finished()) {
                            if (getPossibleMoves(currentLoc).size() == 0) {
                                // if there are no places to go, goback

                                addToExhausted(stack.peek(), currentLoc);

                                Location keep = stack.peek();
                                //System.out.println("-------------------");
                                //System.out.println("Possible moves: " + getPossibleMoves(currentLoc));
                                //System.out.println("Been To List: " + exhausted.get(convertLocToIndex(currentLoc)));
                                //System.out.println(currentLoc);
                                board[currentLoc.getRow()][currentLoc.getCol()] = 0;
                                stack.pop();
                                currentLoc = stack.peek();
                                counter = board[currentLoc.getRow()][currentLoc.getCol()] + 1;
                                clearExhausted(keep);
                            } else {
                                //System.out.println("-------------------");
                                //System.out.println("Possible moves: " + getPossibleMoves(currentLoc));
                                //System.out.println(currentLoc);
                                counter = board[currentLoc.getRow()][currentLoc.getCol()] + 1;

                                currentLoc = getNext(getPossibleMoves(currentLoc));
                                // set the current board to the right counter
                                addToExhausted(stack.peek(), currentLoc);
                                //System.out.println("Been To List: " + exhausted.get(convertLocToIndex(currentLoc)));
                                board[currentLoc.getRow()][currentLoc.getCol()] = counter;
                                // thats it for now
                                //System.out.println("Top of stack = " + stack.peek());
                                stack.add(currentLoc);
                            }
                        } else {
                            System.out.println("DONE");
                        }



                        if(step) {
                            setRunning(false);
                            step = false;
                        }
                        lastUpdate = l;
                    }

                }
                hc.draw();
            }

        };
        animationTimer.start();




        stage.show();
        System.out.println("Which algorithm would you like to use?");
        System.out.println("[1] First Choice"); // BILLION SECONDS
        System.out.println("[2] Walls"); // 11 seconds, 5x5, 2,2 - >2.30m, 8x8, 1,2
        System.out.println("[3] Corners"); // over a minute, 5x5, 2,2 - >2min, 8x8, 1,2
        System.out.println("[4] Walls + Distance"); // 10 seconds, 5x5, 2,2 - 1min 8x8, 1,2
        System.out.println("[5] Walldords rule? (Square with least Space)");
        //choice = myScanner.nextInt();


    }

    // public void clearExhause
    public void clearExhausted(Location loc) {
        int locIndex = convertLocToIndex(loc);
        for(int i = exhausted.get(locIndex).size() - 1; i >= 0; i--) {
            exhausted.get(locIndex).remove(i);
        }
    }

    public ArrayList<Location> getPossibleMoves(Location loc) {
        Location topLeft = new Location(loc.getRow() - 2, loc.getCol() - 1);
        Location topRight = new Location(loc.getRow() - 2, loc.getCol() + 1);
        Location midLeft = new Location(loc.getRow() - 1, loc.getCol() - 2);
        Location midRight = new Location(loc.getRow() -1, loc.getCol() + 2);
        Location bottomMidLeft = new Location(loc.getRow() + 1, loc.getCol() - 2);
        Location bottomMidRight = new Location(loc.getRow() + 1, loc.getCol() + 2);
        Location bottomLeft = new Location(loc.getRow() + 2, loc.getCol() - 1);
        Location bottomRight = new Location(loc.getRow() + 2, loc.getCol() + 1);


        ArrayList<Location> arr = new ArrayList<>();

        if(isValid(topLeft)) {
            if(board[topLeft.getRow()][topLeft.getCol()] == 0) {
                arr.add(topLeft);
            }
        }
        if(isValid(topRight)) {
            if(board[topRight.getRow()][topRight.getCol()] == 0) {
                arr.add(topRight);
            }
        }
        if(isValid(midLeft)) {
            if(board[midLeft.getRow()][midLeft.getCol()] == 0) {
                arr.add(midLeft);
            }
        }
        if(isValid(midRight)) {
            if(board[midRight.getRow()][midRight.getCol()] == 0) {
                arr.add(midRight);
            }
        }
        if(isValid(bottomMidLeft)) {
            if(board[bottomMidLeft.getRow()][bottomMidLeft.getCol()] == 0) {
                arr.add(bottomMidLeft);
            }
        }
        if(isValid(bottomMidRight)) {
            if(board[bottomMidRight.getRow()][bottomMidRight.getCol()] == 0) {
                arr.add(bottomMidRight);
            }
        }
        if(isValid(bottomLeft)) {
            if(board[bottomLeft.getRow()][bottomLeft.getCol()] == 0) {
                arr.add(bottomLeft);
            }
        }
        if(isValid(bottomRight)) {
            if(board[bottomRight.getRow()][bottomRight.getCol()] == 0) {
                arr.add(bottomRight);
            }
        }
        // adds all possible moves that a knight can do and makes sure they are VCALIA



        if(choice == 5) {
            if(exhausted.get(convertLocToIndex(currentLoc)).size() != 0) {
                choice = 4;
            }
        } else {
            for (int i = arr.size() - 1; i >= 0; i--) {
                if (inExhausted(loc, getNext(arr))) {
                    arr.remove(getNext(arr));
                }
            }
        }
        return arr;
    }

    public boolean inExhausted(Location source, Location dest) {
        int sourceIndex = convertLocToIndex(source);
        for(Location loc: exhausted.get(sourceIndex)) {
            if(dest.equals(loc)) {
                return true;
            }
        }
        return false;
        // return true if the destination is in the source exhausted list, false otherwise
    }

    public Location getNext(ArrayList<Location> locs) {

        // given an arraylist of possible, valid moves, choose one
        if(choice == 1) {
            if (locs.size() > 0) {
                return locs.get(0);
            }
        }

        // Algorithm 2, favor the walls
        if(choice == 2) {
            if (locs.size() > 0) {
                Location temp = locs.get(0);
                for (Location temps : locs) {
                    if (temps.getCol() - board[0].length == -1) {
                        return temps; // if it is touching the wall pick it
                    }
                    if (temps.getRow() - board.length == -1) {
                        return temps;
                    }

                    if (temps.getCol() - board[0].length < temp.getCol() - board[0].length) {
                        // if it is closer to the edges, choose it
                        if (temps.getRow() - board.length < temp.getRow() - board.length) {
                            temp = temps;
                        }

                    }
                }
                return temp;
            }
        }



        // Favor corners? kinda worked?\
        if(choice == 3) {
            if (locs.size() > 0) {
                int bottomRight = numCols * numRows;
                int bottomLeft = (board.length + 1) * (numCols - board[0].length);
                int topLeft = (numCols - board[0].length) * (numRows - board.length);
                int topRight = (numCols) * (numRows - board.length);
                Location temp = locs.get(0);
                for (Location temps : locs) {
                    int number = (temps.getCol() + 1) * (temp.getRow() + 1);
                    int tempNumber = (temp.getCol() + 1) * (temp.getRow() + 1);
                    int c = 0;
                    if (Math.abs(number - topLeft) < Math.abs(tempNumber - topLeft)) {
                        c++;
                    }
                    if (Math.abs(number - bottomLeft) < Math.abs(tempNumber - bottomLeft)) {
                        c++;
                    }
                    if (Math.abs(number - topRight) < Math.abs(tempNumber - topRight)) {
                        c++;
                    }
                    if (Math.abs(number - bottomRight) < Math.abs(tempNumber - bottomRight)) {
                        c++;
                    }
                    if (c >= 2) {
                        temp = temps;
                    }
                }
                return temp;
            }
        }


        // WTF DOES THIS DO BUT IT MIGHT BE GOOD
        if(choice == 4) {
            if (locs.size() > 0) {
                Location temp = locs.get(0);
                for (Location temps : locs) {
                    double distanceFormulaTemps = Math.sqrt((temps.getCol() - stack.peek().getCol()) ^ 2 + (temps.getRow() - stack.peek().getRow()) ^ 2);
                    double distanceFormulaTemp = Math.sqrt((temp.getCol() - stack.peek().getCol()) ^ 2 + (temp.getRow() - stack.peek().getRow()) ^ 2);
                    if (temps.getCol() - board[0].length == -1) {
                        return temps; // if it is touching the wall pick it
                    }
                    if (temps.getRow() - board.length == -1) {
                        return temps;
                    }
                    if (distanceFormulaTemps < distanceFormulaTemp) {
                        temp = temps;
                    }
                }
                return temp;
            }
        }

        if(choice == 5) {
            if (locs.size() > 0) {
                Location temp = locs.get(0);
                for(Location temps: locs) {
                    try {
                        int possibleMoveTemps = getPossibleMoves(temps).size();
                        int possibleMoveTemp = getPossibleMoves(temp).size();
                        if(possibleMoveTemps < possibleMoveTemp) {
                            temp = temps;
                        }
                    } catch (StackOverflowError e) {
                        System.out.println("cant");
                        return currentLoc;
                    }
                }
                return temp;
            }
        }

        return currentLoc;
    }

    public int getCounter() {
        return counter;
    }

    public boolean finished() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }



    public void addToExhausted(Location source, Location dest) {
        // add to the source Location, we have tried the dest location
        int sourceIndex = convertLocToIndex(source);
        exhausted.get(sourceIndex).add(dest);
    }

    public ArrayList<Location> checkExhausted(Location loc) {
        return exhausted.get(convertLocToIndex(loc));
    }

    public boolean isValid(Location loc) {
        return loc.getRow() < board.length && loc.getRow() >= 0 && loc.getCol() < board[0].length && loc.getCol() >= 0;
    }



    public int convertLocToIndex(Location loc) {
        // convert a location to an index in the exhausted list
        return (loc.getRow()*(numCols)) + loc.getCol();
    }



    public void setRunning(boolean val) {
        isRunning = val;
    }

    public void setStep(boolean val) {
        step = val;
    }

    public Location getCurrentLoc() {
        return currentLoc;
    }

    public void setStart(Location loc) {
        currentLoc = loc;
        board[currentLoc.getRow()][currentLoc.getCol()] = 1;
        stack.add(currentLoc);
    }

    private void initExhaustedList() {
        for(int i = 0; i < 130; i++) {
            exhausted.add(new ArrayList<Location>());
        }
    }

    public boolean getStep() {
        return step;
    }


    public int[][] getBoard() {
        return board;

    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public static void main(String[] args) {
        launch();
    }
}