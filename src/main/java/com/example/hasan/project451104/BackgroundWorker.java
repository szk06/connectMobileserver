package com.example.hasan.project451104;

/**
 * Created by Hasan on 04/05/2016.
 */
import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.*;
import java.net.*;


/**
 * Created by Sami Kanafani on 5/4/2016.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String>{

    Context context;
    AlertDialog alertDialog;

/*    BackgroundWorker(WifiP2pManager.PeerListListener ctx){
        context = (Context) ctx;
    }
*/


    @Override
    protected  String doInBackground(String... params){
        String type = params[0];

        //Conncetion String with the Server

        //For local Connection use this one:
        String login_url = "http://10.168.52.117:8080/conn/p2p.php";
        /////////////////////////

        //For online connection use this one
        //String login_url = "http://connect451.rf.gd/connection.php";
        if(type.equals("connect") || type.equals("disconnect") ){
            try {

                /////////////////////////Declaration of the strings to send////////////////////
                String myname = params[1];
                String mymac = params[2];
                String hismac = params[3];
                String hisname = params[4];
                /////////////////////////////////////////////

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                ///Don't forget to add the passed strings here (if you add any info to be sent)
                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"+
                        URLEncoder.encode("myname","UTF-8")+"="+URLEncoder.encode(myname,"UTF-8")+"&"+
                        URLEncoder.encode("mymac","UTF-8")+"="+URLEncoder.encode(mymac,"UTF-8")+"&"+
                        URLEncoder.encode("hismac","UTF-8")+"="+URLEncoder.encode(hismac,"UTF-8")+"&"+
                        URLEncoder.encode("hisname","UTF-8")+"="+URLEncoder.encode(hisname,"UTF-8");
                /////////////////////////////
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line;
                while((line= bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPreExecute(){

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Update Server Status");
    }
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        Log.d("result",result);
        alertDialog.show();

    }
    protected void onProgressUpdate(Void...values){
        super.onProgressUpdate(values);
    }



}
