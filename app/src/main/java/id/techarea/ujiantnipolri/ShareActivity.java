package id.techarea.ujiantnipolri;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.jackpocket.pulse.Pulse;
import com.jackpocket.pulse.layouts.PulseLayout;
import com.jackpocket.pulse.layouts.PulsingView;
import com.szugyi.circlemenu.view.CircleLayout;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class ShareActivity extends AppCompatActivity implements CircleLayout.OnItemClickListener {

    protected PulseLayout pulseLayoutImg;
    protected CircleLayout circleLayout;

    private String caption = "Temukan aplikasi Ujian TNI POLRI di Play Store" +
            "Aplikasi yang sangat bermanfaat untuk lolos ujian";
    private String type = "image/*";
    private Uri uri;
    private String dirFile;
    private File imageFileToShare;
    private CallbackManager callbackManager;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_share);

        FacebookSdk.sdkInitialize(getApplicationContext());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        dirFile = Environment.getExternalStorageDirectory().toString() + "/SimulasiToefl/skor";
        imageFileToShare = new File(dirFile, "skor.jpg");
        uri = Uri.fromFile(imageFileToShare);

        circleLayout = (CircleLayout) findViewById(R.id.circleLayout);
        circleLayout.setFirstChildPosition(CircleLayout.FirstChildPosition.NORTH);
        circleLayout.setOnItemClickListener(this);

        PrintKeyHash();
    }

    private void PrintKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("id.techarea.ujiantnipolri",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature: info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view) {
        switch (view.getId()) {
            case R.id.shareFb:
                callbackManager = CallbackManager.Factory.create();

                List<String> permissionNeeds = Arrays.asList("publish_actions");

                final ShareDialog shareDialog = new ShareDialog(ShareActivity.this);
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(ShareActivity.this, "sukses", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

                manager = LoginManager.getInstance();
                manager.logInWithPublishPermissions(this, permissionNeeds);
                manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        shareDialog.show(mShareDialog(uri));
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
                break;
            case R.id.shareWa:
                if (verificateSosmed("com.whatsapp")) {
                    createWhatsappIntent(uri);
                } else {
                    NotFound("WhatsApp tidak ditemukan");
                }
                break;

            case R.id.shareIg:
                if (verificateSosmed("com.instagram.android")){
                    createInstaIntent(type, uri);
                } else {
                    NotFound("Instagram tidak ditemukan");
                }
                break;

            case R.id.shareLine:
                if (verificateSosmed("jp.naver.line.android")){
                    createLineIntent(uri);
                } else {
                    NotFound("Line tidak ditemukan");
                }
                break;
        }
    }

    private ShareContent mShareDialog(Uri imagePath) {
        SharePhoto photo = new SharePhoto.Builder()
                .setImageUrl(imagePath)
                .setCaption(caption)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        return content;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void createLineIntent(Uri u) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, u);
        sendIntent.putExtra(Intent.EXTRA_TEXT, caption);
        sendIntent.setType(type);
        sendIntent.setPackage("jp.naver.line.android");
        startActivity(sendIntent);
    }

    private void createInstaIntent(String type, Uri u) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType(type);
        sendIntent.putExtra(Intent.EXTRA_STREAM, u);
        sendIntent.setPackage("com.instagram.android");
        startActivity(Intent.createChooser(sendIntent, "Share to "));
    }

    private void createWhatsappIntent(Uri u) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, u);
        sendIntent.putExtra(Intent.EXTRA_TEXT, caption);
        sendIntent.setType(type);
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    private boolean verificateSosmed(String paket) {

        boolean installed = false;

        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(paket,    0);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;

    }

    private void NotFound(String pesan) {
        AlertDialog.Builder aa = new AlertDialog.Builder(this);
        aa.setMessage(pesan);
        aa.setCancelable(true);
        aa.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        aa.show();
    }
}
