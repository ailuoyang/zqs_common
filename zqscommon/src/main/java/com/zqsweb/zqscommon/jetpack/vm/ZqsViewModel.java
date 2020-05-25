package com.zqsweb.zqscommon.jetpack.vm;

import com.zqsweb.zqscommon.app.ZqsApp;

import androidx.lifecycle.AndroidViewModel;

/*
 *   @author zhangqisheng
 *   @date 2020-05-25 9:53
 *   @description 一个用于Application
 */
public class ZqsViewModel extends AndroidViewModel {
    public ZqsViewModel() {
        super(ZqsApp.getApp());
    }
}
