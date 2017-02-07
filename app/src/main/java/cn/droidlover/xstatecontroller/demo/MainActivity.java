package cn.droidlover.xstatecontroller.demo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.droidlover.xstatecontroller.XStateController;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_showLoading;
    Button bt_showError;
    Button bt_showEmpty;
    Button bt_showContent;

    SwipeRefreshLayout swipeLayout;

    XStateController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_showLoading = (Button) findViewById(R.id.bt_showLoading);
        bt_showError = (Button) findViewById(R.id.bt_showError);
        bt_showEmpty = (Button) findViewById(R.id.bt_showEmpty);
        bt_showContent = (Button) findViewById(R.id.bt_showContent);
        controller = (XStateController) findViewById(R.id.controller);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        bt_showLoading.setOnClickListener(this);
        bt_showEmpty.setOnClickListener(this);
        bt_showError.setOnClickListener(this);
        bt_showContent.setOnClickListener(this);

        controller.loadingView(View.inflate(this, R.layout.view_loading, null));
        controller.errorView(View.inflate(this, R.layout.view_error, null));

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.showLoading();
                swipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                        controller.showContent();
                    }
                }, 2000L);
            }
        });

        controller.registerStateChangeListener(new XStateController.SimpleStateChangeListener() {

            @Override
            public void onStateChange(int oldState, int newState) {
                super.onStateChange(oldState, newState);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_showContent:
                controller.showContent();
                break;

            case R.id.bt_showEmpty:
                controller.showEmpty();
                break;

            case R.id.bt_showError:
                controller.showError();
                break;

            case R.id.bt_showLoading:
                controller.showLoading();
                break;
        }
    }
}
