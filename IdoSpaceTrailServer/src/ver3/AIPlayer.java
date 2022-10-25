package ver3;

/**
 *  Document   : AIPlayer - Minimax Player...
 *  Created on : 04/12/2019, 14:11:00
 *  Author     : ilan peretz (ilanperets@gmail.com)
 */
public class AIPlayer extends Player
{
    private int maxTimeInMills; // for Minimax max Time
    
    public AIPlayer(String sign)
    {
        super(Player.TYPE_AI, sign);
        maxTimeInMills = 5*1000;  // 5sec
    }

    public int getMaxTimeInMills()
    {
        return maxTimeInMills;
    }

    public void setMaxTimeInMills(int maxTimeInMills)
    {
        this.maxTimeInMills = maxTimeInMills;
    }
    
    
    @Override
    public Move getMove(Model model)
    {
        //Move move = model.minimax();
        System.out.println("AIPlayer getMove() done!\n");
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
        }
        return new Move();
    }

    @Override
    public String toString()
    {
        return "AIPlayer{" + "maxTimeInMills=" + maxTimeInMills + '}';
    }

    
}
