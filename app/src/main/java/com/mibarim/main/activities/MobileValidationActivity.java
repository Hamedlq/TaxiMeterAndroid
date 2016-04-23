package com.mibarim.main.activities;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.core.Constants;
import com.mibarim.main.models.ApiResponse;
import com.mibarim.main.models.TokenResponse;
import com.mibarim.main.services.AuthenticateService;
import com.mibarim.main.services.RegisterService;
import com.mibarim.main.ui.HandleApiMessages;
import com.mibarim.main.ui.TextWatcherAdapter;
import com.mibarim.main.util.SafeAsyncTask;
import com.mibarim.main.util.Toaster;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import timber.log.Timber;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

/**
 * Created by Hamed on 2/27/2016.
 */
public class MobileValidationActivity extends AccountAuthenticatorActivity {
    private AccountManager accountManager;

    /**
     * PARAM_GRANT_TYPE
     */
    public static final String PARAM_GRANT_TYPE = "password";

    /**
     * PARAM_REPONSE_TYPE
     */
    public static final String PARAM_REPONSE_TYPE = "token";


    @Inject
    AuthenticateService authenticateService;
    @Inject
    RegisterService registerService;
    @Inject
    Bus bus;


    @Bind(R.id.et_regMobile)
    protected AutoCompleteTextView regMobileText;
    @Bind(R.id.et_regPassword)
    protected EditText regPasswordText;
    @Bind(R.id.b_register)
    protected Button registerButton;

    private final TextWatcher watcher = validationTextWatcher();

    private SafeAsyncTask<Boolean> registerTask;

    private String regMobile;

    private String regPassword;

    private String authToken;

    private String authTokenType;

    private TokenResponse loginResponse;

    private ApiResponse response;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        BootstrapApplication.component().inject(this);

        accountManager = AccountManager.get(this);

        setContentView(R.layout.register_activity);

        ButterKnife.bind(this);

        regPasswordText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && registerButton.isEnabled()) {
                    handleRegister(registerButton);
                    return true;
                }
                return false;
            }
        });

        regPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(final TextView v, final int actionId,
                                          final KeyEvent event) {
                if (actionId == IME_ACTION_DONE && registerButton.isEnabled()) {
                    handleRegister(registerButton);
                    return true;
                }
                return false;
            }
        });

        regMobileText.addTextChangedListener(watcher);
        regPasswordText.addTextChangedListener(watcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        updateUIWithValidation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getText(R.string.please_wait));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(final DialogInterface dialog) {
                if (registerTask != null) {
                    registerTask.cancel(true);
                }
            }
        });
        return dialog;
    }

    private TextWatcher validationTextWatcher() {
        return new TextWatcherAdapter() {
            public void afterTextChanged(final Editable gitDirEditText) {
                updateUIWithValidation();
            }

        };
    }

    private void updateUIWithValidation() {
        final boolean populated = populated(regMobileText) && populated(regPasswordText);
        registerButton.setEnabled(populated);
    }

    private boolean populated(final EditText editText) {
        return editText.length() > 0;
    }

    public void handleRegister(final View view) {
        if (registerTask != null) {
            return;
        }
        regMobile = regMobileText.getText().toString();
        regPassword = regPasswordText.getText().toString();
        showProgress();

        registerTask = new SafeAsyncTask<Boolean>() {
            public Boolean call() throws Exception {

                /*User loginResponse = bootstrapService.authenticate(mobile, password,PARAM_GRANT_TYPE,PARAM_REPONSE_TYPE);
                token = loginResponse.getSessionToken();*/
                response = registerService.register(regMobile, regPassword);
                if (response.Errors.size() == 0 && response.Status.equals("OK")) {
                    return true;
                }
/*
                    loginResponse = authenticateService.authenticate(regMobile, regPassword,PARAM_GRANT_TYPE,PARAM_REPONSE_TYPE);
                    if(loginResponse!=null && loginResponse.access_token!=null && !loginResponse.access_token.equals("") ){
                        authToken = loginResponse.access_token;
                        return true;
                    }else if(loginResponse.error!=null || loginResponse.error!=""){
                        //Toaster.showLong(RegisterActivity.this, loginResponse.error, R.drawable.toast_error);
                        return false;
                    }
                }else
                {
                    return false;
                }
*/
                return false;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                // Retrofit Errors are handled inside of the {
                if (!(e instanceof RetrofitError)) {
                    final Throwable cause = e.getCause() != null ? e.getCause() : e;
                    if (cause != null) {
                        Toaster.showLong(MobileValidationActivity.this, cause.getMessage());
                    }
                }
            }

            @Override
            public void onSuccess(final Boolean authSuccess) {
                onRegisterResult(authSuccess);
            }

            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                registerTask = null;
            }
        };
        registerTask.execute();
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    public void onRegisterResult(final boolean result) {
        if (result) {
            finishLogin();
        } else {
            Timber.d("onAuthenticationResult: failed to authenticate");
            new HandleApiMessages(MobileValidationActivity.this, response).showMessages();
        }
    }

    protected void finishLogin() {
        final Intent intent = new Intent();
        intent.putExtra(Constants.Auth.REG_MOBILE, regMobile);
        intent.putExtra(Constants.Auth.REG_PASSWORD, regPassword);
        setResult(RESULT_OK, intent);
        finish();
    }

}
