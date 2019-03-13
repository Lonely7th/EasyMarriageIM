package com.em.im.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.em.im.R;
import com.em.im.base.ServiceValue;
import com.em.im.util.LoginUtil;
import com.em.im.util.PhoneFormatCheckUtil;
import com.em.im.util.SignUtil;
import com.em.im.util.ToastUtil;
import com.em.im.view.dialog.LoadingDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_login)
    Button btnLogin;

    //是否可以获取验证码
    private boolean getVerCode = false;
    //是否可以登录
    private boolean toLogin = false;

    private int countDown = 0;
    private Timer timer = new Timer();

    private LoadingDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(PhoneFormatCheckUtil.isPhoneLegal(s.toString())){
                    btnGetCode.setAlpha(1.0f);
                    getVerCode = true;
                }else{
                    btnGetCode.setAlpha(0.6f);
                    getVerCode = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    btnLogin.setAlpha(0.6f);
                    toLogin = false;
                }else{
                    btnLogin.setAlpha(1.0f);
                    toLogin = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //开启定时器
        timer.schedule(timerTask, 1000, 1000);

        loadingDialog = new LoadingDialog(LoginActivity.this);
    }

    /**
     * 获取验证码
     */
    private void getVerCode() {

    }

    /**
     * 开始登录
     */
    private void toLogin(){
        OkGo.<String>post("").tag(this)
                .params("phoneNumber", edtPhone.getText().toString().trim())
                .params("verCode", "")
                .params(SignUtil.getParams(false))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        try {
                            JSONObject body = new JSONObject(response.body());
                            if(body.getInt("ResultCode") == ServiceValue.HttpSuccess){
                                LoginUtil.loginSuccess(body.getString("ResultData"));
                                ToastUtil.show(LoginActivity.this,"登录成功");
                                finish();
                            }else{
                                ToastUtil.show(LoginActivity.this,"网络异常");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        loadingDialog.show();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        ToastUtil.show(LoginActivity.this,"网络异常");
                        loadingDialog.dismiss();
                    }
                });
    }

    /**
     * 开始倒计时
     */
    private void startTimer() {
        getVerCode = false;
        countDown = 60;
    }

    /**
     * 倒计时相关
     */
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    btnGetCode.setText(countDown + "s");
                    if (countDown <= 0) {
                        getVerCode = true;
                        btnGetCode.setText("获取动态密码");
                    }
            }
            super.handleMessage(msg);
        }
    };
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (countDown > 0) {
                countDown--;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }

    @OnClick({R.id.back_btn, R.id.btn_get_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_get_code:
                if(getVerCode){
                    startTimer();
                    getVerCode();
                }
                break;
            case R.id.btn_login:
                if(toLogin){
                    toLogin();
                }
                break;
        }
    }
}
