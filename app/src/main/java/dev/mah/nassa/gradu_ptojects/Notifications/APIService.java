package dev.mah.nassa.gradu_ptojects.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA8MrVZ84:APA91bG8JF8_bd0O5U98FbIomT0ifYvtEj57NtyLkqFOpZ-vLsaTIByFrjf_0fwg73sqSCxWRfYba88I_QFsBCKzgy9OQ3Ffj3GxfLZvAFV0_D2xBXEl0w93ghRN2LdzfFQlxlDngdF3"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
