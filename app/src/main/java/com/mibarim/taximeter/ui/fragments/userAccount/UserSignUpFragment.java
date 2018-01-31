package com.mibarim.taximeter.ui.fragments.userAccount;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.mibarim.taximeter.BootstrapApplication;
import com.mibarim.taximeter.R;
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

public class UserSignUpFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.input_mobile_sign_up)
    EditText phoneNumber;
    @Bind(R.id.input_password_sign_up)
    EditText password;
    @Bind(R.id.confirm_password)
    EditText confirmPassword;
    @Bind(R.id.btn_sign_up)
    Button signUp;
    @Bind(R.id.link_login)
    TextView login;
    @Bind(R.id.sign_up_errors)
    TextView signUpErrors;
    @Bind(R.id.sign_up_scroll)
    ScrollView scrollView;

    @Inject
    UserInfoService userInfoService;
    UserInfoModel userInfoModel;
    ApiResponse apiResponse;

    String checkPhoneNumber, checkPassword, checkConfirmPassword;

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
        View view = localInflater.inflate(R.layout.user_sign_up_layout, container, false);
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
        if (checkConfirmPassword.matches("") || checkPassword.matches("") || checkPhoneNumber.matches("")) {
            signUpErrors.append(getString(R.string.fill_all_field) + "\n");
            return false;
        } else {
            if (checkPhoneNumber.startsWith("09")) {
                if (checkPhoneNumber.length() == 11) {
                    if (checkPassword.length() < 6) {
                        signUpErrors.append(getString(R.string.not_enough_character) + "\n");
                        return false;
                    } else if (checkPassword.equals(checkConfirmPassword))
                        return true;
                    else {
                        signUpErrors.append(getString(R.string.confirm_password) + "\n");
                        return false;
                    }
                } else {
                    signUpErrors.append(getString(R.string.wrong_phone_number) + "\n");
                    return false;
                }
            } else if (checkPhoneNumber.startsWith("+989")) {
                if (checkPhoneNumber.length() == 13) {
                    if (checkPassword.length() < 6) {
                        signUpErrors.append(getString(R.string.not_enough_character) + "\n");
                        return false;
                    } else if (checkPassword.equals(checkConfirmPassword))
                        return true;
                    else {
                        signUpErrors.append(getString(R.string.confirm_password) + "\n");
                        return false;
                    }
                } else {
                    signUpErrors.append(getString(R.string.wrong_phone_number) + "\n");
                    return false;
                }
            } else {
                signUpErrors.append(getString(R.string.wrong_phone_number) + "\n");
                return false;
            }
        }
    }

    private void sendUserInfo() {
        final UserIdentityCodeFragment fragment = new UserIdentityCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phone_number", checkPhoneNumber);
        fragment.setArguments(bundle);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.creating_account));
        progressDialog.show();
        userInfoModel.setPhoneNumber(checkPhoneNumber);
        userInfoModel.setPassword(checkPassword);
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                apiResponse = userInfoService.sendUserInfoSignUp(userInfoModel);
                return true;
            }

            @Override
            protected void onSuccess(Boolean aBoolean) throws Exception {
                super.onSuccess(aBoolean);
                progressDialog.dismiss();
                if (apiResponse.StatusCode == 200)
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.push_left_in, R.animator.push_left_out)
                            .replace(R.id.user_account, fragment)
                            .commit();
                else if (apiResponse.StatusCode == 401) {
                    signUpErrors.append(getString(R.string.phone_number_signed_up));
                    scrollView.scrollTo((int) signUpErrors.getX(), (int) signUpErrors.getY());
                } else
                    Toast.makeText(getActivity(), "مشکل در ثبت نام", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);
                Toast.makeText(getActivity(), "مشکل در ثبت نام", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        signUpErrors.setText("");
        switch (v.getId()) {
            case R.id.btn_sign_up:
                checkPhoneNumber = phoneNumber.getText().toString();
                checkPassword = password.getText().toString();
                checkConfirmPassword = confirmPassword.getText().toString();
                if (checkInfo())
                    sendUserInfo();
                else
                    scrollView.scrollTo((int) signUpErrors.getX(), (int) signUpErrors.getY());
                break;
            case R.id.link_login:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.push_left_in, R.animator.push_left_out)
                        .replace(R.id.user_account, new UserLoginFragment())
                        .commit();
                break;
        }
    }
}
