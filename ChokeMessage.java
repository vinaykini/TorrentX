import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ChokeMessage extends Message {

	private static final long serialVersionUID = 5L;
	
	/**
	 * send a choke message when any peer should not download from the current peer
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public ChokeMessage() throws InterruptedException, IOException
	{		
		this.MsgType = "ChokeMessage";
		this.MsgTypeValue = 0;
		this.MsgLength = 1;

		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
	}
		
}
