package com.example.vlada.geomusicandroidclient.events;

import com.example.vlada.geomusicandroidclient.api.model.Record;

public class ShowRecordTitleEvent {
    private final Record record;

    public ShowRecordTitleEvent(Record record) {
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }
}
