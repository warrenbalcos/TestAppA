package com.wfdb.testappa;

import com.wfdb.testappa.enums.MathOperationEnum;

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
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.submit("data");
        Assert.assertEquals("data", view.echo);
    }

    @Test
    public void submitAdd() {
        MainPresenter presenter = new MainPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.submitAdd(2, 2);
        Assert.assertEquals(MathOperationEnum.ADD, view.op);
    }

    @Test
    public void submitSubtract() {
        MainPresenter presenter = new MainPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.submitSubtract(2, 2);
        Assert.assertEquals(MathOperationEnum.SUBTRACT, view.op);
    }

    @Test
    public void submitMultuply() {
        MainPresenter presenter = new MainPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.submitMultiply(2, 2);
        Assert.assertEquals(MathOperationEnum.MULTIPLY, view.op);
    }

    @Test
    public void submitDivide() {
        MainPresenter presenter = new MainPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.submitDivide(2, 2);
        Assert.assertEquals(MathOperationEnum.DIVIDE, view.op);
    }

    @Test
    public void onEchoReceived() {
        MainPresenter presenter = new MainPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.onEchoReceived(1, "echo data");
        Assert.assertEquals("echo data 1", view.alert);
    }

    @Test
    public void onEchoError() {
        MainPresenter presenter = new MainPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        presenter.onEchoError("error msg");
        Assert.assertEquals("error msg", view.error);
    }

    class MockView implements MainContract.View {
        MainContract.Presenter presenter;
        String alert;
        String error;
        String echo;
        float first, second;
        MathOperationEnum op;

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

        @Override
        public void sendMathOperationRequest(float first, float second, MathOperationEnum operation) {
            this.first = first;
            this.second = second;
            this.op = operation;
        }
    }
}