package com.damirh.easypark.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.damirh.easypark.R;
import com.damirh.easypark.views.BoundedMapView;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.modules.MapTileDownloader;
import org.osmdroid.tileprovider.modules.MapTileFilesystemProvider;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.modules.NetworkAvailabliltyCheck;
import org.osmdroid.tileprovider.modules.TileWriter;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import model.Parking;
import connector.ParkingFunctions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STARTX = "start_x";
    private static final String ARG_STARTY = "start_y";
    private double startX;
    private double startY;
    public List<Parking> p;
    private BoundedMapView mapView;
    private ArrayList<OverlayItem> parkingSpotOverlays;
    private ItemizedOverlay<OverlayItem> mMyLocationOverlay;
    private ResourceProxy mResourceProxy;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(double x, double y) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_STARTX, x);
        args.putDouble(ARG_STARTY, y);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            startX = getArguments().getDouble(ARG_STARTX);
            startY = getArguments().getDouble(ARG_STARTY);
        }
        p = new ArrayList<Parking>();
        new ParkingFunctions(this).execute("S");

    }
    public void setParkingList(List<Parking> lp1) {
        for(int i = 0;i < lp1.size();i++) {
            this.p.add(lp1.get(i));
        }
        populateMap();
    }
    public void populateRealData() {
        parkingSpotOverlays = new ArrayList<OverlayItem>();
        Log.d("myTag",this.p.size()+" u crtanju");
        int n = 1;
        if(this.p != null) {
            n = this.p.size();
        }
        for (int i=0; i < n; i++) {
            Log.d("myTag",this.p.get(i).get_latitude()+" "+this.p.get(i).get_longitude()+" unutar RealData");
            parkingSpotOverlays.add(new OverlayItem("Parking #"+this.p.get(i).get_parkingID(), "TEST", new org.osmdroid.util.GeoPoint(this.p.get(i).get_latitude(), this.p.get(i).get_longitude())));
        }
    }
    public void populateDummyData() {
        parkingSpotOverlays = new ArrayList<OverlayItem>();

            parkingSpotOverlays.add(new OverlayItem("Parking 1", " asda" + "", new org.osmdroid.util.GeoPoint(startX, startY)));
            parkingSpotOverlays.add(new OverlayItem("Parking 2", "Opis za parking 2", new org.osmdroid.util.GeoPoint(startX + 0.001f, startY - 0.001f)));
            parkingSpotOverlays.add(new OverlayItem("Parking 3", "Opis za parking 3", new org.osmdroid.util.GeoPoint(startX - 0.041f, startY + 0.003f)));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (BoundedMapView) mView.findViewById(R.id.mapView);
        mapView.setTileSource(GetTileSource());
        mapView.setClickable(true);
        mapView.setMultiTouchControls(true);


        mapView.getController().setZoom(16);

        mapView.getController().setCenter(new org.osmdroid.util.GeoPoint(startX, startY));


        //GeoPoint geoP = new GeoPoint(48115436,-1638084);
        //mapView.getController().setCenter(new GeoPoint((int)startX,(int)startY));

        return mView;
    }


    private void populateMap() {
        populateRealData();
        mResourceProxy = new DefaultResourceProxyImpl(getActivity().getApplicationContext());
        mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(parkingSpotOverlays,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                                                     final OverlayItem item) {
                        Toast.makeText(
                                getActivity(),
                                item.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index,
                                                   final OverlayItem item) {
                        Toast.makeText(
                                getActivity(),
                                item.getSnippet() ,Toast.LENGTH_LONG).show();
                        return false;
                    }
                }, mResourceProxy);
        this.mapView.getOverlays().add(this.mMyLocationOverlay);
        mapView.invalidate();
    }

    private MapTileProviderArray buildProviderArray() {
        final Context context = getActivity();
        final Context applicationContext = context.getApplicationContext();
        final IRegisterReceiver registerReceiver = new SimpleRegisterReceiver(applicationContext);

        // Create a custom tile source
        final ITileSource tileSource = new XYTileSource("Mapnik", ResourceProxy.string.mapnik, 9, 18, 256, ".png", new String[]{"http://tile.openstreetmap.org/"});

        // Create a file cache modular provider
        final TileWriter tileWriter = new TileWriter();
        final MapTileFilesystemProvider fileSystemProvider = new MapTileFilesystemProvider(registerReceiver, tileSource);


        // Create a download modular tile provider
        final NetworkAvailabliltyCheck networkAvailabliltyCheck = new NetworkAvailabliltyCheck(context);
        final MapTileDownloader downloaderProvider = new MapTileDownloader(tileSource, tileWriter, new NetworkAvailabliltyCheck(context));

        // Create a custom tile provider array with the custom tile source and the custom tile providers
        return new MapTileProviderArray(tileSource, registerReceiver, new MapTileModuleProviderBase[] { fileSystemProvider, downloaderProvider });
    }

    public ITileSource GetTileSource() {
        return new XYTileSource("Mapnik", ResourceProxy.string.mapnik, 1, 18, 256, ".png", new String[]{"http://tile.openstreetmap.org/"});
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }

}
