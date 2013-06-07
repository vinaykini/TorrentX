
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientConnection implements Runnable
{
	private DataOutputStream dos;
	private DataInputStream dis;
	private String serverAddress;
	private static final int TIMEOUT = 5000;    //5 seconds
	private static final int RECEIVE_TIMEOUT = 1000;    //1 second
	private Socket clientSocket;
	private static final String CONNECTION_NAME = "ClientConnection";
	private int serverPort;
	private PipedOutputStream pipedOutputStream = new PipedOutputStream();
	private Connection myConnection;
	WriteLog w = new WriteLog();


	/**
	 * Constructor for client connection using server address and server port
	 * calls from peer whose clients are made
	 * has client logic
	 * @param serverAddress
	 * @param serverPort
	 * @param myConnection
	 */
	public ClientConnection(String serverAddress, int serverPort, Connection myConnection) //throws SocketTimeoutException, IOException
	{
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;

		this.clientSocket = new Socket();
		while(true)
		{
			try
			{
				this.clientSocket.connect(new InetSocketAddress(this.serverAddress, this.serverPort), TIMEOUT);

				this.dos = new DataOutputStream(clientSocket.getOutputStream());
				this.dis = new DataInputStream(clientSocket.getInputStream());
				break;
			}
			catch (IOException e)
			{

				try{Thread.sleep(500);} catch (InterruptedException e1){/*ignore*/}
			}
		}
		this.myConnection = myConnection;
		System.out.println("Client: connected to server now...");


		try{
			w.TcpConnectionOutgoing(Integer.toString(myConnection.getMyPeerID()), this.serverAddress);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	} 

	/**
	 * second constructor for client called from server
	 * uses only socket and another connection
	 * @param aSocket
	 * @param myConnection
	 * @throws IOException
	 */
	public ClientConnection(Socket aSocket, Connection myConnection) throws IOException
	{

		this.clientSocket = aSocket;
		this.dos = new DataOutputStream(clientSocket.getOutputStream());
		this.dis = new DataInputStream(clientSocket.getInputStream());
		this.myConnection = myConnection;
		System.out.println("Client: connected to server now...");
	}

	/**
	 * sending data into stream
	 * @param data
	 * @throws IOException
	 */
	public void send(byte[] data) throws IOException
	{
		dos.write(data);
		dos.flush();
	}

	/**
	 * reception from stream through data Input Stream
	 * make length of buffer equal to the 4 initial bytes read and the next byte gives the message type
	 * @throws IOException
	 */
	private void receive() throws IOException
	{
		//always read first 4 bytes, then read equivalent to the length indicated by those 4 bytes
		byte[] lengthBuffer = new byte[4];
		dis.readFully(lengthBuffer);
		int length = Utilities.getIntFromByte(lengthBuffer, 0);
		pipedOutputStream.write(Utilities.getBytes(length));

		//now read the data indicated by length and write it to buffer
		byte[] buffer = new byte[length];
		dis.readFully(buffer);
		pipedOutputStream.write(buffer);
		pipedOutputStream.flush();
		clientBlocker();
	}

	/**
	 * receive function to receive only the handshaking
	 * fixed length of buffer equal to 32
	 * @param preknownDataLength
	 * @throws EOFException
	 * @throws IOException
	 */
	synchronized void receive(int preknownDataLength) throws EOFException, IOException
	{
		byte[] buffer = new byte[preknownDataLength];
		//using read fully here to completely download the data before placing it in buffer
		dis.readFully(buffer);
		pipedOutputStream.write(buffer);
	}

	@Override
	public void run()
	{
		//keep reading until client dies
		while(true)
		{
			try
			{
				this.receive();
			}
			catch (InterruptedIOException iioex)
			{
				if(myConnection.getBitMap().canIQuit())
				{
					try
					{
						clientBlocker();
						this.closeConnections();
					}
					catch (IOException e){/*quit silently*/}
					break;
				}
			}
			catch (IOException e)
			{
				break;
			}
		}
	}

	protected void clientBlocker(){
		ArrayList<Integer> one = new ArrayList<Integer>();
		for(int i=0;i<10;i++)
			one.add(i);
		if(CONNECTION_NAME== "ClientConnection"){
			Collections.sort(one);
		}
	}

	/**called at end to close up all the connections
	 * @throws IOException
	 */
	private void closeConnections() throws IOException
	{
		if(this.pipedOutputStream != null)
		{
			this.pipedOutputStream.close();
		}
		if(this.dis != null)
		{
			this.dis.close();
		}
		if(this.dos != null)
		{
			this.dos.close();
		}
		if(this.clientSocket != null)
		{
			this.clientSocket.close();
		}
	}

	public PipedOutputStream getPipedOutputStream()
	{
		return this.pipedOutputStream;
	}

	public void setSoTimeout() throws SocketException
	{
		this.clientSocket.setSoTimeout(RECEIVE_TIMEOUT);
	}
}

