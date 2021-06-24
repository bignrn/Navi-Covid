package com.e.navi_covid.ui.myfavorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.navi_covid.MainActivity;
import com.e.navi_covid.R;

import java.util.List;

public class MyFavorite_Adapter extends RecyclerView.Adapter<MyFavorite_Adapter.MainViewHolder> {

    private List<GalleryFragment.RowData> rowDataList;

    MyFavorite_Adapter(List<GalleryFragment.RowData> rowDataList){
        this.rowDataList = rowDataList;
    }

    /**
     * 1行分のデータ
     */
    static class MainViewHolder extends RecyclerView.ViewHolder{
        //インスタンス化
        TextView Title;
        TextView subTitle;
        Button delete;
        Button edit;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            delete = itemView.findViewById(R.id.button);
            edit = itemView.findViewById(R.id.editButton);
        }
    }

    /**
     * ViewHolder作るメソッド
     * 最初しか呼ばれない
     * こおでViewHolderのlayoutファイルをインフレーとして生成
     * ViewHolder -> RecyclerView
     */
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MainViewHolder(view);
    }

    /**
     * ViewHolderとRecyclerViewをバインドする
     * 1行のViewに対して共有でやりたい処理をここに書く。
     *
     */
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int possition){
        GalleryFragment.RowData rowData = this.rowDataList.get(possition);

        //テキスト
        holder.Title.setText(rowData.Title);
        holder.subTitle.setText(rowData.subTitle);

        //ボタン
        holder.delete.setText(rowData.delete);
        holder.edit.setText(rowData.edit);
    }

    /**
     * リストの行数
     */
    public int getItemCount() {
        return rowDataList.size();

    }
}
