import java.io.*;

/**
 * Message that is used to indicate that a peer is interested to transfer data
 * @author karuna
 *
 */
public class InterestedMessage extends Message 
{
	private static final long serialVersionUID = 7L;

	/**
	 * Interested Message : Length(4bytes) + type(1byte)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public InterestedMessage() throws InterruptedException, IOException
	{		
		this.MsgType = "InterestedMessage";
		this.MsgTypeValue = 2;
		this.MsgLength = 1;

		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
	}
}
