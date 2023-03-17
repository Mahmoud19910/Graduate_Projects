package dev.mah.nassa.gradu_ptojects.Constants;

import java.util.ArrayList;

public class PersonActivityArray {

    public  static ArrayList<String> getPersonActivityList(){
        ArrayList<String> list=new ArrayList<>();
        list.add("كثير الجلوس\n" +
                " (قليل أو معدوم النشاط)");
        list.add("نشاط خفيف\n" +
                " (تمارين خفيفة / رياضة 1-3 في الأسبوع)");
        list.add("نشاط متوسط\n" +
                " (تمارين معتدلة / رياضة 3-5في الأسبوع)");
        list.add("نشاط عالي\n" +
                " (تمارين شاقة / رياضة 6-7في الأسبوع)");
        list.add("نشاط فائق \n" +
                " (تمارين صعبة جدا / تدريب مضاعف)");
        return  list;
    }
}
