package com.github.jackkell.mimicryproject;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseStorable {

    void addToDatabase(SQLiteDatabase db);
    void removeFromDatabase(SQLiteDatabase db);
    String getID(SQLiteDatabase db);
}
