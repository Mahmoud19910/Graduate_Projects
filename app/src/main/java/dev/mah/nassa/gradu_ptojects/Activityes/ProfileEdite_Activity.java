package dev.mah.nassa.gradu_ptojects.Activityes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import dev.mah.nassa.gradu_ptojects.Constants.PersonActivityArray;
import dev.mah.nassa.gradu_ptojects.Constants.Vital_Equations;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersHealthInfoViewModel;
import dev.mah.nassa.gradu_ptojects.MVVM.UsersViewModel;
import dev.mah.nassa.gradu_ptojects.Modles.UsersInfo;
import dev.mah.nassa.gradu_ptojects.Modles.Users_Health_Info;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.databinding.ActivityProfileEditeBinding;

public class ProfileEdite_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private UsersViewModel usersViewModel;
    private UsersHealthInfoViewModel usersHealthInfoViewModel;
    private ActivityProfileEditeBinding binding;
    private UsersInfo usersInfo, usersInfoUpdate;

    FirebaseFirestore db;
    // Uri indicates, where the image will be picked from
    String downloadUrl;
    // Uri indicates, where the image will be picked from
    Uri imageUri;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    // instance for firebase storage and StorageReference
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String activityLeveSpin, gender;
    private boolean checkGender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usersViewModel = ViewModelProviders.of(ProfileEdite_Activity.this).get(UsersViewModel.class);
        usersHealthInfoViewModel = ViewModelProviders.of(ProfileEdite_Activity.this).get(UsersHealthInfoViewModel.class);
        binding = ActivityProfileEditeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.profileEditSpn.setOnItemSelectedListener(ProfileEdite_Activity.this);
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //الحصول على بيانات المستخدم
        Intent intent = getIntent();
        usersInfo = (UsersInfo) intent.getSerializableExtra("profile");

        // Uncheck or reset the radio buttons initially
        binding.groupradio.clearCheck();
        binding.groupradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // The flow will come here when
                // any of the radio buttons in the radioGroup
                // has been clicked

                // Check which radio button has been clicked

            }
        });


        //Spinner Ad
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                PersonActivityArray.getPersonActivityList());
        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        binding.profileEditSpn.setAdapter(ad);

        //show data
        gitIntentShowActivity();


        //Save object
        binding.profileEditIvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(binding.profileEditTvName.getText())) {
                    binding.profileEditTvName.setError("ارجاء ادخال الأسم");
                } else if (TextUtils.isEmpty(binding.profileEditTvPhone.getText())) {
                    binding.profileEditTvPhone.setError("ارجاء ادخال رقم الهاتف");
                } else if (TextUtils.isEmpty(binding.profileEditTvEmail.getText())) {
                    binding.profileEditTvEmail.setError("ارجاء ادخال حسابك جيميل");
                } else if (TextUtils.isEmpty(binding.profileEditTvWeight.getText())) {
                    binding.profileEditTvWeight.setError("ارجاء ادخال وزنك");
                } else if (TextUtils.isEmpty(binding.profileEditTvHeight.getText())) {
                    binding.profileEditTvHeight.setError("ارجاء ادخال طولك");
                } else if (TextUtils.isEmpty(binding.profileEditTvAge.getText())) {
                    binding.profileEditTvAge.setError("ارجاء ادخال عمرك");
                } else {
                    // calling method to add data to Firebase Firestore.
                    //ارسال الأوبجكت بعد تعديل عليه الى الفاير ستور
                    usersInfoUpdate = new UsersInfo(usersInfo.getUid(), binding.profileEditTvName.getText().toString(), binding.profileEditTvPhone.getText().toString()
                            , binding.profileEditTvAge.getText().toString(), binding.profileEditTvHeight.getText().toString(),
                            binding.profileEditTvWeight.getText().toString(), binding.profileEditTvEmail.getText().toString());

                    usersInfoUpdate.setPhoto(usersInfo.getPhoto());
                    usersInfoUpdate.setActivityLevel(activityLeveSpin);
                    usersInfoUpdate.setGender(usersInfo.getGender());
                    usersInfoUpdate.setPass(usersInfo.getPass());
                    if (imageUri == null) {
                        // تعديل بيانات المستخدم في قاعدة البيانات المحلية
                        usersViewModel.updateUsers(usersInfoUpdate);
                        getUsersHealthUpdate();
                        Intent intent1 = new Intent();
                        intent1.putExtra("profile", usersInfoUpdate);
                        setResult(RESULT_OK, intent1);
                        finish();
                    } else uploadImage();
                }

            }
        });

        //back
        binding.profileEditIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                binding.profileEditSpn.setSelection(usersInfo.getItemSpn());
                Toast.makeText(getBaseContext(), "item" + usersInfo.getItemSpn(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.profileEditIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }

    //عرض البيانات من intent
    private void gitIntentShowActivity() {

        if(usersInfo.getPhoto()==null || usersInfo.getPhoto().isEmpty()){
            binding.profileEditIvImage.setImageDrawable(getDrawable(R.drawable.user));

        }else {
            Glide.with(getBaseContext())
                    .load(usersInfo.getPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.profileEditIvImage);
        }

        binding.profileEditSpn.setSelection(usersInfo.getItemSpn());
        binding.profileEditTvName.setText(usersInfo.getName());
        binding.profileEditTvAge.setText(usersInfo.getEage());
        binding.profileEditTvHeight.setText(usersInfo.getLength());
        binding.profileEditTvWeight.setText(usersInfo.getWeight());
        binding.profileEditTvEmail.setText(usersInfo.getEmail());
        binding.profileEditTvPhone.setText(usersInfo.getPhone());
    }

    // Select Image method
    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        imageUri = data.getData();
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the URI of the selected image from the intent data
            imageUri = data.getData();
            try {

                // Setting image on image view using Bitmap

                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                imageUri);
                binding.profileEditIvImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (imageUri != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "UserInfo/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image

            // Upload the image to Firebase Storage and add a success listener to get the download URL
            UploadTask uploadTask = ref.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                ref.getDownloadUrl().addOnSuccessListener(uri -> {

                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast
                            .makeText(ProfileEdite_Activity.this,
                                    "Image Uploaded!!",
                                    Toast.LENGTH_SHORT)
                            .show();


                    downloadUrl = uri.toString();
                    Toast.makeText(this, "Uri", Toast.LENGTH_SHORT).show();

                    usersInfoUpdate.setPhoto(downloadUrl);
                    FireStore_DataBase.updateObject("UsersInfo", usersInfo.getUid(), usersInfoUpdate);

                    Intent intent1 = new Intent();
                    intent1.putExtra("profile", usersInfoUpdate);
                    setResult(RESULT_OK, intent1);
                    finish();
                    // Use the download URL to display the image or save it to a database
                    // ...
                });
            }).addOnFailureListener(exception -> {
                // Handle any errors
                // ...
                // Error, Image not uploaded
                progressDialog.dismiss();
                Toast
                        .makeText(ProfileEdite_Activity.this,
                                "Failed " + exception.getMessage(),
                                Toast.LENGTH_SHORT)
                        .show();
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    // Progress Listener for loading
                    // percentage on the dialog box

                    double progress = (100.0 * snapshot.getBytesTransferred()
                            / snapshot.getTotalByteCount());
                    progressDialog.setMessage(
                            "Uploaded "
                                    + (int) progress + "%");
                }
            });
        }
    }

    //الحصول على قيمة ال position Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        activityLeveSpin = PersonActivityArray.getPersonActivityList().get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //Listener Radio
    public void onRadioButtonClicked(View view) {

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:
                gender = binding.radioButtonMale.getText().toString();
                checkGender = true;
                break;

            case R.id.female:
                gender = binding.radioButtonFemale.getText().toString();
                checkGender = true;
                break;
        }
    }

    // ميثود تحديث وتعديل بينات الصحية عند التعديل على البيانات الشخصية
    public void getUsersHealthUpdate() {
        usersHealthInfoViewModel.getUsersHealthById(usersInfo.getUid()).observe(ProfileEdite_Activity.this, new Observer<Users_Health_Info>() {
            @Override
            public void onChanged(Users_Health_Info usersHealthInfo) {

                double calories = Vital_Equations.caloriDailyRequirment(binding.profileEditTvAge.getText().toString(), binding.profileEditTvHeight.getText().toString(),
                        activityLeveSpin, binding.profileEditTvWeight.getText().toString(), usersInfoUpdate.getGender());
                double water = Vital_Equations.waterQuantity(binding.profileEditTvWeight.getText().toString());
                usersHealthInfo.setCaloriesNumber(calories);
                usersHealthInfo.setWaterDrink(water);
                usersHealthInfo.setCaloriesGained(0.0);
                usersHealthInfo.setBurnedCaloriesNumber(0.0);
                usersHealthInfoViewModel.updateUsersHealth(usersHealthInfo);

                // تعديل البيانات الصحية في فايربيز
                FireStore_DataBase.updateObject("UsersInfo", usersInfo.getUid(), usersInfoUpdate);
                FireStore_DataBase.updateObject("UsersHealthInfo", usersInfo.getUid(), usersHealthInfo);


            }
        });

    }
}