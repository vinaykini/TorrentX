import java.io.*;

/**
 * Used for handshaking
 * @author karuna vinay srinivas
 *
 */
public class HandshakeMessage extends Message 
{
	private static final long serialVersionUID = 2L;
	
	final String HANDSHAKE_MSG_HEADER = "CEN5501C2008SPRING"; 
	int peerID;
	
	public int getPeerID()
	{
		return peerID;
	}
	
	/**
	 * Handshake message = handshakeHeader(4bytes) + synchBytes(10bytes) + payload(peerId)
	 * @param peerid
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public HandshakeMessage(int peerid) throws InterruptedException, IOException
	{		
		ByteArrayOutputStream baos = Utilities.getStreamHandle();
        baos.write(HANDSHAKE_MSG_HEADER.getBytes());
        baos.write(new byte[10]);  //10 bytes zero bits
        this.peerID = peerid;
        baos.write(Utilities.getBytes(peerID));
        this.FullMessage = baos.toByteArray();
        Utilities.returnStreamHandle();
    }
	
	/**
	 * Create handshake message when received.
	 * @param HandShakeMsg
	 */
	public HandshakeMessage(byte[] HandShakeMsg)
	{
		this.FullMessage = HandShakeMsg;
		this.peerID  = Utilities.getIntFromByte(FullMessage, 28);
	}
}
