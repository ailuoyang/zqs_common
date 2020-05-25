package com.zqsweb.zqs_common;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/*
 *   @author zhangqisheng
 *   @date 2020-05-22 14:33
 *   @description
 */
public class MyViewModel extends AndroidViewModel {

    LiveData<String> mUsername;

    public MyViewModel(@NonNull Application application) {
        super(application);
    }


}
