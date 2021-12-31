package com.example.myclg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myclg.Models.profileinfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    ImageView calview;
    EditText dob,name,email,number,empid;
    String Sname,Snumber,Semail,Sempid,Sdes,Sdpt,Sdob;
    public static final int GalleryPick = 1;
    ProgressDialog loadingbar;
    CircleImageView uploadimg;
    private Uri ImageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String employeeid;
    Spinner spin,spin2;
    String Semployeeid,Spasswword;

    String[] designation = { "Professor","Assoc.Professor","Asst.Professor","Lecturer"};
    String[] dept = { "CSE","CIVIL","MECHANICAL","EEE","ECE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        calview= findViewById(R.id.calenderview1);
        loadingbar=new ProgressDialog(this);
        dob=findViewById(R.id.dob);
        employeeid = getIntent().getStringExtra("name");
        spin = (Spinner) findViewById(R.id.Dept);
        spin2 =(Spinner)findViewById(R.id.Designation);
        name =findViewById(R.id.user_name);
        email=findViewById(R.id.user_email);
        number=findViewById(R.id.user_phone_number);
        empid=findViewById(R.id.empid);
        Semployeeid=getIntent().getStringExtra("empid");
        Spasswword=getIntent().getStringExtra("password");
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("CSE").child("Employees");
        empid.setText(Semployeeid);
        Button submit =findViewById(R.id.btn_submit_user_infor);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
        uploadimg=(CircleImageView)findViewById(R.id.profile_image);
        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        spin2.setOnItemSelectedListener(this);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dept);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        ArrayAdapter ab = new ArrayAdapter(this,android.R.layout.simple_spinner_item,designation);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(ab);

        calview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();
            }
        });

    }
    private void ValidateProductData()
    {
        Sname=name.getText().toString();
        Snumber=number.getText().toString();
        Semail=email.getText().toString();
        Sempid=empid.getText().toString();
        Sdes=designation.toString();
        Sdpt=dept.toString();

        if (ImageUri==null)
        {
            Toast.makeText(this, "Product Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sname))
        {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Snumber)){
            Toast.makeText(this, "Number is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sempid))
        {
            Toast.makeText(this, "Employee id is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Semail))
        {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sdob))
        {
            Toast.makeText(this, "Date of Birth is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sdes))
        {
            Toast.makeText(this, "Designation is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sdpt))
        {
            Toast.makeText(this, "Department is required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingbar.setMessage("Please Wait");
        loadingbar.setTitle("Adding New Product");
        loadingbar.setCancelable(false);
        loadingbar.show();
        UploadImage();


    }

    public void UploadImage() {

        if (ImageUri != null) {

            loadingbar.setTitle("Image is Uploading...");
            loadingbar.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(ImageUri));
            storageReference2.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempName = name.getText().toString().trim();
                            String TempDesignation = spin2.getSelectedItem().toString().trim();
                            String Tempemail = email.getText().toString().trim();
                            String Tempempid =empid.getText().toString().trim();
                            String Tempnumber=number.getText().toString().trim();
                            String Tempdept=spin.getSelectedItem().toString().trim();
                            String Tempdob=Sdob.trim();
                            loadingbar.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    profileinfo userProfileInfo = new profileinfo(TempName,Tempnumber,Tempemail,Tempempid,TempDesignation,Tempdept,Tempdob, url);
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(Tempempid).setValue(userProfileInfo);

                                    Intent intent=new Intent(NewActivity.this,LoginActivity.class);
                                    intent.putExtra("empid",Semployeeid);
                                    intent.putExtra("password",Spasswword);
                                    startActivity(intent);
                                }
                            });


//                            @SuppressWarnings("VisibleForTests")
//
//                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName,TempImageDescription,TempImagePrice, taskSnapshot.getUploadSessionUri().toString());
//                            String ImageUploadId = databaseReference.push().getKey();
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(NewActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            uploadimg.setImageURI(ImageUri);
        }
    }





    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog =new DatePickerDialog(this,this, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String[] monthName={"January","February","March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        String Smonth= monthName[month];
        String date = dayOfMonth+"/"+Smonth+"/"+year;
        dob.setText(date);
        Sdob= dayOfMonth+""+Smonth+""+year;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void OpenGallery()
    {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }
}