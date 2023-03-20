package dev.mah.nassa.gradu_ptojects.FireBase_Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

import dev.mah.nassa.gradu_ptojects.Activityes.Home_Activity;
import dev.mah.nassa.gradu_ptojects.Activityes.UsersInformation_Activity;
import dev.mah.nassa.gradu_ptojects.Interfaces.VerificationIdListener;


public class PhoneAuth {

    private static VerificationIdListener listener;

   public static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    // onCreate تنفذ في
    public static void sendSmsCode(Context context , String phoneNumber){

        listener= (VerificationIdListener) context;

        PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // Verification successful, authenticate the user
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Verification failed, show error message
                Toast.makeText(context, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // Save the verification ID and resending token to use later
                listener.getverificationId(verificationId);
            }
        };


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                (Activity) context, // Activity (for callback binding)
                callbacks // OnVerificationStateChangedCallbacks
        );

    }



    // ميثود يتم استدعائها عند الضغط على زر تأكيد الرمز
    public static PhoneAuthCredential veryfiedAccount(String storedVerificationId , String smsCode ){
        return  PhoneAuthProvider.getCredential(storedVerificationId, smsCode);
    }

    // ميثود يتم استدعائها عند الضغط على زر تأكيد الرمز
    public static void signInWithPhoneAuthCredential(PhoneAuthCredential credential, Context context , String name , String phone ,String pass) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            String uid=user.getUid();
                            Intent intent=new Intent(context , UsersInformation_Activity.class);
                            intent.putExtra("name",name);
                            intent.putExtra("phone",phone);
                            intent.putExtra("pass",pass);
                            intent.putExtra("uid",uid);

                            context.startActivity(intent);
                            ((Activity) context).finish();

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(context, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


}
