package connector;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;

import com.damirh.easypark.MapActivity;
import com.damirh.easypark.fragments.MapFragment;

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

/**
 * Created by Dado on 13.1.2015.
 */
public class ParkingFunctions extends AsyncTask<String,Void,List<Parking>> {
    private MapFragment ma;
    private Exception exception;
    public ParkingFunctions (MapFragment mf) {
        ma = mf;
    }
    protected void onPreExecute(MapFragment mf) {
        try{
            // Set Request parameter
            ma = mf;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    @Override
    protected List<Parking> doInBackground(String... url) {
        List<Parking> lp = new ArrayList<Parking>();
        Log.d("myTag", "Usao u funkciju!");
        try {
            String result = "PRIJE";
            HttpGet get = new HttpGet("http://easypark.site11.com/park.json");
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(get);
            result = EntityUtils.toString(response.getEntity());
            JSONArray parkinglist = new JSONArray(result);
            int n = parkinglist.length();
            for (int i =0;i<n; i++) {
                JSONObject rs = parkinglist.getJSONObject(i);
                Parking p = new Parking();
                p.set_parkingID(rs.getInt("_parkingID"));
                p.set_creator(rs.getString("_creator"));
                p.set_longitude(rs.getDouble("_longitude"));
                p.set_latitude(rs.getDouble("_latitude"));
                p.set_note(rs.getString("_note"));
                p.set_price(rs.getLong("_price"));
                p.set_pictureID(rs.getInt("_pictureID"));
                p.set_totalnumber(rs.getInt("_totalnumber"));
                Boolean b = (rs.getBoolean("_isthereCamera"));
                p.set_isthereCamera(b);
                p.set_isthereGuard(rs.getBoolean("_isthereGuard"));
                p.set_isthereLight(rs.getBoolean("_isthereLight"));
                p.set_isthereGoodEntrance(rs.getBoolean("_isthereGoodEntrance"));
                p.set_isthereRoof(rs.getBoolean("_isthereRoof"));
                p.set_isthereRoad(rs.getBoolean("_isthereRoad"));
                p.set_secrating(rs.getLong("_secrating"));
                p.set_ovrrating(rs.getLong("_ovrrating"));
                p.set_telefon(rs.getString("_telefon"));
                p.set_freespots(rs.getInt("_freespots"));
                p.set_takenspots(rs.getInt("_takenspots"));
                lp.add(p);
            }
            return lp;
        } catch (Exception e) {
            Log.d("myTag", e.getMessage());
        }
        return null;
    }
    public void  onPostExecute(List<Parking> lplocal) {
        try {
            super.onPostExecute(lplocal);
            ma.setParkingList(lplocal);
        }
        catch (Exception e) {
            Log.d("myTag", e.getMessage());
        }
    }
}
