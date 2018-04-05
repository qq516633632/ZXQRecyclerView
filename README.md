# ZXQRecyclerView
RecyclerView+下拉刷新+上拉加载+自定义头布局+配置加载跟多等。
可进行下拉刷新，上拉加载更多，可添加头布局。有多个加载动画可以选择。

## 效果图

下拉刷新效果

![](https://github.com/qq516633632/ZXQRecyclerView/blob/master/img/device-2018-04-04-185046.gif '下拉刷新')

上拉加载效果

![](https://github.com/qq516633632/ZXQRecyclerView/blob/master/img/device-2018-04-04-205643.gif '上拉加载')

## 使用方法

1.Gradle 添加

`compile 'com.zhuxiaoqing.zxqrecyclerviewlibrary:ZXQRecyclerView:1.0.0'`

2.布局文件中
```xml
<com.zhuxiaoqing.zxqrecyclerviewlibrary.view.ZXQRecyclerView
        android:id="@+id/dmo_zxq_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></com.zhuxiaoqing.zxqrecyclerviewlibrary.view.ZXQRecyclerView>

```
3.java代码中
```
        zxqRecyclerView = (ZXQRecyclerView) findViewById(R.id.dmo_zxq_view);//得到控件
        dmoAdapter = new DmoAdapter();//适配器
        zxqRecyclerView.setLayoutManager(new LinearLayoutManager(this));//添加布局管理器
        dom_header_view=LayoutInflater.from(this).inflate(R.layout.dom_head_view,zxqRecyclerView,false);//这里是头布局
        zxqRecyclerView.setLoadingListener(this);//监听回调函数
        //设置加载跟多文字
        zxqRecyclerView.setFootViewText("全力加载中", "宝宝已经到底了");
        //设置头布局
        zxqRecyclerView.addHeaderView(dom_header_view);
        zxqRecyclerView.setAdapter(dmoAdapter);

```
Activty 实现 ZXQRecyclerView.LoadingListener接口

刷新回调
```
 @Override
    public void onRefresh() {
        zxqRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                zxqRecyclerView.refreshComplete();//刷新完成
                dmoAdapter.setCount(10);
                dmoAdapter.notifyDataSetChanged();
                zxqRecyclerView.setNoMore(false);
            }
        }, 2000);
    }
```
下拉加载回调
```
   @Override
    public void onLoadMore() {
        if(dmoAdapter.getCount()>20){
            zxqRecyclerView.setNoMore(true);
        }else {
            zxqRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    zxqRecyclerView.loadMoreComplete();//下拉刷新完成
                    dmoAdapter.addCount();
                    dmoAdapter.notifyDataSetChanged();


                }
            }, 2000);
        }

    }
```
DMO适配器
```java
  public class DmoAdapter extends RecyclerView.Adapter<DmoAdapter.ViewHolder> {

        private int count = 10;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dmo_item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.onBindViewHolder(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView item_name_view;
            private String leable="第%s个列表";

            public ViewHolder(View itemView) {
                super(itemView);
                item_name_view= (TextView) itemView.findViewById(R.id.item_name_view);
            }

            public void onBindViewHolder(int position){
                item_name_view.setText(String.format(leable,position+1));
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
```
## 其他方法
1 设置加载中文字和没有更多文字
```
 zxqRecyclerView.setFootViewText("全力加载中", "宝宝已经到底了");
```
2 设置刷新头文字颜色

```
 setTopTextColor(int color)
```
3 是否显示刷新时间
```
setShowTime(boolean showTime)

```
4 设置加载更多字体和动画颜色

```
setLoadMoerColor(int color)
```
5 设置没有更多字体颜色

```
setNoMoreTextViewColor(int color)

```


## 注意事项
### 1 下标问题

因为下拉刷新本质是一个Item,添加的头布局也是以一个Item添加进去的。所以在具体需要根据下标对实际Item做事情的时候需要注意

1.1例如删除动画
```
//例子 伪代码
 orderBeans.remove(position);
 //这里是没有添加头布局的情况下 需要将下标+1（下拉刷新占一个item）
 adapter.notifyItemRemoved(position+1);
 adapter.notifyItemRangeChanged(position+1, orderBeans.size()-position);
 
```

1.2 刷新指定item
``` 
 //这里是没有添加头布局的情况下 需要将下标+1（下拉刷新占一个item）
adapter.notifyItemChanged(position + 1, AllCode.VIDEO_ITEM_UP);

```

1.3 findViewHolderForAdapterPosition
```
//伪代码 下标需要加上+1（没有添加头部局时 如果添加了头布局 则需要根据添加的头布局数量来对应修改加的值）
(NewSelfTimerAdapterForTwo.ViewHolder) selftimer_xview.findViewHolderForAdapterPosition(position + 1);

```


