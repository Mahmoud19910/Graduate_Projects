package dev.mah.nassa.gradu_ptojects.Activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import dev.mah.nassa.gradu_ptojects.Adapters.DoctorsAdapter;
import dev.mah.nassa.gradu_ptojects.Adapters.MessageAdapter;
import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.DataBase.RealTime_DataBase;
import dev.mah.nassa.gradu_ptojects.DataBase.Storage_Firebase;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.Doctor;
import dev.mah.nassa.gradu_ptojects.Modles.MessagesModles;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityChatBinding;

public class Chat_Activity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private Doctor doctor;
    private MessageAdapter messageAdapter;
    private String senderRoom , reciverRoom;
    private DatabaseReference databaseReferenceSender , databaseReferenceReciver;
    private UsersViewModel usersViewModel;
    private String photo;
    private String name;
    int REQUEST_CODE_GALLERY = 2;
    private Uri uri;
    private MessagesModles messagesModles;
    private String resciverId;
    private String doctorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // فحص الاتصال بالانترنت
        boolean internet = SharedFunctions.checkInternetConnection(this);
        if(!internet){
            Toast.makeText(this, "للأسف أنت غير متصل بالانترنت !! ", Toast.LENGTH_LONG).show();
        }

        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        // انشاء غرفتين اتصال تحدد من المرسل ومن المرسل اليه
        senderRoom = loadUid()+doctor.getId();
        reciverRoom = doctor.getId()+loadUid();

        // التأكد من اتصال المستخدم
        if(doctor.isSesion()){
            binding.block.setImageDrawable(getDrawable(R.drawable.online));
            binding.onlinetv.setText("متصل");
        }else {
            binding.block.setImageDrawable(getDrawable(R.drawable.ofline));
            binding.onlinetv.setText("غير متصل");
        }



        binding.nameptv.setText(doctor.getName());

        // التأكد من أن الصورة ليس قيمة فارغة
        if(doctor.getImage() == null || doctor.getImage().isEmpty()){
            binding.profileImage.setImageDrawable(getDrawable(R.drawable.user));
        }else {
            Glide.with(Chat_Activity.this).load(doctor.getImage()).into(binding.profileImage);
        }




        //Adapter
        messageAdapter = new MessageAdapter(Chat_Activity.this , loadUid());
        binding.chatrecycle.setAdapter(messageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Chat_Activity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setReverseLayout(true); // Set reverse layout
        binding.chatrecycle.setLayoutManager(layoutManager);
        binding.chatrecycle.scrollToPosition(messageAdapter.getItemCount()-1);


        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("Chat Messages").child(senderRoom);
        databaseReferenceReciver = FirebaseDatabase.getInstance().getReference("Chat Messages").child(reciverRoom);


        // جلب الرسائل المرسلة و المستلمة
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clearAdapter();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    messagesModles = dataSnapshot.getValue(MessagesModles.class);
                    messageAdapter.addMessage(messagesModles);
                    binding.chatrecycle.scrollToPosition(0);

                    // جلب رقم التعريف الخاص بالشخص الذي استلم الرسالة لمعرفة أنه متصل أم غير متصل
                    if(doctor.getId().equals(messagesModles.getReciverId())){
                        saveReciverId(messagesModles.getReciverId());
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Chat_Activity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RealTime_DataBase.getAllDoctorsFromRealTime(this, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o ) {

                Doctor doctors = (Doctor) o;
                doctorId = doctors.getId();

//                // فحص أن المستخدم الذي في الدردرشة متصل أم لا
//                    if(doctors.getId().equals(loadReciverId())){
//                        if(doctors.isSesion()){
//                            binding.block.setImageDrawable(getDrawable(R.drawable.online));
//
//                        }else {
//                            binding.block.setImageDrawable(getDrawable(R.drawable.ofline));
//                        }
//                    }


            }
        });



        // ارسال الرسالة
        binding.sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText =  binding.messageEdit.getText().toString();
                binding.messageEdit.setText("");
                binding.chatrecycle.scrollToPosition(messageAdapter.getItemCount()-1);
                if(messageText.trim().length()>0){

                    //Null لايساوي  Url  فحص أن قيمة الصورة عنوان
                    if(uri == null){
                        sendMessaging(messageText , "");
                    }else {
                        uploadImage(uri, Chat_Activity.this ,  new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                sendMessaging(messageText , o.toString());
                                binding.messagePhoto.setVisibility(View.INVISIBLE); // بعد الارسال اخفاء الصورة

                            }
                        });
                    }

                }
            }
        });

        // Opem Gallery
        binding.attachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGalleryPermission();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && RESULT_OK == resultCode) {
             uri = data.getData();
            binding.messagePhoto.setImageURI(uri);
            binding.messagePhoto.setVisibility(View.VISIBLE);
        }

    }

    // جلب رقم المعرف للمستخدم
    private String loadUid(){
        SharedPreferences sharedPreferences = getSharedPreferences("saveUid" , Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid" , "");
    }

    // ارسال الرسالة و تخزينها في قاعدة البينات
    private void sendMessaging(String message , String messgePhotot){
        String messId = UUID.randomUUID().toString();

        // جلب البيانات من قاعدة البينات (الاسم و الصورة) لارسال الرسالة
        usersViewModel.getUsersByUid(loadUid()).observe(Chat_Activity.this, new Observer<UsersInfo>() {
            @Override
            public void onChanged(UsersInfo usersInfo) {

                name = usersInfo.getName();
                photo = usersInfo.getPhoto();

            }
        });

        String messageId = databaseReferenceSender.push().getKey();

        MessagesModles mesg = new MessagesModles(messId , message , messgePhotot , name , doctor.getId(), loadUid() , photo , SharedFunctions.getTimeAtTheMoment() , messageId );
        messageAdapter.addMessage(mesg);

        databaseReferenceSender
                .child(messageId)
                .setValue(mesg);

        databaseReferenceReciver
                .child(messageId)
                .setValue(mesg);
    }

    // Check Gallery Permission
    public void checkGalleryPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
        }

    }


    //و ارجاع رابط الصورة لارسالها Storage  رفع الصورة على
    public void uploadImage(Uri imageUri , Context context , OnSuccessListener listener) {
        if (imageUri != null) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            // Defining the child of storageReference
            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageReference = storage.getReference();
            StorageReference ref = storageReference.child("Chat Images/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image

            // Upload the image to Firebase Storage and add a success listener to get the download URL
            UploadTask uploadTask = ref.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                ref.getDownloadUrl().addOnSuccessListener(uri -> {

                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();

                    String   downloadUrl = uri.toString();
                    listener.onSuccess(downloadUrl);
                    uri=null;


                });
            }).addOnFailureListener(exception -> {
                // Handle any errors
                // ...
                // Error, Image not uploaded
                progressDialog.dismiss();
                Toast.makeText(context, "Failed " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    // Progress Listener for loading
                    // percentage on the dialog box

                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    public void saveReciverId(String reciveId){
     SharedPreferences sharedPreferences =    getSharedPreferences("reciveId" , Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("id" , reciveId);
    editor.apply();
    }

    // جلب رقم المعرف للمستخد
    private String loadReciverId() {
        SharedPreferences sharedPreferences = getSharedPreferences("reciveId", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }


}