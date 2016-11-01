package br.com.robson.fut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Robson on 02/10/2016.
 */

public class Connection {

    private Context context;
    private String result;
    private ICallback mCallback;


    public Connection(Context context, ICallback dao)
    {
        this.context = context;
        this.mCallback = dao;
    }

    public void doRequest(MessageRequest request) throws Exception {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(request);
        } else {
            throw new Exception("Rede indisponível");
        }

    }

    private class DownloadWebpageTask extends AsyncTask<MessageRequest, Void, MessageResponse> {

        @Override
        protected MessageResponse doInBackground(MessageRequest... requests) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(requests[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return new MessageResponse("Unable to retrieve web page. URL may be invalid.",
                        requests[0].getMessageCodeEnum());
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(MessageResponse result) {
            mCallback.notifyDataRefreshed(result);
        }
    }

    private MessageResponse downloadUrl(MessageRequest request) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(request.getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            if (request.isNotConvertToString())
            {
                MessageResponse messageResponse = new MessageResponse("",request.getMessageCodeEnum());
                Bitmap img = BitmapFactory.decodeStream(is);
                messageResponse.setBitmap(img);
                return messageResponse;
            }

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);


            return new MessageResponse(contentAsString, request.getMessageCodeEnum());

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        StringBuilder result = new StringBuilder();
        while((line = bufferedReader.readLine()) != null)
            result.append(line);
        return result.toString();
    }
}
