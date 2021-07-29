/**
 * @FileName    MainAction //ファイル名
 * @FileName    MainActivity //ファイル名
 * @class       MainActivity//クラス名
 * @date        2021/6/24   //更新日
 *
 * コメント参考：https://neet-rookie.hatenablog.com/entry/2019/09/09/142358
 */

package com.e.navi_covid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ツールバーの設定
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //フローティングアクションボタンの設定、マテリアルデザインコンポーネント
        //現在地ボタンの実装と変色
        FloatingActionButton now = findViewById(R.id.fab);
        now.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        //現在地ボタンのタップ時の処理の記述
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // ここにクリック時の処理を書く

                // MyCalendar クラスを呼び出す Intent を生成
                Intent intent = new Intent(MainActivity.this, Fav_reg.class);
                // intent オブジェクトに、パラメータ month＝ｲﾝﾃﾞｯｸｽ値という KeyValue 情報を持たせる
                intent.putExtra("x", "緯度");
                intent.putExtra("y", "経度");
                intent.putExtra("住所", "アドレス");
                // Intent 呼び出しを実行する
                startActivity(intent);
            }
        });

        //以下ドロワ―メニューバーの設定
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView item = findViewById(R.id.action_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //追記箇所@MainActivity→Settings
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
    //ここまで追記

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}