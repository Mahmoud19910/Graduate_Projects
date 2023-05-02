package dev.mah.nassa.gradu_ptojects.Interfaces;

public interface UsersInfoListener {
    public void getFragmentNumber(int fragNumber);

    // fragment جلب بيانات المستخدم من
    abstract void getInfoUsers(String gender, String length, String weight, String eage, String illness, Long alarmTime);

    public void getActivityLevel(int activityIndex , String activityLeve );
}
