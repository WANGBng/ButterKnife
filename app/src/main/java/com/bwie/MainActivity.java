package com.bwie;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity";
    //    2）	绑定所有控件，绑定其中一部分，不给分；
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.zhujie)
    Button zhujie;
    @BindView(R.id.addlist)
    Button addlist;
    @BindView(R.id.yuan_xing)
    Button yuanXing;
    @BindView(R.id.yuan_jiao)
    Button yuanJiao;
    @BindView(R.id.kuan_gao)
    Button kuanGao;
    @BindView(R.id.loge_gif)
    Button logeGif;
    @BindView(R.id.simple_yuan)
    SimpleDraweeView simple_yuan;
    @BindView(R.id.simple_yuan_jiao)
    SimpleDraweeView simple_yuan_jiao;
    @BindView(R.id.loge_gi)
    SimpleDraweeView loge_gi;
    @BindView(R.id.kuan_bi)
    SimpleDraweeView kuan_bi;

    Unbinder unbinder;

//    private String sheJiUrl="http://www.zhaoapi.cn/images/quarter/ad1.png";
//    private String gifUrl="http://www.zhaoapi.cn/images/girl.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //2.	使用ButterKnife依赖注入框架，实现如下功能
        unbinder = ButterKnife.bind(this);

    }
//3）	绑定具有点击功能的控件；
    @OnClick({R.id.yuan_xing, R.id.yuan_jiao, R.id.kuan_gao, R.id.loge_gif,R.id.zhujie,R.id.addlist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yuan_xing:
                //圆形的
                Uri uri = Uri.parse("http://www.zhaoapi.cn/images/quarter/ad1.png");
                DraweeController cont = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setOldController(simple_yuan.getController())
                        .build();
                RoundingParams roundingParams = new RoundingParams();
                roundingParams.setRoundAsCircle(true);
                simple_yuan.setController(cont);
                break;
            case R.id.yuan_jiao://圆角的

                Uri uri_yuan_jiao = Uri.parse("http://www.zhaoapi.cn/images/quarter/ad1.png");
                DraweeController uri_yuan_jiaoc = Fresco.newDraweeControllerBuilder()
                        .setUri(uri_yuan_jiao)
//                        .setRoundedCornerRadius
                        .setOldController(simple_yuan_jiao.getController())
                        .build();
                RoundingParams roundingParam =new RoundingParams();
                roundingParam.setBorder(Color.RED,2);
                roundingParam.setRoundAsCircle(true);
                simple_yuan_jiao.setController(uri_yuan_jiaoc);
                break;
            case R.id.kuan_gao://宽高的

                Uri kuan_gao = Uri.parse("http://www.zhaoapi.cn/images/quarter/ad1.png");
                DraweeController kua_gao = Fresco.newDraweeControllerBuilder()
                        .setUri(kuan_gao)
//                        .setControllerListener(con)
                        .build();

                kuan_bi.setController(kua_gao);
                break;
            case R.id.loge_gif://动画的
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri("http://www.zhaoapi.cn/images/girl.gif")
                        .setAutoPlayAnimations(true)
                        .setOldController(loge_gi.getController())
                        .build();
                loge_gi.setController(controller);
                break;
            case R.id.zhujie://2.	反射、注解；
                User user = new User("Hello world");

                 Toast.makeText(this,"name的值是++++"+user.getName(),Toast.LENGTH_SHORT).show();

                break;
            case R.id.addlist:
                User us = new User("ssss");
//                us.getClass().toString().toCharArray();
                //textView1.setText(String.valueOf(us.getClass().toString()));
                ArrayList<Integer> users = new ArrayList<>();
                users.add(3);

                try {
                    Class<? extends ArrayList> clazz = users.getClass();
                    Method method = clazz.getMethod("add",Object.class);
                    method.invoke(users,"这是发一个");
                    method.invoke(users,"这是发两个个");
                    method.invoke(users,"这是发上个个");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (Object item:users) {
                    Log.e(TAG, "onViewClicked: "+ item);
                    Toast.makeText(this,"我是反射的item+++"+item,Toast.LENGTH_SHORT).show();
                }

                break;

            default:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //4）	生命周期进行ButterKnife解绑；
        unbinder.unbind();
    }
}
