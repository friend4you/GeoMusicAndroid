package com.example.vlada.geomusicandroidclient.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vlada.geomusicandroidclient.Application;
import com.example.vlada.geomusicandroidclient.MainActivity;
import com.example.vlada.geomusicandroidclient.MusicService;
import com.example.vlada.geomusicandroidclient.R;
import com.example.vlada.geomusicandroidclient.SearchRecordAdapter;
import com.example.vlada.geomusicandroidclient.api.model.Record;
import com.example.vlada.geomusicandroidclient.events.PlayRecordEvent;
import com.example.vlada.geomusicandroidclient.events.ShowRecordTitleEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.example.vlada.geomusicandroidclient.MainActivity.currentRecord;


public class FavouritesFragment extends Fragment {

    public final static int PERMISSION_REQUEST_CODE = 555;
    private List<Record> recordList = new ArrayList<Record>();
    private int lastRecordId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites_records, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.favorites_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recordList = Application.getSharedInstance().getStorage().getRecords();
        SearchRecordAdapter adapter = new SearchRecordAdapter(recordList, record -> {
            EventBus.getDefault().post(new ShowRecordTitleEvent(record));
            EventBus.getDefault().post(new PlayRecordEvent(record));
        });

        adapter.notifyDataSetChanged();

        if (adapter.getItemCount() != 0) {
//            showSongTitle(MusicService.activeRecord);
        }

        recycler.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    /*    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
                    },
                    PERMISSION_REQUEST_CODE);
        } else {

            lastRecordId = Application.getSharedInstance().getStorage().getLastRecordId();


            if (lastRecordId != -1 && currentRecord != null) {

                MusicService.activeRecord = currentRecord;
            }
            ((MainActivity) getActivity()).setupPlayerStrip(currentRecord);
        }*/
    }

    public void getRecords() {
        recordList.clear();
        String[] columns = {MediaStore.Audio.AudioColumns._ID, MediaStore.Audio.AudioColumns.ARTIST, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.DATA};
        String where = MediaStore.Audio.AudioColumns.IS_MUSIC + " <> 0";
        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, where, null, null);





    }
}
