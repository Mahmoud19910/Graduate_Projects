package dev.mah.nassa.gradu_ptojects.FireBase_Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dev.mah.nassa.gradu_ptojects.Interfaces.Gmail_Acc_Info_Listener;

public class Gmai_Auth {
    public static int RC_SIGN_IN = 2;
    public static GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInOptions gso;
    public static Gmail_Acc_Info_Listener gmail_acc_info_listener;

    //OnCreate تنفذ في
    public static void createGoogleAuth(Context context) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    //تنفذ عند الضغط على زر تسجيل جوجل
    public static void onClickGoogleBut(Context context) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    //onActivityResult تنفذ في
    public static void  onResultGoolgleAuth(Context context ,int requestCode , Intent data) {
        gmail_acc_info_listener=(Gmail_Acc_Info_Listener) context;
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Use account.getIdToken() to authenticate with your server.
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                String id=account.getId();
                String email=account.getEmail();
                String name=account.getDisplayName();
                Uri photoUrl=account.getPhotoUrl();



                gmail_acc_info_listener.getGmailInfoListener(name , photoUrl , email , id);
            } catch (Exception e) {
                // The ApiException status code indicates the detailed failure reason.
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // عند الضغط على زر تسجيل الخروج
    public static void onSignOut(Context context){
        FirebaseAuth.getInstance().signOut();
        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
        Toast.makeText(context, "Sign Out", Toast.LENGTH_SHORT).show();
    }

}
