package com.wfdb.testappa;

import com.wfdb.testappa.enums.MathOperationEnum;

import java.util.Locale;

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
    public void submitAdd(float first, float second) {
        view.sendMathOperationRequest(first, second, MathOperationEnum.ADD);
    }

    @Override
    public void submitSubtract(float first, float second) {
        view.sendMathOperationRequest(first, second, MathOperationEnum.SUBTRACT);
    }

    @Override
    public void submitMultiply(float first, float second) {
        view.sendMathOperationRequest(first, second, MathOperationEnum.MULTIPLY);
    }

    @Override
    public void submitDivide(float first, float second) {
        view.sendMathOperationRequest(first, second, MathOperationEnum.DIVIDE);
    }

    @Override
    public void onMathResultReceived(float first, float second, float result, MathOperationEnum operation) {
        view.showAlert(String.format(Locale.getDefault(), "operation: %s | %.2f and %.2f | result: %.2f", operation.name(), first, second, result));
    }

    @Override
    public void onEchoReceived(long count, String content) {
        view.showAlert(content + " " + count);
    }

    @Override
    public void onEchoError(String message) {
        view.showError(message);
    }

    @Override
    public void onMathError(String message) {
        view.showError(message);
    }
}
