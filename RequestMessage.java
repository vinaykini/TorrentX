import java.io.*;

public class RequestMessage extends Message {
	private static final long serialVersionUID = 11L;
	
	public RequestMessage(int pieceIndex) throws InterruptedException, IOException
	{
		this.MsgType = "RequestMessage";
		this.MsgTypeValue = 6;
		this.MsgLength = (Utilities.getBytes(pieceIndex)).length+1;
		
		//utilities.getBytes(pieceIndex) is payload for this message
		
		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		baos.write(Utilities.getBytes(pieceIndex));
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
	}
}
