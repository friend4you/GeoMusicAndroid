package com.example.vlada.geomusicandroidclient;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class AccountManager extends AppCompatActivity {

    private Button changePassword;
    private Button confirm;
    private Button selectCategories;
    private EditText userName;
    private EditText email;
    private ImageView userImage;
    private Uri mImageCaptureUri;

    private final int REQUEST_CROP_ICON = 10;
    private final int REQUEST_TAKEN_IMAGE = 11;
    private final int CAMERA_TYPE = 12;
    private final int GALLERY_TYPE = 13;
    private final int PERMISSION_EXTERNAL_STORAGE = 22;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appBarLayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account manager");
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        userImage = (ImageView) findViewById(R.id.linearLayout3);
        registerForContextMenu(userImage);
        userName = (EditText) findViewById(R.id.account_userName);
        userName.setText(Application.getSharedInstance().getStorage().getUserNickname());
        email = (EditText) findViewById(R.id.account_email);
        email.setText(Application.getSharedInstance().getStorage().getUserEmail());
        changePassword = (Button) findViewById(R.id.account_changePassword);
        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManager.this, ChangePassword.class);
            startActivity(intent);
        });
        confirm = (Button) findViewById(R.id.account_confirm);
        confirm.setOnClickListener(v -> {
        });
        selectCategories = (Button) findViewById(R.id.account_selectCategories);
        selectCategories.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManager.this, SelectCategory.class);
            startActivity(intent);
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_image_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.selectFromGalary:
                Intent pickImageGallery = new Intent(AccountManager.this, PickImageActivity.class);
                pickImageGallery.putExtra("SELECT_TYPE", GALLERY_TYPE);
                startActivityForResult(pickImageGallery, REQUEST_TAKEN_IMAGE);
                break;
            case R.id.selectFromCamera:
                Intent pickImageCamera = new Intent(AccountManager.this, PickImageActivity.class);
                pickImageCamera.putExtra("SELECT_TYPE", CAMERA_TYPE);
                startActivityForResult(pickImageCamera, REQUEST_TAKEN_IMAGE);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case REQUEST_TAKEN_IMAGE:
                String picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);

                break;
            case REQUEST_CROP_ICON:
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                userImage.setImageBitmap(selectedBitmap);
                break;
        }


    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = FileProvider.getUriForFile(AccountManager.this, "com.example.vlada.geomusicandroidclient.fileprovider", f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_CROP_ICON);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException error) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
