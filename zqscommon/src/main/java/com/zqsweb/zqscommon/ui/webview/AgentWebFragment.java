package com.zqsweb.zqscommon.ui.webview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.zqsweb.zqscommon.R;
import com.zqsweb.zqscommon.base.BaseFragment;

import androidx.annotation.Nullable;
/*https://github.com/Justson/AgentWeb
* https://blog.csdn.net/qugengting/article/details/85101935
* */
public class AgentWebFragment extends BaseFragment {
	public AgentWeb mAgentWeb;
    public String url;

	public AgentWebFragment() {

	}

    public AgentWebFragment(String url) {
        this.url = url;
    }

	@Override
	protected int getAnnoLayoutId() {
		return R.layout.zqs_common_fragment_agent_web;
	}

	@Override
	public void initImmersionBar() {
		super.initImmersionBar();
		ImmersionBar.with(this).statusBarColor("#ffffffff").statusBarDarkFont(true).init();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.back).setOnClickListener(v -> getActivity().finish());
		mAgentWeb = AgentWeb.with(this)
				.setAgentWebParent(view.findViewById(R.id.webview_con), -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
				.useDefaultIndicator(Color.TRANSPARENT, 0)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
				.setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
				.setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
				.setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
				.interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
				.createAgentWeb()//创建AgentWeb。
				.ready()//设置 WebSettings。
				.go(url); //WebView载入该url地址的页面并显示。
		//AgentWebConfig.debug();

		// AgentWeb 4.0 开始，删除该类以及删除相关的API
//        DefaultMsgConfig.DownloadMsgConfig mDownloadMsgConfig = mAgentWeb.getDefaultMsgConfig().getDownloadMsgConfig();
		//  mDownloadMsgConfig.setCancel("放弃");  // 修改下载提示信息，这里可以语言切换
 
		// AgentWeb 没有把WebView的功能全面覆盖 ，所以某些设置 AgentWeb 没有提供 ， 请从WebView方面入手设置。

//		mAgentWeb.getWebCreator().getWebView().setOnLongClickListener();
 
 
		mAgentWeb.getWebCreator().getWebView().getSettings().setJavaScriptEnabled(true);
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		//优先使用网络
		mAgentWeb.getWebCreator().getWebView().getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//将图片调整到适合webview的大小
		mAgentWeb.getWebCreator().getWebView().getSettings().setUseWideViewPort(true);
		//支持内容重新布局
		mAgentWeb.getWebCreator().getWebView().getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		//支持自动加载图片
		mAgentWeb.getWebCreator().getWebView().getSettings().setLoadsImagesAutomatically(true);
		//当webview调用requestFocus时为webview设置节点
		mAgentWeb.getWebCreator().getWebView().getSettings().setNeedInitialFocus(true);
		//自适应屏幕
		mAgentWeb.getWebCreator().getWebView().getSettings().setUseWideViewPort(true);
		mAgentWeb.getWebCreator().getWebView().getSettings().setLoadWithOverviewMode(true);
		//开启DOM storage API功能（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 javascript 来操作这些数据。）
		mAgentWeb.getWebCreator().getWebView().getSettings().setDomStorageEnabled(true);
		//支持缩放
		mAgentWeb.getWebCreator().getWebView().getSettings().setBuiltInZoomControls(true);
		mAgentWeb.getWebCreator().getWebView().getSettings().setSupportZoom(true);
 
		//允许webview对文件的操作
		mAgentWeb.getWebCreator().getWebView().getSettings().setAllowFileAccess(true);
		mAgentWeb.getWebCreator().getWebView().getSettings().setAllowFileAccessFromFileURLs(true);
		mAgentWeb.getWebCreator().getWebView().getSettings().setAllowUniversalAccessFromFileURLs(true);
		mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				((TextView) getView().findViewById(R.id.title)).setText(title);
			}

		});

		mAgentWeb.getWebCreator().getWebView().setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && mAgentWeb.getWebCreator().getWebView().canGoBack()) { // 表示按返回键时的操作
						mAgentWeb.getWebCreator().getWebView().goBack(); // 后退
						// webview.goForward();//前进
						return true; // 已处理
					} else if (keyCode == KeyEvent.KEYCODE_BACK) {
						getActivity().moveTaskToBack(true);
					}
				}
				return false;
			}
		});
	}
 
	@Override
	public void onResume() {
		mAgentWeb.getWebLifeCycle().onResume();//恢复
		super.onResume();
	}
 
	@Override
	public void onPause() {
		mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
		super.onPause();
	}
 
	@Override
	public void onDestroyView() {
		mAgentWeb.getWebLifeCycle().onDestroy();
		super.onDestroyView();
	}
 
}