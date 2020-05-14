package com.unesc.artesmarciaisapp.database.dao;

import android.database.sqlite.SQLiteDatabase;
import com.unesc.artesmarciaisapp.database.helper.DBOpenHelper;

public class AbstractDAO {

    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected final void Open() {
        db = db_helper.getWritableDatabase();
    }

    protected final void Close() {
        db_helper.close();
    }

}
