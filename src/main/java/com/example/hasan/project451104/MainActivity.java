package com.example.hasan.project451104;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
*/
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //////////////////Declaration of Strings to be sent to the server////////////

    static String mymacaddress = "";
    String flag = "";
    static String mydevicename = "";
    //////////////////End of Declaration of Strings//////////////////////////////

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  //  private GoogleApiClient client;
    public AccessibilityService context;
    private final IntentFilter intentFilter = new IntentFilter();
    private final IntentFilter mIntentFilter = new IntentFilter();

    static boolean isWifiP2pEnabled = false;

    WiFiDirectBroadcastReceiver mReceiver;

    public static void setIsWifiP2pEnabled(boolean b) {
        isWifiP2pEnabled = b;
    }

    static ArrayList<String> trial = new ArrayList<>();//olddevicenames static
    static ArrayList<String> StaticAddresses = new ArrayList<>();
    static ArrayList<Date> latestDetection = new ArrayList<>();
    static ArrayList<String> Combiner = new ArrayList<>();
    static ArrayList<Boolean> Covered = new ArrayList<>();
    static ArrayList<Integer> StaticFrequency = new ArrayList<>();
    static ArrayList<String> StaticDisconnected = new ArrayList<>();
    static ArrayList<Long> StaticSession = new ArrayList<>();
    static ArrayList<Long> StaticCumulative = new ArrayList<>();

    static ListView lv;
    int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    //    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        //getMAC
        TextView t4 = (TextView) findViewById(R.id.textView);
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String macaddress = manager.getConnectionInfo().getMacAddress();
        t4.setText(macaddress);
        //endgetMAC

        //get wifi
        TextView t1 = (TextView) findViewById(R.id.textView2);
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (wifi.isWifiEnabled()) {
            t1.setText("On");
            t1.setTextSize(20);

        } else {
            t1.setText("Off");
            t1.setTextSize(20);
        }
        //    BLUETOOTH
        TextView t2 = (TextView) findViewById(R.id.textView3);
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

        if (bluetooth == null) {
            t2.setText("Device does not possess Bluetooth capabilities");
        } else {
            if (bluetooth.isEnabled()) {
                t2.setText("ON");
                t2.setTextSize(20);
            } else {
                t2.setText("OFF");
                t2.setTextSize(20);
            }
        }

        //      CELLULAR
        TextView t3 = (TextView) findViewById(R.id.textView4);
        boolean mobileDataAllowed = Settings.Secure.getInt(getContentResolver(), "mobile_data", 1) == 1;
        if (mobileDataAllowed) {
            t3.setText("ON");
            t3.setTextSize(20);
        } else {
            t3.setText("OFF");
            t3.setTextSize(20);
        }

        //IntentFilter mIntentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        //  **************WIFI P2P**********************************
        final TextView t9 = (TextView) findViewById(R.id.textView5);
        final WifiP2pManager p2p = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        final WifiP2pManager.Channel channel = p2p.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(p2p, channel, this);

        // trial.add(mReceiver.olddeviceNames.get(0));



        p2p.discoverPeers(channel,
                new ActionListener() {
                    @Override
                    public void onSuccess() {

                        t9.setText("Success");

                    }

                    @Override
                    public void onFailure(int i) {
                        t9.setText("Failure! Reason: " + String.valueOf(i));
                    }

                }


        );

    }
    public void updatelist(View v) {

        for (int i = 0; i < trial.size(); i++) {

        if (!Covered.get(i) ) {
            Combiner.add(i, trial.get(i) + "\n" + StaticAddresses.get(i) + "\n" + "Latest Detection: " + latestDetection.get(i)+
                    "\n" + "Frequency of Detection: " + StaticFrequency.get(i)
            + "\n" + "Currently: " + StaticDisconnected.get(i)
        + "\n" + "Latest session duration: " + StaticSession.get(i)/1000 + " seconds"
            + "\n" + "Cumulative connectivity duration: " + StaticCumulative.get(i)/1000 + " seconds");
            Covered.set(i, true);
        }

            else {
            Combiner.set(i, trial.get(i) + "\n" + StaticAddresses.get(i) + "\n" + "Latest Detection: " + latestDetection.get(i) +
                    "\n" + "Frequency of Detection: " + StaticFrequency.get(i)
                    + "\n" + "Currently: " + StaticDisconnected.get(i)
                    + "\n" + "Latest session duration: " + StaticSession.get(i)/1000 + " seconds"
                    + "\n" + "Cumulative connectivity duration: " + StaticCumulative.get(i)/1000 + " seconds");
        //update latestdetection
        }



        }
        RadioButton SortName = (RadioButton) findViewById(R.id.radioButton);
        RadioButton SortDetection = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton SortFrequency = (RadioButton) findViewById(R.id.radioButton3);
        if(SortName.isChecked()) {
        for (int j = 0; j < trial.size(); j++) {
                for (int i = j + 1; i < trial.size(); i++) {
                    if (trial.get(i).compareTo(trial.get(j)) < 0) {
                        String temp = Combiner.get(j);
                        Combiner.set(j, Combiner.get(i));
                        Combiner.set(i, temp);
                    }
                }

            }
        }
        else if (SortDetection.isChecked()) {
            for (int j = 0; j < Combiner.size(); j++) {
                for (int i = j + 1; i < Combiner.size(); i++) {
                    if (latestDetection.get(i).getTime() - (latestDetection.get(j).getTime()) < 0) {
                        String temp = Combiner.get(j);
                        Combiner.set(j, Combiner.get(i));
                        Combiner.set(i, temp);
                    }
                }

            }
        }
        else if (SortFrequency.isChecked()){
            for (int j = 0; j < Combiner.size(); j++) {
                for (int i = j + 1; i < Combiner.size(); i++) {
                    if (StaticFrequency.get(i).compareTo(StaticFrequency.get(j)) < 0) {
                        String temp = Combiner.get(j);
                        Combiner.set(j, Combiner.get(i));
                        Combiner.set(i, temp);


                    }
                }

            }
        }
        else {
            //do nothing
        }
        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, Combiner
        );

        lv.setAdapter(arrayAdapter);
       // trial.clear();
    }


    public void onWifiClick(View v) {
        TextView t1 = (TextView) findViewById(R.id.textView2);
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (wifi.isWifiEnabled()) {
            t1.setText("On");
            t1.setTextSize(20);

        } else {
            t1.setText("Off");
            t1.setTextSize(20);
        }

    }





    public void onBluetoothClick(View v) {
        TextView t2 = (TextView) findViewById(R.id.textView3);
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

        if (bluetooth == null) {
            t2.setText("Device does not possess Bluetooth capabilities");
        } else {
            if (bluetooth.isEnabled()) {
                t2.setText("ON");
                t2.setTextSize(20);
            } else {
                t2.setText("OFF");
                t2.setTextSize(20);
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void onCellularClick(View v) {
        TextView t3 = (TextView) findViewById(R.id.textView4);
        //boolean mobileDataEnabled = false;
        //ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //boolean is3g = cm.getAllNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        /*try{
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            mobileDataEnabled = (Boolean)method.invoke(cm);
        }
        catch (Exception e){
        }*/
        //NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        //boolean info = networkInfo.isConnected();
        //if (info==true)
        // t3.setText("YES");
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        boolean mobileDataAllowed = Settings.Secure.getInt(getContentResolver(), "mobile_data", 1) == 1;
        if (mobileDataAllowed) {
            t3.setText("ON");
            t3.setTextSize(20);
        } else {
            t3.setText("OFF");
            t3.setTextSize(20);
        }
    }

    public void GetMAC(View v) {
        TextView t4 = (TextView) findViewById(R.id.textView);
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String macaddress = manager.getConnectionInfo().getMacAddress();
        t4.setText(macaddress);

    }
    public void sendtoserver(View v) {
        TextView t = (TextView) findViewById(R.id.textView6);
        for (int i = 0; i < trial.size(); i++)
            t.setText(trial.get(i));
    }


    public void Wifip2p (View v) {
        final TextView t1 = (TextView) findViewById(R.id.textView5);
        final WifiP2pManager p2p = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        final WifiP2pManager.Channel channel = p2p.initialize(this, getMainLooper(), null);
        WiFiDirectBroadcastReceiver mReceiver = new WiFiDirectBroadcastReceiver(p2p, channel, this);

        //IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        p2p.discoverPeers(channel, new ActionListener() {

            @Override
                    public void onSuccess() {
                        t1.setText("Success");
                    }
                    @Override
                    public void onFailure(int i) {
                        t1.setText("Failure! Reason: "+ String.valueOf(i));
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      /*  client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.hasan.project451104/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onResume(){
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       /* Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.hasan.project451104/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }
    public static class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
        public WiFiDirectBroadcastReceiver(){}//constructor for apk
        public ArrayList<String> olddeviceNames = new ArrayList<>();
        ArrayList<String> olddeviceAddresses = new ArrayList<>();
        ArrayList<Integer> FrequencyOfDetection = new ArrayList<>();
        ArrayList<Boolean> Disconnected = new ArrayList<>();
        ArrayList<Long> cumulativeTime = new ArrayList<>();
        ArrayList<Long> cumulativeTime1 = new ArrayList<>();
        //ArrayList<Date> latestDetection = new ArrayList<>();
        private static final String TAG = "PTP_Recv";
        private WifiP2pManager p2p;
        private WifiP2pManager.Channel channel;
        private Activity mainActivity;
        private ArrayList<WifiP2pDevice> PeerNames= new ArrayList<>();
        public WiFiDirectBroadcastReceiver(WifiP2pManager mp2p, WifiP2pManager.Channel mchannel, MainActivity mmainActivity) {
            this.p2p = mp2p;
            this.channel=mchannel;
            this.mainActivity=mmainActivity;
           /* try {
                this.wait(30000);
            } catch (Exception e) {
                Log.d("p2pcheck", e.getMessage());
            }*/
        }
        /*
         * (non-Javadoc)
         * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
         * android.content.Intent)
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                // Determine if Wifi P2P mode is enabled or not, alert
                // the Activity.
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    MainActivity.setIsWifiP2pEnabled(true);
                } else {
                    MainActivity.setIsWifiP2pEnabled(false);
                }
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

                p2p.requestPeers(channel, new WifiP2pManager.PeerListListener() {
                            @Override
                            public void onPeersAvailable(WifiP2pDeviceList peers) {
                                   if (peers != null) {
                                    PeerNames.clear();
                                    PeerNames.addAll(peers.getDeviceList());
                                    ArrayList<String> deviceNames = new ArrayList<>();
                                       ArrayList<String> deviceAddresses = new ArrayList<>();
                                       Date date = new Date();
                                                //deviceNames.clear();
                                    for (WifiP2pDevice device : PeerNames) {
                                            deviceNames.add(device.deviceName);
                                            deviceAddresses.add(device.deviceAddress);
                                            Log.d("p2pcheck", device.deviceAddress);
                                            Log.d("p2pcheck", device.deviceName);
                                            Log.d("p2pcheck", String.valueOf(date));
                                    /*    BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                                        backgroundWorker.execute("connect",mydevicename, mymacaddress,  device.deviceAddress,device.deviceName);
*/
                                    }
                                    Log.d("p2pcheck", "BEFORE FOR LOOP");
                                    Log.d("p2pcheck", String.valueOf(olddeviceNames.size()));
                                    Log.d("p2pcheck", String.valueOf(deviceNames.size()));
                                    for (int i=0; i<olddeviceNames.size();i++){
                                        Log.d("p2pcheck1", "OLD DEVICE MASTER: " + olddeviceNames.get(i));
                                        Log.d("p2pcheck", "I AM HERE");
                                        boolean stillhere = false;
                                        for (int j=0; j<deviceNames.size();j++){

                                            Log.d("p2pcheck1", "new device: " + deviceNames.get(j));
                                            if (olddeviceNames.get(i).equals(deviceNames.get(j))) {
                                                stillhere = true;
                                                latestDetection.set(i, date);
                                                Log.d("p2pcheck", olddeviceNames.get(i) + " is still here.");
                                                if (Disconnected.get(i)) {
                                                    FrequencyOfDetection.add(i, FrequencyOfDetection.get(i)+1);
                                                    StaticFrequency.add(i, StaticFrequency.get(i)+1);
                                                    Disconnected.set(i, false);
                                                    StaticDisconnected.set(i, "Connected");
                                                    cumulativeTime.set(i, date.getTime());
                                                    StaticSession.set(i, (long) 0);
                                                }

                                                Log.d("p2pcheck", "Freq of detection: " + FrequencyOfDetection.get(i));                                                //Log.d("p2pcheck", deviceNames.get(j) + " is still here.");
                                                //olddeviceNames.remove(i);
                                                 }

                                            }
                                        if (!stillhere && Disconnected.get(i).equals(false)){
                                            Log.d("p2pcheck", olddeviceNames.get(i) + " disconnected!");
                                            Disconnected.set(i, true);
                                            StaticDisconnected.set(i, "Disconnected!");
                                            StaticSession.set(i, date.getTime() - cumulativeTime.get(i));
                                            cumulativeTime.set(i, date.getTime() - cumulativeTime.get(i));
                                            cumulativeTime1.set(i, cumulativeTime1.get(i)+cumulativeTime.get(i));

                                            StaticCumulative.set(i, StaticCumulative.get(i)+ StaticSession.get(i));

                                            Log.d("p2pcheck", "Session duration of detection: " + String.valueOf(cumulativeTime.get(i)/1000) + " seconds");
                                            Log.d("p2pcheck", "Cumulative time of detection: " + cumulativeTime1.get(i)/1000 + " seconds");
                                            //Enter Disconnected Code
/*
                                            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                                            backgroundWorker.execute("disconnect",mydevicename, mymacaddress, olddeviceAddresses.get(i),olddeviceNames.get(i));
*/
                                        }
                                    }
                                    Log.d("p2pcheck", "I AM AT CLEAR");
                                    Log.d("p2pcheck", "olddeviceNames: " + String.valueOf(olddeviceNames.size()));
                                    Log.d("p2pcheck", "deviceNames: " + String.valueOf(deviceNames.size()));
                                    //olddeviceNames.clear();
                                    for(int i=0;i<deviceNames.size();i++) {
                                        Log.d("p2pcheck", "I am in the for loop");
                                        boolean duplicate=false;
                                        for(int j=0; j<olddeviceNames.size();j++) {
                                            if(olddeviceNames.get(j).equals(deviceNames.get(i))) {
                                            duplicate =true;

                                                //don't add because it will be duplicate
                                                //doing this to preserve the order. important for the sorting requirement
                                                //every time we add a new device, check it's in the olddeviceNames first
                                            }
                                             //if it's not, add it

                                            }
                                        if (!duplicate) {
                                            olddeviceNames.add(deviceNames.get(i));
                                            olddeviceAddresses.add(deviceAddresses.get(i));
                                            FrequencyOfDetection.add(1);
                                            StaticFrequency.add(1);
                                            Disconnected.add(false);
                                            cumulativeTime.add(date.getTime());
                                            cumulativeTime1.add(date.getTime()-date.getTime());

                                            trial.add(deviceNames.get(i));
                                            Covered.add(false);
                                            StaticAddresses.add(deviceAddresses.get(i));
                                            StaticDisconnected.add("Connected!");
                                            latestDetection.add(date);
                                            StaticSession.add((long) 0);
                                            StaticCumulative.add(date.getTime()-date.getTime());
                                        }

                                    }
                                    Log.d("p2pcheck", "I'm out of the for loop. olddeviceNames: " + olddeviceNames.size());

                                    //deviceNames.clear();
                                        //Log.d("p2p", device.primaryDeviceType);
                                } else {
                                    Toast.makeText(mainActivity, "EMPTY!", Toast.LENGTH_SHORT).show();
                                    Log.d("p2pcheck", "EMPTY!");
                                }
                            }
                        }
                );
                Log.d("p2pcheck", "there's been a change in peers list");


            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

                // Connection state changed!  We should probably do something about
                // that.

            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
               /* DeviceListFragment fragment = (DeviceListFragment) MainActivity.getFragmentManager()
                        .findFragmentById(R.id.frag_list);
                fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                        WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));*/

            }
        }

    }
}


