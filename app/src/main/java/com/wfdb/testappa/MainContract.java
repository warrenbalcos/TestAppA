package com.wfdb.testappa;

import com.wfdb.testappa.enums.MathOperationEnum;

/**
 * Created by warren on 2019-08-21.
 */
public interface MainContract {

    interface View {

        void setPresenter(Presenter presenter);

        void showAlert(String message);

        void showError(String message);

        void sendEcho(String data);

        void sendMathOperationRequest(float first, float second, MathOperationEnum operation);

    }

    interface Presenter {

        void setView(View view);

        void submit(String data);

        void submitAdd(float first, float second);

        void submitSubtract(float first, float second);

        void submitMultiply(float first, float second);

        void submitDivide(float first, float second);

        void onMathResultReceived(float first, float second, float result, MathOperationEnum operation);

        void onEchoReceived(long count, String content);

        void onEchoError(String message);

        void onMathError(String message);
    }

}
