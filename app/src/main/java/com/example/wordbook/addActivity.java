package com.example.wordbook;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cc.shinichi.library.tool.utility.ui.ToastUtil;

public class addActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;

    Button add;

    String name="";
    String jieshi = "";
    String lijv="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add=findViewById(R.id.add);
        editText1=findViewById(R.id.editView1);
        editText2=findViewById(R.id.editView2);
        editText3=findViewById(R.id.editView3);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editText1.getText().toString();
                jieshi=editText2.getText().toString();
                lijv=editText3.getText().toString();
                new Data1(name,jieshi,lijv).save();
                if (!name.equals("")&&!jieshi.equals("")&&!lijv.equals("")){
                    Intent intent_return = new Intent();
                    intent_return.putExtra("name",name);
                    intent_return.putExtra("jieshi",jieshi);
                    intent_return.putExtra("lijv",lijv);
                    setResult(RESULT_OK,intent_return);
                    finish();
                }else {
                    ToastUtil.getInstance()._short(addActivity.this,"请输入完整数据");
                }
            }
        });
    }

}
