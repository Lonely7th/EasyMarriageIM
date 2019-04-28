package com.em.im.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.em.im.R;
import com.em.im.activity.fragment.MainFragment;
import com.em.im.activity.fragment.PersonalFragment;
import com.em.im.base.ServiceValue;
import com.em.im.bean.UserBean;
import com.em.im.bean.event.RefreshMainFEvent;
import com.em.im.callback.OnAlterDialogListener;
import com.em.im.util.LoginUtil;
import com.em.im.util.SignUtil;
import com.em.im.util.SystemUtil;
import com.em.im.util.UpdateApkUtil;
import com.em.im.view.dialog.AlertDialog;
import com.em.im.view.dialog.SelectDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab1_Iv)
    ImageView tab1Iv;
    @BindView(R.id.tab1_tv)
    TextView tab1Tv;
    @BindView(R.id.rl_tab1)
    RelativeLayout rlTab1;
    @BindView(R.id.tab2_Iv)
    ImageView tab2Iv;
    @BindView(R.id.rl_tab2)
    RelativeLayout rlTab2;
    @BindView(R.id.tab3_Iv)
    ImageView tab3Iv;
    @BindView(R.id.tab3_tv)
    TextView tab3Tv;
    @BindView(R.id.rl_tab3)
    RelativeLayout rlTab3;

    Gson gson = new Gson();

    Fragment currentFragment = new Fragment();
    FragmentManager manager;
    int currentTabIndex = 0;

    Fragment mainFragment, plFragment;

    SelectDialog selectDialog = null;
    List<String> selectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();

        checkVersion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        selectList.add("相册选择");
        selectList.add("拍照选择");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        manager = getFragmentManager();
        mainFragment = new MainFragment();
        plFragment = new PersonalFragment();
        switchFragment(mainFragment);
    }

    /**
     * 刷新用户状态
     */
    private void updateUserInfo(){
        if(LoginUtil.isLogin()){
            OkGo.<String>get("").tag(this)
                    .params("user_no", LoginUtil.getUserInfo().getUserNo())
                    .params("follow_no", LoginUtil.getUserInfo().getUserNo())
                    .params(SignUtil.getParams(true))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                            try {
                                JSONObject body = new JSONObject(response.body());
                                if(body.getInt("ResultCode") == ServiceValue.HttpSuccess){
                                    LoginUtil.changeUserInfo(gson.fromJson(body.getString("ResultData"), UserBean.class));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.home_fragment, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    /**
     * 切换底部的状态
     */
    private void changeBottomStatus() {
        //清空底部所有图标的状态
        tab1Tv.setTextColor(getResources().getColor(R.color.colorGary));
        tab1Iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        tab3Tv.setTextColor(getResources().getColor(R.color.colorGary));
        tab3Iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        switch (currentTabIndex){
            case 0:
                tab1Tv.setTextColor(getResources().getColor(R.color.colorBlue));
                tab1Iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                break;
            case 1:
                break;
            case 2:
                tab3Tv.setTextColor(getResources().getColor(R.color.colorBlue));
                tab3Iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                break;

        }
    }

    @OnClick({R.id.rl_tab1, R.id.rl_tab2, R.id.rl_tab3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tab1:
                if(currentTabIndex == 0){// 刷新
                    EventBus.getDefault().post(new RefreshMainFEvent());
                }else{
                    currentTabIndex = 0;
                    changeBottomStatus();
                    switchFragment(mainFragment);
                }
                break;
            case R.id.rl_tab2:
                if(!LoginUtil.isLogin()){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    return ;
                }
                selectDialog = new SelectDialog(MainActivity.this, selectList, new SelectDialog.OnSelectListener() {
                    @Override
                    public void onItemSelect(View view, int position, long id) {
                        switch (position){
                            case 0:
//                                startImagePicker(ContentKey.SelectPic_Gallery);
                                break;
                            case 1:
//                                startImagePicker(ContentKey.SelectPic_Camera);
                                break;
                        }
                    }
                });
                selectDialog.show();
                break;
            case R.id.rl_tab3:
                currentTabIndex = 2;
                changeBottomStatus();
                switchFragment(plFragment);
                break;
        }
    }

    /**
     * 开始选择图片
     */
    private void startImagePicker(int type) {

    }

    /**
     * 获取版本更新信息
     */
    private void checkVersion() {
        OkGo.<String>get("").tag(this)
                .params(SignUtil.getParams(false))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        try {
                            JSONObject body = new JSONObject(response.body());
                            if(body.getInt("ResultCode") == ServiceValue.HttpSuccess){
                                JSONObject resultData = body.getJSONObject("ResultData");
                                String code = resultData.getString("Code");
                                final String url = resultData.getString("ApkPath");
                                if(!code.equals(SystemUtil.getVersionName(MainActivity.this))){ // 需要升级
                                    new AlertDialog(MainActivity.this, resultData.getString("UpdateContent"), "更新", new OnAlterDialogListener() {
                                        @Override
                                        public void onRightClick() {
                                            UpdateApkUtil.downLoadFile(MainActivity.this,url);
                                        }

                                        @Override
                                        public void onLeftClick() {
                                        }
                                    }).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}