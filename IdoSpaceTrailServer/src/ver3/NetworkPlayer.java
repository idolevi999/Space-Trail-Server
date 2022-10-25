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

public class NetworkPlayer extends Player
{
     private ClientData clientData;
    
    public NetworkPlayer(String sign, ClientData client)
    {
        super(Player.TYPE_NETWORK, sign);
        this.clientData = client;
    }
    
    public ClientData getClientData()
    {
        return clientData;
    }

    public void setClientData(ClientData clientData)
    {
        this.clientData = clientData;
    }
    
    
    @Override
    public Move getMove(Model model)
    {
        Move clientMove = null; //clientData.getMove();
        System.out.println("NetworkPlayer getMove() done!\n");
        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
        }
        return clientMove;
    }

    @Override
    public String toString()
    {
        return "NetworkPlayer{" + "clientData=" + clientData + '}';
    }
    
    
}
