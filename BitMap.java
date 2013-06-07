import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;


public class BitMap
{
	private final String fileName;

	private Map<Integer, byte[]> peerIDtoPiece;
	private Map<Integer, AtomicInteger> peerIDtoCountDownload;
	private Set<Integer> piecesAskedfor;
	private FileIO myFileHandler;
	private final String myDirectory;
	private final int myPeerID;
	private final int totalPiecesRequired;
	private byte[] fullBitMap;
	private Connection myConnection;

	public BitMap(int myPeerID, CommonConfig myCommonConfig, Set<Integer> peerConfigIDs,
			boolean doIHaveFile, Connection myConnection,Map<Integer,PeerConfig> map) throws IOException
			{

		this.myConnection = myConnection;
		this.myPeerID = myPeerID;
		this.fileName = myCommonConfig.getFileName();
		this.peerIDtoPiece = new ConcurrentHashMap<Integer, byte[]>();
		this.peerIDtoCountDownload = new ConcurrentHashMap<Integer, AtomicInteger>();
		this.piecesAskedfor = new ConcurrentSkipListSet<Integer>();
		this.totalPiecesRequired = (int)Math.ceil((double)myCommonConfig.getFileSize() / myCommonConfig.getPieceSize());
		int totalBytesRequiredForPieces = (int)Math.ceil((double)totalPiecesRequired / 8);

		//initialize maps with all peerIDs (including mine) and 0s in value field
		for(Integer PeerID : peerConfigIDs)
		{
			this.peerIDtoPiece.put(PeerID, new byte[totalBytesRequiredForPieces]);
			this.peerIDtoCountDownload.put(PeerID, new AtomicInteger(0));


		}
		this.myDirectory = System.getProperty("user.dir") + "/peer_" + myPeerID;

		this.myFileHandler = new FileIO(myDirectory + "/" + myCommonConfig.getFileName(),
				myCommonConfig.getFileSize(), myCommonConfig.getPieceSize());

		//create the BitMap which will be finally required

		this.fullBitMap = getfullBitMap(totalPiecesRequired);
		
		//create a dummy file on disk for storage if I don't have a complete file
		if(!doIHaveFile)
		{
			System.out.println("create dummy file created");
			this.myFileHandler.createDummyFile();
		}
		else
		{
			// check first if file actually exists or not
			System.out.println(myDirectory);

			File tempFile = new File(myDirectory + "/" + myCommonConfig.getFileName());
			if (!tempFile.exists())
			{	System.out.println("File name im looking for:: "+myCommonConfig.getFileName());
				System.out.println("FATAL FNFv1");
				System.exit(0);
			}

			// check if file size matches the file-size specified in Common.cfg
			if (tempFile.length() != myCommonConfig.getFileSize())
			{
				System.out.println("FATAL filesize");
				System.exit(0);
			}

			this.peerIDtoPiece.put(myPeerID, this.fullBitMap);

		}
		System.out.println("BitMap created");
			}

	private byte[] getfullBitMap(int totalPiecesRequired)
	{
		showBitMap();
		//add 1 to all of bits in fullBitMap
		int len = (int)Math.ceil((double)totalPiecesRequired/8);
		byte[] fullBitMap = new byte[len];
		for(int i = 0; i < len; i++)
		{
			fullBitMap[i] = (byte)0xFF;
		}
		int lastBytePieces = totalPiecesRequired & 7;   //totalPiecesRequired & 7 = totalPiecesRequired % 8
		if(lastBytePieces > 0)  //then zero-filling is required
		{
			fullBitMap[len - 1] = (byte)(fullBitMap[len - 1]&0xFF >>> (8 - lastBytePieces));
		}
		return fullBitMap;
	}



	/**
	 * This method will update the piece map with the received piece id and store the piece on disk.
	 * Keep mainitaing the number of pieces received.
	 * @param pieceID 
	 * @throws IOException
	 */
	public void reportPieceReceived(int pieceID, byte[] pieceData) throws IOException
	{
		myFileHandler.writeFilePiece(pieceID, pieceData);
		synchronized (this)
		{
			byte[] myFileBitMap = this.peerIDtoPiece.get(myPeerID);
			this.updateBitMapWithPiece(myFileBitMap, pieceID);
			this.peerIDtoCountDownload.get(myPeerID).addAndGet(1);  
			findBits(myPeerID);
		}


		if(canIQuit())
		{
			//signal Connection which will make every thread quit.
			this.myConnection.QuitProcess();
		}
	}

	private void showBitMap()
	{
		StringBuilder sb = new StringBuilder("Peer ");
		for(Integer a : this.peerIDtoPiece.keySet())
		{
			sb.append(a + ": " + this.peerIDtoPiece.get(a)[0] + "\n");
		}
	}

	/**
	 * getters setters for piecedata
	 * @param pieceIndex
	 * @return
	 * @throws IOException
	 */
	public byte[] getPieceData(int pieceIndex) throws IOException
	{
		return myFileHandler.getChunk(pieceIndex);
	}
	
	public byte[] getMyFileBitMap()
	{
		return this.peerIDtoPiece.get(myPeerID);
	}

	public int getTotalPieceCount()
	{
		return totalPiecesRequired;
	}
	
	/**
	 * getters setters to receive the count of downloaded pieces
	 * @param peerID
	 * @return
	 */
	public int getDownloadedPieceCount(int peerID)
	{
		return this.peerIDtoCountDownload.get(peerID).get();
	}
	
	public byte[] getPeerBitMap(int peerID)
	{
		return this.peerIDtoPiece.get(peerID);
	}

	public synchronized void setPeerBitMap(int peerID, byte[] BitMap)
	{
		this.peerIDtoPiece.put(peerID, BitMap);
	}

	public String getFileName()
	{
		return fileName;
	}

	/**
	 * updates the peer's BitMap with the piece id received. This method also updates piece download count for given peerID
	 * @param peerID
	 * @param pieceIndex
	 */
	public void reportPeerPieceAvailablity(int peerID, int pieceIndex)
	{
		synchronized(this)
		{
			byte[] peerFileBitMap = this.peerIDtoPiece.get(peerID);
			updateBitMapWithPiece(peerFileBitMap, pieceIndex);
			this.peerIDtoCountDownload.get(peerID).addAndGet(1);
		}
	

		if(canIQuit())
		{
			//signal Connection which will make every thread quit.
			this.myConnection.QuitProcess();
		}
	}

	/**
	 * check whether the whole file has been downloaded or not
	 * if whole file is received at this peer then return true else return false
	 * @return
	 */
	public boolean canIQuit()
	{
		for(byte[] aBitMap : this.peerIDtoPiece.values())
		{
			if(!isBitMapFinal(aBitMap))
			{
				return false;
			}
		}
		return true;
	}

	private boolean isBitMapFinal(byte[] BitMap)
	{
		int len = BitMap.length;
		for(int i = 0; i < len; i++)
		{
			if((BitMap[i] ^ fullBitMap[i]) != 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the given piece index data is contained by us.
	 * @param pieceIndex 
	 * @return true if we have the piece data, false if we don't
	 */
	public boolean doIHavePiece(int pieceIndex)
	{
		showBitMap();
		byte[] myFileBitMap = this.peerIDtoPiece.get(myPeerID);
		int pieceLocation = pieceIndex / 8;
		int bitLocation = pieceIndex & 7;   // = pieceIndex % 8
		if((myFileBitMap[pieceLocation] & (1 << bitLocation)) != 0)  // == 0 means we don't have that piece
		{
			return true;
		}
		return false;
	}

	private void updateBitMapWithPiece(byte[] peerFileBitMap, int pieceIndex)
	{
		showBitMap();
		int pieceLocation = pieceIndex / 8;
		int bitLocation = pieceIndex & 7;   // = pieceIndex % 8
		peerFileBitMap[pieceLocation] |= (1 << bitLocation);
	}

	
	public boolean doIHaveAnyPiece()
	{
		byte[] myFileBitMap = this.peerIDtoPiece.get(myPeerID);
		final int mask = 0x000000FF;
		final int len = myFileBitMap.length;
		for(int i = 0; i < len; i++)
		{
			if((myFileBitMap[i] & mask) != 0)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * check if any interesting piece is contained in the peer to whom connection is being made
	 * @param anotherPeerID 
	 * @return true if peer has any interesting piece, false, if no interesting piece 
	 */
	public boolean hasInterestingPiece(int anotherPeerID)
	{
		showBitMap();
		byte[] myFileBitMap = this.peerIDtoPiece.get(myPeerID);
		final int len = myFileBitMap.length;
		byte[] peerFileBitMap = this.peerIDtoPiece.get(anotherPeerID);
		findBits(anotherPeerID);
		for(int i = 0; i < len; i++)
		{
			
			if((0xFF&(int)(myFileBitMap[i] | peerFileBitMap[i])) > (0xFF&(int)myFileBitMap[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public void findBits(int peerID){
		try{
			showBitMap();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * get any random piece not present with me but present in my connected peer
	 * @param peerID ID of the peer which has the desired piece
	 * @return piece index. If -1, then no interesting piece was found.
	 */
	public int getPeerPieceIndex(int peerID)
	{
		showBitMap();
		byte[] myFileBitMap = this.peerIDtoPiece.get(myPeerID);
		int desiredPieceID = -1;        
		//check the first available piece which is not requested, if found add to requested piece list
		byte[] peerBitMap = this.peerIDtoPiece.get(peerID);
		final int len = myFileBitMap.length;
		List<Integer> possiblePieces = new ArrayList<Integer>();
		
		for(int i = 0; i < len; i++)
		{
			if((0xFF&(int)(myFileBitMap[i] | peerBitMap[i])) > (0xFF&(int)myFileBitMap[i]))
			{
				for(int j = 0; j < 8; j++)
				{
					//if peer has the piece and I don't have it, request it
					if((myFileBitMap[i] & (1 << j)) == 0 && (peerBitMap[i] & (1 << j)) != 0)
					{
						int attemptedPieceIndex = i*8 + j;
						desiredPieceID = findAndLogRequestedPiece(attemptedPieceIndex);
						if (desiredPieceID != -1)
							possiblePieces.add(desiredPieceID);
					}
				}
			}
		}
		if (possiblePieces.size() != 0)
		{
			// generate a random number 0 and size of possiblePieces
			Random rand = new Random();
			int idx = rand.nextInt(possiblePieces.size());
			
			// access that element from the possiblePieces list and return
			int pieceIndex = possiblePieces.get(idx);
			this.piecesAskedfor.add(pieceIndex);
			return pieceIndex;
		}
		return -1;
	}

	private synchronized int findAndLogRequestedPiece(int pieceIndex)
	{
		if(this.piecesAskedfor.contains(pieceIndex))
		{
			return -1;
		}
		return pieceIndex;
	}

	
}
