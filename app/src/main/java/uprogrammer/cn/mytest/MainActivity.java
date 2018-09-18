package uprogrammer.cn.mytest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import lufei.com.order.R;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button btn_refresh;
    private Button btn_clear_cache;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 手机文件夹处理
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    //  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        File outerPath = Environment.getExternalStorageDirectory();
        verifyStoragePermissions(this);

        webView = (WebView) findViewById(R.id.webview);
        //WebView加载web资源
//        webView.loadUrl("file:///android_asset/index.html");
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_clear_cache = (Button) findViewById(R.id.btn_clear_cache);
        webView.loadUrl("http://192.168.2.173:5533/login.html");
        webView.setWebViewClient(new WebViewClient() {

        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启database storage API功能
        settings.setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        String cacheDirPath = outerPath.getCanonicalPath() + "/ZHT/download";
        Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
        settings.setAppCachePath(cacheDirPath);
        settings.setAppCacheEnabled(true);
        Log.i("databasepath", settings.getDatabasePath());

        btn_clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearCache(true);
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
// Assume thisActivity is the current activity
//        int permissionCheck = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_CALENDAR);
//
//        try {
//            File file = new File(outerPath.getCanonicalPath().toString()+"/ZHT/cache/test.txt");
//            if(!file.exists())
//            {
////                File image = File.createTempFile("test", "txt",file1);
//
//                file.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(file);
//            String content = "user=dsdsds";
//            fos.write(content.getBytes());
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}