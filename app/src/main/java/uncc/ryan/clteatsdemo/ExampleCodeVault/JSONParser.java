package uncc.ryan.clteatsdemo.ExampleCodeVault;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import uncc.ryan.clteatsdemo.SearchActivity;

/**
 * Created by Ryrid on 4/1/2017.
 */

public class JSONParser extends AsyncTask<String, Integer, List<HashMap<String,String>>> {
    JSONObject jsonObject;

    @Override
    protected List<HashMap<String,String>> doInBackground(String... jsonData){
        List<HashMap<String,String>> places = null;
        Place_JSON place_json = new Place_JSON();

        try{
            jsonObject = new JSONObject(jsonData[0]);

            places = place_json.parse(jsonObject);
        }catch (Exception e){
            Log.d("Exception",e.toString());
        }
        return places;
    }

    @Override
    protected void onPostExecute(List<HashMap<String,String>> list) {
        Log.d("Map", "list size: " + list.size());

        SearchActivity.mMap.clear();

        for (int i = 0; i < list.size(); i++) {

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);


            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");

            Log.d("Map", "place: " + name);

            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");

            LatLng latLng = new LatLng(lat, lng);

            // Setting the position for the marker
            markerOptions.position(latLng);

            markerOptions.title(name + " : " + vicinity);

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            // Placing a marker on the touched position
            Marker m = SearchActivity.mMap.addMarker(markerOptions);
        }
    }
}
