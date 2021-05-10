package com.meme.p2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.meme.p2.Adapters.SearchAda;
import com.meme.p2.Database.Database;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class Med_search extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAda searchAda;
    List<String> suggestList = new ArrayList<>();
    LottieAnimationView lV;
    TextView searchtext;
    EditText searchView;
    GridView gridView;
    String [] labels = {"Location","Photo","Search"};
    String [] locations={"Al- ashrafiah /Taj ","Amman /Marka","Zarqa/ Al_Rusayfa","Amman/ Sahab","Zarqa / Qasabet Az_Zarqa","Al Balqa' / Ash_shuna al-Janubiyya","Al Balqa' / Qasabet Al_Balqa","Al Balqa' / Deir Allah"};
    String selectedLocation="";
    int [] images = {R.drawable.ic_location,R.drawable.ic_image,R.drawable.ic_ss};
    Dialog dialog,dialog1,dialog3;
    Animation topAnim,scaleUp,scaleDown;
    String recognizedText="";
    String searchText="";

    private static final int CAMERA_REQUEST_CODE=200;
    private static final int STORAGE_REQUEST_CODE=400;
    private static final int IMAGE_PICK_GALLERY_CODE=1000;
    private static final int IMAGE_PICK_CAMERA_CODE=1001;

    String cameraPermission[];
    String storagePermission[];
    Uri image_uri;

    TextView mResultET,alertText;
    ImageView mPreviewIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_med_search);

        searchtext=findViewById(R.id.searchtext);
        searchView=findViewById(R.id.searchView);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        lV=findViewById(R.id.animationsearch);
        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);
        lV.setAnimation(topAnim);
        searchtext.setAnimation(topAnim);
        dialog=new Dialog(Med_search.this);
        dialog1=new Dialog(Med_search.this);
        dialog3=new Dialog(Med_search.this);


        gridView=findViewById(R.id.imagesgrid);
        SearchAdapter adapter=new SearchAdapter(Med_search.this,labels,images);
        gridView.setAdapter(adapter);

        openPopUpAlert();
        lV.playAnimation();

        //////
        mResultET=findViewById(R.id.resultEt);
        mPreviewIv=findViewById(R.id.imageIv);
        alertText=findViewById(R.id.alertText);
        ///////

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Toast.makeText(Med_search.this, "Location spinner", Toast.LENGTH_SHORT).show();
                    openPopUpAlert2();
                }
                else if(position==1)
                {
                    Toast.makeText(Med_search.this, "Image", Toast.LENGTH_SHORT).show();
                    showImageImportDialog();
                }
                else{

                   // Toast.makeText(Med_search.this, "search", Toast.LENGTH_SHORT).show();
                   searchText = searchView.getText().toString();
                   if(recognizedText.equals("") && searchText.equals("")){
                       Toast.makeText(Med_search.this, "No I Can't", Toast.LENGTH_SHORT).show();
                       openPopUpAlert3();
                   }else{
                       if(searchText.equals("")){
                           getData(recognizedText);
                           Toast.makeText(Med_search.this, "Search Using Image", Toast.LENGTH_SHORT).show();
                       }else if(recognizedText.equals("")){
                           getData(searchText);
                           Toast.makeText(Med_search.this, "Search Using Text", Toast.LENGTH_SHORT).show();
                       }
                   }
                }
            }
        });

        //camera permission
        cameraPermission=new String []{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE  };
        //storage permission
        storagePermission=new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        

    }
    public void getData(String text){
        ParseQuery<ParseObject> getPharmacy=ParseQuery.getQuery("PH");
        getPharmacy.whereEqualTo("ph_address",selectedLocation);
        //get The MedicanName
        ParseQuery<ParseObject>getmed=ParseQuery.getQuery("Med");
        getmed.whereEqualTo("med_name",text);
        getPharmacy.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(object!=null){

                    String id=object.get("ph_id").toString();
                    Log.i("pop","ID :"+id);
                    getmed.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if(e==null){

                                String med_id=object.get("med_id").toString();
                                Log.i("pop","Med : "+med_id);
                                //get The Quantity
                                ParseQuery<ParseObject>getq=ParseQuery.getQuery("Quantity");
                                getq.whereEqualTo("ph_id",id);
                                getq.whereEqualTo("med_id",id);
                                getq.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        if(object!=null){
                                            String quant=object.get("quantity").toString();
                                            Log.i("pop","Quan : "+quant);
                                        }
                                    }
                                });
                            }else{
                                Log.i("pop",e.getMessage());
                            }
                        }
                    });
                }else{
                    Log.i("pop",e.getMessage());
                }
            }
        });

    }
    private void openPopUpAlert3()
        {
            dialog3.setContentView(R.layout.one_way);
            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //ImageView alert=dialog.findViewById(R.id.alert);
            LottieAnimationView alertanim=dialog3.findViewById(R.id.alertanim);
            TextView gotit=dialog3.findViewById(R.id.gotit);
            ImageView close=dialog3.findViewById(R.id.close);
            alertanim.playAnimation();
            gotit.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        gotit.startAnimation(scaleUp);
                    }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        gotit.startAnimation(scaleDown);
                    }

                    dialog3.dismiss();
                    lV.setAnimation(topAnim);
                    searchtext.setAnimation(topAnim);
                    return false;
                }

            });
       /* gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog3.dismiss();
                    lV.setAnimation(topAnim);
                    searchtext.setAnimation(topAnim);
                }
            });
            dialog3.show();
        }



    private void openPopUpAlert2() {
        dialog1.setContentView(R.layout.location);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LottieAnimationView locationanim=dialog1.findViewById(R.id.locationanim);
        TextView ok=dialog1.findViewById(R.id.ok);
        ImageView close=dialog1.findViewById(R.id.close1);
        Spinner location=dialog1.findViewById(R.id.location);

        ArrayAdapter<String> locationdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,locations);
        locationdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(locationdapter);
        locationanim.playAnimation();
        ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    ok.startAnimation(scaleUp);

                }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    ok.startAnimation(scaleDown);
                }
                selectedLocation=location.getSelectedItem().toString();
                Toast.makeText(Med_search.this, "You select "+selectedLocation, Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
                return false;
            }

        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                selectedLocation=location.getSelectedItem().toString();
                Toast.makeText(Med_search.this, "You select "+selectedLocation, Toast.LENGTH_SHORT).show();

            }
        });

        dialog1.show();

    }


    private void showImageImportDialog() {
        //items to display in dialog
        String [] items={"Camera","Gallery"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(Med_search.this);
        //set title
        dialog.setTitle("Select Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0)
                {
                    //camera option clicked
                    if(!checkCameraPermission())
                    {
                        //camera permission not allowed , request it
                        requestCameraPermission();
                    }else
                    {
                        //permission allowed , take picture
                        pickCamera();
                    }
                }

                if(which==1)
                {
                    //gallery option clicked

                    if(!checkStoragePermission())
                    {
                        //camera permission not allowed , request it
                        requestStoragePermission();
                    }
                    else
                    {
                        //permission allowed , take picture
                        pickGallery();
                    }
                }
            }
        });

        dialog.create().show();//show dialog
    }

    private boolean checkCameraPermission() {
        //check camera permission and return result
        //inorder to get high quality image , we have to save image into external storage first
        //before inserting to image view that's why storage permission will also be required
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result &&result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result= ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result ;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }

    private void pickCamera() {
        //intent to take image from camera,it will also be save to storage to get high quality image
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");//title of the picture
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image to text");//description
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private void pickGallery() {

        // intent to pick image from gallery
        Intent intent=new Intent(Intent.ACTION_PICK);

        //set intent type to image

        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void openPopUpAlert() {
        dialog.setContentView(R.layout.search_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //ImageView alert=dialog.findViewById(R.id.alert);
        LottieAnimationView alertanim=dialog.findViewById(R.id.alertanim);
        TextView gotit=dialog.findViewById(R.id.gotit);
        ImageView close=dialog.findViewById(R.id.close);
        alertanim.playAnimation();
        gotit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    gotit.startAnimation(scaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    gotit.startAnimation(scaleDown);
                }

                dialog.dismiss();
                lV.setAnimation(topAnim);
                searchtext.setAnimation(topAnim);
                return false;
            }

        });
       /* gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                lV.setAnimation(topAnim);
                searchtext.setAnimation(topAnim);
            }
        });
        dialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case CAMERA_REQUEST_CODE:

                if(grantResults.length >0)
                {
                    boolean cameraAccepted =grantResults[0]== PackageManager.PERMISSION_GRANTED;

                    boolean writeStorageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted)
                    {
                        pickCamera();
                    }
                    else
                    {
                        Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case STORAGE_REQUEST_CODE:
                if(grantResults.length >0)
                {
                    boolean writeStorageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(writeStorageAccepted)
                    {
                        pickGallery();
                    }
                    else
                    {
                        Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //handle image result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get image from camera

        if(resultCode==RESULT_OK)
        {
            if(requestCode==IMAGE_PICK_GALLERY_CODE)
            {
                //get image from gallery now crop it
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON)//Enable image guidelines
                        .start(this);
            }
            if(requestCode==IMAGE_PICK_CAMERA_CODE)
            {
                //get image from camera now crop it

                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON)//Enable image guidelines
                        .start(this);
            }
        }

//get cropped image

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode==RESULT_OK)
            {
                Uri resultUri= result.getUri();//get Image Uri

                //set Image to Image View
               mPreviewIv.setImageURI(resultUri);

                //get drawable bitmap
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();
                TextRecognizer recognizer=new TextRecognizer.Builder(getApplicationContext()).build();
                if(!recognizer.isOperational())
                {
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Frame frame= new Frame.Builder().setBitmap(bitmap).build();

                    SparseArray<TextBlock> items=recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //get text from sb until there is no text

                    for(int i=0 ; i<items.size() ; i++)
                    {
                        TextBlock myItem=items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }

                    //set text to edit text
                    recognizedText=sb.toString().trim();
                    alertText.setVisibility(View.VISIBLE);
                    mResultET.setVisibility(View.VISIBLE);
                    mResultET.setText(recognizedText);
                }
            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                //if there is any error show it
                Exception error=result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}