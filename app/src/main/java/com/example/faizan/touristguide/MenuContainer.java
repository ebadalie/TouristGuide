package com.example.faizan.touristguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.support.v7.widget.CardView;

import com.example.faizan.touristguide.controller.Controller;
import com.example.faizan.touristguide.controller.JsonParse;
import com.example.faizan.touristguide.model.TouristVenue;

import org.apache.http.NameValuePair;

import org.json.JSONObject;

/**
 * Created by Faizan on 1/22/2015.
 */
public class MenuContainer extends FragmentActivity {

    ViewFlipper mViewFlipper;
    List<MenuCard> listOfCards;
    RadioGroup buttonRadGroup;
    int index = 0;
    CustomAnimation customAnimation;


    Context cntx;
    String type;
    CustomeView myCustomeView;
    MyLocationListener locationListener ;

    Button next ,prev;
    int currIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tutorial);
        next  = (Button) (findViewById(R.id.navbar)).findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(v);
            }
        });

        prev  = (Button) (findViewById(R.id.navbar)).findViewById(R.id.skip);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous(v);
            }
        });


        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        type = getIntent().getStringExtra("type");
        ((TextView)findViewById(R.id.panelbar).findViewById(R.id.pnltxt)).setText(type);
        listOfCards = GetCards();
        currIndex = GetCurrentVenue();

        locationListener = new MyLocationListener(this);
        cntx = this;
        customAnimation = new CustomAnimation();
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewSwitcher);
        buttonRadGroup = (RadioGroup) ((this.findViewById(R.id.navbar)).findViewById(R.id.hour_radio_group) );

        for(MenuCard currCard  : listOfCards)
        {
            mViewFlipper.addView(CreateACard(currCard));
        }
        myCustomeView = new CustomeView(getLayoutInflater().inflate(R.layout.search_dialogue,null));
    }

    private int GetCurrentVenue() {

        currIndex = 0 ;
        if((SpinFrag.queryinDomain == null)
                || (SpinFrag.queryinDomain[0] == null || SpinFrag.queryinDomain[0].isEmpty()) ) {

            return currIndex;

        }else{
            for(MenuCard currCard  : listOfCards)
            {
                if(currCard.name.toLowerCase().contains(SpinFrag.queryinDomain[0].toLowerCase()))
                {
                    // Found current venue
                    break;
                }
                currIndex++;
            }
        }
        return currIndex;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewFlipper.setDisplayedChild(currIndex);
    }


    public void radio_click(View view)
    {

    }
    public void next(View view)
    {
        if(index < mViewFlipper.getChildCount()) {
            mViewFlipper.setInAnimation(customAnimation.inFromRightAnimation());
            mViewFlipper.setOutAnimation(customAnimation.outToLeftAnimation());

            mViewFlipper.showNext();
            index++;

            ToggleRadioButton();
        }
    }

    public void Locate(View view)
    {
        if(isConnected()) {

            String currentLocation = getLocation();

            String locc = "http://maps.google.com/maps?saddr="+currentLocation+"&daddr="
            +listOfCards.get(mViewFlipper.getDisplayedChild()).reaLocation;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(locc));

            try {
                this.startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(this, "Install Android Map App First", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Network Connectivity Failed", Toast.LENGTH_LONG).show();
        }

    }

    public String getLocation() {


        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10 * 1000, (float) 10.0, locationListener);
        return locationListener.currRealLoca;

    }

    public void Gallary(View view)
    {
        Intent intxt = new Intent(cntx,GridContainer.class);
        intxt.putExtra("type",type);
        intxt.putExtra("index",mViewFlipper.getDisplayedChild());
        startActivity(intxt);
    }

    public void Call(View view)
    {

    }
    public void Reserve(View view)
    {
        Intent intn = new Intent(this,ReservationActivity.class);
        String url = GetReservationUrl();
        //if(url != null) {
            intn.putExtra("reservation_url",url );
            startActivity(intn);
        //}
//        else
//        {
//            // Manual Reservation
//            final AlertDialog d = new AlertDialog.Builder(this)
//                    .setView(this.myCustomeView.MaininputView)
//                    .setTitle("Reservation")
//                    .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
//                    .setNegativeButton(android.R.string.cancel, null)
//                    .create();
//
//            this.myCustomeView.setInputView((LinearLayout)this.myCustomeView.MaininputView.findViewById(R.id.inputpnel));
//            this.myCustomeView.setLoadingView((LinearLayout)this.myCustomeView.MaininputView.findViewById(R.id.inputloadingPanel));
//            this.myCustomeView.setpBar((ProgressBar) this.myCustomeView.getLoadingView().findViewById(R.id.inputloadingPanel).findViewById(R.id.loadingprogress));
//
//            final EditText iUserName = (EditText) this.myCustomeView.getInputView().findViewById(R.id.editusername);
//            final EditText iPass = (EditText) this.myCustomeView.getInputView().findViewById(R.id.editpassword);
//            final EditText iPhone = (EditText) this.myCustomeView.getInputView().findViewById(R.id.editphone);
//            // create an alert dialog
//            d.setOnShowListener(new DialogInterface.OnShowListener() {
//                @Override
//                public void onShow(DialogInterface dialog) {
//                    Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
//                    b.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // TODO Do something
//                            Log.i("------", "Clicked Positive ");
//                            String uName = iUserName.getText().toString();
//                            String pass = iPass.getText().toString();
//                            String phone = iPhone.getText().toString();
//
//                            if(uName == "" || pass == "")
//                            {
//                                Toast.makeText(cntx,"Invalid UserName or Password",Toast.LENGTH_LONG).show();
//                            }
//                            else if(isConnected())
//                                MakeAReservation(uName, pass, phone,myCustomeView);
//                            else
//                                Toast.makeText(cntx,"Network Not avilable",Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    Button b2 = d.getButton(AlertDialog.BUTTON_NEGATIVE);
//                    b2.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View view) {
//                            // TODO Do something
//                            Log.i("------", "Clicked Positive ");
//                            d.dismiss();
//
//                        }
//                    });
//                }
//            });
//
//            myCustomeView.setD(d);
//
//            d.show();
//
//            }

    }

    private String GetReservationUrl() {

        // identify type using Type var
        type = type.toLowerCase();
        String url =  null;
        int tempIndex = this.mViewFlipper.getDisplayedChild();
        if(type.contains("rest"))
        {
            url =  CustomeResource.rest_url_arry[tempIndex];
        }
        else if(type.contains("att"))
        {
            url =  CustomeResource.att_url_arry[tempIndex];
        }
        else if(type.contains("hot"))
        {
            url =  CustomeResource.hotel_url_arry[tempIndex];
        }
        else
            url =  CustomeResource.rent_url_arry[tempIndex];

        // Get current Element using currIndex var

        return url;
    }


    private void MakeAReservation(String uName, String pass, String phone, CustomeView d) {
        Log.i("------", "MakeAReservation ");
        new ReservationService(d).execute(new String []{uName,pass,phone});
    }

    void ToggleRadioButton()
    {
       // ( (RadioButton)(buttonRadGroup.getChildAt(index)) ).setChecked(true);
    }

    public void referesh_click(View view)
    {}

    public void previous(View view)
    {
        if (index >= 0) {
            mViewFlipper.setInAnimation(customAnimation.inFromLeftAnimation());
            mViewFlipper.setOutAnimation(customAnimation.outToRightAnimation());

            mViewFlipper.showPrevious();
            index--;
            ToggleRadioButton();
        }
    }

    private View CreateACard(MenuCard currCard) {

        CardView card = new CardView(this);
         View child =  this.getLayoutInflater().inflate(R.layout.custom_card, null);
        ( (ImageView )child.findViewById(R.id.crd_imag)).setBackgroundResource(currCard.imgRes);

        ( (TextView)(child.findViewById(R.id.crd_txt))).setText(currCard.name +Character.LINE_SEPARATOR+currCard.type
                +Character.LINE_SEPARATOR+""+Character.LINE_SEPARATOR+currCard.desc);
        card.addView(child);

        return card;
    }

    private List<MenuCard> GetCards() {

        List<TouristVenue> listOfVenues = Controller.GetObject(this).GetVenues(type);

            List<MenuCard> list = new ArrayList<MenuCard>();
            int []arr  = CustomeResource.GetCR(type);
            for (int i = 0 ; i  < listOfVenues.size() ; i++)
            {
                TouristVenue tV = listOfVenues.get(i);
                list.add(new MenuCard(tV.getName(),tV.getLocation(),tV.getDescription(), tV.getType(),arr[i]) );
            }
            return list;
    }

    private void UpdateDB() {

    }

    class CustomAnimation
    {
        public Animation inFromRightAnimation() {

            Animation inFromRight = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                    Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
            );
            inFromRight.setDuration(500);
            inFromRight.setInterpolator(new AccelerateInterpolator());
            return inFromRight;
        }
        public Animation outToLeftAnimation() {
            Animation outtoLeft = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
                    Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
            );
            outtoLeft.setDuration(500);
            outtoLeft.setInterpolator(new AccelerateInterpolator());
            return outtoLeft;
        }

        public Animation outToRightAnimation() {
            Animation outtoRight = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
                    Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
            );
            outtoRight.setDuration(500);
            outtoRight.setInterpolator(new AccelerateInterpolator());
            return outtoRight;
        }
        public Animation inFromLeftAnimation() {
            Animation inFromLeft = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
                    Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
            );
            inFromLeft.setDuration(500);
            inFromLeft.setInterpolator(new AccelerateInterpolator());
            return inFromLeft;
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class ReservationService extends AsyncTask<String [], Integer, String> {
        CustomeView d;
        public ReservationService(CustomeView d) {
            this.d = d;
        }

        protected String doInBackground(String[]... attb) {
            String responce = "";

            try {
            JsonParse Obg = new JsonParse();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String [] data = attb[0];
            String url = "http://restaurants.base.pk/";//{%22reservation%22:1,%22refresh%22:0,%22username%22:%22"+data[0]+"%22,%22password%22:%22"+data[1]+"%22}";
            JSONObject obg = new JSONObject();
                obg.accumulate("reservation", "1");
                obg.accumulate("refresh", "0");
                obg.accumulate("password", data[0]);
                obg.accumulate("username", data[1]);
                String jsonObjectInString =obg.toString();

                url = Uri.parse(url)
                        .buildUpon()
                        .appendQueryParameter("jason",  jsonObjectInString)
                        .build().toString();


                responce = Obg.makeHttpRequest(url,"POST", params);
                Log.i("------ &*****", "ReservationService Do in bg = " + responce);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return responce;
        }


        @Override
        protected void onPreExecute() {

            d.getInputView().setVisibility(View.GONE);
            d.getLoadingView().setVisibility(View.VISIBLE);
            d.getpBar().setProgress(1);

            Log.i("------", "ReservationService OnPreExe ");

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            d.getpBar().setProgress(values[0]);

        }

        protected void onPostExecute(String result) {


            d.getInputView().setVisibility(View.VISIBLE);
            d.getLoadingView().setVisibility(View.GONE);

            if(result.contains("1")) {
                Toast.makeText(cntx, "Reservation Succes", Toast.LENGTH_LONG).show();
                UpdateDB();
            }else
                Toast.makeText(cntx, "Reservation failed", Toast.LENGTH_LONG).show();

            //Dismiss the dialogue once everything is OK.
            this.d.getD().dismiss();
            Log.i("------", "ReservationService OnPostExe ");
        }
    }

    private class CustomeView
    {
        View MaininputView;
        LinearLayout inputView,loadingView;
        ProgressBar pBar ;
        AlertDialog d;

        public AlertDialog getD() {
            return d;
        }

        public void setD(AlertDialog d) {
            this.d = d;
        }

        private CustomeView(View maininputView, LinearLayout inputView, LinearLayout loadingView, ProgressBar pBar) {
            MaininputView = maininputView;
            this.inputView = inputView;
            this.loadingView = loadingView;
            this.pBar = pBar;
        }

        public CustomeView(View maininputView) {
            MaininputView = maininputView;
        }

        public LinearLayout getInputView() {
            return inputView;
        }

        public void setInputView(LinearLayout inputView) {
            this.inputView = inputView;
        }

        public LinearLayout getLoadingView() {
            return loadingView;
        }

        public void setLoadingView(LinearLayout loadingView) {
            this.loadingView = loadingView;
        }

        public ProgressBar getpBar() {
            return pBar;
        }

        public void setpBar(ProgressBar pBar) {
            this.pBar = pBar;
        }
    }
}
/*----------Listener class to get coordinates ------------- */
class MyLocationListener implements LocationListener {
    Context contxt;
    MyLocationListener(Context contxt) {
        this.contxt = contxt;
    }

    String currRealLoca = "";
    @Override
    public void onLocationChanged(Location loc) {
        Toast.makeText(
                contxt,
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        String latitude = "Latitude: " + loc.getLatitude();
        /*-------to get City-Name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(contxt, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName = addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currRealLoca = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;

    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}

 class CustomeResource
{
    static public int [] GetCR(String type)
    {
        type = type.toLowerCase();
        if(type.contains("rest"))
        {
            return CustomeResource.rest;
        }
        else if(type.contains("att"))
        {
            return CustomeResource.att;
        }
        else if(type.contains("hot"))
        {
            return CustomeResource.hotel;
        }
        return CustomeResource.rent;
    }

    static public int [][] GetCRArray(String type)
    {
        type = type.toLowerCase();
        if(type.contains("rest"))
        {
            return CustomeResource.rest_Arry;
        }
        else if(type.contains("att"))
        {
            return CustomeResource.att_Arry;
        }else if(type.contains("hot"))
        {
            return CustomeResource.hotel_Arry;
        }
        return CustomeResource.rent_Arry;
    }



    static int [] rest = {R.drawable.rest_lamosh,R.drawable.rest_lal_qila,R.drawable.rest_royal_taj,R.drawable.rest_china_city};
    static int [] rest_ll = { R.drawable.rs_ll_one,R.drawable.rs_ll_two,R.drawable.rs_ll_three,R.drawable.rs_ll_four,R.drawable.rs_ll_five,R.drawable.rs_ll_six};
    static int [] rest_lm = {R.drawable.rs_lo_one,R.drawable.rs_lo_two,R.drawable.rs_lo_three,R.drawable.rs_lo_four,R.drawable.rs_lo_five,R.drawable.rs_lo_six,
            R.drawable.rs_lo_seven,R.drawable.rs_lo_eight};
    static int [] rest_rt = {R.drawable.rs_rt_one,R.drawable.rs_rt_two,R.drawable.rs_rt_three,R.drawable.rs_rt_four,R.drawable.rs_rt_five,
            R.drawable.rs_rt_six,R.drawable.rs_rt_seven,R.drawable.rs_rt_eight};
    static int [] rest_cc = {R.drawable.rs_cc_one,R.drawable.rs_cc_two,R.drawable.rs_cc_three,R.drawable.rs_cc_four,R.drawable.rs_cc_five};
    static int [][] rest_Arry  = {rest_lm,rest_ll,rest_rt,rest_cc};

    static int [] hotel = {R.drawable.hotel__faran_img1,R.drawable.hotel_city_gate_img1,R.drawable.hotel_indus_img1};
    static int [] hotel_c = {R.drawable.hotel_city_gate_img1,R.drawable.hotel_city_gate_img2,R.drawable.hotel_city_gate_img3,R.drawable.hotel_city_gate_img4,
            R.drawable.hotel_city_gate_img6,R.drawable.hotel_city_gate_img7};
    static int [] hotel_i = {R.drawable.hotel_indus_img1,R.drawable.hotel_indus_img2,R.drawable.hotel_indus_img3,
            R.drawable.hotel_indus_img4,R.drawable.hotel_indus_img5,R.drawable.hotel_indus_img6,
            R.drawable.hotel_indus_img7,R.drawable.hotel_indus_img8,R.drawable.hotel_indus_img9, R.drawable.hotel_indus_logo};

    static int [] hotel_f = {R.drawable.hotel__faran_img1,R.drawable.hotel_faran_img2,R.drawable.hotel_faran_img3,
            R.drawable.hotel_faran_img4,R.drawable.hotel_faran_img5};
    static int [][] hotel_Arry  = {hotel_f,hotel_c,hotel_i};

    static int [] rent = {R.drawable.r_c_one,R.drawable.r_d_one};
    static int [] rent_c = {R.drawable.r_c_one,R.drawable.r_c_two,R.drawable.r_c_three,R.drawable.r_c_four};
    static int [] rent_d = {R.drawable.r_d_one,R.drawable.r_d_two,R.drawable.r_d_three};
    static int [][] rent_Arry  = {rent_c,rent_d};

    static int [] att = {R.drawable.at_r_one,R.drawable.at_c_one , R.drawable.at_sm_one};
    static int [] att_r = {R.drawable.at_r_one,R.drawable.at_r_two,R.drawable.at_r_three,R.drawable.at_r_four,R.drawable.at_r_five,R.drawable.at_r_six,
            R.drawable.at_r_seven,R.drawable.at_r_eight,R.drawable.at_r_nine};
    static int [] att_c = {R.drawable.at_c_one,R.drawable.at_c_two,R.drawable.at_c_three,R.drawable.at_c_four,R.drawable.at_c_five};
    static int [] att_sm = {R.drawable.at_sm_one,R.drawable.at_sm_two,R.drawable.at_sm_three,R.drawable.at_sm_four,R.drawable.at_sm_five,R.drawable.at_sm_six,
            R.drawable.at_sm_seven,R.drawable.at_sm_eight};

    static int [][] att_Arry  = {att_r,att_c,att_sm};


    static String [] rest_url_arry = new String[]{"http://www.la-moosh.com/#!/pageMain",
            "http://www.lalqila.com/hyderabad.php",
            "http://eatoye.pk/hyderabad/royal-taj-autobhan-road",
            "null"};

    static String [] rent_url_arry = new String[]{"http://www.daewoo.com.pk/reservation01.asp?menu_set=1","http://www.rentacarhyderabad.com/onlinebooking.htm"};

    static String [] hotel_url_arry = new String[]{"http://www.jovago.com/en-gb/pakistan/hyderabad/hotel/o8006/hotel-faran?gclid=CJLKiLr3zMMCFebItAodpi4ALw",
            "http://www.tripadvisor.com/Hotel_Review-g670334-d6106473-Reviews-Hotel_City_Gate-Hyderabad_Sindh_Province.html",
            "http://www.indushotel.com/main.html "};

    static String [] att_url_arry = new String[]{null,"http://www.facebook.com/l.php?u=http%3A%2F%2Fcine-moosh.com%2F&h=oAQHLe-2U",null};

}


