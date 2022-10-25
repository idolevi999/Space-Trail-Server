/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

/**
 *
 * @author idole
 */
public abstract class Player 
{
     // constants 
    // ======================================================
    // for player type
    public static final String TYPE_AI = "AI_PLAYER";
    public static final String TYPE_NETWORK = "NETWORK_PLAYER";
    // for player sign
    public static final String SIGN_X = "X_PLAYER";
    public static final String SIGN_O = "O_PLAYER";
    
    // player fields
    private String type;    // "AI_PLAYER" or "NETWORK_PLAYER" 
    private String sign;    // "X_PLAYER" or "O_PLAYER" ("BLACK_PLAYER" or "WHITE_PLAYER")

    
    // constructor for Sub-Classes ONLY!  
    // can't create new Object from this class!
    public Player(String type, String sign)
    {
        this.type = type;
        this.sign = sign;
    }
    
    public Player(ClientData client)
    {
        
    }
    
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    // sub-class must to implements this method!
    public abstract Move getMove(Model model);
    
    //public abstract void makeMove(Model model);
            
    @Override
    public String toString()
    {
        return "Player{" + "type=" + type + ", sign=" + sign + '}';
    }
    
}
