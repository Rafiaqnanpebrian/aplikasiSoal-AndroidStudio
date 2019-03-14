package id.techarea.ujiantnipolri;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.jackpocket.pulse.Pulse;
import com.jackpocket.pulse.layouts.PulseLayout;
import com.jackpocket.pulse.layouts.PulsingView;
import com.szugyi.circlemenu.view.CircleLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static id.techarea.ujiantnipolri.HasilActivity.setViewToBitmapImage;

public class ShareActivity extends AppCompatActivity implements CircleLayout.OnItemClickListener {

    protected PulseLayout pulseLayoutImg;
    protected CircleLayout circleLayout;
    private RipplePulseLayout pulseIg;
    private RipplePulseLayout pulseWa;
    private RipplePulseLayout pulseLine;
    private RipplePulseLayout pulseFb;
    private RipplePulseLayout pulseShare;

    private String caption = "Temukan aplikasi Ujian TNI POLRI di Play Store" +
            "Aplikasi yang sangat bermanfaat untuk lolos ujian";
    private String type = "image/*";
    private Uri uri;
    private String dirFile;
    private File imageFileToShare;
    private CallbackManager callbackManager;
    private LoginManager manager;

    private ImageButton menu;
    private DrawerLayout mDrawer;
    private NavigationView nvView;
    private LinearLayout share, repeat, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_share);

        mDrawer = (DrawerLayout) findViewById(R.id.drawerShare);
        menu = (ImageButton) findViewById(R.id.btn_menu_share);
        nvView = (NavigationView) findViewById(R.id.nvViewShare);
        share = (LinearLayout) findViewById(R.id.share_button);
        exit = (LinearLayout) findViewById(R.id.exit_button);
        repeat = (LinearLayout) findViewById(R.id.repeat_button);
        
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.START);
            }
        });
        setupDrawerContent(nvView);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawers();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog();
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatDialog();
            }
        });

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

        pulseIg = findViewById(R.id.pulseIg);
        pulseIg.startRippleAnimation();

        pulseWa = findViewById(R.id.pulseWa);
        pulseWa.startRippleAnimation();

        pulseFb = findViewById(R.id.pulseFb);
        pulseFb.startRippleAnimation();

        pulseLine = findViewById(R.id.pulseLine);
        pulseLine.startRippleAnimation();

        pulseShare = findViewById(R.id.pulseShare);
        pulseShare.startRippleAnimation();
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/SimulasiToefl/skor");
        myDir.mkdirs();
        /*Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);*/

        String fname = "skor.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void repeatDialog() {
        final Dialog alert1 = new Dialog(ShareActivity.this, android.R.style.Theme_Black_NoTitleBar);
        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0060baff")));
        alert1.setContentView(R.layout.exit_dialog);

        TextView textView = (TextView) alert1.findViewById(R.id.text_info);
        Button tidak = (Button) alert1.findViewById(R.id.tidak);
        Button ya = (Button) alert1.findViewById(R.id.iya);

        textView.setText("Apakah Anda ingin mengulang simulasi?");
        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.cancel();
            }
        });

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent simulasi = new Intent(ShareActivity.this, PilihUjianActivity.class);
                startActivity(simulasi);
                finish();
            }
        });
        alert1.setCancelable(false);
        alert1.show();
    }

    private void exitDialog() {
        final Dialog alert1 = new Dialog(ShareActivity.this, android.R.style.Theme_Black_NoTitleBar);
        alert1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert1.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0060baff")));
        alert1.setContentView(R.layout.exit_dialog);

        Button tidak = (Button) alert1.findViewById(R.id.tidak);
        Button ya = (Button) alert1.findViewById(R.id.iya);
        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.cancel();
            }
        });

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        alert1.setCancelable(false);
        alert1.show();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                break;
            case R.id.nav_second_fragment:
                break;
            case R.id.nav_third_fragment:
                break;
            default:
        }

        mDrawer.closeDrawers();
    }

    private void PrintKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("id.techarea.ujiantnipolri",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
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
                if (verificateSosmed("com.instagram.android")) {
                    createInstaIntent(type, uri);
                } else {
                    NotFound("Instagram tidak ditemukan");
                }
                break;

            case R.id.shareLine:
                if (verificateSosmed("jp.naver.line.android")) {
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
            ApplicationInfo info = getPackageManager().getApplicationInfo(paket, 0);
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
