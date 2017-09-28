package com.zhuxiaoqing.zxqrecyclerview;

import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuxiaoqing.zxqrecyclerviewlibrary.view.ZXQRecyclerView;

public class MainActivity extends AppCompatActivity implements ZXQRecyclerView.LoadingListener {
    private ZXQRecyclerView zxqRecyclerView;
    private DmoAdapter dmoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zxqRecyclerView = (ZXQRecyclerView) findViewById(R.id.dmo_zxq_view);
        dmoAdapter = new DmoAdapter();
        zxqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        zxqRecyclerView.setLoadingListener(this);
        zxqRecyclerView.setFootViewText("全力加载中", "宝宝已经到底了");
        zxqRecyclerView.setNoMoreTextAndLinColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        zxqRecyclerView.setLoadMoerColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        zxqRecyclerView.setAdapter(dmoAdapter);
    }

    @Override
    public void onRefresh() {
        zxqRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                zxqRecyclerView.refreshComplete();
                dmoAdapter.setCount(10);
                dmoAdapter.notifyDataSetChanged();
                zxqRecyclerView.setNoMore(false);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        if(dmoAdapter.getCount()>20){
            zxqRecyclerView.setNoMore(true);
        }else {
            zxqRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    zxqRecyclerView.loadMoreComplete();
                    dmoAdapter.addCount();
                    dmoAdapter.notifyDataSetChanged();


                }
            }, 2000);
        }

    }

    public class DmoAdapter extends RecyclerView.Adapter<DmoAdapter.ViewHolder> {

        private int count = 10;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dmo_item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        @Override
        public int getItemCount() {
            return count;
        }

        private void addCount() {
            count += 10;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
