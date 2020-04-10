package com.taogu.replacerecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private List<ShopBean> shopBeans=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    private void initView(){
        mRecyclerView=findViewById(R.id.mian_recycler);
        mAdapter=new MainAdapter(this,shopBeans);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//瀑布流布局管理
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止item乱跳动
        mRecyclerView.setLayoutManager(manager);//设置recyclerview布局管理器
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(DisplayUtils.dp2px(this,16),2));//item间距
    }
    private void initData(){
        if(shopBeans.size()==0){
            String jsonStr=JsonUtils.getJson("shopData.json",this);
            shopBeans.addAll(JSON.parseArray(jsonStr,ShopBean.class));
            mAdapter.notifyDataSetChanged();
        }
    }
}
