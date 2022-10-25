package ver2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author idole
 */
public class Game 
{

    public int k = 1;
    
    private ClientData player1;
    private ClientData player2;
    private ClientData currentPlayer;      	// לשמירת סימן השחקן הנוכחי
    private Model logicModel;
    private Move move;
    private boolean checkPress;
    

    public Game(ClientData player1, ClientData player2,int user1,int user2) 
    {
        startGame(player1 ,player2 ,user1 ,user2);
    }
    
    public void startGame(ClientData playerOne, ClientData playerTwo, int user1, int user2) 
    {
        this.player1 = playerOne;
        this.player2 = playerTwo;
        checkPress = false;
        //logicModel = new Model();
        
        initGame();
        
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {  
            
            
            //play.writePlayer1(play.getLogicModel().);
            while(logicModel.isGameOver() == false)
            {
                try {
                    
                    
                    //writePlayer1(new Message(Const.SET_TITLE, user1));
                    writePlayer1(new Message(Const.SET_TITLE, user1));
                    writePlayer2(new Message(Const.SET_TITLE, user2));
                    
                    writePlayer1(new Message(Const.YOUR_TURN,getLocationPlayer()));
                    writePlayer2(new Message(Const.PARTNER_TURN));
                    
                    setCurrentPlayer(getPlayer1());
                    
                    Message loc = null;
                    checkPress = false;
                    
                    while(checkPress == false)
                    {
                    loc  = getPlayer1().readMessage();
                    if(loc == null)
                        break;
                    //proccessStateOfGameOver(loc);
                    doTurn(1,loc);
                    }
                    
                    if(logicModel.isGameOver() == true)
                    {
                        break;
                    }   
                    
                    writePlayer1(Const.PARTNER_TURN);
                    writePlayer2(new Message(Const.YOUR_TURN,getLocationPlayer()));
                    setCurrentPlayer(getPlayer2());
                    
                    loc = null;
                    checkPress = false;
                    
                    
                    while(checkPress == false)
                    {
                    loc  = getPlayer2().readMessage();
                    if(loc == null)
                        break;
                    //proccessStateOfGameOver(loc);
                    doTurn(2,loc);
                    }
//                    
                    if(k == 1)
                        setStateBeforeWin();
                    
                    k++;
                } catch (IOException ex) 
                {
                    System.out.println("game - close");
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }

        
            }
            
                try {
                    
                    //game over  - המשחק נגמר
                    System.out.println("win-----------------");
                    gameOver(player1,player2,user1,user2);
                    
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }

            

           

            

        }).start();
        
    }
        
        
    
    public void doTurn(int i, Message msg) throws IOException, ClassNotFoundException
    {
        
        boolean whereHePress = whereHePress(msg);

        if(whereHePress)
            {
                boolean chckEat = checkEat(msg);

                if(chckEat == false)
                {
                    move = logicModel.getMoveSo(msg.getLoc().getRow(),msg.getLoc().getCol());
                    ArrayList<Location> listShow =  logicModel.getAllPossibleMoves(msg.getLoc().getRow(), msg.getLoc().getCol(),logicModel.getCurrentPlayer());
                    conversionModelToViewAraayy(listShow);
                    ArrayList<Location> listLoc = logicModel.getLocationPlayer();
                    conversionModelToViewAraayy(listLoc);
                    Location locOld = msg.getLoc();
                    locOld.setRow(conversionModelToView(msg.getLoc().getRow()));
                    locOld.setCol(conversionModelToView(msg.getLoc().getCol()));
                    
                    if(i == 1)
                    {
                    writePlayer1(new Message(Const.SHOW_POSBILE_MOVES_TURN,listShow,listLoc,'b',locOld));
                    writePlayer2(new Message(Const.SHOW_POSBILE_MOVES_NO_TURN,listShow,listLoc,'y',locOld));
                    }
                    else
                    {
                    writePlayer1(new Message(Const.SHOW_POSBILE_MOVES_NO_TURN,listShow,listLoc,'b',locOld));
                    writePlayer2(new Message(Const.SHOW_POSBILE_MOVES_TURN,listShow,listLoc,'y',locOld));
                    }
                    checkPress = false;
                }

                else
                {
                    setEat(msg);
                    int row  = conversionModelToView(logicModel.getMove().getDestinationRow());
                    int col  = conversionModelToView(logicModel.getMove().getDestinationCol());


                    writePlayer1(new Message(Const.SET_MOVE_AND_SWITCH,new Location(row, col),logicModel.getMove()));
                    writePlayer2(new Message(Const.SET_MOVE_AND_SWITCH,new Location(row, col),logicModel.getMove()));
                    
                    
                    checkPress = true;
                }

            }
        else
            {
               doMove(msg);
               int row  = conversionModelToView(logicModel.getMove().getDestinationRow());
               int col  = conversionModelToView(logicModel.getMove().getDestinationCol());
               
               writePlayer1(new Message(Const.SET_MOVE_AND_SWITCH, new Location(row, col),logicModel.getMove()));
               writePlayer2(new Message(Const.SET_MOVE_AND_SWITCH,new Location(row, col),logicModel.getMove()));
               
               checkPress = true;
            }
                    
    }
    
    public void proccessStateOfGameOver(Message loc) throws IOException 
    {
        if(loc.getMsg().equals(Const.INIT_STATE_BEFORE_WIN))
        {
            setStateBeforeWin();
            
        }
        System.out.println("in game - game over");
                    
    }
    
        //logicModel = new Model();
    

    public void setCurrentPlayer(ClientData player) 
    {
        this.currentPlayer = player;
    }
        
    public void setStateBeforeWin() throws IOException 
    {
        logicModel.initStateBeforeWin();
        //logicModel.printMat();
        writePlayer1(Const.INIT_STATE_BEFORE_WIN);
        writePlayer2(new Message(Const.INIT_STATE_BEFORE_WIN));
        //logicModel.printMat();
    }
    
    public ClientData getPlayer1() 
    {
        return player1;
    }
    
    public ClientData getPlayer2() 
    {
        return player2;
    }
    
    public void initGame()
    {
        // איתחול הלוח הלוגי
        logicModel = new Model();
        //logicModel.initGameLogic();
    }

    public Model getLogicModel() {
        return logicModel;
    }
    
    public void writePlayer1(Message obj) throws IOException 
    {
        player1.writeMessage(obj);
    }
    
    public void writePlayer1(String msg) throws IOException 
    {
        player1.writeMessage(msg);
    }
    public void writePlayer2(Message obj) throws IOException 
    {
        player2.writeMessage(obj);
    }
    
    public Message readPlayer1() throws IOException, ClassNotFoundException
    {
        Message obj =  (Message)player1.readMessage();
        
        return obj;
    }
    
    public Message readPlayer2() throws IOException, ClassNotFoundException
    {
        Message obj =  (Message)player2.readMessage();
        
        return obj;
    }

    public boolean whereHePress(Message loc) 
    {
        int row = loc.getLoc().getRow();
        int col = loc.getLoc().getCol();
        
//        row = conversionViewToModel(row);
//        col = conversionViewToModel(col);
        
        boolean howPress = logicModel.getHowPress(row,col);
        
        
      return howPress;
        
    }
    
    public int conversionViewToModel(int x)
    {
        x = x /2 ;
        return x;
    }

     public void gameOver(ClientData player1, ClientData player2, int user1, int user2) throws IOException, InterruptedException 
     {
        char player = logicModel.getOtherPlayer();
        writePlayer1(new Message(Const.GAME_OVER,player));
        writePlayer2(new Message(Const.GAME_OVER,player));
        initGame();
        Thread.sleep(5000);
        startGame(player1, player2, user1, user2);
         
     }
    

    public boolean checkEat(Message loc) 
    {
//        int newRow = conversionViewToModel(loc.getLoc().getRow());
//        int newCol = conversionvi(loc.getLoc().getCol());
        int newRow = loc.getLoc().getRow();
        int newCol  = loc.getLoc().getCol();
       
        
        boolean checkEat = logicModel.chckEat(newRow,newCol,logicModel.getCurrentPlayer());
        System.out.println("b >>>>>>>>>>>>>>>>" + checkEat);
        return checkEat;
    }
     
       
    public ArrayList<Location> buildMove(Message loc) 
    {
        move = logicModel.getMoveSo(loc.getLoc().getRow(),loc.getLoc().getCol());
        ArrayList<Location> pusbileMoves = logicModel.getAllPossibleMoves(move.getSourceRow(), move.getSourceCol(),logicModel.getCurrentPlayer());
        conversionModelToViewAraayy(pusbileMoves);
        System.out.println(pusbileMoves);
        return pusbileMoves;
    }
    
    public ArrayList<Location> conversionModelToViewAraayy(ArrayList<Location> con)
    {
        for (int i = 0; i < con.size() ; i++) 
        {
            con.get(i).setRow(con.get(i).getRow()*2);
            con.get(i).setCol(con.get(i).getCol()*2);
        }
        return con;
    }

    public Move setEat(Message loc) 
    {
        move = logicModel.getMoveDe(loc.getLoc().getRow(),loc.getLoc().getCol());
        move.setIfEat(true);
        moveTo(move);
        move = null; 
        logicModel.switchPlayer();
        return move;
    }
    
    public void moveTo(Move move) 
    {
        logicModel.applyMove(move);
        
        int newRow = move.getDestinationRow();
        int newCol = move.getDestinationCol();
        int oldRow = move.getSourceRow();
        int oldCol = move.getSourceCol();
        
//        
//        newRow = conversionModelToView(newRow);
//        newCol = conversionModelToView(newCol);
//        oldRow = conversionModelToView(oldRow);
//        oldCol = conversionModelToView(oldCol);
//        
        
        logicModel.printMat();
       
    }
    
    public int conversionModelToView(int x)
    {
        x = x * 2 ;
        return x;
    }
    
    public ArrayList<Location> getLocationPlayer()
    {
        ArrayList<Location> locationPlayer; 
        locationPlayer = logicModel.getLocationPlayer();
        locationPlayer = conversionModelToViewAraayy(locationPlayer);
        
        return locationPlayer;
    }

    public Move doMove(Message loc) 
    {
        move = logicModel.getMoveDe(loc.getLoc().getRow(),loc.getLoc().getCol());
        logicModel.setdestinationLoc(loc.getLoc().getRow(),loc.getLoc().getCol());
        moveTo(move);
        logicModel.switchPlayer();
        return move;
    }

    public ArrayList<Location> getListOfBlue() 
    {
       return logicModel.getButtonOfPlayerLoc(Model.BLUE_PLAYER); 
    }

    public ArrayList<Location> getListOfYellow() 
    {
        return logicModel.getButtonOfPlayerLoc(Model.YELLOW_PLAYER); 
    }


   

    }

