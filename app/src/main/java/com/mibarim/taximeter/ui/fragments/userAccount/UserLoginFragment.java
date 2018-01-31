package com.mibarim.taximeter.ui.fragments.userAccount;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
import com.mibarim.taximeter.favorite.FavoritePlaceActivity;
import com.mibarim.taximeter.models.ApiResponse;
import com.mibarim.taximeter.models.UserInfoModel;
import com.mibarim.taximeter.services.UserInfoService;
import com.mibarim.taximeter.util.SafeAsyncTask;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Arya on 1/14/2018.
 */

public class UserLoginFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.input_mobile_login)
    EditText phoneNumber;
    @Bind(R.id.input_password_login)
    EditText password;
    @Bind(R.id.btn_login)
    Button login;
    @Bind(R.id.link_sign_up)
    TextView signUp;
    @Bind(R.id.login_errors)
    TextView loginErrors;
    @Bind(R.id.login_scroll)
    ScrollView scrollView;

    @Inject
    UserInfoService userInfoService;
    UserInfoModel userInfoModel;
    ApiResponse apiResponse;

    String checkPhoneNumber, checkPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        userInfoModel = new UserInfoModel();
        apiResponse = new ApiResponse();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.user_login_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    private boolean checkInfo() {
        if (checkPassword.matches("") || checkPhoneNumber.matches("")) {
            loginErrors.append(getString(R.string.fill_all_field) + "\n");
            return false;
        } else {
            if (checkPhoneNumber.startsWith("09")) {
                if (checkPhoneNumber.length() == 11) {
                    if (checkPassword.length() < 6) {
                        loginErrors.append(getString(R.string.wrong_password) + "\n");
                        return false;
                    } else
                        return true;
                } else {
                    loginErrors.append(getString(R.string.wrong_phone_number) + "\n");
                    return false;
                }
            } else if (checkPhoneNumber.startsWith("+989")) {
                if (checkPhoneNumber.length() == 13) {
                    if (checkPassword.length() < 6) {
                        loginErrors.append(getString(R.string.wrong_password) + "\n");
                        return false;
                    } else
                        return true;
                } else {
                    loginErrors.append(getString(R.string.wrong_phone_number) + "\n");
                    return false;
                }
            } else {
                loginErrors.append(getString(R.string.wrong_phone_number) + "\n");
                return false;
            }
        }
    }


    private void sendUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.logging_in));
        progressDialog.show();
        userInfoModel.setPhoneNumber(checkPhoneNumber);
        userInfoModel.setPassword(checkPassword);
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                apiResponse = userInfoService.sendUserInfoLogin(userInfoModel);
                return true;
            }

            @Override
            protected void onSuccess(Boolean aBoolean) throws Exception {
                super.onSuccess(aBoolean);
                progressDialog.dismiss();
                switch (apiResponse.StatusCode) {
                    case 200:
                        startFavoritePlace();
                        break;
                    case 404:
                        loginErrors.setText(getString(R.string.no_account));
                        scrollView.scrollTo((int) loginErrors.getX(), (int) loginErrors.getY());
                        break;
                    case 401:
                        loginErrors.setText(getString(R.string.wrong_password_for_account));
                        scrollView.scrollTo((int) loginErrors.getX(), (int) loginErrors.getY());
                        break;
                }
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                progressDialog.dismiss();
            }
        }.execute();
    }

    private void startFavoritePlace() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user_token", Context.MODE_PRIVATE);
        preferences.edit()
                .putString("token", "Bearer " + apiResponse.Messages.get(0))
                .apply();
        startActivity(new Intent(getActivity(), FavoritePlaceActivity.class));
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        loginErrors.setText("");
        switch (v.getId()) {
            case R.id.btn_login:
                checkPhoneNumber = phoneNumber.getText().toString();
                checkPassword = password.getText().toString();
                if (checkInfo())
                    sendUserInfo();
                else
                    scrollView.scrollTo((int) loginErrors.getX(), (int) loginErrors.getY());
                break;
            case R.id.link_sign_up:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.push_left_in, R.animator.push_left_out)
                        .replace(R.id.user_account, new UserSignUpFragment())
                        .commit();
                break;
        }
    }
}
