package com.zhuxiaoqing.zxqrecyclerviewlibrary.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.zhuxiaoqing.zxqrecyclerviewlibrary.R;
import com.zhuxiaoqing.zxqrecyclerviewlibrary.utils.ProgressStyle;

public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;
    private int loadmoer_color=0xffB5B5B5;
    private int style= ProgressStyle.SysProgress;
    private LinearLayout listview_foot_progress_rootview;
    private LinearLayout no_more_root_view;//没有更多了的视图
    private LinearLayout mContainer;
    private TextView no_more_textview;//没有更多
    private View no_more_linview_1;
    private View no_more_linview_2;


    public LoadingMoreFooter(Context context) {
		super(context);
        initLoadIngView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
        initLoadIngView();
	}

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
        no_more_textview.setText(noMoreHint);
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    private void initLoadIngView(){
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.listview_footer, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        progressCon= (SimpleViewSwitcher) findViewById(R.id.listview_foot_progress);
        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(loadmoer_color);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);
        mText= (TextView) findViewById(R.id.listview_foot_more);
        no_more_textview= (TextView) findViewById(R.id.no_more_textview);
        no_more_linview_1=findViewById(R.id.no_more_linview_1);
        no_more_linview_2=findViewById(R.id.no_more_linview_2);

        loadingHint = (String)getContext().getText(R.string.listview_loading);//加载中问文字
        noMoreHint = (String)getContext().getText(R.string.nomore_loading);//没有更多文字
        loadingDoneHint = (String)getContext().getText(R.string.loading_done);//加载完成文字
        mText.setText(loadingHint);

        listview_foot_progress_rootview= (LinearLayout) findViewById(R.id.listview_foot_progress_rootview);
        no_more_root_view= (LinearLayout) findViewById(R.id.no_more_root_view);

    }

    public void setProgressStyle(int style) {
        if(style == ProgressStyle.SysProgress){
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
            this.style=style;
            AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(loadmoer_color);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setText(loadingHint);
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
                mText.setText(loadingDoneHint);
                no_more_root_view.setVisibility(GONE);
                listview_foot_progress_rootview.setVisibility(View.VISIBLE);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                listview_foot_progress_rootview.setVisibility(GONE);
                no_more_root_view.setVisibility(VISIBLE);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 设置加载更多字体颜色和动画颜色
     * @param color
     */
    public void setLoadMoerColor(int color){
        mText.setTextColor(color);
        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(color);
        progressView.setIndicatorId(style);
        progressCon.setView(progressView);
    }

    /**
     * 设置加载没有更多的线条颜色
     * @param color
     */
    public void setNoMoreLinColor(int color){
        no_more_linview_1.setBackgroundColor(color);
        no_more_linview_2.setBackgroundColor(color);

    }

    /**
     * 设置没有更多字体颜色
     * @param color
     */
    public void setNoMoreTextViewColor(int color){
        no_more_textview.setTextColor(color);
    }

    /**
     * 设置没有更多文字和线条颜色
     * @param color
     */
    public void setNoMoreTextAndLinColor(int color){
        setNoMoreLinColor(color);
        setNoMoreTextViewColor(color);
    }



}
