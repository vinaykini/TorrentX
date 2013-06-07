import java.io.*;

/**
 * send not interested if no pieces that we require present with the other peer 
 * @author karuna srinivas vinay
 *
 */
public class NotInterestedMessage extends Message 
{
private static final long serialVersionUID = 8L;
	
	public NotInterestedMessage() throws InterruptedException, IOException
	{		
		this.MsgType = "NotInterestedMessage";
		this.MsgTypeValue = 3;
		this.MsgLength = 1;

		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
	}

}
