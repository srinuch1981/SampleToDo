package com.example.android.architecture.blueprints.todoapp.baseArch;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.base.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.base.BaseView;

/**
 * Created by schava on 6/14/2017.
 */

public class BaseViewModel <V extends BaseView, P extends BasePresenter<V>>
        extends ViewModel {

    private P presenter;

    void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }
        Log.i("ARC", "setPresenter- mPresenter set");
    }

    P getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        presenter.onDestroyed();
        presenter = null;

        Log.i("ARC", "onCleared- mPresenter cleared");
    }
}