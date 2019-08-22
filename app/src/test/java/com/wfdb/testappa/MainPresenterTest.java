package com.wfdb.testappa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by warren on 2019-08-21.
 */
public class MainPresenterTest {

    private MockView view;

    @Before
    public void setUp() throws Exception {
        view = new MockView();
    }

    @Test
    public void submit() {
        MainPresenter presenter = new MainPresenter();
        presenter.setView(view);
        presenter.submit("data");
        Assert.assertEquals("data 1", view.echo);
    }

    @Test
    public void onEchoReceived() {
        MainPresenter presenter = new MainPresenter();
        presenter.setView(view);
        presenter.onEchoReceived(1, "echo data");
        Assert.assertEquals("echo data 1", view.echo);
    }

    @Test
    public void onEchoError() {
        MainPresenter presenter = new MainPresenter();
        presenter.onEchoError("error msg");
        Assert.assertEquals("error msg", view.error);
    }

    class MockView implements MainContract.View {
        MainContract.Presenter presenter;
        String alert;
        String error;
        String echo;

        @Override
        public void setPresenter(MainContract.Presenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public void showAlert(String message) {
            this.alert = message;
        }

        @Override
        public void showError(String message) {
            this.error = message;
        }

        @Override
        public void sendEcho(String data) {
            this.echo = data;
            presenter.onEchoReceived(1, data);
        }
    }
}