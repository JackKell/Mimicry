package com.github.jackkell.mimicryproject.databaseobjects;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

//An interface that is attached to any object that needs to be stored within the database
public interface DatabaseStorable {

    //Each DatabaseStorable object needs an addToDatabase function.  This allows the object to be stored in the database
    void addToDatabase(SQLiteDatabase db);
    //Each DatabaseStorable object needs a removeFromDatabase function.  THis allows the object to be removed from the database
    void removeFromDatabase(SQLiteDatabase db);
    //Grabs the ID from the database for the object.  This helps finding other database entries easier.
    String getID(SQLiteDatabase db);
}
