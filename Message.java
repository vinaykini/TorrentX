import java.io.Serializable;

/**
 * Message should be serialized
 * has message type value and full payload message
 * @author karuna srinivas vinay
 *
 */
public class Message implements Serializable 
{
	protected static final long serialVersionUID = 1L;
	String MsgType;
	int MsgLength;
	byte[] FullMessage;
	int MsgTypeValue;


	public int getMsgTypeValue()
	{
		return MsgTypeValue;
	}

	public void setMsgTypeValue(int msgVal)
	{
		this.MsgTypeValue = msgVal;
	}

	public String getMsgType()
	{
		return MsgType;
	}

	public void setMsgType(String msg)
	{
		this.MsgType = msg;
	}

	public int getMsgLength()
	{
		return MsgLength;
	}

	public void setMsgLength(int msglen)
	{
		this.MsgLength = msglen;
	}

	public byte[] getFullMessage()
	{
		return FullMessage;
	}

	public void setFullMessage(byte[] pack)
	{
		this.FullMessage = pack;
	}
}
