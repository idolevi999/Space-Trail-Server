package ver1;



import java.io.Serializable;
import java.util.Random;

/**
 * Document: Location - מגדירה את הטיפוס מקום בלוח זהו זוג של שורה ועמודה.
 * 
 * Created on : 30/10/2019
 * 
 * Author     : Ilan Peretz (ilanperets@gmail.com)
 */
public class Location implements Serializable
{
    // תכונות
    private int row;    // מספר שורה
    private int col;    // מספר עמודה

    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public Location(int n)
    {
        Random rnd = new Random();
        this.row = rnd.nextInt(n);
        this.col = rnd.nextInt(n);
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    @Override
    public String toString()
    {
        return "{row=" + row + ", col=" + col + "}";
    }
}
