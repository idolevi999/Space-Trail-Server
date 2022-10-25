package ver2;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */






/**
 *
 * @author idolevi = idolevi99@gmail.com
 */
public class Move implements Serializable
{
    private Location loc;
    private Location sourceLoc;
    private Location destinationLoc;
    private boolean ifEat;
    private char player;
    private int score;
    private int depth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    /**
     * פעולה בונה המקבלת ניקוד ומכניסה אותו לתוך העצם
     * @param score 
     */
    Move(int score) 
    {
        this.score = score;
    }

    public Move(int score, int depth, int x) 
    {
        this.score = score;
        this.depth = depth;
    }
    
    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
    
//    public Move(int row, int col) {
//        super(row, col);
//    }
    
    public Move()
    {
        
    }
    
    
    public Move(int sourceRow,int sourceCol,int deRow,int deCol) 
    {
        sourceLoc = new Location(deRow, deCol);
        destinationLoc = new Location(deRow, deCol);
        
//        
//        sourceLoc = new Location();
//        destinationLoc = new Location();
//        
        sourceLoc.setRow(sourceRow);
        sourceLoc.setCol(sourceCol);
        destinationLoc.setRow(deRow);
        destinationLoc.setCol(deCol);
    }
    /**
     * פעולה בונה עם מקור ויעד של המהלך וכולל אם התבצעה אכילה או לא
     * @param sourceRow
     * @param sourceCol
     * @param deRow
     * @param deCol
     * @param ifEat 
     */
    public Move(int sourceRow,int sourceCol,int deRow,int deCol,boolean ifEat) 
    {
        sourceLoc = new Location(deRow, deCol);
        destinationLoc = new Location(deRow, deCol);
        
        sourceLoc.setRow(sourceRow);
        sourceLoc.setCol(sourceCol);
        destinationLoc.setRow(deRow);
        destinationLoc.setCol(deCol);
        
        this.ifEat = ifEat;
    }
    
    public Move(int sourceRow,int sourceCol,int deRow,int deCol,boolean ifEat,char player) 
    {
        sourceLoc = new Location(deRow, deCol);
        destinationLoc = new Location(deRow, deCol);
        
        sourceLoc.setRow(sourceRow);
        sourceLoc.setCol(sourceCol);
        destinationLoc.setRow(deRow);
        destinationLoc.setCol(deCol);
        
        this.ifEat = ifEat;
        this.player = player;
    }
     
    /**
     * פעולה בונה המקבלת שורה ועמודה בלוח
     * @param row
     * @param col 
     */ 
    public Move(int row, int col)
    {
        this.loc = new Location(row, col);
        loc.setRow(row);
        loc.setCol(col);
 
    }
    
    public Location getLoc() {
        return loc;
    }

    public int getRow() {
        return loc.getRow();
    }
    
    public int getCol() {
        return loc.getCol();
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
    
    public void setLocRow(int Row) {
        this.loc.setRow(Row);
    }
    
     public void setLocCol(int Col) {
        this.loc.setCol(Col);
    }
//
//    public Move(int row, int col) 
//    {
//        destinationLoc.setRow(row);
//        destinationLoc.setCol(col);
//    }
//
//    public Move(int row, int col) 
//    {
//        sourceLoc.setRow(row);
//        sourceLoc.setCol(col);
//    }

    public boolean getIfEat() {
        return ifEat;
    }

    public void setIfEat(boolean ifEat) {
        this.ifEat = ifEat;
    }
    

    public void setSourceLoc(Location sourceLoc) {
        this.sourceLoc = sourceLoc;
    }

    public void setDestinationLoc(int row,int col) {
        this.destinationLoc.setRow(row);
        this.destinationLoc.setCol(col);
    }

    public void setDestinationLoc(Location destinationLoc) {
        this.destinationLoc = destinationLoc;
    }
    

    public int getSourceRow() {
        return sourceLoc.getRow();
    }

    public Location getSourceLoc() {
        return sourceLoc;
    }

    @Override
    public String toString() {
        return "Move{" + "sourceLoc=" + sourceLoc + ", destinationLoc=" + destinationLoc + ", Eat=" + ifEat + ", Loc=" + loc + ", player=" + player +'}';
    }
    
    
    public int getSourceCol() {
        return sourceLoc.getCol();
    }
    
    public int getDestinationRow() {
        return destinationLoc.getRow();
    }
    
    public int getDestinationCol() {
        return destinationLoc.getCol();
    }

    public Location getDestinationLoc() {
        return destinationLoc;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
}
