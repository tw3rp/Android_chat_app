package com.example.waychat;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends Activity {
	
	int bytesRead=0;
	PrintWriter out;
	String nil=null;
	public void clearentername(){
		runOnUiThread(new Runnable() {
			public void run(){
			EditText text = (EditText)findViewById(R.id.entername);
		text.setText(nil);}});
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		Button send =(Button) findViewById(R.id.send);
	    send.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View arg0){
	    		EditText text = (EditText)findViewById(R.id.entername);
	    		String convtext = text.getText().toString();
	    		out.println(convtext);
	    		//text.setText(nil);
	    		clearentername();
	    	}
	    });
		
		//String convtext = text.getText().toString();
		
		
		
		Button start =(Button) findViewById(R.id.start);
	    start.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
	    		Button button = (Button) v;
	    		button.setVisibility(View.GONE);
				Thread cThread = new Thread(new ClientThread());
				cThread.start();
			}
	    	final class ClientThread implements Runnable{
	    		public void viewmessage(final String printthis){runOnUiThread(new Runnable() {
	   		     @Override
			     public void run() {TextView text1 = (TextView) findViewById(R.id.text1);
	    			text1.append(printthis);
	    			//EditText text8 = (EditText)findViewById(R.id.entername);
	    			//text8.setText(nil);

			//stuff that updates ui

			    }
			});
	    			
	    		}
	    		
	    		
	    		
				public void run() {
						
					EditText text7 = (EditText)findViewById(R.id.entername);
		    		String convtext = text7.getText().toString();
		    		clearentername();
		    		
		    		//text_vi text = (EditText)findViewById(R.id.entername);
		    		/*TextView text1 = (TextView) findViewById(R.id.text1);
		    		text1.setText(convtext);*/
					
					Socket sock1; 
					
					try{
						sock1 = new Socket("ec2-54-68-62-42.us-west-2.compute.amazonaws.com", 9001);
					   viewmessage("Waiting...");
					
			          
			          viewmessage("Accepted connection : " + sock1);
			          
			          //counter++;
			       // receive file
			        
			          // InputStream is = sock1.getInputStream();
			            BufferedReader in = new BufferedReader(new InputStreamReader(sock1.getInputStream()));
			            //BufferedReader br = new BufferedReader(i);
			            //int buffsize = sock1.getReceiveBufferSize();
			            //byte [] mybytearray  = new byte [buffsize];
			            //FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/recieved1.jpg"); // destination path and name of file
			            out = new PrintWriter(sock1.getOutputStream(), true);
			            while (true) {
			                String line = in.readLine();
			                if (line.startsWith("SUBMITNAME")) {
			                	
			                	viewmessage(convtext);
			                	out.println(convtext);
			                	clearentername();
			                } else if (line.startsWith("NAMEACCEPTED")) {
			                    continue;
			                } else if (line.startsWith("MESSAGE")) {
			                	viewmessage(line.substring(8) + "\n");
			                    Date date = new Date();
			                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
			                    String formattedDate = sdf.format(date);
			                    System.out.println(formattedDate);
			                    viewmessage(formattedDate+"\n");
			                }
			            }
			            
			            
			            //out.flush();
			            
			           // out.close();



			         // sock1.close();
					
}
					
					catch (UnknownHostException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
				
					
					// TODO Auto-generated method stub
					}
	    	}});
	    
	    } 
	    
	    


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
