package com.example.vlada.geomusicandroidclient.events;

import com.example.vlada.geomusicandroidclient.api.model.Record;

public class PlayRecordEvent {
    private final Record record;


    public PlayRecordEvent(Record record) {
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }
}
