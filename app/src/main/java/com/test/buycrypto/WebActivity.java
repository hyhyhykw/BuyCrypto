package com.test.buycrypto;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ViewUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created time : 2021/11/3 16:23.
 *
 * @author 10585
 */
public class WebActivity extends AppCompatActivity {
    private final String moonPayId = "walletAddress";

    private final String addAddress = "javascript:try{" +
            "let input = document.getElementById('" + moonPayId + "');" +
            "let change = document.createEvent('Event');" +
            "change.initEvent('input', true, true);" +
            "input.setAttribute('value','%s');" +
            "input.dispatchEvent(change);" +
            "}catch(e) {" +
            "console.log(e);" +
            "}";


    private final String listenContentChange = "javascript:try{" +
            "let targetNode = document.body;" +
            "let config = { attributes: true, childList: true, subtree: true };" +
            "let observer = new MutationObserver(function(mutationsList) {" +
            "    let input = document.getElementById('" + moonPayId + "');" +
            "    if (input!==null){" +
            "        android.disableInput();" +
            "        input.onclick=function () {" +
            "            android.pickAddress();" +//android对应addJavascriptInterface的name，pickAddress 对应JavascriptInterface注解的方法
            "        }" +
            "    }else{" +
            "        android.enableInput();" +
            "    }" +
            "});" +
            "observer.observe(targetNode, config);" +
            "}catch(e){" +
            "console.log(e);" +
            "}";

    private WebView mWebView;

    private boolean needJs;
    private FrameLayout mLytRoot;
    private LinearLayout mLytSelectAddress;
    private LinearLayout mLytAddress1;
    private LinearLayout mLytAddress2;
    private LinearLayout mLytAddress3;

    private int screenWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.web);
        mLytRoot = findViewById(R.id.lyt_root);
        mLytSelectAddress = findViewById(R.id.lyt_select_address);
        mLytAddress1 = findViewById(R.id.lyt_address1);
        mLytAddress2 = findViewById(R.id.lyt_address2);
        mLytAddress3 = findViewById(R.id.lyt_address3);

        screenWidth = ScreenUtils.getScreenWidth();
        mLytSelectAddress.setTranslationX(screenWidth);

        String url = getIntent().getStringExtra("url");
        needJs = url.contains("moonpay");


        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!needJs) return;
                if (null != mWebView) {
                    mWebView.evaluateJavascript(listenContentChange, value -> {

                    });
                }
            }
        });
        mWebView.addJavascriptInterface(new AndroidJsInterface(), "android");
        mLytAddress1.setOnClickListener(v -> {
            togglePick(false);
            String javascript = String.format(addAddress, "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh");
            LogUtils.d("javascript====>" + javascript);
            mWebView.evaluateJavascript(javascript, value -> {
            });
        });
        mLytAddress2.setOnClickListener(v -> {
            togglePick(false);
            String javascript = String.format(addAddress, "0x56473200cf43aa30380b8c86b96deaa1c67cad6f");
            LogUtils.d("javascript====>" + javascript);
            mWebView.evaluateJavascript(javascript, value -> {
            });
        });
        mLytAddress3.setOnClickListener(v -> {
            togglePick(false);
            String javascript = String.format(addAddress, "0x56473200cf43aa30380b8c86b96deaa1c67cad6f");
            LogUtils.d("javascript====>" + javascript);
            mWebView.evaluateJavascript(javascript, value -> {
            });
        });

        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mLytSelectAddress.getTranslationX() == 0) {
            togglePick(false);
            return;
        }
        super.onBackPressed();
    }

    public class AndroidJsInterface {

        @JavascriptInterface
        public void pickAddress() {
            ViewUtils.runOnUiThread(() -> {
                KeyboardUtils.hideSoftInput(mWebView);
                togglePick(true);
            });
        }

        @JavascriptInterface
        public void disableInput() {
            ViewUtils.runOnUiThread(() -> {
                mLytRoot.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
                mLytRoot.setFocusable(true);
                mLytRoot.requestFocus();
                mLytRoot.setFocusableInTouchMode(true);

//                mWebView.clearFocus();
                mWebView.setFocusable(false);
                mWebView.setFocusableInTouchMode(false);
            });

        }


        @JavascriptInterface
        public void enableInput() {
            ViewUtils.runOnUiThread(() -> {
                mLytRoot.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                mLytRoot.setFocusable(false);
                mLytRoot.setFocusableInTouchMode(false);

                mWebView.setFocusable(true);
                mWebView.setFocusableInTouchMode(true);
            });
        }
    }

    private ObjectAnimator mObjectAnimator;
    private boolean isAnim = false;

    private void togglePick(boolean show) {
        if (isAnim) return;
        if (null == mObjectAnimator) {
            mObjectAnimator = ObjectAnimator.ofFloat(mLytSelectAddress, "translationX", screenWidth, 0);
            mObjectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAnim = false;
                }
            });
        }
        isAnim = true;
        if (show) {
            mObjectAnimator.start();
        } else {
            mObjectAnimator.reverse();
        }
    }
}