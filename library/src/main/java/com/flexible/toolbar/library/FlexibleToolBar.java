package com.flexible.toolbar.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * Create By Chan Youvita on 03/06/2019
 */
public class FlexibleToolBar extends Toolbar {
    public static final int GB_LEFT  = 0;
    public static final int GB_RIGHT = 1;

    @IntDef(value = {
            GB_RIGHT,
            GB_LEFT
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface GroupButton {}

    private GridLayout mGroupLeft;
    private GridLayout mGroupRight;
    private LinearLayoutCompat.LayoutParams mParams;
    private Context mContext;
    private TextView mTitle;
    private BizToolBarListener mListener;

    public void setOnToolBarClickListener(BizToolBarListener listener) {
        mListener = listener;
    }

    public FlexibleToolBar(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public FlexibleToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        initView();
    }

    /*
     * init view
     */
    private void initView() {
        try {
            RelativeLayout.LayoutParams params;
            RelativeLayout mMainLayout = new RelativeLayout(mContext);

            /*
             * group left
             */
            mGroupLeft  = new GridLayout(mContext);
            params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mMainLayout.addView(mGroupLeft, params);

            /*
             * group right
             */
            mGroupRight = new GridLayout(mContext);
            params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mMainLayout.addView(mGroupRight, params);

            /*
             * center title
             */
            mTitle = new TextView(mContext);
            mTitle.setTextSize(18);
            mTitle.setTextColor(ContextCompat.getColor(mContext,R.color.color_ffffff));
            params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            mMainLayout.addView(mTitle, params);

            setContentInsetsAbsolute(0,0);
            addView(mMainLayout);

            mParams     = new LinearLayoutCompat.LayoutParams(LayoutParams.WRAP_CONTENT, getActionBarHeight());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * set title toolbar for text center
     */
    public void setToolBarTitle(String text) {
        mTitle.setText(text);
    }

    /*
     * for image text button group left or right
     */
    public void setToolBarButton(@GroupButton int groupButton, String text, int id, int margin_left, int margin_right) {
        /*
         * set margins to text view of the first text index or last index
         */
        mParams.setMargins(margin_left, 0, margin_right, 0);

        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setTextSize(18);
        textView.setLayoutParams(mParams);
        textView.setTextColor(ContextCompat.getColor(mContext,R.color.color_ffffff));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        /*
         * add view to each groups
         */
        addViewByGroup(groupButton, addViewToParent(textView, id));
    }

    /*
     * for image button group left or right
     */
    public void setToolBarButton(@GroupButton int groupButton, int drawableId, int id, int margin_left, int margin_right) {
        /*
         * set margins to text view of the first text index or last index
         */
        mParams.setMargins(margin_left, 0, margin_right, 0);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(drawableId);
        imageView.setLayoutParams(mParams);
        /*
         * add view to each groups
         */
        addViewByGroup(groupButton, addViewToParent(imageView, id));
    }

    /*
     * add view to parent
     */
    private LinearLayout addViewToParent(View view, int id) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(mParams);
        linearLayout.setId(id);
        linearLayout.addView(view);
        return linearLayout;
    }

    /*
     * add view to each by group in gridlayout
     */
    private void addViewByGroup(@GroupButton int groupButton, LinearLayout linearLayout) {
        switch (groupButton) {
            case GB_LEFT:
                mGroupLeft.addView(linearLayout);
                break;

            case GB_RIGHT:
                mGroupRight.addView(linearLayout);
                break;
        }
        /*
         * set listener for button click
         */
        setToolBarButtonEnableClick(groupButton);
    }

    /*
     * enable listener for button
     */
    private void setToolBarButtonEnableClick(final @GroupButton int groupButton) {
        int childCount = (groupButton == GB_LEFT) ? mGroupLeft.getChildCount() : mGroupRight.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = (groupButton == GB_LEFT) ? mGroupLeft.getChildAt(i) : mGroupRight.getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setSelected(!v.isSelected()); // for only selector button
                    mListener.onToolBarClicked(groupButton, v.getId());
                }
            });
        }
    }

    /*
     * get action bar height
     */
    @SuppressLint("ObsoleteSdkInt")
    private int getActionBarHeight() {
        int[] abSzAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            abSzAttr = new int[] { android.R.attr.actionBarSize };
        } else {
            abSzAttr = new int[] { R.attr.actionBarSize };
        }
        @SuppressLint("Recycle") TypedArray a = mContext.obtainStyledAttributes(abSzAttr);
        return a.getDimensionPixelSize(0, -1);
    }

    public interface BizToolBarListener {
        void onToolBarClicked(@GroupButton int groupButton, int id);
    }
}
