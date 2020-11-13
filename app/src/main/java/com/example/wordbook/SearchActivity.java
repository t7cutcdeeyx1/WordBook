package com.example.wordbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ImageButton back;
    RecyclerView recyclerView;
    EditText editText;
    List<Data1> list;
    Adapter_Data1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        back=findViewById(R.id.back);
        recyclerView=findViewById(R.id.recyclerView);
        editText=findViewById(R.id.edit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String word=s.toString();

                if (word.equals("")){
                    list=new ArrayList<>();
                    adapter=new Adapter_Data1(list);
                    recyclerView.setAdapter(adapter);
                }else {
                    list= DataSupport.select("name", "jieshi", "lijv")
                            .where(" name like ?", "%" + word + "%")
                            .find(Data1.class);
                    adapter=new Adapter_Data1(list);
                    recyclerView.setAdapter(adapter);
                }

            }
        });


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

        public void onBindViewHolder(Adapter_Data1.ViewHolder holder, int position) {
            Data1 data1 = my_data.get(position);
            holder.textView1.setText(data1.getName());
            holder.textView2.setText(data1.getJieshi());
            holder.textView3.setText(data1.getLijv());
            System.out.print(data1.getName());
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView1;
            TextView textView2;
            TextView textView3;


            public ViewHolder(View view) {
                super(view);
                textView1=view.findViewById(R.id.name);
                textView2=view.findViewById(R.id.jieshi);
                textView3=view.findViewById(R.id.lijv);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
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
