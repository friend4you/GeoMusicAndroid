package com.example.vlada.geomusicandroidclient;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AccountManager extends AppCompatActivity {

    private Button changePassword;
    private Button confirm;
    private Button selectCategories;
    private EditText userName;
    private EditText email;
    private ImageView userImage;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_manager);

        userImage = (ImageView) findViewById(R.id.account_userImage);
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
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
                break;
            case R.id.selectFromCamera:
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0: // camera
                if(resultCode == RESULT_OK){

                }
                break;
            case 1: // galary
                if(resultCode ==RESULT_OK){

                }
        }
    }
}
