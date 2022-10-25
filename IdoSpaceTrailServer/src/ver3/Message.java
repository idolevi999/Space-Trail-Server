package ver3;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Document   : Message אובייקט הודעה שיעבור בין השרת ללקוח.
 * 
 * Created on : 30/10/2019
 * 
 * Author     : Ilan Peretz (ilanperets@gmail.com)
 */
public class Message implements Serializable
{
    private String msg;    // תוכן ההודעה
    private String data;  
    private int[] arr;      // מידע נוסף
    private Location loc;
    private int num;
    private Move move;
    private char c;
    private char [][] mat;
    private ArrayList<Location> listLoc;
    private ArrayList<Location> listShow;
    private ArrayList<Location> listOfBlue;
    private ArrayList<Location> listOfYellow;

//    Message(String howIsIt, int row1, int col1) 
//    {
//        this.msg = howIsIt;
//        this.loc.setRow(row1);
//        this.loc.setCol(col1);
//    }

    public Message(String msg, Location loc) {
        this.msg = msg;
        this.loc = new Location(loc.getRow(), loc.getCol());
    }

    Message(String SHOW_POSBILE_MOVES, ArrayList<Location> listShow, ArrayList<Location> listLoc, char c, Location loc) 
    {
        this.msg = SHOW_POSBILE_MOVES;
        this.listShow = listShow;
        this.listLoc = listLoc;
        this.c = c;
        this.loc =  new Location(loc.getRow(), loc.getCol());
    }

    Message(String SET_MOVE_AND_SWITCH, Location loc, Move move,ArrayList<Location> list) 
    {
        this.msg = SET_MOVE_AND_SWITCH;
        this.loc =  new Location(loc.getRow(), loc.getCol());;
        this.move = move;
        this.listLoc = list;
    }

    Message(String SET_MOVE_AND_SWITCH, Location location, Move move) 
    {
        this.msg = SET_MOVE_AND_SWITCH;
        this.loc =  new Location(location.getRow(), location.getCol());;
        this.move = move;
    }

    public char getC() {
        return c;
    }

    
    public Message(String msg, ArrayList<Location> listShow) 
    {
        this.msg = msg;
        this.listShow = listShow;
    }

    Message(String SHOW_POSBILE_MOVES, ArrayList<Location> listShow, ArrayList<Location> listloc) 
    {
        this.msg = SHOW_POSBILE_MOVES;
        this.listShow = listloc;
        this.listLoc = listShow;
    }

    Message(String SET_MOVE_AND_SWITCH, Move moveToGui, ArrayList<Location> listloc) 
    {
        this.msg = SET_MOVE_AND_SWITCH;
        this.move = moveToGui;
        this.listLoc = listloc;
    }

    Message(String SET_MOVE_AND_SWITCH, char[][] mat, ArrayList<Location> listloc) 
    {
        this.msg = SET_MOVE_AND_SWITCH;
        this.mat = mat;
        this.listLoc = listloc;
    }

    Message(String SHOW_POSBILE_MOVES, ArrayList<Location> listShow, ArrayList<Location> listLoc, char c)
    {
        this.msg = SHOW_POSBILE_MOVES;
        this.listShow = listShow;
        this.listLoc = listLoc;
        this.c = c;
    }

    public char[][] getMat() {
        return mat;
    }

    public ArrayList<Location> getListShow() {
        return listShow;
    }
    
    public void setListLoc(ArrayList<Location> listLoc) {
        this.listLoc = listLoc;
    }

    public ArrayList<Location> getListLoc() {
        return listLoc;
    }
    

    public String getData() {
        return data;
    }

    public int[] getArr() {
        return arr;
    }

    public Location getLoc() {
        return loc;
    }

    public int getNum() {
        return num;
    }

    public Move getMove() {
        return move;
    }

    
    public Message()
    {
        this.data = null;
        this.arr = null;
    }
    
    public Message(String msg)
    {
        this.msg = msg;
    }

    public Message(String msg, int num) 
    {
        this.msg = msg;
        this.num = num;
    }
    
    public Message(String msg, char player) 
    {
        this.msg = msg;
        this.c = player;
    }
    
    public Message(String msg, Move move)
    {
        this.msg = msg;
        this.move = move;
    }
    
    public Message(String msg, String data)
    {
        this.msg = msg;
        this.data = data;
    }
    
    public Message(String msg, int[] arr)
    {
        this.msg = msg;
        this.arr = arr;
    }
    
    public String getMsg() 
    {
        return msg;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public ArrayList<Location> getListOfBlue() {
        return listOfBlue;
    }

    public ArrayList<Location> getListOfYellow() {
        return listOfYellow;
    }
    
        
       @Override
    public String toString() {
        return "Message{" + "msg=" + msg + ", data=" + data + ", arr=" + arr + ", loc=" + loc + ", num=" + num + ", move=" + move + ", c=" + c + ", mat=" + mat + ", listLoc=" + listLoc + ", listShow=" + listShow + ", listOfBlue=" + listOfBlue + ", listOfYellow=" + listOfYellow + '}';
    }
}
