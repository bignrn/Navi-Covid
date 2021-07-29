package com.e.navi_covid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

public class Fav_reg extends AppCompatActivity {

    //ダイアログフラグメントの宣言
    private DialogFragment dialogFragment;
    //フラグメントマネージャーの宣言
    private FragmentManager fragmentManager;
    //テキストビューの宣言
    private TextView AT;                            //中央の住所のテキストビュー
    private EditText memo;                          //メモのテキストビュー
    private EditText point;                         //目的地名のテキストビュー
    //緯度経度を格納する変数の宣言
    private int address_x;                          //緯度
    private int address_y;                          //経度
    //住所、目的地、メモの内容を格納する変数の宣言
    private String address;                         //住所
    private String memobox;                        //メモの内容の入れ物
    private String pointing;                       //目的地名


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_reg);

        // Intent オブジェクトを取得する
        Intent intent = getIntent();
        //緯度経度、住所の初期化
        address_x = 0;
        address_y = 0;
        address = "";

        //テキストビューの関連付け
        memo = findViewById(R.id.memo);
        point = findViewById(R.id.point);
        AT = findViewById(R.id.address);

        //ボタンの関連付けと宣言
        Button btn1 = findViewById(R.id.OK);
        Button btn2 = findViewById(R.id.NO);


        if (intent != null) {
            if (intent.hasExtra("x")) {
                //インテントからデータを取得して、変数に格納する
                address_x = intent.getIntExtra("x", 0); //ｲﾝﾃﾝﾄからﾃﾞｰﾀを取得する(0 はﾃﾞﾌｫﾙﾄ値)
                address_y = intent.getIntExtra("y", 0);
                address = intent.getStringExtra("住所");

                //データを取れているか確認用のコード(後々削除)
                AT.setText("〒000-0000 \n 住所：" + address_x + "." + address_y + address);

            }
        }


        //ボタンにクリック機能の実装(登録の実行)
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //フラグメントマネージャーの宣言
                fragmentManager = getSupportFragmentManager();

                //DialogFragment を継承したAlertDialogFragmentのインスタンス
                dialogFragment = new AlertDialogFragment();
                //DialogFragmentの表示
                dialogFragment.show(fragmentManager , "register dialog");
            }
        });
        //ボタンにクリック機能の実装(登録の中止)
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //フラグメントマネージャの宣言
                fragmentManager = getSupportFragmentManager();

                //DialogFragment を継承したcanselDialogのインスタンス
                dialogFragment = new canselDialog();
                //DialogFragmentの表示
                dialogFragment.show(fragmentManager , "cansel dialog");
            }
        });

    }

    /**
     * 登録の確認を行う際に呼び出されるダイアログ
     * アラートダイアログフラグメントの作成
     * DIalogFragmentを継承したクラス
     */
    public static class AlertDialogFragment extends DialogFragment{
        //選択肢のリスト
        private String[] menulist = {"登録" , "キャンセル"};

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle saveInstanceState) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            //タイトル
            alert.setTitle("登録しますか。");
            alert.setItems(menulist, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int idx) {
                    Fav_reg regs = (Fav_reg) getActivity();
                    //選択１(登録する)
                    if (idx == 0) {
                        //setMassage(menulist[0]);
                        //HomeFragmentの住所、目的地、メモの内容をFav_regの変数に格納する
                        regs.pointing = regs.point.getText().toString();
                        regs.memobox = regs.memo.getText().toString();
                        regs.address = regs.AT.getText().toString();

                        //登録内容の存在の有無で登録か、変更かの分岐
                        if (regs.EXIST() == null) {
                            //内容を登録するメソッドを起動(未実装)
                            regs.Regstar();
                        } else {
                            //内容を変更するメソッドを起動(未実装)
                            regs.update();
                        }
                        //フラグメントマネージャの宣言
                        regs.fragmentManager = regs.getSupportFragmentManager();

                        //DialogFragment を継承したAlertDialogFragmentのインスタンス
                        regs.dialogFragment = new checkDialog();
                        //DialogFragmentの表示
                        regs.dialogFragment.show(regs.fragmentManager, "test alert dialog");

                    }
                    //選択2(登録キャンセル)
                    else {
                        //setMassage(menulist[1]);
                    }
                }
            });
            return alert.create();
        }

        /**
         *.(ドット)の前の変数に文字列を格納するメソッド
         * @param message
         */
        private void setMassage(String message){
            //Fav_regクラスの宣言
            Fav_reg reg = (Fav_reg) getActivity();
            //Fav_regクラスがちゃんとある場合に実行
            if(reg != null){
                //regにmessageの内容を格納する
                reg.setTextView(message);
            }
        }

    }

    /**
     * 登録の中断をする際に呼び出されるダイアログ
     * canselDialogの宣言
     * DialogFragmentを継承したクラス
     */
    public static class canselDialog extends DialogFragment{
        //選択肢のリスト
        private String[] menulist = {"中止" , "続行"};

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle saveInstanceState){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            //タイトル
            alert.setTitle("編集を中止しますか？");
            alert.setItems(menulist , new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog , int idx){
                    //選択１(キャンセル)
                    if(idx == 0){
                        //Fav_regクラスの宣言
                        Fav_reg reg = (Fav_reg) getActivity();
                        //アクティビティを閉じるメソッドの実行
                        reg.end();

                    }
                }
            });
            return alert.create();
        }
    }

    /**
     * 登録したことを知らせるダイアログ
     * checkDialogの宣言
     * DialogFragmentを継承したクラス
     */
    public static class checkDialog extends DialogFragment{
        //選択肢のリスト
        private String[] menulist = {"はい"};

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle saveInstanceState){

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            //タイトル
            alert.setTitle("登録できました。");
            alert.setItems(menulist , new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog , int idx){
                    if(idx == 0){
                        //Fav_regクラスの宣言
                        Fav_reg reg =(Fav_reg) getActivity();
                        //アクティビティを閉じるメソッドの実行
                        reg.end();

                    }
                }
            });

            return alert.create();
        }

        private void setMassage(String message){
            Fav_reg reg = (Fav_reg) getActivity();
            if(reg != null){
                reg.setTextView(message);
            }
        }
    }

    /**
     * 住所(AT)に引数(message)の内容を格納するメソッド
     * @param message
     */
    public void setTextView(String message){
        AT.setText(message);
    }

    //登録YESを押すことで、データを登録する処理（メソッド）が必要       (if文でupdateの方と場合分け)
    public void Regstar(){
        //insert into xxxx values ('目的地名'  , 'メモ' , '緯度' , '経度' , '住所' , '郵便番号');
        String sql = "INSERT INTO XXXXX VALUES('" + pointing + "' , '" + memobox + "' , '" + address_x + "' ," +
                " '" + address_y + "' , '" + address + "');";
        //確認用
        setTextView(sql);

    }

    //既に登録してあるものを登録しようとしている場合、こちらの処理を実行しなければならない
    public void update(){
        //update xxxx set pointing = '変更後の地名' , memobox = '変更後のメモ' where address_x = '変更地点の緯度' AND address_y = '変更地点の経度';
        String sql = "UPDATE XXXXX ";
        sql += "SET '目的地名' = '" + pointing + "' , 'メモ' = '" + memobox + "' ";
        sql += "where '緯度' = '" + address_x + "' AND '経度' = '" + address_y + "';";
        //確認用
        setTextView(sql);

        //finish();
    }

    //既に登録してあるかどうかの判定用
    public String EXIST(){
        //select '目的地名' from xxxx where '緯度' = address_x AND '経度' = address_y;
        String sql = "SELECT '目的地名' from XXXXX ";
        sql += "where '緯度' = '" + address_x + "' AND '経度' = '" + address_y + "';";

        return "sqlの中身";
    }

    public void end(){
        finish();
    }



}