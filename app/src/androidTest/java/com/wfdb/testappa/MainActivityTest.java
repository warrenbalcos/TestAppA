package com.wfdb.testappa;

import android.content.BroadcastReceiver;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.wfdb.testappa.enums.MathOperationEnum;
import com.wfdb.testappa.utils.Constants;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by warren on 2019-08-22.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void submitData() {
        MockPresenter presenter = new MockPresenter();
        presenter.setView(activityRule.getActivity());
        activityRule.getActivity().setPresenter(presenter);

        Espresso.onView(ViewMatchers.withId(R.id.content_input)).perform(ViewActions.typeText("content data"));
        Espresso.onView(ViewMatchers.withId(R.id.submit_button)).perform(ViewActions.click());

        Assert.assertEquals("content data", presenter.data);
    }

    @Test
    public void submitAdd() {
        MockPresenter presenter = new MockPresenter();
        presenter.setView(activityRule.getActivity());
        activityRule.getActivity().setPresenter(presenter);

        Espresso.onView(ViewMatchers.withId(R.id.number_input_1)).perform(ViewActions.typeText("3"));
        Espresso.onView(ViewMatchers.withId(R.id.number_input_2)).perform(ViewActions.typeText("3"));
        Espresso.onView(ViewMatchers.withId(R.id.add_button)).perform(ViewActions.click());
        Assert.assertEquals(6, presenter.result, 2);
        Assert.assertEquals(MathOperationEnum.ADD, presenter.op);
    }

    @Test
    public void submitSubtract() {
        MockPresenter presenter = new MockPresenter();
        presenter.setView(activityRule.getActivity());
        activityRule.getActivity().setPresenter(presenter);

        Espresso.onView(ViewMatchers.withId(R.id.number_input_1)).perform(ViewActions.typeText("3"));
        Espresso.onView(ViewMatchers.withId(R.id.number_input_2)).perform(ViewActions.typeText("2"));
        Espresso.onView(ViewMatchers.withId(R.id.subtract_button)).perform(ViewActions.click());
        Assert.assertEquals(1, presenter.result, 2);
        Assert.assertEquals(MathOperationEnum.SUBTRACT, presenter.op);
    }

    @Test
    public void submitMultiply() {
        MockPresenter presenter = new MockPresenter();
        presenter.setView(activityRule.getActivity());
        activityRule.getActivity().setPresenter(presenter);

        Espresso.onView(ViewMatchers.withId(R.id.number_input_1)).perform(ViewActions.typeText("3"));
        Espresso.onView(ViewMatchers.withId(R.id.number_input_2)).perform(ViewActions.typeText("3"));
        Espresso.onView(ViewMatchers.withId(R.id.multiply_button)).perform(ViewActions.click());
        Assert.assertEquals(9, presenter.result, 2);
        Assert.assertEquals(MathOperationEnum.MULTIPLY, presenter.op);
    }

    @Test
    public void submitDivide() {
        MockPresenter presenter = new MockPresenter();
        presenter.setView(activityRule.getActivity());
        activityRule.getActivity().setPresenter(presenter);

        Espresso.onView(ViewMatchers.withId(R.id.number_input_1)).perform(ViewActions.typeText("30"));
        Espresso.onView(ViewMatchers.withId(R.id.number_input_2)).perform(ViewActions.typeText("5"));
        Espresso.onView(ViewMatchers.withId(R.id.divide_button)).perform(ViewActions.click());
        Assert.assertEquals(6, presenter.result, 2);
        Assert.assertEquals(MathOperationEnum.DIVIDE, presenter.op);
    }

    @Test
    public void echoReceived() {
        MockPresenter presenter = new MockPresenter();
        MainActivity activity = activityRule.getActivity();
        BroadcastReceiver receiver = activity.getReceiver();

        presenter.setView(activity);
        activity.setPresenter(presenter);

        Intent response = new Intent();
        response.setAction(Constants.ECHO_RESPONSE_ACTION);
        response.putExtra(Constants.ECHO_CONTENT, "content data");
        response.putExtra(Constants.ECHO_COUNT, 1L);
        receiver.onReceive(activity, response);

        Assert.assertEquals(1L, presenter.count);
        Assert.assertEquals("content data", presenter.content);
    }

    @Test
    public void echoError() {
        MockPresenter presenter = new MockPresenter();
        MainActivity activity = activityRule.getActivity();
        presenter.setView(activity);
        activity.setPresenter(presenter);
        BroadcastReceiver receiver = activity.getReceiver();

        Intent response = new Intent();
        response.setAction(Constants.ECHO_ERROR_ACTION);
        response.putExtra(Constants.ERROR_MESSAGE, "error message");
        receiver.onReceive(activity, response);

        Assert.assertEquals("error message", presenter.errorMessage);
    }

    class MockPresenter implements MainContract.Presenter {

        MainContract.View view;
        String data;
        long count;
        String content;
        String errorMessage;
        float first, second, result;
        MathOperationEnum op;

        @Override
        public void setView(MainContract.View view) {
            this.view = view;
        }

        @Override
        public void submit(String data) {
            this.data = data;
        }

        @Override
        public void submitAdd(float first, float second) {
            this.first = first;
            this.second = second;
            this.result = first + second;
            this.op = MathOperationEnum.ADD;
        }

        @Override
        public void submitSubtract(float first, float second) {
            this.first = first;
            this.second = second;
            this.result = first - second;
            this.op = MathOperationEnum.SUBTRACT;
        }

        @Override
        public void submitMultiply(float first, float second) {
            this.first = first;
            this.second = second;
            this.result = first * second;
            this.op = MathOperationEnum.MULTIPLY;
        }

        @Override
        public void submitDivide(float first, float second) {
            this.first = first;
            this.second = second;
            this.result = first / second;
            this.op = MathOperationEnum.DIVIDE;
        }

        @Override
        public void onMathResultReceived(float first, float second, float result, MathOperationEnum operation) {
            this.first = first;
            this.second = second;
            this.op = operation;
            switch (op) {
                case ADD:
                    this.result = first + second;
                    break;
                case SUBTRACT:
                    this.result = first - second;
                    break;
                case DIVIDE:
                    this.result = first / second;
                    break;
                case MULTIPLY:
                    this.result = first * second;
                    break;
            }
        }

        @Override
        public void onEchoReceived(long count, String content) {
            this.count = count;
            this.content = content;
        }

        @Override
        public void onEchoError(String message) {
            this.errorMessage = message;
        }

        @Override
        public void onMathError(String message) {
            this.errorMessage = message;
        }
    }
}