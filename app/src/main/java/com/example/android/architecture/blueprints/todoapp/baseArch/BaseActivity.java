package com.example.android.architecture.blueprints.todoapp.baseArch;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.example.android.architecture.blueprints.todoapp.base.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.base.BaseView;

/**
 * Created by schava on 6/14/2017.
 */



public abstract class BaseActivity <P extends BasePresenter<V>, V extends BaseView> extends LifecycleActivity {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
        }
        presenter = viewModel.getPresenter();
        // presenter.attachLifecycle(getLifecycle());
        presenter.onViewAttached((V) this);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // presenter.detachLifecycle(getLifecycle());
        presenter.onViewDetached();
    }

    protected abstract P initPresenter();
}
