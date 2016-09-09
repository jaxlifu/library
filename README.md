#Library
一个实现了包括`自定义recyclerView`,`pickerView`,`multiimageselector`,以及`utils`,`customView`,的库!  
##支持的功能
*  recyclerView 上拉加载,下拉记载更多功能,以及多种类型的Item  
*  类似于IOS的Picker的效果  
*  类似微信的多图选择功能
*  类似于IOS的ActionSheet的效果
*  圆形图片`CircleImagView`
*  在原生的ViewPager中设置是否支持滑动的`ScrollAvailableViewPager`
*  类似微信好友标题的TAG`TagsGroup`
*  固定高度自适应宽度的ImageView`WrapWidthImageView`
*  固定宽度自适应高度的ImageView`WrapHeightImageView`
*  新增了TabNavigator导航
*  and so on...

##使用library
* gradle
```
dependencies{
    compile 'com.yimeng:library:1.0.1'
}

```

* eclipse
[eclipse jar libs 传送门](https://github.com/RainliFu/library/tree/master/library/libs) or [直接下载地址](https://github.com/RainliFu/library/blob/master/library/libs/library.jar)

* 其他引用说明,library中引用了`eventbus`,`logger`,`glide`,`dexter`,请注意不要重复引用
```
    compile 'com.orhanobut:logger:1.3'
    compile 'org.greenrobot:eventbus:3.0.0'
    compile 'com.github.bumptech.glide:glide:3.7.0'

    compile 'com.karumi:dexter:2.2.2'
```

* 使用方法
在Application中的oncreate中初始化,如果没有Application类就创建并在AndroidManifest.xml中注册
```
 @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Dexter.initialize(this);
        ApplicationUtils.initialize(this);
    }
```