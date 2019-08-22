package com.wfdb.testappa;

/**
 * Created by warren on 2019-08-21.
 */
public interface MainContract {

    interface View {

        void setPresenter(Presenter presenter);

        void showAlert(String message);

        void showError(String message);

        void sendEcho(String data);

    }

    interface Presenter {

        void setView(View view);

        void submit(String data);

        void onEchoReceived(long count, String content);

        void onEchoError(String message);
    }

}
