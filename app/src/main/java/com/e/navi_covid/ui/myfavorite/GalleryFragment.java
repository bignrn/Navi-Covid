package com.e.navi_covid.ui.myfavorite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.navi_covid.R;

import java.util.ArrayList;
import java.util.List;

public class  GalleryFragment extends Fragment{
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    //Adapter にセットするデータ
    private  int data = 1;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myfavorite, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

//                textView.setText(s);
            }
        });

        //リストを表示するメソッド
        /**
         * RecyclerView のテストコード
         *
         */
        //インスタンス化
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);

        //レイアウトのサイズを変更しない設定をONにする
        //recyclerView.setHasFixedSize(true);

        //layoutManagerえをセット
        //ここで一列とか二列とか選べる
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Adapter生成
        RecyclerView.Adapter mainAdapter = new MyFavorite_Adapter(createRowData());
        recyclerView.setAdapter(mainAdapter);

        //ボタンの処理
        Button del = root.findViewById(R.id.button);     //削除
        Button edi = root.findViewById(R.id.editButton); //編集

        //トースト
        Toast tost = Toast.makeText(getContext(), "msg", Toast.LENGTH_LONG);

        tost.show();

        //削除

        return root;
    }

    /**
     * 文字入れる
     *
     * @return
     */
    private List<RowData> createRowData(){
        List<RowData> dataSet = new ArrayList<>();

        int i = 0;
        while(i < 5){
            RowData data = new RowData();

            data.Title = "Title";
            data.subTitle = "subTitle";
            data.delete = "delete";
            data.edit = "edit";

            dataSet.add(data);
            i++;
        }

        return dataSet;
    }

    /*
        MyFavorite
     */
    //型を保持する場所?
    class RowData{
        String subTitle;
        String Title;
        String delete;
        String edit;
    }
}
