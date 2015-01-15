package connector;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.damirh.easypark.MapActivity;
import com.damirh.easypark.fragments.LoginFragment;
import com.damirh.easypark.fragments.MapFragment;
import com.damirh.easypark.fragments.ParkingDetailsFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import model.Parking;
import model.Person;

/**
 * Created by Dado on 13.1.2015.
 */
public class loginUserJSON extends AsyncTask<String,Void,Person> {
    private Exception e;
    private String usr,psw;
    private MapActivity lf;
    public loginUserJSON(String user, String pass, MapActivity lf1) {
        usr = user;
        psw = pass;
        lf = lf1;
    }
    protected void onPreExecute() {
        try{
            // Set Request parameter
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    protected void onProgressUpdate(Integer... progress) {
        Toast.makeText(
                lf,
                "Provjeravam upis!", Toast.LENGTH_LONG).show();
    }
    @Override
    protected Person doInBackground(String... url) {
        Person p = new Person();
        try {
            String result = "PRIJE";
            HttpGet get = new HttpGet("http://easypark.site11.com/person.json");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(get);
            result = EntityUtils.toString(response.getEntity());
            JSONObject o = new JSONObject(result);
            String tempusr = o.getString("_email");
            if (!(o.toString().equals("{}") && tempusr.equals(usr) && o.getString("_password").equals(psw)))
                if (usr != "") {
                    Log.d("myTag", "USAO!");
                    p.set_email(o.getString("_email"));
                    p.set_firstname(o.getString("_firstname"));
                    p.set_lastname(o.getString("_lastname"));
                    p.set_personID(o.getInt("_personID"));
                    p.set_phonenumber(o.getString("_phonenumber"));
                    p.set_type(o.getInt("_type"));
                } else {
                    p.set_personID(0);
                }
            else {
                p.set_personID(0);
            }
            publishProgress();
            Log.d("myTag", p.get_lastname());
            return p;
        } catch (Exception e) {
            Log.d("myTag", e.getMessage());
        }
        return null;
    }
    public void  onPostExecute(Person lplocal) {
        try {
            super.onPostExecute(lplocal);
            lf.loginPerson(lplocal);
        }
        catch (Exception e) {
            Log.d("myTag", e.getMessage());
        }
    }
}
