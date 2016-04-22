package com.github.jackkell.mimicryproject;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.github.jackkell.mimicryproject.entity.DatabaseOpenHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

public class DatabaseTest extends AndroidTestCase {
    private DatabaseOpenHelper db;

    @Override
    protected void setUp() throws Exception {
        Log.d("test","setup");
        System.out.println("Setting things up");
    }

    @Test
    public void testDB() {
        System.out.println("Running dbTest()");
    }
}
