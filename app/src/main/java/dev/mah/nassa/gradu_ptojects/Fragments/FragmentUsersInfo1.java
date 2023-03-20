package dev.mah.nassa.gradu_ptojects.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.mah.nassa.gradu_ptojects.Constants.SharedFunctions;
import dev.mah.nassa.gradu_ptojects.Modles.AlarmReceiver;
import dev.mah.nassa.gradu_ptojects.R;
import dev.mah.nassa.gradu_ptojects.Interfaces.UsersInfoListener;
import dev.mah.nassa.gradu_ptojects.databinding.FragmentUsersInfo1Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUsersInfo1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUsersInfo1 extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private FragmentUsersInfo1Binding binding;
    private UsersInfoListener listener;
    private String gender, length, weight, eage, illness;
    private Long alarmTime;
    private boolean checkInput = false;
    private boolean checkGender = false;
    private boolean checkIllness = false;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UsersInfoListener) {
            listener = (UsersInfoListener) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersInfo1Binding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // عند الضغط على زر التالي يقوم النترفيس بارسال قيمة الفراقمنت المتوجه له الى أكتيفيتي حتى يتم تغييير المؤشر
        binding.nextBtUserInfo.setOnClickListener(this);
        binding.timeDialogBt.setOnClickListener(this);
        binding.usersInfoRadiBut.setOnCheckedChangeListener(this);
        binding.illnessRadioGroup.setOnCheckedChangeListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.nextBtUserInfo:
                checkInput = SharedFunctions.checkEnterdDataInUserInfo(binding.editEage, binding.editLength, binding.editWeight, getContext());
                if (checkInput == true && checkGender == true && checkIllness == true) {
                    listener.getFragmentNumber(2);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framUsersInfo, new FragmentUsersInfo2());
                    fragmentTransaction.addToBackStack("stack");
                    fragmentTransaction.commit();
                    listener.getInfoUsers(gender, binding.editLength.getText().toString(),
                            binding.editWeight.getText().toString(), binding.editEage.getText().toString(), checkIllness, alarmTime);
                } else if (checkGender == false) {
                    Toast.makeText(getContext(), "الرجاء اختيار الجنس", Toast.LENGTH_LONG).show();
                } else if (checkIllness == false) {
                    Toast.makeText(getContext(), "الرجاء تحديد هل أنت مصاب بالسكري ؟؟", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.timeDialogBt:
                createTimePickerDialog();
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.male:
                gender = binding.male.getText().toString();
                checkGender = true;
                break;

            case R.id.female:
                gender = binding.female.getText().toString();
                checkGender = true;
                break;
            case R.id.yes:
                Toast.makeText(getContext(), binding.yes.getText().toString(), Toast.LENGTH_SHORT).show();
                binding.timeDialogBt.setVisibility(View.VISIBLE);
                binding.timeTextView.setVisibility(View.VISIBLE);
                illness = binding.yes.getText().toString();
                checkIllness = true;

                break;
            case R.id.no:
                Toast.makeText(getContext(), binding.no.getText().toString(), Toast.LENGTH_SHORT).show();
                binding.timeDialogBt.setVisibility(View.GONE);
                binding.timeTextView.setVisibility(View.GONE);
                illness = binding.no.getText().toString();
                checkIllness = false;

                break;
        }

    }

    // Create Time Picker Dialog
    public void createTimePickerDialog() {

        // Create a calendar instance to set the initial time in the picker
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
// Create a new instance of the time picker dialog and set the initial time
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        // Handle the time selection here
                        // Handle the time selection here
                        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getContext(), AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        String formattedTime = dateFormat.format(calendar.getTime());
                        binding.timeDialogBt.setText(formattedTime);

                        // لتخزينها في قاعدة البيانات long يجب تحويل الوقت الى
                        alarmTime = calendar.getTimeInMillis();

                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);// تعليق التنبيه الى حين وصوله للوقت المحدد



                    }
                },
                hour,
                minute,
                false
        );
// Show the time picker dialog
        timePickerDialog.show();
    }

}