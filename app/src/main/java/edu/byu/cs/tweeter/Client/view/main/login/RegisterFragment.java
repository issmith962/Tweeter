package edu.byu.cs.tweeter.Client.view.main.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Objects;

import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import byu.edu.cs.tweeter.shared.utils.ByteArrayUtils;
import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LoginAttemptTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.RegisterAttemptTask;
import edu.byu.cs.tweeter.Client.view.main.MainActivity;
import edu.byu.cs.tweeter.R;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment implements LoginPresenter.View, RegisterAttemptTask.RegisterAttemptObserver, LoginAttemptTask.LoginAttemptObserver {
    private LoginPresenter presenter;
    private ImageView profilePicture;
    private String imageURL;
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private Button registerButton;
    private EditText mAliasField;
    private EditText mPasswordField;
    private EditText mNameField;
    private String mAlias;
    private String mPassword;
    private String mName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageURL = null;
        imageUri = null;
        mAlias = "";
        mPassword = "";
        mName = "";
        ((MainActivity) getActivity()).setState(3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        presenter = new LoginPresenter(this);
        profilePicture = view.findViewById(R.id.profile_picture);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "upload photo!", Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(gallery, "Select picture"), PICK_IMAGE);
            }
        });
        mNameField = view.findViewById(R.id.register_name);
        mNameField.setText("");
        mNameField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        mAliasField = view.findViewById(R.id.register_alias);
        mAliasField.setText("");
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

        mPasswordField = view.findViewById(R.id.register_password);
        mPasswordField.setText("");
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
        registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (mName != null && mAlias != null && mPassword != null && imageUri != null) {
                    RegisterAttemptTask registerAttemptTask = new RegisterAttemptTask(presenter, RegisterFragment.this);
                    String imageByteString = null;
                    if (imageUri != null) {
                        try {
                            InputStream iStream = getActivity().getContentResolver().openInputStream(imageUri);
                            byte[] imageData = ByteArrayUtils.bytesFromInputStream(iStream);
                            imageByteString = Base64.getEncoder().encodeToString(imageData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        RegisterRequest registerRequest = new RegisterRequest(mName, mAlias, mPassword, imageByteString);
                        registerAttemptTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, registerRequest);
                    }
                    else {
                        RegisterRequest registerRequest = new RegisterRequest(mName, mAlias, mPassword);
                        registerAttemptTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, registerRequest);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Context applicationContext = MainActivity.getContextOfApplication();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), imageUri);
                profilePicture.setImageBitmap(bitmap);
                scaleImage(profilePicture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerAttempted(RegisterResponse registerResponse) {
        if (registerResponse.getNewUser() == null) {
            Toast.makeText(getActivity(), "Registration failed!", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginAttemptTask task = new LoginAttemptTask(presenter, this);
            LoginRequest request = new LoginRequest(registerResponse.getNewUser().getAlias(), registerResponse.getPassword());
            hideKeyboardFrom(Objects.requireNonNull(getContext()), Objects.requireNonNull(getView()));
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        }
        // after user has registered..
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    @Override
    public void loginAttempted(LoginResponse loginResponse) {
        if (loginResponse.getAuthToken() == null) {
            Toast.makeText(getContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            ((MainActivity) getActivity()).setState(0);
            ((MainActivity) Objects.requireNonNull(getActivity())).updateUser();
            ((MainActivity) getActivity()).loadUserDisplay();
            ((MainActivity) getActivity()).startFeedState();
        }
    }

    private void scaleImage(ImageView view) throws NoSuchElementException {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
            bitmap = Ion.with(view).getBitmap();
        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(80);
        Log.i("Test", "original width = " + Integer.toString(width));
        Log.i("Test", "original height = " + Integer.toString(height));
        Log.i("Test", "bounding = " + Integer.toString(bounding));

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + Float.toString(xScale));
        Log.i("Test", "yScale = " + Float.toString(yScale));
        Log.i("Test", "scale = " + Float.toString(scale));

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        Log.i("Test", "scaled width = " + Integer.toString(width));
        Log.i("Test", "scaled height = " + Integer.toString(height));

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

        Log.i("Test", "done");
    }

    private int dpToPx(int dp) {
        float density = getActivity().getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

//    private byte[] getBytes(InputStream inputStream) throws IOException {
//        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];
//
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            byteBuffer.write(buffer, 0, len);
//        }
//        return byteBuffer.toByteArray();
//    }

//    private Bitmap getImageBitmap(String url) {
//        Bitmap bm = null;
//        try {
//            URL aURL = new URL(url);
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//            Log.e(TAG, "Error getting bitmap", e);
//        }
//        return bm;
//    }





}