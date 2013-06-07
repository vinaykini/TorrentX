import java.io.IOException;
import java.util.*;

public class ManageOptimisticNeighbours implements Runnable
{
	private Connection myConnection;
	WriteLog w = new WriteLog();

	public ManageOptimisticNeighbours(Connection Connection)
	{
		this.myConnection = Connection;
	}

	@Override
	public void run()
	{
		try
		{
			this.optUnchokedPeer();
		} catch (Exception e)
		{
			e.printStackTrace();
		} 
	}

	/**
	 * select one peer out of all peers in connected peer list and unchoke it
	 * at the same time choke one of already connected peers with least uploading rate
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void optUnchokedPeer() throws IOException, InterruptedException
	{

		Integer prevPeer = myConnection.getUnchokedPeer_Prev();
		if (prevPeer != -1)
			myConnection.reportChokedPeer(myConnection.getUnchokedPeer_Prev());

		Set<Integer> chokedPeersSet = myConnection.getChokedPeers();
		List<Integer> interestedAndChoked = new LinkedList<Integer>();
		interestedAndChoked.addAll(myConnection.getmyInterestedNeighbours());
		interestedAndChoked.retainAll(chokedPeersSet);
		if(interestedAndChoked.size() > 0)
		{
			Random rand = new Random();
			int selectedPeer = interestedAndChoked.get(rand.nextInt(interestedAndChoked.size()));
			myConnection.reportUnchokedPeer(selectedPeer);
			myConnection.setUnchokedPeer_Prev(selectedPeer);
			w.OptUnchokedNeighbours(Integer.toString(myConnection.getMyPeerID()), Integer.toString(selectedPeer));
		}
	}
}
