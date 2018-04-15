package com.mohamed.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private final int RC_SIGN_IN = 1001;
    FirebaseAuth auth;
    private ImageView facebookLogin, googleLogin, twitterLogin;
    private EditText mEditText_email, mEditText_password;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private TwitterLoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
               "6e5fL5pGOrImfYRrQVpNrUsPK",
                "oy0U0fyZv8maEy8dHGyYV758gkl605b6c6wVu8ZL62LW52GlS8");

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);

        setContentView(R.layout.activity_login);
        setupViews();

        mLoginButton = findViewById(R.id.button_twitter_login);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
//                Log.d(TAG, "twitterLogin:success" + result);
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
//                Log.w(TAG, "twitterLogin:failure", exception);
                //  updateUI(null);
            }
        });

//        PackageInfo packageInfo;
//        String key = null;
//        try {
//            String packageName = getApplicationContext().getPackageName();
//            packageInfo = getPackageManager().getPackageInfo(packageName,
//                    PackageManager.GET_SIGNATURES);
//            Log.e("Package Name=", getApplicationContext().getPackageName());
//            for (Signature signature : packageInfo.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                key = new String(Base64.encode(md.digest(), 0));
//                Log.e("Key_Hash=", key);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("Name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("No such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("Exception", e.toString());
//        }

        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        initial_GoogleLogin();
        initial_facebookLogin();

    }

    private void setupViews() {
        facebookLogin = findViewById(R.id.iv_fb);
        googleLogin = findViewById(R.id.iv_gmail);
        twitterLogin = findViewById(R.id.iv_twitter);
        mEditText_password = findViewById(R.id.et_password);
        mEditText_email = findViewById(R.id.et_email);
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                findViewById(R.id.button_facebook_login).performClick();

            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
                signIn();
            }
        });
        twitterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.performClick();
            }
        });
    }

    private void initial_GoogleLogin() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();

    }

    private void handleTwitterSession(TwitterSession session) {
//        Log.d(TAG, "handleTwitterSession:" + session);
        // [START_EXCLUDE silent]
        showProgress();
        // [END_EXCLUDE]

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgress();
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != RC_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                hideProgress();
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                Log.d("GOOGLE_ERROR", result.toString());
            }
        }
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void finishActivity(View view) {
        finish();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        hideProgress();
                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String email = "", name = "", url = "";
                                if (!TextUtils.isEmpty(user.getEmail()))
                                    email = user.getEmail();
                                {
                                    if (!TextUtils.isEmpty(user.getDisplayName()))
                                        name = user.getDisplayName();
                                    else
                                        name = email;
                                    if (!TextUtils.isEmpty(user.getPhotoUrl().toString()))
                                        url = user.getPhotoUrl().toString();
                                    //   addUserData(user.getUid(), email, name, "", "", "", url);
                                }
                            }
                        } else {
                            hideProgress();
                            Log.d("GOOGLE_ERROR", task.getException() + "");
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        hideProgress();
    }

    private void initial_facebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                hideProgress();
            }

            @Override
            public void onError(FacebookException error) {
                hideProgress();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String email = "", name = "", url = "";
                                if (!TextUtils.isEmpty(user.getEmail()))
                                    email = user.getEmail();
                                {
                                    if (!TextUtils.isEmpty(user.getDisplayName()))
                                        name = user.getDisplayName();
                                    else
                                        name = email;
                                    url = "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?height=200";
                                }
                            } else
                                Toast.makeText(LoginActivity.this, "null", Toast.LENGTH_SHORT).show();
                        } else {

                            if (Profile.getCurrentProfile() != null) {
                                LoginManager.getInstance().logOut();
                            }
                            hideProgress();
                        }
                    }
                });
    }

    private void hideProgress() {
        mProgressDialog.dismiss();
    }

    private void showProgress() {
        mProgressDialog.show();
    }

}
