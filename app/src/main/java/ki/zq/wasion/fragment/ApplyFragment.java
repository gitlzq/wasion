package ki.zq.wasion.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ki.zq.wasion.R;
import ki.zq.wasion.datepicker.CustomDatePicker;
import ki.zq.wasion.datepicker.DateFormatUtils;

public class ApplyFragment extends Fragment {

    private CustomDatePicker mTimerPicker;

    public ApplyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initTimerPicker();
        return inflater.inflate(R.layout.fragment_apply, container, false);
    }

    private void initTimerPicker() {
        String beginTime = "1994年1月1日 00:00";
        long oneYear = 365 * 24 * 3600000L;

        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis() + oneYear * 10, true);
        String currentTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        //tv_add_time.setText(currentTime);
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(requireContext(), timestamp -> DateFormatUtils.long2Str(System.currentTimeMillis(), true), beginTime, endTime);
        mTimerPicker.setCancelable(true); // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCanShowPreciseTime(true);// 显示时和分
        mTimerPicker.setScrollLoop(false);// 允许循环滚动
        mTimerPicker.setCanShowAnim(true);// 允许滚动动画
    }

}
