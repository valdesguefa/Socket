import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
class Recevoir extends Thread
{
	private BufferedReader br;
	public Recevoir(BufferedReader br)
	{
	    this.br=br;
	}
	
	@Override
	public void run()
	{
		try
		{
			
			while(true)
			{
				String str1=br.readLine();
				
				if((str1.length()!=0)&&(!(str1.equals("[votre message a ete recu avec succes]"))))
				{
					System.out.println("");
					System.out.println("vous avez recu un nouveau message ");
					System.out.println("->"+str1);	
				}
				else
				{
					//System.out.println("");
					System.out.println(""+str1);	
				}
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
class Envoyer extends Thread
{
	private PrintWriter pw;
	private String str;
	public Envoyer(String str, PrintWriter a)
	{
		this.str=str;
		this.pw=a;
	}
@Override
public void run()
{
	
	if(str.length()!=0)
	{
		 pw.println(str);
	}
}
	
	
}

public class Client {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

Scanner sc=new Scanner(System.in);
		try
		{
			String fin="fin";
			Socket s=new Socket("localhost",1234);
			InputStream is=s.getInputStream();
			InputStreamReader isr= new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			
			OutputStream os=s.getOutputStream();
			PrintWriter pw=new PrintWriter(os,true);
			String str="";
			
			Recevoir recevoir=new Recevoir(br);
			recevoir.start();
			
			System.out.print("CONEXION ETABLIE,  entrez votre message\n");
			while(!(str.equals(fin))){
				
				
				//System.out.print("CONEXION ETABLIE,  entrez votre message\n");
								str=sc.nextLine();
				
				Envoyer envoyer=new Envoyer(str, pw);
				envoyer.start();
			  }
			
			System.out.println("--------------------------");
			System.out.println("ARRET DE LA COMMUNICATION");
			System.out.println("--------------------------");
			is.close();
			isr.close();
			br.close();
			pw.close();
			os.close();
			s.close();
		}
		catch(IOException e)
		
		{
			e.printStackTrace();
		}
 }

}
