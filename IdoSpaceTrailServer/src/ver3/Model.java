/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

import java.util.ArrayList;


/**
 *
 * @author idole
 */
public class Model 
{
    // קבועים
    public static final char BLUE_PLAYER = 'B';        // סימן שחקן כחול
    public static final char YELLOW_PLAYER = 'Y';        // סימן שחקן צהוב
    public static final char NO_PLAYER = ' ';       // סימן מצב שאין שחקן  תא ריק
    public static final char ARROW = 'A';       // סימן חץ על הלוח
    public static final char EMPTY = 'E';       // סימן חץ על הלוח
    public static final String NORTH = "N";       // סימן חץ צפון על הלוח
    public static final String EAST = "E";       // סימן חץ מזרח על הלוח
    public static final String WEST = "W";       // סימן חץ על הלוח
    public static final String SOUTH = "S";       // סימן חץ על הלוח
    public static final String NORTH_EAST = "NE";       // סימן חץ על הלוח
    public static final String NORTH_WEST = "NW";       // סימן חץ על הלוח
    public static final String SOUTH_EAST = "SE";       // סימן חץ על הלוח
    public static final String SOUTH_WEST = "SW";       // סימן חץ על הלוח
    public static final String NULL = "-";       // סימן חץ על הלוח
    public static final char PLAYER_1 = 'B'; //שחקן כחול 
    public static final char PLAYER_2 = 'Y'; //שחקן צהוב 
       
    
    
    
    // תכונות
    private char currentPlayer;      	// לשמירת סימן השחקן הנוכחי
    private char[][] mat;               // לשמירת לוח משחק לוגי
    private int boardSize;
    private String [][][] driection;
    private String [][] dr;
    private Move move;
    private boolean chckEat;
    private int numPlayerBlue;
    private int numPlayerYellow;
    
    public Model()
    {
        initGameLogic();
    }
    
    public void initGameLogic()
    {
       
        // יצירת לוח לוגי לצורך בדיקות וקבלת החלטות במשחק
        mat = new char[7][7];
        
        // איתחול הלוח הלוגי
        initMatLogic();
        numPlayerBlue = 7;
        numPlayerYellow = 7;
       initDirArrows();    
       currentPlayer = BLUE_PLAYER;
       
       boardSize = 7;
     
    }
    
    public void initDirArrows() 
    {
        
     driection = new String [][][] {
         
         {{EAST,SOUTH},{NULL},{SOUTH_WEST,SOUTH},{NULL},{EAST,WEST,SOUTH},{NULL},{SOUTH}},
         
         {{NULL},{NORTH_WEST,SOUTH_EAST},{NULL},{NORTH_WEST,NORTH_EAST},{NULL},{NORTH_WEST,NORTH_EAST},{NULL}},
            
         {{SOUTH,NORTH_EAST},{NULL},{NORTH,SOUTH,NORTH_EAST,EAST,WEST,SOUTH_EAST},{NULL},{NORTH_WEST,NORTH_EAST,SOUTH_EAST,SOUTH,SOUTH_WEST,EAST},{NULL},{SOUTH_WEST,NORTH_WEST,WEST}},
            
         {{NULL},{NORTH_WEST,NORTH_EAST},{NULL},{NULL},{NULL},{SOUTH_EAST,SOUTH_WEST},{NULL}},
            
         {{NORTH_EAST,EAST,SOUTH_EAST},{NULL},{WEST,NORTH_WEST,NORTH,NORTH_EAST,SOUTH_WEST},{NULL},{WEST,NORTH_WEST,NORTH,EAST,SOUTH,SOUTH_WEST},{NULL},{NORTH,SOUTH_WEST}},
     
         {{NULL},{SOUTH_WEST,SOUTH_EAST},{NULL},{SOUTH_EAST,SOUTH_WEST},{NULL},{NORTH_WEST,SOUTH_EAST},{NULL}},
          
         {{NORTH},{NULL},{WEST,EAST,NORTH},{NULL},{NORTH_EAST,NORTH},{NULL},{NORTH,WEST}}};
     
        
        
    }

    public Move getMove() {
        return move;
    }

    
    public ArrayList<Location> getAllPossibleMoves(int row, int col ,char player) 
    {
        ArrayList<Location> list = new ArrayList();
        
        //בדיקת מהלכים אפשריים עבור תאים מיוחדים 
        
        if(row == 2 && col == 2)
        {
            list.add(new Location(0,2));
            list.add(new Location(1,3));
            list.add(new Location(2,4));
            list.add(new Location(4,4));
            list.add(new Location(4,2));
            list.add(new Location(2,0));
            list = removeUnPusibileMove(list,player);
            return list;
        }
        
        if(row == 2 && col == 4)
        {
            list.add(new Location(1,3));
            list.add(new Location(1,5));
            list.add(new Location(2,6));
            list.add(new Location(3,5));
            list.add(new Location(4,4));
            list.add(new Location(4,2));
            list = removeUnPusibileMove(list,player);
            return list;
        }
        
         if(row == 4 && col == 2)
        {
            list.add(new Location(4,0));
            list.add(new Location(3,1));
            list.add(new Location(2,2));
            list.add(new Location(2,4));
            list.add(new Location(5,1));
            list.add(new Location(5,3));
            list = removeUnPusibileMove(list,player);
            return list;
        }
        
        if(row == 4 && col == 4)
        {
            list.add(new Location(4,2));
            list.add(new Location(2,2));
            list.add(new Location(2,4));
            list.add(new Location(4,6));
            list.add(new Location(6,4));
            list.add(new Location(5,3));
            list = removeUnPusibileMove(list,player);
            return list;
        }
        
        String[] dirs = driection[row][col]; 
       
        for(int i = 0; i< dirs.length; i++)
        {
           // System.out.print(Arrays.toString(dirs) + " ");
           // System.out.print(dirs[i]+ " ");
            switch (dirs[i])
            {
                case EAST:          list.add(new Location(row, col+2));
                                    break;
                case SOUTH_EAST:    list.add(new Location(row+1, col+1));
                                    break;
                case SOUTH:         list.add(new Location(row+2, col));
                                    break;
                case SOUTH_WEST:    list.add(new Location(row+1, col-1));
                                    break;
                case WEST:          list.add(new Location(row, col-2));
                                    break;
                case NORTH_WEST:    list.add(new Location(row-1, col-1));
                                    break;           
                case NORTH:         list.add(new Location(row-2, col));
                                    break;
                case NORTH_EAST:    list.add(new Location(row-1, col+1));
                                    break;
            }
        }
        
       //הסרת מהלכים אפשריים
       list = removeUnPusibileMove(list,player);
       
        System.out.println("list in fun -  " + list);
    
      return list;
    }
    
    
    public void initStateBeforeWin()
    {
        for(int col = 0; col < 7; col+=2)
              mat[0][col] = EMPTY;
        for(int col = 1; col < 7; col+=2)
              mat[0][col] = NO_PLAYER;
        
        for(int col = 1; col < 7; col+=2)
              mat[1][col] = EMPTY;
        for(int col = 0; col < 7; col+=2)
              mat[1][col] = NO_PLAYER;
        
        for(int col = 0; col < 7; col+=2)
              mat[2][col] = EMPTY;
        for(int col = 1; col < 7; col+=2)
              mat[2][col] = NO_PLAYER;
        
        for(int col = 0; col < 7; col++)
        {
            if(col==1)
                mat[3][1] = EMPTY;
            else if(col==5)
                mat[3][5] = EMPTY;
            else
            mat[3][col] = NO_PLAYER;
        }

        for(int col = 0; col < 7; col+=2)
              mat[4][col] = EMPTY;
        for(int col = 1; col < 7; col+=2)
              mat[4][col] = NO_PLAYER;
        
        for(int col = 1; col < 7; col+=2)
              mat[5][col] = EMPTY;
        for(int col = 0; col < 7; col+=2)
              mat[5][col] = NO_PLAYER;
        
        for(int col = 0; col < 7; col+=2)
              mat[6][col] = EMPTY;
        for(int col = 1; col < 7; col+=2)
              mat[6][col] = NO_PLAYER;
        
        mat[5][3] = BLUE_PLAYER;
        
        mat[5][5] = BLUE_PLAYER;
        
        mat[1][3] = YELLOW_PLAYER;
        
        mat[1][5] = YELLOW_PLAYER;
    }
    
    
    
    public void initMatLogic() 
    {
      
        for(int col = 0; col < 7; col+=2)
              mat[0][col] = YELLOW_PLAYER;
        for(int col = 1; col < 7; col+=2)
              mat[0][col] = NO_PLAYER;
        
        for(int col = 1; col < 7; col+=2)
              mat[1][col] = YELLOW_PLAYER;
        for(int col = 0; col < 7; col+=2)
              mat[1][col] = NO_PLAYER;
        
        for(int col = 0; col < 7; col+=2)
              mat[2][col] = EMPTY;
        for(int col = 1; col < 7; col+=2)
              mat[2][col] = NO_PLAYER;
        
        for(int col = 0; col < 7; col++)
        {
            if(col==1)
                mat[3][1] = EMPTY;
            else if(col==5)
                mat[3][5] = EMPTY;
            else
            mat[3][col] = NO_PLAYER;
        }

        for(int col = 0; col < 7; col+=2)
              mat[4][col] = EMPTY;
        for(int col = 1; col < 7; col+=2)
              mat[4][col] = NO_PLAYER;
        
        for(int col = 1; col < 7; col+=2)
              mat[5][col] = BLUE_PLAYER;
        for(int col = 0; col < 7; col+=2)
              mat[5][col] = NO_PLAYER;
        
        for(int col = 0; col < 7; col+=2)
              mat[6][col] = BLUE_PLAYER;
        for(int col = 1; col < 7; col+=2)
              mat[6][col] = NO_PLAYER;
        
    }
    
    public void printMat()
    {
        for(int row = 0; row < 7; row++)
        {
            for(int col = 0; col < 7; col++)
            {
                if(mat[row][col] == NO_PLAYER)
                    System.out.print("-");
                else
                    System.out.print(mat[row][col]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public ArrayList<String> getDriection(int row,int col) 
    {
        ArrayList<String> pusbibleMove = new ArrayList();
        for (int i = 0; i < 2; i++) 
        {
          if(driection[row][col][i] != null)
              pusbibleMove.add(driection[row][col][i]);
          else
              break;
        }
        return pusbibleMove;
    }
    
    //ArrayList<Move> pusbibleMove = new ArrayList();
    
    
    
    public boolean isPlayer(int row,int col)
    {
        if(mat[row][col] == EMPTY)
            return true;
        return false;
    }
    
    public ArrayList<Location> getLocationPlayer()
    {
        ArrayList<Location> LocationPlayer = new ArrayList();
        
         for(int row = 0; row < boardSize; row++)
         {
            for(int col = 0; col < boardSize; col++)
            {
                if(mat[row][col] == currentPlayer)
                    LocationPlayer.add(new Location(row,col));
            }
         }
         
        return LocationPlayer;
    }

    public void setMoveToUndo(int newRow, int newCol, int oldRow, int oldCol, boolean ifEat) 
    {
        if(ifEat)
            mat[oldRow][oldCol] = currentPlayer;
        else    
            mat[oldRow][oldCol] = EMPTY;
        
        if(currentPlayer != YELLOW_PLAYER)
            mat[newRow][newCol] = YELLOW_PLAYER;
        else
            mat[newRow][newCol] = BLUE_PLAYER;
        
        printMat();
    }
    
    public char getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public void switchPlayer()
    {
        if(currentPlayer == PLAYER_2)
            currentPlayer = PLAYER_1;
        else
            currentPlayer = PLAYER_2;
    }

    public char switchPlayerChar(char player)
    {
        if(player == YELLOW_PLAYER)
            player = BLUE_PLAYER;
        else
            player = YELLOW_PLAYER;
        
        return player;
    }
     
     
    public ArrayList<Move> getButtonOfPlayer(char player) 
    {
        ArrayList<Move> buttonOfPlayer = new ArrayList();
        for(int row = 0; row < boardSize; row++)
         {
            for(int col = 0; col < boardSize; col++)
            {
                if(mat[row][col] == player)
                    buttonOfPlayer.add(new Move(row,col));
            }
         }
        return buttonOfPlayer;
    }
    
    public ArrayList<Location> getButtonOfPlayerLoc(char player) 
    {
        ArrayList<Location> buttonOfPlayer = new ArrayList();
        for(int row = 0; row < boardSize; row++)
         {
            for(int col = 0; col < boardSize; col++)
            {
                if(mat[row][col] == player)
                    buttonOfPlayer.add(new Location(row,col));
            }
         }
        return buttonOfPlayer;
    }

    public boolean getHowPress(int row,int col) 
    {
       if(mat[row][col] != EMPTY)
           return true;
       else
           return false;
    }

    public boolean chckEat(int row ,int col,char player) 
    {
        if(mat[row][col] != player && mat[row][col] != EMPTY)
           return true;
       else
           return false;
    }

    public boolean chckEat(Move eatMove) 
    {
        if(mat[eatMove.getDestinationRow()][eatMove.getDestinationCol()] != currentPlayer 
                && mat[eatMove.getDestinationRow()][eatMove.getDestinationCol()] != EMPTY)
           return true;
       else
           return false;
    }
    
    public void setEatMat(Move move) 
    {
        mat[move.getDestinationRow()][move.getDestinationCol()] = move.getPlayer();
        if(move.getPlayer() == BLUE_PLAYER)
            numPlayerBlue--;
        else
            numPlayerYellow--;
    }
    
    public boolean checkForWin (char player)
    {
        //System.out.println("if winner " + getButtonOfPlayer(getOtherPlayerChar(player)).toString());
       
        if(getButtonOfPlayer(getOtherPlayerChar(player)).isEmpty())
            return true;
        else
            return false;
        
        
//        
//        int counter = 0;
//        for(int row = 0; row < boardSize; row++)
//         {
//            for(int col = 0; col < boardSize; col++)
//            {
//                if(mat[row][col] == player)
//                    counter = 0;
//                else
//                    counter++;
//            }
//         }
//        if(counter == 49)
//            return true;
//        else
//            return false;
        
    }

    public void setUndoMoveTo(int newRow, int newCol, int oldRow, int oldCol) 
    {
        mat[newRow][newCol] = EMPTY;
        
        if(currentPlayer != PLAYER_1)
             mat[oldRow][oldCol] = PLAYER_1;
        else
             mat[oldRow][oldCol] = PLAYER_2;
        
        printMat();
    }
    
    

    public char getPlayerUndo(int row, int col) 
    {
        if(mat[row][col] == PLAYER_1)
            return PLAYER_1;
        else
            return PLAYER_2;
        
    }

    public char getOtherPlayer() 
    {
        if(currentPlayer == BLUE_PLAYER)
            return YELLOW_PLAYER;
        else
            return BLUE_PLAYER;
    }
    
    public char getOtherPlayerChar(char player) 
    {
        if(player == BLUE_PLAYER)
            return YELLOW_PLAYER;
        else
            return BLUE_PLAYER;
    }
    
    

    public Move getMoveSo(int row, int col) 
    {
         move = new Move();
         move.setSourceLoc(new Location(row, col));
         move.setPlayer(currentPlayer);
        return move;
    }
    
    public Move getMoveDe(int row, int col) 
    {
        // move = new Move(row, col);
        move.setPlayer(currentPlayer);
        move.setDestinationLoc(new Location(row, col));
        return move;
    }

    public char getHowIsIt(int row, int col) 
    {
        char c;
        c = mat[row][col];
        
        return c;
    }

    public ArrayList<Location> removeUnPusibileMove(ArrayList<Location> list, char player) 
    {
            
        ArrayList<Location> toRemove = new ArrayList();
        for (int i = 0; i < list.size() ; i++) 
        {
            int row1 = list.get(i).getRow();
            int col1 = list.get(i).getCol();
            if(mat[row1][col1] == player)
                toRemove.add(list.get(i));
        }
        
        for (int i = 0; i < toRemove.size() ; i++) 
            list.remove(toRemove.get(i));
    
        return list;
    }

    public void setdestinationLoc(int row, int col) 
    {
        move.setDestinationLoc(new Location(row, col));
    }
    
    /**
     * פעולה המקבלת משתנה בולאיני
     * ומחזירה את כל המהלכים האפשריים במצב הלוח כרגע לשחקן מסויים
     * השחקן נקבע לפי המשתנה הבוליאני
     * @param isMax
     * @return ArrayList Move
     */
    public ArrayList<Move> allPosibileMoveForCurrentPlayer(boolean isMax)
    {
        // player == true --> blue_player
        // player == flase --> yellow_player
        
        char player;
        
        if(isMax)
            player = currentPlayer;
        else 
            player = getOtherPlayerChar(currentPlayer);
        
        ArrayList<Move> allMove;
        ArrayList<Move> allMove1 = new ArrayList();
        ArrayList<Location> allMovetool;
        
        allMove = getButtonOfPlayer(player);
        
        for (int i = 0; i < allMove.size(); i++) 
        {
            int row  = allMove.get(i).getLoc().getRow();     
            int col  = allMove.get(i).getLoc().getCol();
            

            allMovetool = getAllPossibleMoves(row, col,player);
            if(allMovetool != null)
            {
            for (int j = 0; j < allMovetool.size(); j++) 
            {
               boolean eat;
               
                if(chckEat(allMovetool.get(j).getRow(),allMovetool.get(j).getCol(),player))
                    eat = true;
                else
                    eat = false;
                
                allMove1.add(new Move(row,col,allMovetool.get(j).getRow(),allMovetool.get(j).getCol(),eat,player));
            }
            }
        }
 
        //System.out.println("all move - " + allMove1);

        return allMove1;
    }
     
    public Move minimax(boolean isMax, int depth)
    {
        
        //printMat();
        if(isGameOver() || depth == 0)
        {
            if(isGameOver())
                System.out.println("*********** GameOver *************");
            int score = getBoardScore(currentPlayer);
            System.out.println("score: " + score);
            return new Move(score);
        }
        
        Move bestMove, tempMove;
        ArrayList<Move> possibleMoves = allPosibileMoveForCurrentPlayer(isMax);
        //System.out.println("possibleMoves: " + possibleMoves);
        if(isMax)
        {
            bestMove = new Move(Integer.MIN_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                applyMove(possibleMoves.get(i));
                tempMove = minimax(false, depth-1);
                undoMove(possibleMoves.get(i));
                
                if(tempMove.getScore() > bestMove.getScore())
                {
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        else
        {
            bestMove = new Move(Integer.MAX_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                applyMove(possibleMoves.get(i));
                tempMove = minimax(true, depth-1);
                undoMove(possibleMoves.get(i));
                
                if(tempMove.getScore() < bestMove.getScore())
                {
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        System.out.println(bestMove);
        return bestMove;
    }

    public Move minimax(boolean isMax)
    {
        
        //printMat();
        if(isGameOver())
        {
            if(isGameOver())
                System.out.println("*********** GameOver *************");
            int score = getBoardScore(currentPlayer);
            //System.out.println("score: " + score);
            return new Move(score);
        }
        
        Move bestMove, tempMove;
        ArrayList<Move> possibleMoves = allPosibileMoveForCurrentPlayer(isMax);
        //System.out.println("possibleMoves: " + possibleMoves);
        if(isMax)
        {
            bestMove = new Move(Integer.MIN_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                applyMove(possibleMoves.get(i));
                tempMove = minimax(false);
               
                undoMove(possibleMoves.get(i));
                
                if(tempMove.getScore() > bestMove.getScore())
                {
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        else
        {
            bestMove = new Move(Integer.MAX_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                applyMove(possibleMoves.get(i));
                tempMove = minimax(true);
                undoMove(possibleMoves.get(i));
                
                if(tempMove.getScore() < bestMove.getScore())
                {
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            } 
        }
        System.out.println(bestMove);
        return bestMove;
    }

    private Move minimax(boolean isMax, long timeMs, int depth)
    {
        System.out.println(depth);
        printMat();
        if(isGameOver() || timeMs <= 0 || depth == 0)
        {
            if(isGameOver())
                System.out.println("*********** GameOver *************");
            int score = getBoardScore(currentPlayer);
            System.out.println("score: " + score);
            System.out.println("depth of minimax - "+ depth);
            return new Move(score);
        }
        
        long t1 = System.currentTimeMillis();
        Move bestMove, tempMove;
        ArrayList<Move> possibleMoves = allPosibileMoveForCurrentPlayer(isMax);
        
        if(isMax)
        {
            bestMove = new Move(Integer.MIN_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                long t2 = System.currentTimeMillis();
                applyMove(possibleMoves.get(i));
                tempMove = minimax(false, timeMs - (t2-t1), depth-1);
                
                if(depth == 0)
                {
//                    System.out.println(tempMove.get);
                    
                    
                    
                }
                undoMove(possibleMoves.get(i));
                
               if(tempMove.getScore() > bestMove.getScore())
                {
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        else
        {
            bestMove = new Move(Integer.MAX_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                long t2 = System.currentTimeMillis();
                applyMove(possibleMoves.get(i));
                tempMove = minimax(true, timeMs - (t2-t1), depth-1);
                undoMove(possibleMoves.get(i));
                
                if(tempMove.getScore() < bestMove.getScore())
                {
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        
        
        //System.out.println("depth of minimax - "+ depth);
        return bestMove;
    }
    /**
     * פעולת השיא של הפרויקט
     * פעולה המחשבת את המהלך הטוב ביותר שאפשרי לביצוע כרגע בלוח
     * הפעולה עובדת לפי האלגורים החישובי מיני מקס
     * הפעולה מחזירה את המהלך הטוב ביותר
     * @param isMax
     * @param timeMs
     * @param depth
     * @param MaxDepht
     * @return Move
     */
    private Move minimax(boolean isMax, long timeMs, int depth , int MaxDepht)
    {
        //System.out.println(MaxDepht);
        //printMat();
        if(timeMs <= 0)
        {
            int score = getBoardScore(currentPlayer);
            return new Move(score,MaxDepht,1);
        }
        
        if(isGameOver() || timeMs <= 0 || MaxDepht == 0)
        {
//            if(isGameOver())
//                System.out.println("*********** GameOver *************");
            int score = getBoardScore(currentPlayer);
            //System.out.println("score: " + score);
            //System.out.println("depth of minimax - "+ depth);
            return new Move(score,MaxDepht,1);
        }
        
        long t1 = System.currentTimeMillis();
        Move bestMove, tempMove;
        ArrayList<Move> possibleMoves = allPosibileMoveForCurrentPlayer(isMax);
        
        if(isMax)
        {
            bestMove = new Move(Integer.MIN_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                long t2 = System.currentTimeMillis();
                applyMove(possibleMoves.get(i));
                tempMove = minimax(false, timeMs - (t2-t1), depth+1, MaxDepht-1);
                
                if(depth == 0)
                {
                    System.out.println("Move # "+ (i+1));
                    printMat();
                    System.out.println("score: " + tempMove.getScore() + "     " + "depth: " + tempMove.getDepth());
                    System.out.println("");
                    System.out.println("============================");
                }
                undoMove(possibleMoves.get(i));
                
               if(tempMove.getScore() > bestMove.getScore())
                {
                    bestMove.setDepth(MaxDepht);
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        else
        {
            bestMove = new Move(Integer.MAX_VALUE);
            for (int i = 0; i < possibleMoves.size(); i++)
            {
                long t2 = System.currentTimeMillis();
                applyMove(possibleMoves.get(i));
                tempMove = minimax(true, timeMs - (t2-t1), depth+1, MaxDepht-1);
                undoMove(possibleMoves.get(i));
                
                if(tempMove.getScore() < bestMove.getScore())
                {
                    bestMove.setDepth(MaxDepht);
                    bestMove.setScore(tempMove.getScore());
                    bestMove.setSourceLoc(possibleMoves.get(i).getSourceLoc());
                    bestMove.setDestinationLoc(possibleMoves.get(i).getDestinationLoc());
                    bestMove.setIfEat(possibleMoves.get(i).getIfEat());
                    bestMove.setPlayer(possibleMoves.get(i).getPlayer());
                }
            }
        }
        
        
        //System.out.println("depth of minimax - "+ depth);
        return bestMove;
    }
 
    /**
     * פעולה המבצעת מהלך משחק על הלוח הלגוי    
     * @param applyMove 
     */
    public void applyMove(Move applyMove)
    {
        char player  = applyMove.getPlayer();
        
//        System.out.println("move in apply -" + applyMove);

        mat[applyMove.getDestinationRow()][applyMove.getDestinationCol()] = player;
        mat[applyMove.getSourceRow()][applyMove.getSourceCol()] = EMPTY;
        
    }

    /**
     * פעולה המבצעת מהלך חוזר על הלוח הלוגי
     * @param undoMove 
     */
    public void undoMove(Move undoMove) 
    {
        char player  = undoMove.getPlayer();
        //System.out.println("undo move player -"+player);
        
        if(undoMove.getIfEat())
        {
            mat[undoMove.getSourceRow()][undoMove.getSourceCol()] = player;
            if(player == BLUE_PLAYER)
                mat[undoMove.getDestinationRow()][undoMove.getDestinationCol()] = YELLOW_PLAYER;
            else
                mat[undoMove.getDestinationRow()][undoMove.getDestinationCol()] = BLUE_PLAYER;
        }
        else
        {
            mat[undoMove.getDestinationRow()][undoMove.getDestinationCol()] = EMPTY;
            mat[undoMove.getSourceRow()][undoMove.getSourceCol()] = player;
        }
    }

    public boolean isGameOver()
    {
        boolean isGameOver;
        if(checkForWin(BLUE_PLAYER) == true || checkForWin(YELLOW_PLAYER) == true)
            isGameOver = true;
        else
            isGameOver = false;
        
        
        return isGameOver;
    }

    private int getBoardScore(char player) 
    {
        if (checkForWin(player) == true)
            return 1000;

        if (checkForWin(getOtherPlayerChar(player)) == true)
            return -1000;

        int sum;
        
        // חישוב ניקוד עבור לוח שהוא לא משחק נגמר על פי האסטרטגיה של המשחק
        //במשחק איקס עיגול אנו נחשב את מספר השורות העמודות והאלכסונים 
        // שהם ניצחון עבור כל שחקן ונחסיר בינהם
        int score1 = 10*getNumWinsForPlayer(player);
        int score2 = 10*getNumWinsForPlayer(getOtherPlayerChar(player));
        
        
        int sum1_2 = score1 - score2;
//        
//        ArrayList<Move> list1 =  allPosibileMoveForCurrentPlayer(true);  // for blue player
//        ArrayList<Move> list2 =  allPosibileMoveForCurrentPlayer(false); // for yellow player
//        
        //בדיקת כמה מהלכים אפשריים יש לכל שחקן
        int score3 = getNumMoveOfPlayer(player);
        int score4 = getNumMoveOfPlayer(getOtherPlayerChar(player));
        
        int sum3_4 = score3 - score4; 
       
        sum = sum1_2 + sum3_4;
        
        
        return sum;
        
    }

    private int getNumWinsForPlayer(char player) 
    {
        if(player == BLUE_PLAYER)
            return numPlayerBlue;
        else
            return numPlayerYellow;
        
//        int counter = 0;
//         for(int row = 0; row < boardSize; row++)
//            for(int col = 0; col < boardSize; col++)
//                if(mat[row][col] == player)
//                    counter++;
//
//         return counter;
    }

    
    
    /**
     * פעולה המבצעת את מהלך המחשב החכם לפי זמן שהוקצב מראש
     * הפעולה תחזיר את המהלך הטוב ביותר שהחזיר האלגוריתם בזמן הנתון מראש
     * @param time
     * @return Move
     */
    public Move doComputerMove(long time) 
    {
        
        long t1 = System.currentTimeMillis();
        //Move compMove = minimax(true,-1); //minimax(true);
        //Move compMove = minimax(true); //minimaxx(true);
       
        Move compMove;
        //compMove = minimax(true ,time*1000L ,0,0);
       
        long msTime = time*1000L; //= time*1000L - (t4-t3); 
        Move save = new Move();


       
        int i = 0;
        while(msTime > 0)
        {
            long t3 = System.currentTimeMillis();
            compMove = minimax(true ,msTime ,0,i); //minimax(true); 
            System.out.println("===============================================================");
            long t4 = System.currentTimeMillis();
            msTime = msTime - (t4-t3); 
            
            
            if(msTime > 0)
                save = compMove;
            i++;
        }
        
        compMove = save;
//        Move compMove = minimax(true ,time*1000L ,0,12); //minimax(true); 
//        long t2 = System.currentTimeMillis();
//        long msTime = t2-t1; 
        
//        System.out.println(", Time: " + msTime + "ms (" + Utils.ms2time(msTime)+")");
//        System.out.println(">>> Minimax Best Move:" + compMove);
//        
        //printMat();
        
        return compMove;
        
    }

    public void setCurrentPlayer(char c) 
    {
        currentPlayer = c;
        
        //        int i  = 0;
//        for (; i < 100; i++) 
//        {
//            if(msTime <= 0)
//                break;
//                    
//            long t3 = System.currentTimeMillis();
//            compMove = minimax(true ,msTime ,0,i); //minimax(true); 
//            long t4 = System.currentTimeMillis();
//            msTime = msTime - (t4-t3); 
//            
//            
//            if(msTime > 0)
//                save = compMove;
//            
//        }
//        compMove = save;
    }

    private int getNumMoveOfPlayer(char player) 
    {
        int sum = 0;
        ArrayList<Move> list3 = getButtonOfPlayer(player);
        for (int i = 0; i < list3.size(); i++) 
        {
            int row  = list3.get(i).getLoc().getRow();     
            int col  = list3.get(i).getLoc().getCol();
            ArrayList<Location> allMovetool = getAllPossibleMoves(row, col,player);
            
            sum = sum + allMovetool.size();
        }
                
        return sum;
    }
        
    
   
    
}

  
    
    
    
    
    

