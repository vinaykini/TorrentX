import java.io.*;

public class UnchokedMessage extends Message {

	private static final long serialVersionUID = 6L;
	
	public UnchokedMessage() throws InterruptedException, IOException
	{		
		this.MsgType = "UnchokedMessage";
		this.MsgTypeValue = 1;
		this.MsgLength = 1;

		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
	}
		
}
