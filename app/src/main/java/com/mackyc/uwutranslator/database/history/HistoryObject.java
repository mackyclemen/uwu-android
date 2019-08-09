package com.mackyc.uwutranslator.database.history;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mackyc.uwutranslator.translators.uwuTranslator;

import java.util.Date;

@Entity(tableName = "history_table")
public final class HistoryObject {

    @PrimaryKey
    @NonNull
    private Date timestamp;

    private String raw;
    private String translated;

    public HistoryObject(@NonNull Date timestamp, String raw, String translated) {
        this.timestamp = timestamp;
        this.raw = raw;
        this.translated = translated;
    }

    public HistoryObject(String input) {
        timestamp = new Date();

        raw = input;
        translated = uwuTranslator.translate(input);
    }

    @NonNull
    public Date getTimestamp() {
        return timestamp;
    }

    public String getRaw() {
        return raw;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTimestamp(@NonNull Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }
}
