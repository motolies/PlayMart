package com.motolies.playmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("tmpBarCode");

        TextView tv = (TextView) findViewById(R.id.txtBarCode);
        tv.setText(barcode);


    }


    protected void onClicked_btnOK(View view) {

        EditText etBarCode = (EditText) findViewById(R.id.txtBarCode);
        EditText etName = (EditText) findViewById(R.id.txtItemName);
        EditText etPrice = (EditText) findViewById(R.id.txtPrice);

        if (etName.getText().toString().length() == 0 || etPrice.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "상품명과 상품가격은 꼭 필요합니다.", Toast.LENGTH_SHORT).show();
        } else {
            Item item = new Item();
            item.setBarCode(etBarCode.getText().toString());
            item.setName(etName.getText().toString());
            item.setPrice(Integer.parseInt(etPrice.getText().toString()));

            Intent intent = new Intent();
            intent.putExtra("item", item);

            setResult(RESULT_OK, intent);
            finish();
        }


    }

    protected void onClicked_btnCancel(View view) {
        //result코드 담기
        setResult(RESULT_CANCELED);
        //Activity 종료하기(자신을 호출한 액티비티로 가기)
       finish();
    }
}
