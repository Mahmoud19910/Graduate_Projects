package dev.mah.nassa.gradu_ptojects.Constants;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SharedFunctions {

    static CountDownTimer countDownTimer;
    // ميثود التحقق من البيانات في واجهة مستخدم جديد
    public static boolean checkEnterdDataInSignUp(EditText editName , EditText editPhone , EditText editPass, CheckBox checkBox , Context context){
        boolean check=true;
        if(editName.getText().toString().isEmpty()){
            editName.setError("هذا الحقل فارغ");
            check=false;
        }
        else
        if(editPass.getText().toString().isEmpty()){
            editPass.setError("هذا الحقل فارغ");
            check=false;
        }
        else
        if(editPhone.getText().toString().isEmpty()){
            editPhone.setError("هذا الحقل فارغ");
            check=false;
        }
        else
        if(!checkBox.isChecked()){
            check=false;
            Toast.makeText(context, "الرجاء الموافقة على الشروط و القوانين", Toast.LENGTH_SHORT).show();
        }
        return check;

    }

    // ميثود انشاء تايمر تنازلي لتذكير كلمة المرور وغيرها و اخفاء بعض العناصر مثل (النصوص و زر اعادة الارسال)
    public static void countDownTimerOnCreate(Button reSend, ProgressBar progress , TextView tvTimer){
        reSend.setVisibility(View.GONE);
        countDownTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int timer=(int)(millisUntilFinished/1000);
                progress.setProgress(timer);
                tvTimer.setText(timer+"");
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
    public static void startTimerAndVisibleUi(ProgressBar progress , TextView tvTimer){
        progress.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.VISIBLE);
        countDownTimer.start();
    }

    public static boolean checkEnterdDataInUserInfo(EditText eage , EditText length , EditText weight ,Context context){
        boolean check=true;
        if(eage.getText().toString().isEmpty()){
            eage.setError("هذا الحقل فارغ");
            check=false;
        }
        else
        if(length.getText().toString().isEmpty()){
            length.setError("هذا الحقل فارغ");
            check=false;
        }
        else
        if(weight.getText().toString().isEmpty()){
            weight.setError("هذا الحقل فارغ");
            check=false;
        }

        return check;

    }



}
