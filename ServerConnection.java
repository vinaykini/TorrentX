import java.io.*;
import java.net.*;


public class ServerConnection implements Runnable
{
	private ServerSocket serverSocket;
	private Connection myConnection;

	private static final int ACCEPT_TIMEOUT = 100000;
	WriteLog w = new WriteLog();

	public ServerConnection(String hostName, int port, Connection myConnection) throws UnknownHostException, IOException
	{
		System.out.println("IM IN THE SERVER CONNECTION CONSTRUCTOR :: hostname = " +hostName+ " port = " + port);
		this.serverSocket = new ServerSocket(port, 0, InetAddress.getByName(hostName.trim()));
		this.serverSocket.setSoTimeout(ACCEPT_TIMEOUT);
		this.myConnection = myConnection;
		w.ServerConnectionIncoming(Integer.toString(myConnection.getMyPeerID()), this.serverSocket.getLocalSocketAddress().toString(), Integer.toString(this.serverSocket.getLocalPort()));

		try{
			Thread.sleep(50);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Server socket ready to run");
	}

	@Override
	public void run()
	{

		while(true)
		{
			Socket ClientSocket = null;
			// wait for connections forever
			try
			{
				ClientSocket = this.serverSocket.accept();
				w.TcpConnectionIncoming(Integer.toString(myConnection.getMyPeerID()), ClientSocket.getInetAddress().getHostName());

				(new Thread(new MessageHandler(new ClientConnection(ClientSocket, myConnection), myConnection))).start();
				System.out.println("Server has been requested by a client");
			}
			catch(InterruptedIOException iioex)
			{
				if(myConnection.getBitMap().canIQuit())
				{
					try
					{
						this.serverSocket.close();
						break;
					} catch (IOException e){}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}

