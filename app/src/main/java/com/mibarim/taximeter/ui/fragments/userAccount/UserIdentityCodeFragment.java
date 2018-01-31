package com.mibarim.taximeter.ui.fragments.userAccount;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class UserIdentityCodeFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.identity_number)
    EditText identityNumber;
    @Bind(R.id.btn_identity)
    AppCompatButton btnIdentity;
    @Bind(R.id.identity_error)
    TextView identityError;
    @Inject
    UserInfoService userInfoService;
    UserInfoModel userInfoModel;
    ApiResponse apiResponse;
    String phoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        userInfoModel = new UserInfoModel();
        apiResponse = new ApiResponse();
        savedInstanceState = getArguments();
        phoneNumber = savedInstanceState.getString("phone_number");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = localInflater.inflate(R.layout.user_identity_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnIdentity.setOnClickListener(this);
    }

    private void checkIdentity() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.checking_identity_code));
        progressDialog.show();
        userInfoModel.setidentity(identityNumber.getText().toString());
        userInfoModel.setPhoneNumber(phoneNumber);
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                apiResponse = userInfoService.sendUserIdentity(userInfoModel);
                return true;
            }

            @Override
            protected void onSuccess(Boolean aBoolean) throws Exception {
                super.onSuccess(aBoolean);
                switch (apiResponse.StatusCode) {
                    case 200:
                        startFavoritePlace();
                        break;
                    case 406:
                        identityError.append(getString(R.string.wrong_identity_code) + "\n");
                        break;
                }
                progressDialog.dismiss();
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
        identityError.setText("");
        checkIdentity();
    }
}
