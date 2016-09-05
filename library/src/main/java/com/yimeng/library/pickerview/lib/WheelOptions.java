package com.yimeng.library.pickerview.lib;

import android.view.View;

import com.yimeng.library.R;

import java.util.ArrayList;


public class WheelOptions {
    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;
    private WheelView wv_option4;

    private ArrayList<String> mOptions1Items;
    private ArrayList<String> mOptions2Items;
    private ArrayList<String> mOptions3Items;
    private ArrayList<String> mOptions4Items;


    public int screenheight;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelOptions(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void setPicker(ArrayList<String> optionsItems) {
        setPicker(optionsItems, null, null, null, false);
    }

    public void setPicker(ArrayList<String> options1Items, ArrayList<String> options2Items, boolean linkage) {
        setPicker(options1Items, options2Items, null, null, linkage);
    }

    public void setPicker(ArrayList<String> options1Items, ArrayList<String> options2Items, ArrayList<String> options3Items, boolean linkage) {
        setPicker(options1Items, options2Items, options3Items, null, linkage);
    }

    public void setPicker(ArrayList<String> options1Items, ArrayList<String> options2Items, ArrayList<String> options3Items, ArrayList<String> options4Items, boolean linkage) {
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;
        this.mOptions4Items = options4Items;

        int len = ArrayWheelAdapter.DEFAULT_LENGTH;

        if (this.mOptions3Items == null)
            len = 8;
        if (this.mOptions2Items == null)
            len = 12;


        // 选项1
        wv_option1 = (WheelView) view.findViewById(R.id.options1);
        wv_option1.setAdapter(new ArrayWheelAdapter<>(mOptions1Items, len));// 设置显示数据
        wv_option1.setCurrentItem(0);// 初始化时显示的数据
        // 选项2
        wv_option2 = (WheelView) view.findViewById(R.id.options2);
        if (mOptions2Items != null)
            wv_option2.setAdapter(new ArrayWheelAdapter<>(mOptions2Items));// 设置显示数据
        wv_option2.setCurrentItem(0);// 初始化时显示的数据
        // 选项3
        wv_option3 = (WheelView) view.findViewById(R.id.options3);
        if (mOptions3Items != null)
            wv_option3.setAdapter(new ArrayWheelAdapter<>(mOptions3Items));// 设置显示数据
        wv_option3.setCurrentItem(0);// 初始化时显示的数据

        wv_option4 = (WheelView) view.findViewById(R.id.options4);
        if (mOptions4Items != null)
            wv_option4.setAdapter(new ArrayWheelAdapter<>(mOptions4Items));// 设置显示数据
        wv_option4.setCurrentItem(0);// 初始化时显示的数据

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = (screenheight / 200) * 5;

        wv_option1.TEXT_SIZE = textSize;
        wv_option2.TEXT_SIZE = textSize;
        wv_option3.TEXT_SIZE = textSize;
        wv_option4.TEXT_SIZE = textSize;

        if (this.mOptions2Items == null)
            wv_option2.setVisibility(View.GONE);
        if (this.mOptions3Items == null)
            wv_option3.setVisibility(View.GONE);
        if (this.mOptions4Items == null)
            wv_option4.setVisibility(View.GONE);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3, String label4) {
        if (label1 != null)
            wv_option1.setLabel(label1);
        if (label2 != null)
            wv_option2.setLabel(label2);
        if (label3 != null)
            wv_option3.setLabel(label3);
        if (label4 != null)
            wv_option4.setLabel(label4);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
        wv_option4.setCyclic(cyclic);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
     *
     * @return
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[4];
        currentItems[0] = wv_option1.getCurrentItem();
        currentItems[1] = wv_option2.getCurrentItem();
        currentItems[2] = wv_option3.getCurrentItem();
        currentItems[3] = wv_option4.getCurrentItem();
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3, int option4) {
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
        wv_option3.setCurrentItem(option3);
        wv_option4.setCurrentItem(option4);
    }
}
