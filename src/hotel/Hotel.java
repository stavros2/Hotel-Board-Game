package hotel;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author stavr
 */

public class Hotel extends Application {
    private Image porta1, porta2, porta3;
    private String curSquare;
    private boolean pressedBuyHotel, pressedE; 
    private Instant start, now;
    private Timer myTimer = new Timer();
    private long seconds, minutes, hours;
    private final Image player1Im = new Image("file::../../resources/Players/player1.png", 50, 50, false, false);
    private final Image player2Im = new Image("file::../../resources/Players/player2.png", 50, 50, false, false);
    private final Image player3Im = new Image("file::../../resources/Players/player3.png", 50, 50, false, false);
    private final ImageView p1 = new ImageView(player1Im), p2 = new ImageView(player2Im), p3 = new ImageView(player3Im);
    private Square[][] gameBoard = new Square[12][15];
    private boolean[][] hasEntrance = new boolean[12][15];
    private boolean[][] canBuild = new boolean[12][15];
    private Entrance[][] entrances = new Entrance[12][15];
    private ArrayList<Player> paiktes;
    private ArrayList<HotelProperty> hotelList;
    private Image zari1;
    private ImageView zariv1;
    private Dice zari;
    private Player current;
    private GridPane board;
    private BorderPane border;
    private BorderPane grafiko;
    private Menu menuGame;
    private Menu menuStatistics;
    private MenuBar menuBar;
    private MenuItem menuHotels; 
    private MenuItem menuEntrances; 
    private MenuItem menuProfits;
    private MenuItem menuStart;
    private MenuItem menuStop;
    private MenuItem menuCards;
    private MenuItem menuExit;
    private VBox actions;
    private Button btnRollDice;
    private Button btnRequestBuilding;
    private Button btnBuyHotel;
    private Button btnBuyEntrance;
    private Button btnRequest;
    private int availableHotels;
    private Stage popupCards;
    private FacultySquare ekkinisi;
    private HashMap<Integer, HotelProperty> hotelMap;
    
    /**
     * The graphical interface of the application is 
     * constructed within start and presented to the user.
     * @param primaryStage the Stage where everything is placed
     */
    @Override
    public void start(Stage primaryStage) {
        availableHotels = 0;
        myTimer = new Timer();
        paiktes = new ArrayList<>();
        popupCards = new Stage();
        hotelList = new ArrayList<>();
        porta1 = new Image("file::../../resources/faculties/entrance1.png", 50, 50, false, false);
        porta2 = new Image("file::../../resources/faculties/entrance2.png", 50, 50, false, false);
        porta3 = new Image("file::../../resources/faculties/entrance3.png", 50, 50, false, false);
        zari1 = new Image("file::../../resources/Dices/roll1.png");
        zariv1 = new ImageView(zari1);
        zari = new Dice();
        board = new GridPane();
        border = new BorderPane();
        grafiko = new BorderPane();
        menuGame = new Menu("Game");
        menuStatistics = new Menu("Statistics");
        menuBar = new MenuBar();
        menuHotels = new MenuItem("Hotels"); 
        menuEntrances = new MenuItem("Entrances"); 
        menuProfits = new MenuItem("Profits");
        menuStart = new MenuItem("Start");
        menuStop = new MenuItem("Stop");
        menuCards = new MenuItem("Cards");
        menuExit = new MenuItem("Exit");
        actions = new VBox();
        btnRollDice = new Button("Roll Dice");
        btnRequestBuilding = new Button("Request Building");
        btnBuyHotel = new Button("Buy Hotel");
        btnBuyEntrance = new Button("Buy Entrance");
        btnRequest = new Button("Request 1000 from the Bank");
        final int rows = 12;
        final int columns = 15;
        
        /**
         * Calling arxikopoiisi to initialize the values used in the game
         */
        menuStart.setOnAction((ActionEvent event) -> {
            arxikopoiisi();
        });
        
        /**
         * Stopping any interface capabilities with the application.
         * Also trying (and failing :P) to stop the timer
         */
        menuStop.setOnAction((ActionEvent event) -> {
            myTimer.cancel();
            myTimer.purge();
            border.setDisable(true);
        });
        
        
        /**
         * Constructing a GridPane with information about every hotel 
         */
        menuCards.setOnAction((ActionEvent event) -> {
            GridPane allCards = new GridPane();
            int i = 0;
            for (HotelProperty h: hotelList){
                Button current = new Button(h.getName() + "(" + h.getHotelID() + ")");
                current.setOnAction((ActionEvent event2) -> {
                    int j = 3;
                    Stage koumpi = new Stage();
                    ArrayList<Zevgari> temp = h.getUpgrades();
                    GridPane flow = new GridPane();
                    flow.setPadding(new Insets(5, 0, 5, 0));
                    flow.add(new Text(h.getName() + " has a buy cost of " + h.getBuyCost() + " and a minimum buy cost of " + h.getMinBuyCost() + "."), 0, 0);
                    flow.add(new Text("Each entrance costs " + h.getEntranceCost()), 0, 1);
                    flow.add(new Text("Upgrades cost and give the following benefits:"), 0, 2);
                    for (Zevgari s:temp)
                        flow.add(new Text(s.getX() + " " + s.getY() + " "), 0, j++);
                    
                    koumpi.setScene(new Scene(flow));
                    koumpi.show();
                });
                allCards.add(current, 0, i);
                i++;
            }
            allCards.setAlignment(Pos.CENTER);
            popupCards.setScene(new Scene(allCards, 400, 300));
            popupCards.setTitle("Cards Information");
            popupCards.show();
        });
        
        /**
         * Closing the application using a menu option
         */
        menuExit.setOnAction((ActionEvent event) -> {
            myTimer.cancel();
            myTimer.purge();
            Platform.exit();
        });
        
        /**
         * Presenting information about every hotel. Unlike
         * the menuCards option, this gives information about
         * the game currently played
         */
        menuHotels.setOnAction((ActionEvent event) -> {
            GridPane ksenodoxeia = new GridPane();
            Stage stage = new Stage();
            String msg;
            int i = 0, j = 0;
            for (HotelProperty h : hotelList){
                msg = "Hotel " + h.getName() + "(" + h.getHotelID() + ") ";
                if (h.getBought())
                    msg = msg + "has been bought by " + h.getOwner() + ".";
                else
                    msg = msg + "hasn't been bought yet.";
                msg = msg + "\nIt has a maximum upgrade level of " + h.getMaxUpgrade() + " and it's currently upgraded to level " + h.getCurUpgrade();
                ksenodoxeia.add(new Text(msg), i, j);
                i++;
                if (i == 3){
                    j++;
                    i = 0;
                }
            }
            
            ksenodoxeia.setGridLinesVisible(true);
            stage.setScene(new Scene(ksenodoxeia));
            stage.setTitle("Hotel Information");
            stage.show();
        });
        
        /**
         * Presenting information about the number of entrances each player has
         */
        menuEntrances.setOnAction((ActionEvent event) -> {
            GridPane eisodoi = new GridPane();
            Stage stage = new Stage();
            Text eisodos;
            int i = 0;
            for (Player player:paiktes){
                eisodos = new Text("Player " + player.getName() + " has " + player.getEisodoi().size() + " Entrances");
                eisodos.setFill(player.getXroma());
                eisodoi.add(eisodos, 0, i);
                i++;
            }
            stage.setScene(new Scene(eisodoi, 275, 80));
            stage.setTitle("Entrances");
            stage.show();
        });
        
        /**
         * Presenting information about the maximum amount of money
         * each player has achieved during the game
         */
        menuProfits.setOnAction((ActionEvent event) -> {
           GridPane kerdi = new GridPane();
           Stage stage = new Stage();
           Text kerdos;
           int i = 0;
           for (Player player:paiktes){
               kerdos = new Text("Player " + player.getName() + " has achieved a maximum of " + player.getMaxMoney() + " MediaLab Dollars");
               kerdos.setFill(player.getXroma());
               kerdi.add(kerdos, 0, i);
               i++;
           }
           stage.setScene(new Scene(kerdi));
           stage.setTitle("Profits");
           stage.show();
        });
        
        menuGame.getItems().addAll(menuStart, menuStop, menuCards, menuExit);
        menuStatistics.getItems().addAll(menuHotels, menuEntrances, menuProfits);
        menuBar.getMenus().addAll(menuGame, menuStatistics);
        menuBar.setPadding(new Insets(15, 12, 15, 12));
        menuBar.setStyle("-fx-background-color: #00CCCC;");
        
        /**
         * Rolling the dice and moves the player accordingly.
         * Also resets all the options for each player's round
         */
        btnRollDice.setOnAction((ActionEvent event) ->{
            Stage payment = new Stage();
            FlowPane pliromi = new FlowPane();
            int num = zari.diceRoll();
            String ikona = "file::../../resources/Dices/roll" + num + ".png";
            Image temp = new Image(ikona);
            ImageView temp2 = new ImageView(temp);
            pressedBuyHotel = false;
            pressedE = false;
            
            current = paiktes.get(0);
            paiktes.remove(0);
            paiktes.add(current);
            moveCurrent(num);
            if (paiktes.size() == 3){
                if ((current.getX() == paiktes.get(0).getX() && current.getY() == paiktes.get(0).getY()) || (current.getX() == paiktes.get(1).getX() && current.getY() == paiktes.get(1).getY()))
                    moveCurrent(1);
                if ((current.getX() == paiktes.get(0).getX() && current.getY() == paiktes.get(0).getY()) || (current.getX() == paiktes.get(1).getX() && current.getY() == paiktes.get(1).getY()))
                    moveCurrent(1);
            }
            else if (paiktes.size() == 2)
                if (current.getX() == paiktes.get(0).getX() && current.getY() == paiktes.get(0).getY())
                    moveCurrent(1);
            
            curSquare = gameBoard[current.getX()][current.getY()].getEidos();
            
            actions.getChildren().remove(9);
            actions.getChildren().remove(8);
            actions.getChildren().remove(7);
            actions.getChildren().remove(6);
            actions.getChildren().remove(5);
            actions.getChildren().add(temp2);
            actions.getChildren().add(new Text("Current Square is of type " + curSquare));
            actions.getChildren().add(new Text(paiktes.get(0).getName() + " it's your turn!"));
            actions.getChildren().add(new Text("Pressing Roll Dice will"));
            actions.getChildren().add(new Text("end " + current.getName() + "'s turn"));
            if (hasEntrance[current.getX()][current.getY()] && !entrances[current.getX()][current.getY()].getOwner().getName().equals(current.getName())){
                HotelProperty ksen = hotelMap.get(entrances[current.getX()][current.getY()].getHotel());
                int amountPerDay = ksen.getUpgrades().get(ksen.getCurUpgrade() - 1).getY();
                int payable = amountPerDay * num;
                current.setMoneyHeld(- payable);
                entrances[current.getX()][current.getY()].getOwner().setMoneyHeld(payable);
                pliromi.getChildren().add(new Text("Player " + current.getName() + " has payed " + payable + " MLs to player    " + entrances[current.getX()][current.getY()].getOwner().getName()));
                pliromi.getChildren().add(new Text( "for a " + num + " day stay at " + hotelMap.get(entrances[current.getX()][current.getY()].getHotel()).getName()));
                payment.setScene(new Scene(pliromi));
                payment.setTitle("Payment Done!");
                payment.show();
            }
            
            if (current.getMoneyHeld() < 0)
                curLost();
        });
        
        /**
         * Allows the player to ask for an upgrade to his/her hotel 
         * as long as it's a neighbor to his/her current square of type E
         */
        btnRequestBuilding.setOnAction((ActionEvent event) -> {
            if (pressedE || !curSquare.equals("E") || hasEntrance[current.getX()][current.getY()])
                popupProblem();
            else{
                int thisX = current.getX(), thisY = current.getY();
                ArrayList<Integer> toUpgrade = new ArrayList<>();
                HotelSquare t = null;
                HotelProperty h = null;
                pressedE = true;
                
                if (gameBoard[thisX][thisY - 1].getEidos().equals("X")){
                    t = (HotelSquare) gameBoard[thisX][thisY - 1];
                    h = hotelMap.get(t.getHotelID());
                    if (current.getHotelsOwned().contains(t.getHotelID()) && h.getCurUpgrade() != h.getMaxUpgrade() && !toUpgrade.contains(t.getHotelID())) 
                        toUpgrade.add(t.getHotelID());
                }
                if (gameBoard[thisX][thisY + 1].getEidos().equals("X")){
                    t = (HotelSquare) gameBoard[thisX][thisY + 1];
                    h = hotelMap.get(t.getHotelID());
                    if (current.getHotelsOwned().contains(t.getHotelID()) && h.getCurUpgrade() != h.getMaxUpgrade() && !toUpgrade.contains(t.getHotelID())) 
                        toUpgrade.add(t.getHotelID());
                }
                if (gameBoard[thisX - 1][thisY].getEidos().equals("X")){
                    t = (HotelSquare) gameBoard[thisX - 1][thisY];
                    h = hotelMap.get(t.getHotelID());
                    if (current.getHotelsOwned().contains(t.getHotelID()) && h.getCurUpgrade() != h.getMaxUpgrade() && !toUpgrade.contains(t.getHotelID())) 
                        toUpgrade.add(t.getHotelID());
                }
                if (gameBoard[thisX + 1][thisY].getEidos().equals("X")){
                    t = (HotelSquare) gameBoard[thisX + 1][thisY];
                    h = hotelMap.get(t.getHotelID());
                    if (current.getHotelsOwned().contains(t.getHotelID()) && h.getCurUpgrade() != h.getMaxUpgrade() && !toUpgrade.contains(t.getHotelID())) 
                        toUpgrade.add(t.getHotelID());
                }
                
                if (toUpgrade.isEmpty()){
                    popupProblem();
                    pressedE = false;
                }
                else
                    selectUpgrade(toUpgrade);
                
            }
        });
        
        /**
         * Allows the player to buy a neighboring hotel
         */
        btnBuyHotel.setOnAction((ActionEvent event) -> {
            int thisX = current.getX(), thisY = current.getY();
            ArrayList<Integer> toBuy = new ArrayList<>();
            HotelSquare t;
            if (pressedBuyHotel || !curSquare.equals("H"))
                popupProblem();
            else {
                if (gameBoard[thisX][thisY - 1].getEidos().equals("X")){
                   t = (HotelSquare) gameBoard[thisX][thisY - 1];
                   if (hotelMap.get(t.getHotelID()).getCurUpgrade() == 0 && !hotelMap.get(t.getHotelID()).getOwner().equals("Bank") && !hotelMap.get(t.getHotelID()).getOwner().equals(current.getName()) && !toBuy.contains(t.getHotelID()))
                       toBuy.add(t.getHotelID());
                }
                if (gameBoard[thisX][thisY + 1].getEidos().equals("X")){
                   t = (HotelSquare) gameBoard[thisX][thisY + 1];
                   if (hotelMap.get(t.getHotelID()).getCurUpgrade() == 0 && !hotelMap.get(t.getHotelID()).getOwner().equals("Bank") && !hotelMap.get(t.getHotelID()).getOwner().equals(current.getName()) && !toBuy.contains(t.getHotelID()))
                       toBuy.add(t.getHotelID());
                }
                if (gameBoard[thisX - 1][thisY].getEidos().equals("X")){
                   t = (HotelSquare) gameBoard[thisX - 1][thisY];
                   if (hotelMap.get(t.getHotelID()).getCurUpgrade() == 0 && !hotelMap.get(t.getHotelID()).getOwner().equals("Bank") && !hotelMap.get(t.getHotelID()).getOwner().equals(current.getName()) && !toBuy.contains(t.getHotelID()))
                       toBuy.add(t.getHotelID());
                }
                if (gameBoard[thisX + 1][thisY].getEidos().equals("X")){
                   t = (HotelSquare) gameBoard[thisX + 1][thisY];
                   if (hotelMap.get(t.getHotelID()).getCurUpgrade() == 0 && !hotelMap.get(t.getHotelID()).getOwner().equals("Bank") && !hotelMap.get(t.getHotelID()).getOwner().equals(current.getName()) && !toBuy.contains(t.getHotelID()))
                       toBuy.add(t.getHotelID());
                }
                
                if (toBuy.isEmpty()) 
                    popupProblem();
                else
                    selectHotel(toBuy);
                
                pressedBuyHotel = true;              
            }
        });
       
        /**
         * Allows the player to buy an entrance for his/her hotel
         */
        btnBuyEntrance.setOnAction((ActionEvent event) -> {
            if ((pressedE && !current.getPassedMayor()) || current.getHotelsOwned().isEmpty() || hasEntrance[current.getX()][current.getY()]) 
                popupProblem();
            else if (!pressedE && curSquare.equals("E")){
                findEntrance();
                pressedE = true;
            }
            else if (current.getPassedMayor()){
                findEntrance();
                current.setPassedMayor(false);
            }
            else if (!pressedE && !curSquare.equals("E"))
                popupProblem();
        });
        
        /**
         * Allows the player to ask for the amount of 12000MLs from the bank
         */
        btnRequest.setOnAction((ActionEvent event) -> {
            if (current.getPassedBank())
                current.setMoneyHeld(1000);
            else
                popupProblem();
            current.setPassedBank(false);
        });
        
        actions.setPadding(new Insets(10));
        actions.setSpacing(8);
        actions.getChildren().addAll(btnRollDice, btnRequestBuilding, btnBuyHotel, btnBuyEntrance, btnRequest, zariv1, new Text("Current square is of type S"), new Text(""), new Text(""), new Text(""));
        actions.setAlignment(Pos.CENTER);
        grafiko.setRight(actions);
        
        grafiko.setTop(new Text(" "));
        board.setGridLinesVisible(true);
        for (int i = 0; i < columns; i++){
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / columns);
            board.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < rows; i++){
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rows);
            board.getRowConstraints().add(rowConst);
        }
        
        
        grafiko.setCenter(board);
        grafiko.setDisable(true);
        menuStatistics.setDisable(true);
        border.setTop(menuBar);
        border.setCenter(grafiko);
        
        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
            myTimer.cancel();
            myTimer.purge();
            Platform.exit();
    });
        primaryStage.setTitle("MediaLab Hotel");
        primaryStage.setScene(new Scene(border, 1580, 950));
        primaryStage.centerOnScreen();
        primaryStage.show();
        
        arxikopoiisi();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * After reading the board this method will put the squares in order.
     * We make the convention that the starting square will be located 
     * either at the bottom end or the right end of the board
     */
    public void createConnections(){
        int startX, startY, curX, curY, prevX, prevY;
        Square l;
        boolean flag = true;
        FacultySquare t = null;
        startX = ekkinisi.getX();
        startY = ekkinisi.getY();
        curX = ekkinisi.getX();
        curY = ekkinisi.getY();
        prevX = ekkinisi.getX();
        prevY = ekkinisi.getY();
        while ((curX != startX || curY != startY) || flag){
            flag = false;
            try{
                l = gameBoard[curX][curY - 1];
                if (!l.getEidos().equals("F") && !l.getEidos().equals("X") && (curY - 1) != prevY){
                    t = (FacultySquare) gameBoard[curX][curY];
                    t.setNextX(curX);
                    t.setNextY(curY - 1);
                    prevX = curX;
                    prevY = curY;
                    curY--;
                    continue;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            
            try{
                l = gameBoard[curX + 1][curY];
                if (!l.getEidos().equals("F") && !l.getEidos().equals("X") && (curX + 1) != prevX){
                    t = (FacultySquare) gameBoard[curX][curY];
                    t.setNextX(curX + 1);
                    t.setNextY(curY);
                    prevX = curX;
                    prevY = curY;
                    curX++;
                    continue;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            
            try{
                l = gameBoard[curX][curY + 1];
                if (!l.getEidos().equals("F") && !l.getEidos().equals("X") && (curY + 1) != prevY){
                    t = (FacultySquare) gameBoard[curX][curY];
                    t.setNextX(curX);
                    t.setNextY(curY + 1);
                    prevX = curX;
                    prevY = curY;
                    curY++;
                    continue;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            
            try{
                l = gameBoard[curX - 1][curY];
                if (!l.getEidos().equals("F") && !l.getEidos().equals("X") && (curX - 1) != prevX){
                    t = (FacultySquare) gameBoard[curX][curY];
                    t.setNextX(curX - 1);
                    t.setNextY(curY);
                    prevX = curX;
                    prevY = curY;
                    curX--;
                    continue;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            
        }
    }
    
    /**
     * This method will return a reference to a node in the border GridPane.
     * @param row is the row the node is located.
     * @param col is the column the node is located.
     * @return a reference to the needed node
     */
    public Node getChild(final int row, final int col){
        for (Node node: board.getChildren()){
            if (GridPane.getRowIndex(node) == null) continue;
            if (GridPane.getRowIndex(node) == row  && GridPane.getColumnIndex(node) == col) return node;
        }
        return null;
    }
    
    /**
     * This method is essential to the application. Reading every 
     * piece of input and giving the right values to each field of the class.
     * The timer is also initialized and scheduled with the important task
     * of constantly updating the upper part of the screen with essential 
     * game information.
     */
    public void arxikopoiisi(){
        Stage lazos = new Stage();
        GridPane seira = new GridPane();
        menuStatistics.setDisable(false);
        grafiko.setDisable(false);
        pressedBuyHotel = true;
        pressedE = true;
            
        paiktes.clear();
        paiktes.addAll(Arrays.asList(new Player("Player1", Color.BLUE), new Player("Player2", Color.RED), new Player("Player3", Color.GREEN)));
        Collections.shuffle(paiktes);
        current = paiktes.get(0);
        
        start = Instant.now();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                try{
                    Platform.runLater(this);
                    HBox title = new HBox(5);
                    Text msg;
                    
                    
                    now = Instant.now();
                    long xronos = Duration.between(start, now).toMillis();
                    title.setSpacing(8);
                    seconds = xronos / 1000 % 60;
                    minutes = xronos / (60 * 1000) % 60;
                    hours = xronos / (60 * 1000 * 60);
                    
                    for (Player p:paiktes){
                        msg = new Text(p.getName() + ": " + p.getMoneyHeld());
                        msg.setFill(p.getXroma());
                        title.getChildren().add(msg);
                    };
                    
                    title.getChildren().add(new Text("Available Hotels: " + availableHotels));
                    title.getChildren().add(new Text("Time Elapsed: " + hours + ":" + minutes + ":" + seconds));
                    
                    grafiko.setTop(title);
                }
                catch (IllegalStateException e){
                }
                }
        }, 100l);
        
        seira.add(new Text("This is the order you will be playing with:"),0,0);
        Text text = new Text("1. " + paiktes.get(0).getName());
        text.setFill(paiktes.get(0).getXroma());
        seira.add(text, 0, 1);
        text = new Text("2. " + paiktes.get(1).getName());
        text.setFill(paiktes.get(1).getXroma());
        seira.add(text, 0, 2);
        text = new Text("3. " + paiktes.get(2).getName());
        text.setFill(paiktes.get(2).getXroma());
        seira.add(text, 0, 3);
        seira.add(new Text("Good luck!"), 0, 4);
        lazos.setScene(new Scene(seira, 400, 100));
        lazos.setTitle("Welcome to Hotel!");
        lazos.show();
           
        board.getChildren().clear();
        try{
            Square trexon;
            HashMap<Integer,Integer> hotels = new HashMap<>();
            Random ran = new Random();
            int ranNum = ran.nextInt(2) + 1, tempNum;
            String boardString = "file::../../resources/board" + ranNum + ".txt";
            File arxeio = new File(boardString);
            Scanner myScan = new Scanner(arxeio).useDelimiter(",|\\r\n");
            String kati;
            StackPane temp;
            for (int i = 0; i < 12; i++)
                for (int j = 0; j < 15; j++){
                    hasEntrance[i][j] = false;
                    canBuild[i][j] = true;
                    entrances[i][j] = null;
                    temp = new StackPane();
                    if (myScan.hasNextInt()){
                        tempNum = myScan.nextInt();
                        trexon = new HotelSquare(i, j, "X", tempNum);
                        kati = Integer.toString(tempNum) + ", 0";
                        hotels.put(tempNum, tempNum);
                        temp.getChildren().add(new Text(kati));
                    }
                    else{
                        kati = myScan.next();
                        temp.getChildren().add(new Text(kati));
                        if (kati.equals("F")){ 
                            trexon = new Square(i, j, "F");
                            temp.setStyle("-fx-background-color: #000000;");
                        }
                        else{
                            trexon = new FacultySquare(i, j, kati);
                            switch (kati) {
                                case "B":
                                    temp.getChildren().add(new ImageView (new Image("file::../../resources/faculties/bank.png", 50, 50, false, false)));
                                    break;
                                case "C":
                                    temp.getChildren().add(new ImageView (new Image("file::../../resources/faculties/townhall.png", 50, 50, false, false)));
                                    break;
                                case "S":
                                    temp.getChildren().add(new ImageView (new Image("file::../../resources/faculties/start.png", 50, 50, false, false)));
                                    temp.getChildren().add(p1);
                                    temp.getChildren().add(p2);
                                    temp.getChildren().add(p3);
                                    ekkinisi = new FacultySquare(i, j, "S");
                                    break;
                                default:
                                    break;
                            }
                            temp.setStyle("-fx-background-color: #FFFF00;");
                        }
                    }
                    gameBoard[i][j] = trexon;
                    temp.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
                    board.add(temp,j,i);
                }
            myScan.close(); 

            availableHotels = hotels.size();
            hotelMap = new HashMap<>();
            hotelList = new ArrayList<>();
            for (Integer value: hotels.values()){
                HotelProperty h = new HotelProperty(value);
                hotelMap.put(value, h);
                hotelList.add(h);
            }
               
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }       
        
        actions.getChildren().remove(9);
        actions.getChildren().remove(8);
        actions.getChildren().remove(7);
        actions.getChildren().remove(6);
        actions.getChildren().add(new Text("Current Square is of type S"));
        actions.getChildren().add(new Text(paiktes.get(0).getName() + " is your turn!"));
        actions.getChildren().add(new Text(""));
        actions.getChildren().add(new Text(""));
        for (Player p:paiktes){
            p.setX(ekkinisi.getX());
            p.setY(ekkinisi.getY());
        }
        createConnections();
    }
    
    /**
     * A method for moving the player on the board.
     * @param steps is the number of steps we want to move forward.
     * Each step is one transition from a FacultySquare to its next
     * FacultySquare as indicated from the createConnections() method
     */
    public void moveCurrent(int steps){
        StackPane stack;
        FacultySquare t;
        ImageView temp3;
        
        if (current.getName().equals("Player1"))
            temp3 = p1;
        else if (current.getName().equals("Player2"))
            temp3 = p2;
        else
            temp3 = p3;
        t = (FacultySquare) gameBoard[current.getX()][current.getY()];
        /*stack = (StackPane) getChild(current.getX(), current.getY());
        stack.getChildren().remove(temp3);*/
        for (int i = 0; i < steps; i++){
            t = (FacultySquare) gameBoard[t.getNextX()][t.getNextY()];
            if (t.getEidos().equals("B"))
                current.setPassedBank(true);
            if (t.getEidos().equals("C"))
                current.setPassedMayor(true);
        }
        stack = (StackPane) getChild(t.getX(), t.getY());
        stack.getChildren().add(temp3);
        current.setX(t.getX());
        current.setY(t.getY());
    }
    
    /**
     * Presenting a popup informing the player that the action he/she
     * asked for is not possible under the current circumstances in-game
     */
    public void popupProblem(){
        Stage problem = new Stage();
        FlowPane flow = new FlowPane();
        flow.getChildren().add(new Text("Sorry, you can't perform this action at this time!"));
        problem.setScene(new Scene(flow));
        problem.setTitle("Uh oh!");
        problem.show();
    }
    
    /**
     * Presenting a radiobutton group for the player to choose the hotel
     * he/she wishes to buy.
     * @param selection is a list containing the id of all the hotels the 
     * player can currently buy
     */
    public void selectHotel(ArrayList<Integer> selection) {
        VBox epiloges = new VBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton choice;
        Stage window = new Stage();
        epiloges.getChildren().add(new Text("Select the hotel you want to purchase"));
        for (Integer i: selection){
            choice = new RadioButton("Hotel " + i + " " + hotelMap.get(i).getName() + " with a cost of " + hotelMap.get(i).getBuyCost() + "/" + hotelMap.get(i).getMinBuyCost());
            choice.setToggleGroup(group);
            epiloges.getChildren().add(choice);
        }
        
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
            int answer;
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            String temp = chk.getText();
            try{
                answer = Integer.parseInt(temp.substring(6,8));
            }
            catch (NumberFormatException e){
                answer = Integer.parseInt(temp.substring(6,7));
            }
            currentBuy(answer);
            window.hide();
        });
        
        window.setScene(new Scene(epiloges));
        window.show();
    }
    
    /**
     * After the player chooses the hotel he/she wishes to buy
     * this method will arrange the transaction.
     * @param hotelid is the ID of the hotel the player wishes to buy
     */
    public void currentBuy(int hotelid){
        HotelProperty h = hotelMap.get(hotelid);
        String idioktitis = h.getOwner();
        Player allos = null;
        if (idioktitis.isEmpty()){
            availableHotels--;
            h.setOwner(current.getName());
            current.setMoneyHeld(- h.getBuyCost());
            current.setHotelsOwned(hotelid);
        }
        else{
            allos = findByName(idioktitis);
            h.setOwner(current.getName());
            current.setMoneyHeld(- h.getMinBuyCost());
            current.setHotelsOwned(hotelid);
            allos.setMoneyHeld(h.getMinBuyCost());
            allos.removeHotel(hotelid);
        }
        
        colorSquares(hotelid);
        
        if (current.getMoneyHeld() < 0)
            curLost();
        
        h.setBought(true);
    }
    
    /**
     * A method for identifying a player using his name.
     * @param onoma is the name of the player we want to find.
     * @return a reference to the wanted Player Object
     */
    public Player findByName(String onoma){
        Player thisOne = null;
        for (Player p: paiktes)
            if (p.getName().equals(onoma)) thisOne = p;
        
        return thisOne;
    }
    
    /**
     * Presenting a radiobutton group for the player to choose the hotel
     * he/she wishes to upgrade.
     * @param selection is a list containing the id of all the hotels the 
     * player can currently upgrade
     */
    public void selectUpgrade(ArrayList<Integer> selection) {
        VBox epiloges = new VBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton choice;
        Stage window = new Stage();
        epiloges.getChildren().add(new Text("Select the hotel you want to upgrade"));
        for (Integer i: selection){
            HotelProperty h = hotelMap.get(i);
            choice = new RadioButton("Hotel " + i + " " + h.getName() + " to level " + (h.getCurUpgrade() + 1) + " with a cost of " + h.getUpgrades().get(h.getCurUpgrade()).getX() + " and each day accomidation of " + h.getUpgrades().get(h.getCurUpgrade()).getY());
            choice.setToggleGroup(group);
            epiloges.getChildren().add(choice);
        }
        
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
            int answer;
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            String temp = chk.getText();
            try{
                answer = Integer.parseInt(temp.substring(6,8));
            }
            catch (NumberFormatException e){
                answer = Integer.parseInt(temp.substring(6,7));
            }
            currentUpgrade(answer);
            window.hide();
        });
        
        window.setScene(new Scene(epiloges));
        window.show();
    }
    
    /**
     * After the player selects the hotel he/she wishes to upgrade, this method
     * will determine the result and update the required fields.
     * @param hotelid is the ID of the hotel the player wishes to upgrade
     */
    public void currentUpgrade(int hotelid){
        HotelProperty h = hotelMap.get(hotelid);
        double ran = Math.random();
        Text msg;
        FlowPane minima = new FlowPane();
        Stage popup = new Stage();
        
        if (ran <= 0.5){
            current.setMoneyHeld(- h.getUpgrades().get(h.getCurUpgrade()).getX());
            h.incUpgrade();
            msg = new Text("Upgrade performed at normal cost");
            popup.setTitle("Upgrade Succesful");
            
            upgradeSquares(hotelid);
        }
        else if (ran <= 0.65){
            h.incUpgrade();
            msg = new Text("It's your lucky day! You have been given a free upgrade to your hotel");
            popup.setTitle("Lucky Day");
            
            upgradeSquares(hotelid);
        }
        else if (ran <= 0.8){
            current.setMoneyHeld(- 2 * h.getUpgrades().get(h.getCurUpgrade()).getX());
            h.incUpgrade();
            msg = new Text("Upgrade perfmored at double cost");
            popup.setTitle("Upgrade Succesful");
            
            upgradeSquares(hotelid);
        }
        else {
            msg = new Text("Your upgrade request has been denied. No change to your account");
            popup.setTitle("Nothing Happened");
        }
        
        
        if (current.getMoneyHeld() < 0)
            curLost();
        
        minima.getChildren().add(msg);
        
        popup.setScene(new Scene(minima));
        popup.show();
    }
    
    /**
     * This method will update the GUI with the level of upgrade
     * the newly upgrade hotel has.
     * @param hotelid is the ID of the hotel which was updated
     */
    public void upgradeSquares(int hotelid){
        HotelSquare h = null;
        StackPane stack = null;
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 15; j++)
                if (gameBoard[i][j].getEidos().equals("X")){
                    h = (HotelSquare) gameBoard[i][j];
                    if (h.getHotelID() == hotelid){
                        stack = (StackPane) getChild(i,j);
                        board.getChildren().remove(stack);
                        
                        stack = new StackPane();
                        stack.getChildren().add(new Text(hotelid + ", " + hotelMap.get(hotelid).getCurUpgrade()));
                        stack.setBorder(new Border(new BorderStroke(current.getXroma(), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
                        board.add(stack,j,i);
                    }     
            }
    }
    
    /**
     * This method will update the GUI with the color of the
     * player who just bought the hotel.
     * @param hotelid is the ID of the hotel which was updated
     */
    public void colorSquares(int hotelid){
        HotelSquare h = null;
        StackPane stack = null;
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 15; j++)
                if (gameBoard[i][j].getEidos().equals("X")){
                    h = (HotelSquare) gameBoard[i][j];
                    if (h.getHotelID() == hotelid){
                        stack = (StackPane) getChild(i,j);
                        stack.setBorder(new Border(new BorderStroke(current.getXroma(), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
                    }
                }
    }
    
    /**
     * Presenting a radiobutton group for the player to choose the hotel
     * he/she wishes to buy an entrance for.
     */
    public void findEntrance(){
        ArrayList<Integer> selection = current.getHotelsOwned();
        VBox epiloges = new VBox();
        ToggleGroup group = new ToggleGroup();
        RadioButton choice;
        Stage window = new Stage();
        epiloges.getChildren().add(new Text("Select the hotel you want to buy an entrance for"));
        for (Integer i: selection){
            HotelProperty h = hotelMap.get(i);
            choice = new RadioButton("Hotel " + i + " " + h.getName() + " with a cost of " + h.getEntranceCost());
            choice.setToggleGroup(group);
            epiloges.getChildren().add(choice);
        }
        
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) -> {
            int answer;
            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            String temp = chk.getText();
            try{
                answer = Integer.parseInt(temp.substring(6,8));
            }
            catch (NumberFormatException e){
                answer = Integer.parseInt(temp.substring(6,7));
            }
            buyEntrance(answer);
            window.hide();
        });
        
        window.setScene(new Scene(epiloges));
        window.show();
    }
    
    /**
     * After the player chooses the hotel he/she wishes to buy an entrance for
     * this method will randomly select a suitable E square to put it on.
     * Also updating the GUI with the new information.
     * @param hotelid is the ID of the hotel the player wishes to buy
     * an entrance for
     */
    public void buyEntrance(int hotelid){
        ArrayList<Zevgari> possibilities = new ArrayList<>();
        Entrance created = null;
        HotelSquare t = null;
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 15; j++){
                if (gameBoard[i][j].getEidos().equals("E") && !hasEntrance[i][j] && canBuild[i][j]){
                    if (gameBoard[i][j - 1].getEidos().equals("X")){
                        t = (HotelSquare) gameBoard[i][j - 1];
                        if (t.getHotelID() == hotelid){
                            possibilities.add(new Zevgari(i,j));
                            continue;
                        }
                    }
                    if (gameBoard[i][j + 1].getEidos().equals("X")){
                        t = (HotelSquare) gameBoard[i][j + 1];
                        if (t.getHotelID() == hotelid){
                            possibilities.add(new Zevgari(i,j));
                            continue;
                        }
                    }
                    if (gameBoard[i - 1][j].getEidos().equals("X")){
                        t = (HotelSquare) gameBoard[i - 1][j];
                        if (t.getHotelID() == hotelid){
                            possibilities.add(new Zevgari(i,j));
                            continue;
                        }
                    }
                    if (gameBoard[i + 1][j].getEidos().equals("X")){
                        t = (HotelSquare) gameBoard[i + 1][j];{
                        if (t.getHotelID() == hotelid)
                            possibilities.add(new Zevgari(i,j));
                        continue;
                        }
                    }
                }
            }
        
        if (possibilities.isEmpty() || hotelMap.get(hotelid).getCurUpgrade() == 0)
            popupProblem();
        else{
            StackPane stack = null;
            Image porta;
            if (current.getName().equals("Player1"))
                porta = porta1;
            else if (current.getName().equals("Player2"))
                porta = porta2;
            else 
                porta = porta3;
            
            Collections.shuffle(possibilities);
            created = new Entrance(possibilities.get(0).getX(), possibilities.get(0).getY(), current, hotelid);
            current.setMoneyHeld(- hotelMap.get(hotelid).getEntranceCost());
            current.setEisodoi(created);
            hasEntrance[possibilities.get(0).getX()][possibilities.get(0).getY()] = true;
            canBuild[possibilities.get(0).getX()][possibilities.get(0).getY()] = false;
            entrances[possibilities.get(0).getX()][possibilities.get(0).getY()] = created;
            
            stack = (StackPane) getChild(possibilities.get(0).getX(),possibilities.get(0).getY());
            stack.getChildren().add(new ImageView(porta));
            
            if (current.getMoneyHeld() < 0)
                curLost();
        }
    }

    /**
     * This method will purge everything in possession of
     * a player whose money are less or equal to 0. 
     * It will make entrances and hotels in his possession unobtainable 
     * by the other players. This method will also update the GUI with 
     * no entrances, no player icon and the list with no Player Object.
     * If only 1 player remains in the game, he/she is pronounced winner
     */
    public void curLost(){
        StackPane stack = null;
        Stage telos = new Stage();
        VBox theEnd = new VBox();
        ImageView temp = null;
        
        if (current.getName().equals("Player1"))
            temp = p1;
        else if (current.getName().equals("Player2"))
            temp = p2;
        else
            temp = p3;
        
        paiktes.remove(current);
        stack = (StackPane) getChild(current.getX(), current.getY());
        stack.getChildren().remove(temp);
        
        for (Integer h:current.getHotelsOwned())
            hotelMap.get(h).setOwner("Bank");
        
        for (Entrance e:current.getEisodoi()){
            stack = (StackPane) getChild(e.getX(), e.getY());
            board.getChildren().remove(stack);
                        
            stack = new StackPane();
            stack.getChildren().add(new Text("E"));
            stack.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
            stack.setStyle("-fx-background-color: #FFFF00;");
            board.add(stack,e.getY(),e.getX());
            hasEntrance[e.getX()][e.getY()] = false;
        }
                    
        if (paiktes.size() == 1){
            border.setDisable(true);
            theEnd.getChildren().add(new Text("Congratulations " + paiktes.get(0).getName() + " you are the winner!"));
            telos.setTitle("We have a Winner!");
            telos.setScene(new Scene(theEnd));
            telos.show();
        }
    }
}