package com.example.root.testproject.Retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.testproject.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 5/31/17.
 */

public class RetrifitActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        api.loadUsers().enqueue(new Callback<List<UserJson>>() {
            @Override
            public void onResponse(Call<List<UserJson>> call, Response<List<UserJson>> response) {
                MyAdapter myAdapter = new MyAdapter(response.body());
                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onFailure(Call<List<UserJson>> call, Throwable t) {}
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<UserJson> userJsons;

        public MyAdapter(List<UserJson> userJsons) {
            this.userJsons = userJsons;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_rec ,parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(userJsons.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return userJsons.size();
        }
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }
}
