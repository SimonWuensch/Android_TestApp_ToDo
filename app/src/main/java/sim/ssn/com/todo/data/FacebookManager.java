package sim.ssn.com.todo.data;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import javax.security.auth.callback.CallbackHandler;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.LoginActivity;
import sim.ssn.com.todo.resource.User;

public class FacebookManager {

    private final static int FACEBOOK_REQUEST_CODE = 20;

    private LoginButton buttonToLoginWithFacebook;
    private CallbackManager mCallbackManager;

    private Activity activity;

    public FacebookManager(Activity activity){
        this.activity = activity;
        initFacebookButton();
    }

    public CallbackManager getCallbackManager(){
        return mCallbackManager;
    }

    private void initFacebookButton(){
        mCallbackManager = CallbackManager.Factory.create();

        buttonToLoginWithFacebook = (LoginButton) activity.findViewById(R.id.activity_login_bFaceBook);
        buttonToLoginWithFacebook.setReadPermissions("public_profile","email","user_birthday");
        buttonToLoginWithFacebook.registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FB", "Access Token: " + loginResult.getAccessToken());
                        getFacebookProfile(loginResult.getAccessToken());
                    }
                    @Override
                    public void onCancel() {
                        Log.d("FB", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        e.printStackTrace();
                        Log.d("FB", "Error");
                    }
                }, FACEBOOK_REQUEST_CODE );
    }

    private void getFacebookProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest( accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                String rawResponse = response.getRawResponse();
                Log.v(FacebookManager.class.getSimpleName(), rawResponse);
                CustomSharedPreferences.addUser(activity, User.JsonToUser(rawResponse));
                ((LoginActivity)activity).doLogin();
            }
        });
        request.executeAsync();
    }

    public static void postOnFacebook(Activity activity, View view) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {

            Log.d("FB", "post");

            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Hello Facebook Users")
                    .setContentDescription(
                            "I’m using my app to post on Facebook!")
                    .setContentUrl(Uri.parse("http://www.welearn.de"))
                    .build();

            ShareDialog shareDialog = new ShareDialog(activity);
            shareDialog.show(linkContent);
        }
    }
}
