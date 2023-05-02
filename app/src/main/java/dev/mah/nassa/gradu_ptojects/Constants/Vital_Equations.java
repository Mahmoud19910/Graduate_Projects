package dev.mah.nassa.gradu_ptojects.Constants;

public class Vital_Equations {

    // حساب كمية المياه
    public static double waterQuantity(String weight) {
        double w = Double.parseDouble(weight);
        return (w * 2) * 21.2 / 1000;
    }

    // حساب السعرات الحرارية اليومية
    public static double caloriDailyRequirment(String eage, String length, String activityLevel, String weight, String gender) {
        double e = Double.parseDouble(eage);
        double l = Double.parseDouble(length);
        double w = Double.parseDouble(weight);
        double BMR = 0; // معدل الأيض
        double totalCalori = 0;

        if (gender.equals("male")) {
            BMR = (10 * w) + (6.25 * l) - (5 * e) + 5;
        } else {
            BMR = (10 * w) + (6.25 * l) - (5 * e) + 161;
        }

        switch (activityLevel) {
            case "كثير الجلوس\n" +
                    " (قليل أو معدوم النشاط)":
                totalCalori = BMR * 1.2;
                break;

            case "نشاط خفيف\n" +
                    " (تمارين خفيفة / رياضة 1-3 في الأسبوع)":
                totalCalori = BMR * 1.375;
                break;

            case "نشاط متوسط\n" +
                    " (تمارين معتدلة / رياضة 3-5في الأسبوع)":
                totalCalori = BMR * 1.55;
                break;
            case "نشاط عالي\n" +
                    " (تمارين شاقة / رياضة 6-7في الأسبوع)":
                totalCalori = BMR * 1.725;
                break;
            case "نشاط فائق \n" +
                    " (تمارين صعبة جدا / تدريب مضاعف)":
                totalCalori = BMR * 1.9;
                break;

        }

        return totalCalori;
    }

    // حساب كتلة الجسم الحرة و معرفة الوزن طبيعي ام لا
    public static String calculateFreeBodyMass(String length , String weight){
        double freeBodyMass = Double.parseDouble(weight) / Math.pow(((Double.parseDouble(length))/100),2);

        if(freeBodyMass>39.9){
            return "تعاني من السمنة المفرطة";
        }
        else
            if(freeBodyMass >= 34.9 && freeBodyMass <= 39.9){
                return "تعاني من السمنة درجة 2";
            }
            else
                if(freeBodyMass >=29.9 && freeBodyMass <= 39.9){
                    return "تعاني من السمنة درجة 1";
                }
                else
                    if(freeBodyMass >= 24.9 && freeBodyMass <= 29.9){
                        return "زيادة في الوزن عن الحد الطبيعي";
                    }
                    else
                        if(freeBodyMass <= 29.9 && freeBodyMass>=18.5){
                            return "وزنك ضمن الحد الطبيعي حافظ على هذا المستوى";
                        }
                        else
                            if (freeBodyMass < 18.5){
                                return "وزنك أقل من الحد الطبيعي اعتني بنفسك جيدا";

                            }else {
                                return "";
                            }
    }
}
