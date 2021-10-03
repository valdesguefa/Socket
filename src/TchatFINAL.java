import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Serveur extends Thread
{
private int port_ecoute;
	
public int getPortcoute() {
		return port_ecoute;
	}
	public void setPortcoute(int portcoute) {
		this.port_ecoute = portcoute;
	}
	public Serveur(int a)
	{
		this.port_ecoute=a;
	}
	int i=0;
@Override
 public void run()
 {
		try {
			
			ServerSocket ss= new ServerSocket(port_ecoute);
			Socket s1=ss.accept();
			String addresse1=s1.getRemoteSocketAddress().toString();
			System.out.println("connexion etablie avec succes avec le client "+i+" d'addresse_IP: "+addresse1);
			
			Socket s2=ss.accept();
		    String addresse2=s2.getRemoteSocketAddress().toString();
		    System.out.println("connexion etablie avec succes avec le client "+i+" d'addresse_IP: "+addresse2);
			
			Conversation conversation1 = new Conversation(s1, s2, addresse1, addresse2, i++);
			Conversation conversation2 = new Conversation(s2, s1, addresse2, addresse1, i++);
			conversation1.start();
			conversation2.start();
			}
		
		    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 }
}

class Conversation extends Thread
{
	private Socket s1,s2;
	private String addresse1, addresse2;
	private int i;
	
	public Socket getS1() {
		return s1;
	}

	public void setS1(Socket s1) {
		this.s1 = s1;
	}

	public Socket getS2() {
		return s2;
	}

	public void setS2(Socket s2) {
		this.s2 = s2;
	}

	public String getAddresse1() {
		return addresse1;
	}

	public void setAddresse1(String addresse1) {
		this.addresse1 = addresse1;
	}

	public String getAddresse2() {
		return addresse2;
	}

	public void setAddresse2(String addresse2) {
		this.addresse2 = addresse2;
	}

	public void seti(int i)
	{
		this.i=i;
	}
	public int geti()
	{
		return i;
	}
	
	public Conversation(Socket a, Socket b, String c, String d, int i)
	{
		this.s1=a;
		this.s2=b;
		this.addresse1=c;
		this.addresse1=c;
		this.i=i;
	}
	
	@Override
	public void run()
	{
		try
		{
		String fin="fin";
		String message="[votre message a ete recu avec succes]";
		//System.out.println("connexion etablie avec succes avec le client "+i+" d'addresse_IP: "+addresse1);
		//STREAM DE S1
		InputStream is1= s1.getInputStream();
		InputStreamReader isr1=new InputStreamReader(is1);
		BufferedReader br1= new BufferedReader(isr1);
		
		OutputStream os1=s1.getOutputStream();
		PrintWriter pw1=new PrintWriter(os1,true);
		
		//STREAM DE S2
		InputStream is2= s2.getInputStream();
		InputStreamReader isr2=new InputStreamReader(is2);
		BufferedReader br2= new BufferedReader(isr2);
		
		OutputStream os2=s2.getOutputStream();
		PrintWriter pw2=new PrintWriter(os2,true);
		String str="";
		/*
		String str = "connexion etablie.......";
		pw2.println(str);
		pw1.println(str);
		*/
		while(!(str.equals(fin)))
		{
		
			String str1=br1.readLine();
			System.out.println("le message recu du client "+i+" d'addresse_IP: "+addresse1+" est :"+str1);
			
			if(str1.length()!=0)
			{
				pw1.println(message);
				pw2.println(str1);
				//pw1.println(message);
				
			}
			//pw1.println(message);
			
	    }	
	
		System.out.println("--------------------------");
		System.out.println("ARRET DE LA COMMUNICATION");
		System.out.println("--------------------------");
		
		//FERMETURE DES STREAMS ET DE LA SOCKET S2
		is2.close();
		isr2.close();
		br2.close();
		os2.close();
		pw2.close();
	s2.close();
		
		//FERMETURE DES STREAMS ET DE LA SOCKET S1
		is1.close();
		isr1.close();
		br1.close();
		os1.close();
		pw1.close();
	s1.close();
	
	}catch(IOException e)
	{
		e.printStackTrace();
	}
	
}
}

public class TchatFINAL  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i=1234;
		Serveur serveur=new Serveur(i);
	    serveur.start();
	
	}
}
