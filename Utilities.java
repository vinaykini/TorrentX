import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Utilities {

	private static ByteArrayOutputStream streamHandle = new ByteArrayOutputStream();
	private static ReentrantLock lock = new ReentrantLock();
	private static Condition borrowedStream = lock.newCondition();
	private static boolean isStreamInUse = false;

	public static byte[] intToBytes( int i ) {
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putInt(i); 
		return bb.array();
	}

	public static byte[] getBytes(int i)
	{
		byte[] result = new byte[4];
		result[0] = (byte) (0xFF & (i >> 24));
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i /*>> 0*/);

		return result;
	}

	public static int byteArrayToInt(byte[] b) 
	{
		return   b[3] & 0xFF |
				(b[2] & 0xFF) << 8 |
				(b[1] & 0xFF) << 16 |
				(b[0] & 0xFF) << 24;
	}

	public static int getIntFromByte(byte[] array, int index){
		ByteBuffer buffer = ByteBuffer.wrap(array, index, 4);
		return buffer.getInt();
	}

	public static synchronized ByteArrayOutputStream getStreamHandle() throws InterruptedException
	{
		lock.lock();
		try
		{
			if(isStreamInUse)
			{
				borrowedStream.await();
			}
			isStreamInUse = true;
			streamHandle.reset();
			return streamHandle;
		}
		finally
		{
			lock.unlock();
		}
	}

	public static void returnStreamHandle()
	{
		lock.lock();
		try
		{
			borrowedStream.signal();
			isStreamInUse = false;
		}
		finally
		{
			lock.unlock();
		}
	}

}


