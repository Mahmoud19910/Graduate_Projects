package dev.mah.nassa.gradu_ptojects.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.mah.nassa.gradu_ptojects.Activityes.Exercices_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.MyMealList_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.StartTraining_Activity;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.Modles.FoodCategory;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.Modles.Sports_Exercises;
import dev.mah.nassa.gradu_ptojects.R;

public class SharedFunctions {

    public static CountDownTimer countDownTimer;
    static Dialog dialog;
    static int counter=0;
    // ميثود التحقق من البيانات في واجهة مستخدم جديد
    public static boolean checkEnterdDataInSignUp(EditText editName, EditText editPhone, EditText editPass, CheckBox checkBox, Context context) {
        boolean check = true;
        if (editName.getText().toString().isEmpty()) {
            editName.setError("هذا الحقل فارغ");
            check = false;
        } else if (editPass.getText().toString().isEmpty()) {
            editPass.setError("هذا الحقل فارغ");
            check = false;
        } else if (editPhone.getText().toString().isEmpty()) {
            editPhone.setError("هذا الحقل فارغ");
            check = false;
        } else if (!checkBox.isChecked()) {
            check = false;
            Toast.makeText(context, "الرجاء الموافقة على الشروط و القوانين", Toast.LENGTH_SHORT).show();
        }
        return check;

    }

    // ميثود التحقق من مدخلات تسجيل الدخول
    public static boolean checkEnterdDataInSignIn(EditText pass, EditText phone, Context context) {
        boolean check = true;
        if (pass.getText().toString().isEmpty()) {
            pass.setError("هذا الحقل فارغ");
            check = false;
        } else if (phone.getText().toString().isEmpty()) {
            phone.setError("هذا الحقل فارغ");
            check = false;
        }

        return check;

    }

    // ميثود انشاء تايمر تنازلي لتذكير كلمة المرور وغيرها و اخفاء بعض العناصر مثل (النصوص و زر اعادة الارسال)
    public static void countDownTimerOnCreate(Button reSend, ProgressBar progress, TextView tvTimer) {
        reSend.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int timer = (int) (millisUntilFinished / 1000);
                progress.setProgress(timer);
                tvTimer.setText(timer + "");
            }

            @Override
            public void onFinish() {
                progress.setVisibility(View.GONE);
                reSend.setVisibility(View.VISIBLE);
                tvTimer.setVisibility(View.GONE);

            }

        };
        countDownTimer.start();

    }

    // ميثود تشغيل تايمر تنازلي لتذكير كلمة المرور وغيرها و اظهار بعض العناصر مثل (النصوص و زر اعادة الارسال)
    public static void startTimerAndVisibleUi(ProgressBar progress, TextView tvTimer) {
        progress.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    public static boolean checkEnterdDataInUserInfo(EditText eage, EditText length, EditText weight, Context context) {
        boolean check = true;
        if (eage.getText().toString().isEmpty()) {
            eage.setError("هذا الحقل فارغ");
            check = false;
        } else if (length.getText().toString().isEmpty()) {
            length.setError("هذا الحقل فارغ");
            check = false;
        } else if (weight.getText().toString().isEmpty()) {
            weight.setError("هذا الحقل فارغ");
            check = false;
        }

        return check;

    }


    // ميثود حفظ عملية التسجيل
    public static void isSignIn(boolean isLoged, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("isLoged", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isSignIn", isLoged);
        editor.apply();

    }


    // ميثود التحقق من مدخلات كلمة المرور الجديدية
    public static boolean checkEnterdNewPass(EditText newPass, EditText phone, Context context) {
        boolean check = true;
        if (newPass.getText().toString().isEmpty()) {
            newPass.setError("هذا الحقل فارغ");
            check = false;
        } else if (phone.getText().toString().isEmpty()) {
            phone.setError("هذا الحقل فارغ");
            check = false;
        }

        return check;

    }


    // Show dialog
    public static void showProgressBar(Context context) {
        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(context.getResources().getLayout(R.layout.loading_progress), null);
        dialog.setContentView(view);
        dialog.create();
        dialog.show();
    }

    //Dismiss Dialog
    public static void dismissDialog() {
        if(dialog != null){
            dialog.dismiss();

        }
    }


    // Check Internet Connection
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Network network = connectivityManager.getActiveNetwork();
        return networkInfo != null && networkInfo.isConnected();
    }



    // Create Timer Dialog to start training
    public static void dialogStartTraining(Context context, int indexFrag, Sports_Exercises sports_exercises, String value, String uid) {

        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.timer_dialog_layout, null, false);
        TextView textView = view.findViewById(R.id.timerCount);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogWindow.setBackgroundDrawableResource(R.drawable.timer_background);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.show();

        countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText((millisUntilFinished / 1000) + "");

            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                Intent intent = new Intent(context, StartTraining_Activity.class);
                intent.putExtra("indexFrag", indexFrag);
                intent.putExtra("obj", sports_exercises);
                intent.putExtra("value", value);
                intent.putExtra("uid", uid);
                context.startActivity(intent);

                // start to a new activivty
            }
        };

    }

    // Increase Calories Number
    public static void caloriesIncrease(TextView caloriesView) {
        String caloriesNum = caloriesView.getText().toString();
        int calories = Integer.parseInt(caloriesNum);
        caloriesView.setText((calories += 5) + "");
    }

    // Decrease Calories Number
    public static void caloriesDecrease(TextView caloriesView) {
        String caloriesNum = caloriesView.getText().toString();
        int calories = Integer.parseInt(caloriesNum);
        if (calories == 0) {
            caloriesView.setText("0");
        } else {
            caloriesView.setText((calories -= 5) + "");
        }
    }

    // Increase Time On Time GoalFragment
    public static void timeIncrease(TextView hourseTv, TextView minutesTv) {
        int hourse = Integer.parseInt(hourseTv.getText().toString());
        int minutes = Integer.parseInt(minutesTv.getText().toString());

        if (minutes == 60) {
            minutesTv.setText("0");
            hourseTv.setText("0" + (hourse += 1));
        } else {
            minutesTv.setText((minutes += 5) + "");
        }

    }

    // Decrease Time On Time GoalFragment
    public static void timeDecrease(TextView hourseTv, TextView minutesTv) {
        int hourse = Integer.parseInt(hourseTv.getText().toString());
        int minutes = Integer.parseInt(minutesTv.getText().toString());

        if (hourse == 0 && minutes == 0) {
            hourseTv.setText("0");
            minutesTv.setText("00");
        } else if (minutes == 0 && hourse != 0) {
            hourseTv.setText("0" + (hourse -= 1));
            minutesTv.setText("60");
        } else if (minutes != 0 && hourse == 0) {
            minutesTv.setText((minutes -= 5) + "");
            hourseTv.setText("0");
        } else {
            minutesTv.setText((minutes -= 5) + "");
        }


    }

    public static void finishTrainingDialog(String goal, String achivmentGoal, Context context, String uid , Sports_Exercises sports_exercises, OnSuccessListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set the dialog title and message
        builder.setTitle("إنهاء النشاط").setMessage("هل أنت متأكد من انهاء النشاط ؟؟");
        // Set the positive button and its click listener
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // حفظ البيانات في قاعدة البيانات
                if (achivmentGoal == null) {
                    Toast.makeText(context, "قم بحرق سعرة واحدة عالأقل", Toast.LENGTH_SHORT).show();
                }
                else
                    if (Double.parseDouble(goal) >= Double.parseDouble(achivmentGoal)) {
                    Toast.makeText(context, "! للأسف لم تصل الى هدفك ", Toast.LENGTH_LONG).show();
                    listener.onSuccess(false);
                    Intent intent = new Intent(context, Exercices_Activity.class);
                    intent.putExtra("obj", sports_exercises);
                    intent.putExtra("uid" , uid);
                    ((Activity) context).startActivity(intent);
                    ((Activity) context).finish();

                } else {
                    Toast.makeText(context, "تهاني لك على الانجاز", Toast.LENGTH_LONG).show();
                    listener.onSuccess(true);
                        Intent intent = new Intent(context, Exercices_Activity.class);
                        intent.putExtra("obj", sports_exercises);
                        intent.putExtra("uid" , uid);
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();

                }


            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the OK button click
                dialog.dismiss(); // Close the dialog
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    // Get Time
    public static String getTimeAtTheMoment(){
        // Get current time
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        // Format the time using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
      return  dateFormat.format(currentTime);
    }

    //Get Date At the moment
    public static String getDateAtTheMoment(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  // Note: Months start from 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date  = day+"/"+month+"/"+ year+"";
        return date;
    }


    //ديلوك لاضافة في قائمة وجباتي
    @SuppressLint("NewApi")
    public static Dialog createFoodDialog(Context context ,String uid, FoodCategory foodCategory){


        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_food_section, null, false);
        LinearLayout linearLayout =  view.findViewById(R.id.layoutDialog);
        linearLayout.setBackgroundColor(context.getColor(R.color.white));
        linearLayout.setBackground(context.getDrawable(R.drawable.corner_raduis_card_food_dialog));
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogWindow.setBackgroundDrawableResource(R.drawable.food_shabe_background);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       //inflate
        Button cancel,add;
        ImageView foodImages,plus,minus;
        TextView caloriesMeal,weight,foodName;


        cancel = view.findViewById(R.id.dialogFood_cancel);
        add = view.findViewById(R.id.dialogFood_add);
        foodImages = view.findViewById(R.id.dialogImage);
        caloriesMeal = view.findViewById(R.id.dialogFood_caloriesMeal);
        weight= view.findViewById(R.id.dialogFood_weight);
        foodName= view.findViewById(R.id.dialogFood_name);
        plus = view.findViewById(R.id.dialogFood_plus);
        minus = view.findViewById(R.id.dialogFood_minus);

        Glide.with(context)
                .load(foodCategory.getFilePath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(foodImages);

        foodName.setText(foodCategory.getNameMeal());
        weight.setText(foodCategory.getWeightMeal());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                My_Meal_List mealList = new My_Meal_List(uid,foodCategory.getId(),foodCategory.getNameMeal()
                        ,foodCategory.getCaloriesMeal(),getTimeAtTheMoment(),getDateAtTheMoment()
                        ,weight.getText().toString());
                FireStore_DataBase.insertMeal(mealList , view.getContext());
                context.startActivity(new Intent(view.getContext(), MyMealList_Activity.class));
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = Integer.parseInt(weight.getText().toString());
                counter++;
               weight.setText(counter+"");
            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if (counter>1)
                counter = Integer.parseInt(weight.getText().toString());
                counter--;
                weight.setText(counter+"");
            }
        });

        caloriesMeal.setText(foodCategory.getCaloriesMeal());
        dialog.setCanceledOnTouchOutside(true);
        dialogWindow.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN){
                    dialog.dismiss();
                    return true;
                }
                return false;
             }
        });
        dialog.show();
        return dialog;
}

    //ديلوك اكتفتي My Meal List
    //عند ضغط على زر الاضافة
    @SuppressLint("NewApi")
    public static Dialog createFoodDialog(Context context , String uid ,OnSuccessListener onSuccessListener){
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_meal_list, null, false);
        LinearLayout linearLayout =  view.findViewById(R.id.layoutDialog);
        linearLayout.setBackgroundColor(context.getColor(R.color.white));
        linearLayout.setBackground(context.getDrawable(R.drawable.corner_raduis_card_food_dialog));
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogWindow.setBackgroundDrawableResource(R.drawable.food_shabe_background);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //inflate
        EditText meal_caloriesMeal , meal_name, meal_weight;
        ImageView plus,minus;
        meal_caloriesMeal = view.findViewById(R.id.dialogMeal_caloriesMeal);
        meal_name = view.findViewById(R.id.dialogMeal_name);
        meal_weight = view.findViewById(R.id.dialogMeal_weight);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button cancel = view.findViewById(R.id.dialogMeal_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button add = view.findViewById(R.id.dialogMeal_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validating the text fields if empty or not.
                if(TextUtils.isEmpty(meal_name.getText())){
                    meal_name.setError("قم بأدخال اسم الوجبة");
                } else if (TextUtils.isEmpty(meal_caloriesMeal.getText())) {
                    meal_caloriesMeal.setError("قم بأدخال عدد سعرات");
                } else if (TextUtils.isEmpty(meal_weight.getText())) {
                    meal_weight.setError("قم بأدخال وزن الوجبة");
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    My_Meal_List mealList = new My_Meal_List(uid,null,meal_name.getText().toString()
                            ,meal_caloriesMeal.getText().toString(),getTimeAtTheMoment(),getDateAtTheMoment()
                            ,meal_weight.getText().toString());
                    //fireStore add Meal
                    FireStore_DataBase.insertMeal(mealList , view.getContext());
                    onSuccessListener.onSuccess(mealList);
                    dialog.dismiss();
                }

            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialogWindow.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()== MotionEvent.ACTION_DOWN){
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
        return dialog;
    }

}
