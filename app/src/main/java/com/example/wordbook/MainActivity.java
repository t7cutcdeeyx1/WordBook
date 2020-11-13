package com.example.wordbook;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Data1> data1List;
    Adapter_Data1 adapter_data1;
    LinearLayoutManager layoutManager;
    Button add;
    Button search;
    Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
        recyclerView = findViewById(R.id.recycler_view);
        data1List=new ArrayList<>(120);
        data1List = DataSupport.order("id desc").find(Data1.class);
        adapter_data1=new Adapter_Data1(data1List);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_data1);

        add =findViewById(R.id.add);
        search = findViewById(R.id.search);
        help=findViewById(R.id.help);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,addActivity.class);
                startActivityForResult(intent,2);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK){
                    String name=data.getStringExtra("name");
                    String jieshi=data.getStringExtra("jieshi");
                    String lijv=data.getStringExtra("lijv");
                    Data1 data1= new Data1(name,jieshi,lijv);
                    data1List.add(0,data1);
                    adapter_data1.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                }
                break;

        }
    }

    class Adapter_Data1 extends RecyclerView.Adapter<Adapter_Data1.ViewHolder> {
        private List<Data1> my_data;

        public Adapter_Data1(List<Data1> data) {
            my_data = data;
        }

        public int getItemCount(){
            int count =my_data.size();
            return count;
        }

        public void onBindViewHolder(final Adapter_Data1.ViewHolder holder, final int position) {
            final Data1 data1 = my_data.get(position);
            holder.textView1.setText(data1.getName());
            holder.textView2.setText(data1.getJieshi());
            holder.textView3.setText(data1.getLijv());
            System.out.print(data1.getName());
            holder.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues valuesx = new ContentValues();
                    valuesx.put("name",holder.textView1.getText().toString());
                    valuesx.put("jieshi",holder.textView2.getText().toString());
                    valuesx.put("lijv",holder.textView3.getText().toString());
                    DataSupport.updateAll(Data1.class, valuesx, "id = ?",data1.getId()+"");
                    holder.textView1.clearFocus();
                    holder.textView2.clearFocus();
                    holder.textView3.clearFocus();
                    //关闭软键盘
                    InputMethodManager imm =

                            (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow( holder.textView1.getWindowToken(),0);
                    imm.hideSoftInputFromWindow( holder.textView2.getWindowToken(),0);
                    imm.hideSoftInputFromWindow( holder.textView3.getWindowToken(),0);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataSupport.delete(Data1.class,data1.getId());
                    data1List.remove(position);
                    adapter_data1.notifyDataSetChanged();
                    holder.textView1.clearFocus();
                    holder.textView2.clearFocus();
                    holder.textView3.clearFocus();
                    //关闭软键盘
                    InputMethodManager imm =

                            (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

                    imm.hideSoftInputFromWindow( holder.textView1.getWindowToken(),0);
                    imm.hideSoftInputFromWindow( holder.textView2.getWindowToken(),0);
                    imm.hideSoftInputFromWindow( holder.textView3.getWindowToken(),0);
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            EditText textView1;
            EditText textView2;
            EditText textView3;
            Button delete;
            Button save;
            View itemView;


            public ViewHolder(View view) {
                super(view);
                itemView=view;
                textView1=view.findViewById(R.id.name);
                textView2=view.findViewById(R.id.jieshi);
                textView3=view.findViewById(R.id.lijv);
                delete=view.findViewById(R.id.delete);
                save= view.findViewById(R.id.save);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            /*给item设置按压点击效果*/
            TypedValue typedValue = new TypedValue();
            parent.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
            view.setBackgroundResource(typedValue.resourceId);
            /*给item设置按压点击效果*/
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }
    }
}
