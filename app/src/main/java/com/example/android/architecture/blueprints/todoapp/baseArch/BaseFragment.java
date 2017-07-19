package com.example.android.architecture.blueprints.todoapp.baseArch;

import com.example.android.architecture.blueprints.todoapp.base.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.base.BaseView;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by schava on 6/14/2017.
 */
public abstract class BaseFragment <P extends BasePresenter<V>, V extends BaseView> extends LifecycleFragment {

    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        if (viewModel.getPresenter() == null) {
            Log.i("ARC", "onViewCreated- mPresenter created at first");
            viewModel.setPresenter(initPresenter());
        }
        mPresenter = viewModel.getPresenter();
        // mPresenter.attachLifecycle(getLifecycle());
        mPresenter.onViewAttached((V) this);

        Log.i("ARC", "onViewCreated- mPresenter attached");
    }

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // mPresenter.detachLifecycle(getLifecycle());
        mPresenter.onViewDetached();

        Log.i("ARC", "onDestroyView- mPresenter detached");
    }

    protected abstract P initPresenter();
}
