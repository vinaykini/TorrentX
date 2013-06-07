import java.io.*;
import java.util.*;

public class WriteLog
{
  //Creating a Log file named log_peer_[peerID].log if one doesnt exist
  public void CreateLog(String peer)
  {
     try {
            String filename = "peer_" + peer+"/log_peer_"+peer+".log";
            File directory = new File("peer_" + peer);
	        directory.mkdir();
	        
			File file = new File(filename);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Log File for Peer "+peer+".");
			bw.newLine();
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}   
  }
  
  //Returns present local date time hours minutes and seconds 
   public String getDate() {
       // Instantiate a Date object
       Date date = new Date();
        
       // display time and date using toString()
       return (date.toString());
   }

  //Writing Log for Incoming TCP connection from Peer 2 to peer 1
  public void TcpConnectionIncoming(String peer1, String peer2)
  {        
	  String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
			File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
 
           try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" is connected from Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
  }
  
  
  public void ServerConnectionIncoming(String peer1, String address, String port)
  {        
	  String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
			File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
 
           try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+"***Server for peer: " +peer1+ " is ready to listen at :" + address + " at port : " + port ;
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
  }
  
  
  // Writing Log for making the TCP connection from Peer1 to Peer2
  public void TcpConnectionOutgoing(String peer1, String peer2)
  {        String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
			File file = new File(filename);
            if (!file.exists()) {
				CreateLog(peer1);
			}
           try
           {
             
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" makes a connection to Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
  }
  
  
  public void PrefNeighbours(String peer1, String prefPeerList)
  {
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" has the Preferred neighbours "+prefPeerList+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }
  
  public void OptUnchokedNeighbours(String peer1, String peer2)
  {
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" has the optimistically unchoked neighbour Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }
  
  
  public void Unchoked(String peer1, String peer2)
  {
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" is unchoked by Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }
  
  public void Choked(String peer1, String peer2)
  {
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" is choked by Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }
  
  public void Have(String peer1, String peer2, int index)
  {
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" received the 'HAVE' message from Peer "+peer2+" for the piece "+index+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }
  
  public void Interested(String peer1, String peer2)
  {
	  String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" received the 'INTERESTED' message from Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
  }
  
  public void NotInterested(String peer1, String peer2)
  {
	  String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" received the 'NOT INTERESTED' message from Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }    
  }
  
  public void PieceDownload(String peer1, String peer2, int index, int pieces)
  {
	  String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(peer1);
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" has downloaded the piece "+index+" from Peer "+peer2+". Now the number of pieces is "+pieces+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
  }
  
  public void DownloadComplete(String peer1)
  {
	  String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
                             
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" has downloaded the complete file.";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
  }
  
  
  ///*****************************log handshake message begind*****************************************
  
  public void ReceivedHandshake(int peer1, int peer2)
  {
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(Integer.toString(peer1));
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" receives handshake from Peer "+peer2+".";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }


  public void SentHandshake(int peer1)
  {
	  
	  		String filename = "peer_" + peer1+"/log_peer_"+peer1+".log";
            File file = new File(filename);
            if (!file.exists()) 
            {
		CreateLog(Integer.toString(peer1));
            }
            
            try
           {
               FileWriter fw = new FileWriter(filename,true); //the true will append the new data
               BufferedWriter bw = new BufferedWriter(fw);
               String str = getDate()+": Peer "+peer1+" sends handshake message. ";
               bw.write(str);//appends the string to the file
               bw.newLine();
               bw.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
      
  }

  ///***************************log handshake message ends here*****************************************

  
}

