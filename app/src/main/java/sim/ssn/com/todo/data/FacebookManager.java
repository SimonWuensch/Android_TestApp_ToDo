package sim.ssn.com.todo.data;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

public class FacebookManager {

    public static void getFacebookProfile( AccessToken accessToken ) {
        GraphRequest request = GraphRequest.newMeRequest( accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.v("LoginActivity", response.getRawResponse());
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
