package tn.iac.inscriptioniac;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//AsyncTask to send data as a http POST request
public class PostDataTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private CoordinatorLayout coordinatorLayout;
    private String[] contactData;

    public PostDataTask(Context context, CoordinatorLayout coordinatorLayout){
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
    }

    @Override
    protected Boolean doInBackground(String... contactData) {
        this.contactData = contactData;
        Boolean result = true;
        String url = contactData[0];
        String name = contactData[1];
        String number = contactData[2];
        String email = contactData[3];
        String study = contactData[4];
        String workshops = contactData[4];
        String teams = contactData[4];
        String postBody="";

        try {
            //all values must be URL encoded to make sure that special characters like & | ",etc.
            //do not cause problems
            postBody = Common.NAME_KEY+"=" + URLEncoder.encode(name, "UTF-8") +
                    "&" + Common.NUMBER_KEY + "=" + URLEncoder.encode(number,"UTF-8") +
                    "&" + Common.EMAIL_KEY + "=" + URLEncoder.encode(email,"UTF-8") +
                    "&" + Common.STUDY_KEY + "=" + URLEncoder.encode(study,"UTF-8") +
                    "&" + Common.WORKSHOPS_KEY + "=" + URLEncoder.encode(workshops,"UTF-8")+
                    "&" + Common.TEAMS_KEY + "=" + URLEncoder.encode(teams,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            result=false;
        }

            /*
            //If you want to use HttpRequest class from http://stackoverflow.com/a/2253280/1261816
            try {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.sendPost(url, postBody);
		}catch (Exception exception){
			result = false;
		}
            */

        try{
            //Create OkHttpClient for sending request
            OkHttpClient client = new OkHttpClient();
            //Create the request body with the help of Media Type
            RequestBody body = RequestBody.create(Common.FORM_DATA_TYPE, postBody);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            //Send the request
            Response response = client.newCall(request).execute();
        }catch (IOException exception){
            result=false;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result){
        //Print Success or failure message accordingly
        if(!result) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, context.getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                    .setAction(context.getResources().getString(R.string.retry), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Create an object for PostDataTask AsyncTask
                            PostDataTask postDataTask = new PostDataTask(context, coordinatorLayout);
                            //execute asynctask
                            postDataTask.execute(contactData);
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);

            snackbar.show();
        }else{

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, context.getResources().getString(R.string.response), Snackbar.LENGTH_LONG);

            snackbar.show();
        }
    }

}