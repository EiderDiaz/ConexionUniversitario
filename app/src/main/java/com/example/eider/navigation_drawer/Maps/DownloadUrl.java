package com.example.eider.navigation_drawer.Maps;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Eider on 30/10/2017.
 */

public class DownloadUrl {

    public  String readUrl(String myUrl, Context context) throws IOException{
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url= new  URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null ){
                stringBuffer.append(line);
            }
            data= stringBuffer.toString();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            Toast.makeText(context," "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
