
package ver3;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientData
{
    private String id;
    private Socket msgSocket, cmdSocket;
    private ObjectInputStream isMsg, isCmd;
    private ObjectOutputStream osMsg, osCmd;
    private ClientData partner;
    

    public ClientData(Socket msgSocket, Socket cmdSocket)
    {
        this.msgSocket = msgSocket;
        this.cmdSocket = cmdSocket;
        try
        {
            // Create input & output streams. output stream MUST create first!
            // ===============================================================
            // create MESSAGES streams.
            // ------------------------------------------------------
            osMsg = new ObjectOutputStream(msgSocket.getOutputStream());
            isMsg = new ObjectInputStream(msgSocket.getInputStream());

            // create COMMANDS streams.
            // ------------------------------------------------------
            osCmd = new ObjectOutputStream(cmdSocket.getOutputStream());
            isCmd = new ObjectInputStream(cmdSocket.getInputStream());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public ClientData(Socket msgSocket, Socket cmdSocket, String id)
    {
        this(msgSocket, cmdSocket);
        this.id = id;
    }

    public void writeMessage(Message msg)
    {
        try
        {
            osMsg.writeObject(msg);
            osMsg.flush();
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
        }
    }

    public void writeMessage(String subject)
    {
        writeMessage(new Message(subject));
    }

    public Message readMessage()
    {
        Message msg = null;
        try
        {
            msg = (Message) isMsg.readObject();
        }
        catch(Exception ex)
        {
            
            //ex.printStackTrace();
        }
        return msg;
    }
    
    public void writeCommand(Message cmd)
    {
        try
        {
            osCmd.writeObject(cmd);
            osCmd.flush();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void writeCommand(String cmd)
    {
        writeCommand(new Message(cmd));
    }

    public Message readCommand()
    {
        Message cmd = null;
        try
        {
            cmd = (Message) isCmd.readObject();
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
        }
        return cmd;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void close()
    {
        try
        {
            msgSocket.close();  // will close the msg socket and is & os streams 
            cmdSocket.close();  // will close the cmd socket and is & os streams 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String getSocketAddress()
    {
        return msgSocket.getRemoteSocketAddress().toString().substring(1);
    }

    public void setPartner(ClientData partner)
    {
        this.partner = partner;
    }

    public ClientData getPartner()
    {
        return this.partner;
    }

    public boolean isOpen()
    {
        return msgSocket != null && cmdSocket!=null && msgSocket.isClosed() && cmdSocket.isClosed();
    }

    @Override
    public String toString()
    {
        return "ClientData{" + "id=" + id + ", msgSocket=" + msgSocket + ", cmdSocket=" + cmdSocket + ", isMsg=" + isMsg + ", isCmd=" + isCmd + ", osMsg=" + osMsg + ", osCmd=" + osCmd + ", partner=" + partner.getId() + '}';
    }
    
}