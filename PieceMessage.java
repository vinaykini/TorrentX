import java.io.*;


public class PieceMessage extends Message{
	private static final long serialVersionUID = 10L;

	public PieceMessage(int pieceIndex, byte[] data) throws IOException, InterruptedException
	{
		this.MsgType = "PieceMessage";
		this.MsgTypeValue = 7;
		this.MsgLength = data.length+4+1;

		ByteArrayOutputStream baos = Utilities.getStreamHandle();
		baos.write(Utilities.getBytes(this.MsgLength));
		baos.write((byte)this.MsgTypeValue);
		baos.write(Utilities.getBytes(pieceIndex));
		baos.write(data);
		FullMessage = baos.toByteArray();
		Utilities.returnStreamHandle();
}
}
