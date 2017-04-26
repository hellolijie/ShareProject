package com.example.test.commonlibrary.customWidget;

import android.content.Context;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lijie on 2017/4/26.
 */

public class DynamicTimeView extends android.support.v7.widget.AppCompatTextView {
    private TimeAdapter timeAdapter;
    private TimeExecutor timeExecutor;

    public DynamicTimeView(Context context) {
        super(context);
        init();
    }

    public DynamicTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        timeExecutor = new TimeExecutor();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        timeExecutor.startTime();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timeExecutor.stopTime();
    }

    /**
     * 设置显示时间字符串适配器
     * @param timeAdapter
     */
    public void setTimeAdapter(TimeAdapter timeAdapter){
        this.timeAdapter = timeAdapter;
    }

    /**
     * 格式化日期数据
     *
     * @param date
     * @param formatStr
     * @return
     */
    private String formatTime(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        return format.format(date);
    }

    /**
     * 计时器
     */
    class TimeExecutor {
        private Runnable callback;

        /**
         * 开始计时
         */
        public void startTime(){
            time();
        }

        /**
         * 停止计时
         */
        public void stopTime(){
            removeCallbacks(callback);
        }

        private void time(){
            postDelayed(callback = new Runnable() {
                @Override
                public void run() {
                    String timeStr = null;

                    Date curDate = new Date();
                    if (timeAdapter != null){
                        timeStr = timeAdapter.getTimeStr(curDate);
                    }
                    if (timeStr == null){
                        timeStr = formatTime(curDate, "yyyy-MM-dd HH:mm:ss");
                    }

                    setText(timeStr);

                    time();
                }
            }, 1000);
        }
    }

    /**
     * 时间显示适配器
     */
    public interface TimeAdapter{
        String getTimeStr(Date curDate);
    }
}
