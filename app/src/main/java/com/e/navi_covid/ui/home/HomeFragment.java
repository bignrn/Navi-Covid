package com.e.navi_covid.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.e.navi_covid.Fav_reg;
import com.e.navi_covid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //現在地ボタンの実装と変色
        FloatingActionButton now = root.findViewById(R.id.fab);
        now.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        //現在地ボタンのタップ時の処理の記述
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // ここにクリック時の処理を書く


                // MyCalendar クラスを呼び出す Intent を生成
                Intent intent = new Intent(getActivity(), Fav_reg.class);
                // intent オブジェクトに、パラメータ month＝ｲﾝﾃﾞｯｸｽ値という KeyValue 情報を持たせる
                intent.putExtra("x", "緯度");
                intent.putExtra("y", "経度");
                intent.putExtra("住所", "アドレス");
                // Intent 呼び出しを実行する
                startActivity(intent);
            }
        });

        //電話ボタンの実装
        FloatingActionButton call = root.findViewById(R.id.call);
        //電話ボタンのタップ時の処理の記述
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "電話をおかけします。", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }

    // 表示させるFragmentを切り替えるメソッドを定義（表示したいFragmentを引数として渡す）
    private void replaceFragment(Fragment fragment) {
        // フラグメントマネージャーの取得
        FragmentManager manager = getFragmentManager(); // アクティビティではgetSupportFragmentManager()?
        // フラグメントトランザクションの開始
        FragmentTransaction transaction = manager.beginTransaction();
        // レイアウトをfragmentに置き換え（追加）
        transaction.replace(R.id.Home_layout, fragment);
        // 置き換えのトランザクションをバックスタックに保存する
        transaction.addToBackStack(null);
        // フラグメントトランザクションをコミット
        transaction.commit();
    }
}