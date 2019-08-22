package com.wfdb.testappa;

/**
 * Created by warren on 2019-08-21.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void submit(String data) {
        view.sendEcho(data);
    }

    @Override
    public void onEchoReceived(long count, String content) {
        view.showAlert(content + " " + count);
    }

    @Override
    public void onEchoError(String message) {
        view.showError(message);
    }
}
