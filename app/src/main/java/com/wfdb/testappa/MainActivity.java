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
import com.wfdb.testappa.enums.MathOperationEnum;
import com.wfdb.testappa.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @BindView(R.id.content_input)
    EditText contentEditText;

    @BindView(R.id.number_input_1)
    EditText firstEditText;

    @BindView(R.id.number_input_2)
    EditText secondEditText;

    @BindView(R.id.submit_button)
    Button submitButton;

    @BindView(R.id.add_button)
    Button addButton;

    @BindView(R.id.subtract_button)
    Button subtractButton;

    @BindView(R.id.multiply_button)
    Button multiplyButton;

    @BindView(R.id.divide_button)
    Button divideButton;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ECHO_RESPONSE_ACTION.equals(intent.getAction())) {
                presenter.onEchoReceived(
                        intent.getLongExtra(Constants.ECHO_COUNT, 0),
                        intent.getStringExtra(Constants.ECHO_CONTENT));
            } else if (Constants.MATH_OPERATION_RESPONSE_ACTION.equals(intent.getAction())) {
                MathOperationEnum op;
                try {
                    op = MathOperationEnum.valueOf(intent.getStringExtra(Constants.OPERATION));
                } catch (Exception e) {
                    showError(e.getMessage());
                    return;
                }
                presenter.onMathResultReceived(
                        intent.getFloatExtra(Constants.FIRST, 0),
                        intent.getFloatExtra(Constants.SECOND, 0),
                        intent.getFloatExtra(Constants.RESULT, 0),
                        op);
            } else if (Constants.ECHO_ERROR_ACTION.equals(intent.getAction())) {
                presenter.onEchoError(intent.getStringExtra(Constants.ERROR_MESSAGE));
            } else if (Constants.MATH_ERROR_ACTION.equals(intent.getAction())) {
                presenter.onMathError(intent.getStringExtra(Constants.ERROR_MESSAGE));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        submitButton.setOnClickListener(view -> presenter.submit(contentEditText.getText().toString()));
        addButton.setOnClickListener(view -> {
            try {
                presenter.submitAdd(
                        Float.valueOf(firstEditText.getText().toString()),
                        Float.valueOf(secondEditText.getText().toString())
                );
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
        subtractButton.setOnClickListener(view -> {
            try {
                presenter.submitSubtract(
                        Float.valueOf(firstEditText.getText().toString()),
                        Float.valueOf(secondEditText.getText().toString())
                );
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
        multiplyButton.setOnClickListener(view -> {
            try {
                presenter.submitMultiply(
                        Float.valueOf(firstEditText.getText().toString()),
                        Float.valueOf(secondEditText.getText().toString())
                );
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
        divideButton.setOnClickListener(view -> {
            try {
                presenter.submitDivide(
                        Float.valueOf(firstEditText.getText().toString()),
                        Float.valueOf(secondEditText.getText().toString())
                );
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });

        // Initialise default presenter
        MainContract.Presenter presenter = new MainPresenter();
        presenter.setView(this);
        setPresenter(presenter);

        registerReceiver(receiver, new IntentFilter(Constants.ECHO_RESPONSE_ACTION));
        registerReceiver(receiver, new IntentFilter(Constants.ECHO_ERROR_ACTION));
        registerReceiver(receiver, new IntentFilter(Constants.MATH_OPERATION_RESPONSE_ACTION));
        registerReceiver(receiver, new IntentFilter(Constants.MATH_ERROR_ACTION));
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

    @Override
    public void sendMathOperationRequest(float first, float second, MathOperationEnum operation) {
        Intent intent = new Intent();
        intent.setAction(Constants.SUBMIT_MATH_OPERATION_ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(Config.getInstance().getAPIServiceComponentName());
        intent.putExtra(Constants.FIRST, first);
        intent.putExtra(Constants.SECOND, second);
        intent.putExtra(Constants.OPERATION, operation.name());
        getApplicationContext().sendBroadcast(intent);
    }

    public BroadcastReceiver getReceiver() {
        return receiver;
    }
}
