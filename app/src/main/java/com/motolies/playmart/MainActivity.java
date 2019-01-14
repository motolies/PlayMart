package com.motolies.playmart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView m_ListView;
    ListViewAdapter m_Adapter;
    RadioGroup rg;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 복사

        db = new DataBaseHelper(this);
        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        db.openDataBase();


//        // Listview Header 만들기
//        View view = (View) getLayoutInflater().inflate(R.layout.list_item, null);
//        TextView tvName = (TextView) view.findViewById(R.id.itemName);
//        tvName.setText("항목");
//        //tvName.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        TextView tvUnitPrice = (TextView) view.findViewById(R.id.itemUnitPrice);
//        tvUnitPrice.setText("단가");
//        tvUnitPrice.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        TextView tvUnit = (TextView) view.findViewById(R.id.itemUnit);
//        tvUnit.setText("수량");
//        tvUnit.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        TextView tvTotalPrice = (TextView) view.findViewById(R.id.itemTotalPrice);
//        tvTotalPrice.setText("금액");
//        tvTotalPrice.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        // 아래와 같이 해줘야 기기에 맞는 DP가 나온다.
//
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        int size = Math.round(20 * dm.density);
//
//        //마진을 쓸려면  아래와 같은 방법을 쓰면 된다.
//        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        param.topMargin = size;
//        view.setLayoutParams(param);
//
//
//        LinearLayout layout = (LinearLayout) findViewById(R.id.barCodeMenu);
//        layout.addView(view, 1);


        //라디오그룹 연동
        rg = (RadioGroup) findViewById(R.id.chkGroup);

        // 아답터와 리스트뷰의 연동
        m_ListView = (ListView) findViewById(R.id.cartList);
        m_Adapter = new ListViewAdapter();
        m_ListView.setAdapter(m_Adapter);

//        m_Adapter.addItem("8888645305061", "빅볼펜", 500);

    }

    //    호출하는 부분
    protected void onClick_scanBarCode(View view) {

        Intent intent = new Intent("com.motolies.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "ALL");
        startActivityForResult(intent, 0);
    }

    //  액티비티간 결과 받는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("rCode", String.format("req : %d, res : %d", requestCode, resultCode));

        if (requestCode == 0) {

            if (resultCode == Activity.RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                //위의 contents 값에 scan result가 들어온다.
                //Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_SHORT).show();
                setItems(contents);
            }

        } else if (requestCode == 1 && resultCode == -1) {
            // 신규 바코드를 저장한다 Cancel 이 아닐 때
            Item item = (Item) data.getSerializableExtra("item");

            Log.d("rCode", item.toString());

            db.insertItem(item.getBarCode(), item.getName(), item.getPrice());

            setItems(item.getBarCode());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setItems(String barCode) {
        ArrayList<Item> items = db.getProduct(barCode);

        if (items.size() > 0) {

            // 바코드를 받아서 추가인지 삭제인지 판단함.
            // 바코드와 이름, 단가를 넘긴다

            RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            if (rb.getText().equals("추가")) {
                m_Adapter.addItem(items.get(0).getBarCode(), items.get(0).getName(), items.get(0).getPrice());
            } else if (rb.getText().equals("제거")) {
                m_Adapter.removeItem(barCode);
            }

            // 바코드를 받아서 장바구니에서 처리하는 부분
            m_Adapter.notifyDataSetChanged();
        } else {
            // 바코드 검색결과가 없다면 등록창을 띄운다
            //Toast.makeText(getApplicationContext(), "바코드 검색결과가 없습니다. 등록해주세요.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, InsertActivity.class);
            intent.putExtra("tmpBarCode", barCode);
            startActivityForResult(intent, 1);

        }
    }


}
