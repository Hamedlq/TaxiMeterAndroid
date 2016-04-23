package com.mibarim.main.ui.fragments.userInfoFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.AddMapActivity;
import com.mibarim.main.activities.UserInfoActivity;
import com.mibarim.main.activities.UserPersonalActivity;
import com.mibarim.main.models.PersonalInfoModel;
import com.mibarim.main.models.enums.PricingOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class UserPersonFragment extends Fragment {

    private static final int USER_REQUEST_CAMERA = 1;
    private static final int USER_SELECT_FILE = 2;
    private LinearLayout layout;

    @Bind(R.id.user_image)
    protected BootstrapCircleThumbnail user_image;
    @Bind(R.id.gender)
    protected TextView gender;
    @Bind(R.id.user_mobile)
    protected TextView user_mobile;
    @Bind(R.id.name)
    protected EditText name;
    @Bind(R.id.family)
    protected EditText family;
    @Bind(R.id.email)
    protected EditText email;
    @Bind(R.id.national_code)
    protected EditText national_code;
    @Bind(R.id.go_on)
    protected BootstrapButton go_on;


    public UserPersonFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) inflater.inflate(R.layout.fragment_user_person, container, false);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        PersonalInfoModel userInfo = null;
        if (getActivity() instanceof UserInfoActivity) {
            userInfo = ((UserInfoActivity) getActivity()).getPersonalInfo();
            go_on.setVisibility(View.GONE);
        } else if (getActivity() instanceof UserPersonalActivity) {
            userInfo = ((UserPersonalActivity) getActivity()).getPersonalInfo();
            go_on.setVisibility(View.VISIBLE);
            go_on.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ((UserPersonalActivity) getActivity()).saveUserInfo();
                        return true;
                    }
                    return false;
                }
            });
        }
        if (userInfo != null) {
            if (userInfo.Gender.equals("1")) {
                gender.setText(R.string.man);
            } else {
                gender.setText(R.string.woman);
            }
            user_mobile.setText(userInfo.Mobile);
            name.setText(userInfo.Name);
            family.setText(userInfo.Family);
            email.setText(userInfo.Email);
            national_code.setText(userInfo.NationalCode);
            if (userInfo.Base64UserPic != null && !userInfo.Base64UserPic.equals("")) {
                byte[] decodedString = Base64.decode(userInfo.Base64UserPic, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                user_image.setImageBitmap(decodedByte);
            }
        }
        gender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    selectGender();
                    return true;
                }
                return false;
            }
        });

        user_image.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View v, MotionEvent event) {
                                              if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                  selectImage();
                                              }
                                              return true;
                                          }
                                      }
        );
    }

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.camera), getString(R.string.fromGallery), getString(R.string.later)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(intent, USER_REQUEST_CAMERA);
                } else if (items[item].equals(getString(R.string.fromGallery))) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    getActivity().startActivityForResult(
                            Intent.createChooser(intent, getString(R.string.choose_pic)),
                            USER_SELECT_FILE);
                } else if (items[item].equals(getString(R.string.later))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void setUserImage(Bitmap bm) {
        user_image.setImageBitmap(bm);
    }

    public PersonalInfoModel getUserInfo() {
        PersonalInfoModel model = new PersonalInfoModel();
        if (gender.getText().equals(R.string.man)) {
            model.Gender = "1";
        } else {
            model.Gender = "2";
        }
        model.Name = name.getText().toString();
        model.Family = family.getText().toString();
        model.Email = email.getText().toString();
        model.NationalCode = national_code.getText().toString();
        return model;
    }

    private void selectGender() {
        final CharSequence[] items = {getString(R.string.man), getString(R.string.woman)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.man))) {
                    gender.setText(getString(R.string.man));
                    dialog.dismiss();
                } else if (items[item].equals(getString(R.string.woman))) {
                    gender.setText(getString(R.string.woman));
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}
