import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A subclass of message with msg type as 5. It is sent as soon as handshaking is done
 * contains information about the pieces of data present with the peer
 * @author karuna vinay and srinivas
 *
 */
public class BitfieldMessage extends Message 
{
	private static final long serialVersionUID = 12L;

	public BitfieldMessage(byte[] payloadBitmap) throws IOException, InterruptedException
	{
		this.MsgType = "Bitfield Message";
		this.MsgTypeValue = 5;
		this.MsgLength = payloadBitmap.length+1;

		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		baos.write(payloadBitmap);
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
	}
}
