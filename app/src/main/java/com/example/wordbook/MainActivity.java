package com.example.wordbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
        recyclerView = findViewById(R.id.recycler_view);
        data1List=new ArrayList<>(120);
        data1List.addAll(DataSupport.findAll(Data1.class));
        adapter_data1=new Adapter_Data1(data1List);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_data1);

        add =findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,addActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK){
                    String name=data.getStringExtra("tiwen");
                    String jieshi=data.getStringExtra("jiaren");
                    String lijv=data.getStringExtra("time");
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

        public void onBindViewHolder(Adapter_Data1.ViewHolder holder, int position) {
            Data1 data1 = my_data.get(position);
            holder.textView1.setText(data1.getName());
            holder.textView2.setText(data1.getJieshi());
            holder.textView3.setText(data1.getLijv());
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
