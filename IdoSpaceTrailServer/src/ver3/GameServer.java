package ver3;





import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * Document : GameServer דוגמה לשרת צאט פשוט .
 *
 * Date     : 18/10/2019
 *
 * Author   : Ilan Peretz (ilanperets@gmail.com)
 */
public class GameServer
{
    // constants
    private static final int DEFAULT_SERVER_PORT = 1234;
    private static final Dimension WIN_SIZE = new Dimension(380, 400);
    private static final Color LOG_BG_COLOR = Color.BLACK;
    private static final Color LOG_FONT_COLOR = Color.GREEN;
    private static final Font LOG_FONT = new Font(Font.MONOSPACED, Font.BOLD, 12);

    // תכונות
    private static String serverIP;
    private static int serverPort;
    private static JFrame win;
    private static JTextArea logArea;
    private static ServerSocket serverSocket;
    private static ArrayList<ClientData> clientsList;
    private static ArrayList<Game> gamesList;
    private static int autoClientID = 1;
//    private static ObjectOutputStream os;
//    private static ObjectInputStream is;
    private static int autoGuestID;
    

    // main
    public static void main(String args[]) throws IOException
    {
        createServerGUI();
        
        setupServerAddress();
         
        createServerSocket();
        
        runServer();
    }

    // set up and run server
    public static void runServer() throws IOException
    {
        log("Server start RUNNING on " + serverIP + ":" + serverPort + "\n");
        
        clientsList = new ArrayList<ClientData>();
        gamesList  = new ArrayList<Game>();
        
        while(true)
        {
            // wait for a client to connect - blocking.
            // if Ok, return client socket. else return null.
            ClientData clientData = waitForClient();
            
            // if clien1 is null serverSocket was closed!
            if(clientData == null)
            {
                System.out.println("Problem with server socket! server will close...");
                break;
            }

            handleClient(clientData);
        }
        

        // close the server
        closeServer();
    }
    
      // handle client login with thread
    private static void handleClient(ClientData clientData)
    {
        // handle client login with thread
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // check login. BLOCKING!
                boolean loginOK = false;
                try {
                    loginOK = clientLogin(clientData);
                    
                } catch (IOException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(loginOK)
                {
                    // add the new ClientData object to the clients list
                    clientsList.add(clientData);

                    try {
                        // try to start a game session
                        tryToStartGameSession();
                    } catch (IOException ex) {
                        Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();

        
        // create thread for client commands
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    // wait for a command from client
                    Message cmd = null;
                    cmd = clientData.readCommand();

                    // check if error msg
                    if(cmd == null)
                    {
                        break;
                    }

                    try {
                        // proccess command from the client
                        proccessCmdFromClient(clientData, cmd);
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    private static void proccessCmdFromClient(ClientData client, Message cmd) throws IOException
    {
        System.out.println(">>> proccessCmdFromClient() " + client.getId() + " say: " + cmd);

        String msgSubj = cmd.getMsg();

        if(msgSubj.equals(Const.CLIENT_EXIT))
        {
            disconnectClient(client);
            
            if(client.getPartner() != null)
            {
                client.getPartner().writeMessage(new Message(Const.PARTNER_EXIT));
                disconnectClient(client.getPartner());
            }
        }
        
        if(msgSubj.equals(Const.GAME_OVER))
        {
            System.out.println("game over idodo");
            
            
        }
    }
        

    private static boolean clientLogin(ClientData client) throws IOException, ClassNotFoundException
    {
        int attempts = 1;
        String errMsg = " ";

        System.out.println("client login");
        while(true)
        {
            client.writeMessage(new Message(Const.LOGIN + " " + attempts + " " + errMsg));

            Message msg1 = client.readMessage();  // read User Name from client
            Message msg2 = client.readMessage();  // read Password from client

            // check if connection to client is lost
            if(msg1 == null || msg2 == null)
            {
                return false;
            }

           
            String un = msg1.getMsg();  // get user name
            String pw = msg2.getMsg();  // get password

            // check if row with user name & password exists in DB
            if(un.isEmpty() || DB.isUserExists(un, pw) && !isUserLogedIn(un))
            {
                // check if this is a GUEST
                if(un.isEmpty())
                {
                    un += "Guest#" + autoGuestID++;
                }

                client.setId(un);

                log(client.getId() + " " + client.getSocketAddress() + " Login!");
                break;
            }
            else
            {
                if(DB.isUserExists(un, pw) && isUserLogedIn(un))
                {
                    errMsg = "This user allready LOGED !";
                }
                else
                {
                    errMsg = "User Name or Password INCORRECT !";
                }
                attempts++;
            }
        }
        return true;
    }

    public static void tryToStartGameSession() throws IOException
    {
        System.out.println(clientsList.size() + "clietss");
        
        // check if last client connected is the first one?
        if(clientsList.size() % 2 != 0)
        {
            ClientData currentClient = clientsList.get(clientsList.size() - 1);
            currentClient.writeMessage(Const.WELCOME + " " + currentClient.getId() + " " + "blue");
            //currentClient.writeMessage(Const.WELCOME);
            System.out.println("no gamee");
        }
        else
        {
            //can start a New Game with the last two clients
            ClientData client1 = clientsList.get(clientsList.size() - 2);
            ClientData client2 = clientsList.get(clientsList.size() - 1);

            client2.writeMessage(Const.WELCOME + " " + client2.getId() + " " + "yellow");
            client1.setPartner(client2);
            client2.setPartner(client1);

            System.out.println("gamee");
            //startGameSession(client1, client2);
            createGame(client1, client2);
        }
    }
    
    
    private static void createGame(ClientData client1, ClientData client2) 
    {
        //clientsList = new ArrayList();
        
        Game play  = new Game(client1, client2,autoClientID,++autoClientID);
        autoClientID++;
        gamesList.add(play);
    }
 

    private static void proccessMsgFromClient(ClientData client, String msg)
    {
        log(client.getId() + ": " + msg);
        
//        if(msg.equals("#exit"))
//        {
//            return;
//        }

        client.writeCommand("Me: " + msg);
        client.getPartner().writeCommand(client.getId() + ": " + msg);
    }
    
    // wait for client to connect. blocking method.
    // when client connect - return socket.
    // if serverSocket closed - return null. 
//    public static ClientData waitForAClient()
//    {
//        ClientData client = null;
//        
//        try
//        {
//            // Wait for a new client to connect. return client socket.
//            Socket clientSocket = serverSocket.accept();
//            client = new ClientData(clientSocket, "user"+autoClientID);
//            log("Client " + client.getId()+ "/" + client.getSocketAddress() + " Connected!");
//        }
//        catch(IOException ex)
//        {
//            //Utils.debug(ex);
//        }
//
//        return client;
//    }

     private static ClientData waitForClient()
    {
        ClientData clientData = null;
        try
        {
            // Wait for a new client to connect. return client socket.
            // Need accept TWO sockets - one for messages & one for commands
            Socket msgSocket = serverSocket.accept();
            Socket cmdSocket = serverSocket.accept();

            // create ClientData object to the new client
            clientData = new ClientData(msgSocket, cmdSocket);
            log("Client " + clientData.getSocketAddress() + " Connected!");
        }
        catch(IOException ex)
        {
            //ex.printStackTrace();
        }

        return clientData;
    }

    
    private static void disconnectClient(ClientData client)
    {
        log(client.getId() + " Disconnected!");
        
        clientsList.remove(client);
        
        client.close();
    }
    

    private static void exitServer()
    {
        int option = JOptionPane.showConfirmDialog(win, "Do you realy want to Exit?", "Server Exit", JOptionPane.YES_NO_OPTION);

        if(option == JOptionPane.OK_OPTION)
            stopServer();
    }

    private static void stopServer()
    {
        try
        {
            // This will throw cause an Exception on serverSocket.accept() in waitForClient() method
            serverSocket.close();

            // close all clients socket. will cause the Thread session to stop
            for(int i = 0; i < clientsList.size(); i++)
                clientsList.get(i).close();

            log("Server Stoped!");
        }
        catch(IOException ex)
        {
            UtilsDebug.debug(ex);
        }
    }

    private static void closeServer()
    {
        if(serverSocket != null && !serverSocket.isClosed())
            stopServer();

        // close GUI 
        win.dispose();
        
        log("Server Closed!");
        
        // close server
        System.exit(0);
    }

    private static void createServerSocket()
    {
        try
        {
            serverSocket = new ServerSocket(serverPort);
            win.setTitle("Chat Server (" + serverIP + ":" + serverPort + ")");
        }
        catch(IOException ex)
        {
            //Utils.debug(ex);
            String errMsg = "Can't run another server on (" + serverIP + ":" + serverPort + ")\n";
            errMsg += "Check if server allready running...";
            UtilsDebug.showErrMsgToUser(win,"Chat Server Error", errMsg);
            closeServer();
        }
    }
        
    // set server address (IP & Port) 
    private static void setupServerAddress()
    {
        try
        {
            // get the Computer IP on this machine
            serverIP = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException ex)
        {
            UtilsDebug.showErrMsgToUser(win, "Chat Server Error", "Can't get Computer IP!");
            System.exit(0);
        }

        serverPort = DEFAULT_SERVER_PORT;

        String port = null;
        
        while(port == null)
        {
            port = JOptionPane.showInputDialog(win, "Enter Server PORT Number [1024-65535]", serverPort);

            if(port != null)
            {
                port = port.replace(" ", ""); // remove all spaces
                try
                {
                    serverPort = Integer.parseInt(port);
                    if(serverPort < 1024 || serverPort > 65535)
                    {
                        log("Port number is NOT between 1024-65535 !");
                        serverPort = DEFAULT_SERVER_PORT;
                        port = null;
                    }
                }
                catch(NumberFormatException ex)
                {
                    log("Port number is NOT between 1024-65535 !");
                    port = null;
                }
            }
        }
    }

    // create server GUI
    private static void createServerGUI()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception ex)
        {
        }

        win = new JFrame();
        win.setSize(WIN_SIZE);
        win.setAlwaysOnTop(true);
        win.setTitle("Chat Server");
        win.setLocationRelativeTo(null);
        win.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        win.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exitServer();
                //System.exit(0);
            }
        });

        // create displayArea
        logArea = new JTextArea();
        logArea.setLineWrap(true);
        logArea.setFont(LOG_FONT);
        logArea.setMargin(new Insets(5, 5, 5, 5));
        logArea.setBackground(LOG_BG_COLOR);
        logArea.setForeground(LOG_FONT_COLOR);

        logArea.setEditable(false);

        // add all components to window
        win.add(new JScrollPane(logArea), BorderLayout.CENTER);

        // show window
        win.setVisible(true);
    }

    private static void log(String msg)
    {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());

        System.out.println(msg);
    }
    
    private static boolean isUserLogedIn(String un)
    {
        for(int i = 0; i < clientsList.size(); i++)
        {
            if(clientsList.get(i).getId().equals(un))
            {
                return true;
            }
        }
        return false;
    }

    
}
