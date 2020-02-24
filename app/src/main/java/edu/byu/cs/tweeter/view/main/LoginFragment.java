package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginAttemptTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class LoginFragment extends Fragment implements LoginPresenter.View, LoginAttemptTask.LoginAttemptObserver {

    private LoginPresenter presenter;
    private EditText mAliasField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private String mAlias;
    private String mPassword;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlias = "";
        mPassword = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false); {
            presenter = new LoginPresenter(this);
            mAliasField = view.findViewById(R.id.alias);
            mAliasField.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mAlias = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });

            mPasswordField = view.findViewById(R.id.password);
            mPasswordField.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mPassword = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
            mSignInButton = (Button) view.findViewById(R.id.sign_in_button);
            mSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    signInButtonClicked();
                }
            });
            return view;
        }
    }

    private void signInButtonClicked() {
        LoginAttemptTask task = new LoginAttemptTask(presenter, this);
        LoginRequest request = new LoginRequest(mAlias, mPassword);
        task.execute(request);
    }

    @Override
    public void loginAttempted(LoginResponse loginResponse) {
        if (loginResponse.getAuthToken() == null) {
            Toast.makeText(getContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            ((MainActivity) Objects.requireNonNull(getActivity())).updateUser();
            ((MainActivity) getActivity()).loadUserDisplay();
            ((MainActivity) getActivity()).startFeedState();
        }
    }
}