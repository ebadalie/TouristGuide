package com.example.faizan.touristguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebad Ali on 1/25/2015.
 */
public class GridContainer extends FragmentActivity implements View.OnClickListener{
    File sdImageMainDirectory;
    ImageButton cameraButton;
    String type="";
    int currViewFlipperIndex;

    Context context;
    public static long curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");
        currViewFlipperIndex = getIntent().getIntExtra("index",0);
        setContentView(R.layout.grid_layout);

        context = this;
        GridView gridView = (GridView)findViewById(R.id.gridview);
        cameraButton = (ImageButton)findViewById(R.id.feedbackpic);
        final MyAdapter adp = new MyAdapter(this);
        gridView.setAdapter(adp);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curr = adp.getItemId(position);
                Intent intent = new Intent(context,SingleItem.class);
                startActivity(intent);
            }
        });
        cameraButton.setOnClickListener(this);

    }

    protected void startCameraActivity() {

        File root = new File(Environment
                .getExternalStorageDirectory()
                + File.separator + "myDir" + File.separator);
        root.mkdirs();
        sdImageMainDirectory = new File(root, "myPicName");

        Uri outputFileUri = Uri.fromFile(sdImageMainDirectory);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 0:
                finish();
                break;

            case -1:

                try {
                    StoreImage(this, Uri.parse(data.toURI()),
                            sdImageMainDirectory);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();


        }

    }

    public static void StoreImage(Context mContext, Uri imageLoc, File imageDir) {
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageLoc);
            FileOutputStream out = new FileOutputStream(imageDir);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            bm.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        startCameraActivity();
    }


    private class MyAdapter extends BaseAdapter
    {
        public List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);
            int [][] arry = CustomeResource.GetCRArray(type);

            for(int i =  0 ; i < arry.length ; i++)
            {
                items.add(new Item("Image "+i, arry[currViewFlipperIndex][i]));
            }
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View v = view;
            ImageView picture;
            TextView name;

            if(v == null)
            {
                v = inflater.inflate(R.layout.grid_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);
            name = (TextView)v.getTag(R.id.text);

            Item item = (Item)getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);
            curr = item.drawableId;

            return v;
        }

        class Item
        {
            public final String name;
            public final int drawableId;

            Item(String name, int drawableId)
            {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }


}
