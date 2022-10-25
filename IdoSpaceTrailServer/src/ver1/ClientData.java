package ver1;





import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Document : ClientData דוגמה לשרת צאט פשוט .
 *
 * Date     : 18/10/2019
 *
 * Author   : Ilan Peretz (ilanperets@gmail.com)
 */
public class ClientData
{
    private String id;
    private Socket socket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private ClientData partner;
    

    public ClientData(Socket socket)
    {
        this.socket = socket;
        try
        {
            this.os = new ObjectOutputStream(socket.getOutputStream());
            this.is = new ObjectInputStream(socket.getInputStream());
        }
        catch(Exception ex)
        {
            UtilsDebug.debug(ex);
        }
    }
    
    public ClientData(Socket socket, String id)
    {
        this.id = id;
        this.socket = socket;
        try
        {
            this.os = new ObjectOutputStream(socket.getOutputStream());
            this.is = new ObjectInputStream(socket.getInputStream());
        }
        catch(Exception ex)
        {
            UtilsDebug.debug(ex);
        }
    }

    public void writeData(String data)
    {
        try
        {
            os.writeUTF(data);
        }
        catch(Exception ex)
        {
            //Utils.debug(ex);
        }
    }

    public String readData()
    {
        String data = null;
        try
        {
            data = is.readUTF();
        }
        catch(Exception ex)
        {
            //Utils.debug(ex);
        }
        return data;
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
            socket.close();  // will close the socket and is & os streams 
        }
        catch(Exception ex)
        {
            //Utils.debug(ex);
        }
    }

    public String getSocketAddress()
    {
        return socket.getRemoteSocketAddress().toString().substring(1);
    }

    public void setPartner(ClientData partner)
    {
        this.partner = partner;
    }
    
    public ClientData getPartner()
    {
        return this.partner;
    }
    
    public void writeObject(Message obj) throws IOException
    {
        os.writeObject(obj);
        os.flush();
    }
    
    public void writeObject(String msg) throws IOException
    {
        writeObject(new Message(msg));
    }

    public Message readObject() throws IOException, ClassNotFoundException 
    {
        Message obj = (Message) is.readObject();
        return obj;
    }

//    @Override
//    public String toString() {
//        return "ClientData{" + "id=" + id + ", socket=" + socket + ", partner=" + partner + '}';
//    }
    
    
    
    
    
    
}
