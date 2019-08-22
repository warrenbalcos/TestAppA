package com.wfdb.testappa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wfdb.testappa.config.Config;
import com.wfdb.testappa.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @BindView(R.id.content_input)
    EditText contentEditText;

    @BindView(R.id.submit_button)
    Button submitButton;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ECHO_RESPONSE_ACTION.equals(intent.getAction())) {
                presenter.onEchoReceived(intent.getLongExtra(Constants.ECHO_COUNT, 0), intent.getStringExtra(Constants.ECHO_CONTENT));
            } else if (Constants.ECHO_ERROR_ACTION.equals(intent.getAction())) {
                presenter.onEchoError(intent.getStringExtra(Constants.ERROR_MESSAGE));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        submitButton.setOnClickListener(view -> presenter.submit(contentEditText.getText().toString()));

        // Initialise default presenter
        MainContract.Presenter presenter = new MainPresenter();
        presenter.setView(this);
        setPresenter(presenter);

        registerReceiver(receiver, new IntentFilter(Constants.ECHO_RESPONSE_ACTION));
        registerReceiver(receiver, new IntentFilter(Constants.ECHO_ERROR_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .create()
                .show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendEcho(String data) {
        Intent intent = new Intent();
        intent.setAction(Constants.SUBMIT_ECHO_ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(Config.getInstance().getAPIServiceComponentName());
        intent.putExtra(Constants.SUBMIT_ECHO_DATA, data);
        getApplicationContext().sendBroadcast(intent);
    }

    public BroadcastReceiver getReceiver() {
        return receiver;
    }
}
