package com.example.runningtracker.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.runningtracker.DBHandler;
//SONIA MUBASHER
//20129528

public class MyContentProvider extends ContentProvider {
    //database
    private DBHandler myDB;

    public static final int RUNS = 1;
    public static final int RUNS_ID = 2;
    public static final int DETAILS = 3;
    public static final int DETAILS_ID = 4;
    public static final int RUNS_DETAILS_ID = 5;

    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(RunningContract.AUTHORITY, RunningContract.TABLE_TOTAL_RUNS, RUNS);
        sURIMatcher.addURI(RunningContract.AUTHORITY, RunningContract.TABLE_TOTAL_RUNS + "/#", RUNS_ID);
        sURIMatcher.addURI(RunningContract.AUTHORITY, RunningContract.TABLE_DETAILS_RUN, DETAILS);
        sURIMatcher.addURI(RunningContract.AUTHORITY, RunningContract.TABLE_DETAILS_RUN + "/#", DETAILS_ID);
        sURIMatcher.addURI(RunningContract.AUTHORITY, RunningContract.TABLE_TOTAL_RUNS + "/#", RUNS_DETAILS_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case RUNS:
                id = sqlDB.insert(RunningContract.TABLE_TOTAL_RUNS,
                        null, values);

                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RunningContract.TABLE_TOTAL_RUNS + "/" + id);
            case DETAILS:
                id = sqlDB.insert(RunningContract.TABLE_DETAILS_RUN,
                        null, values);

                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RunningContract.TABLE_TOTAL_RUNS + "/" + id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }


    }

    @Override
    public boolean onCreate() {
        myDB = new DBHandler(getContext(), null, null, 1);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case RUNS:
                queryBuilder.setTables(RunningContract.TABLE_TOTAL_RUNS);
                break;
            case RUNS_ID:
                queryBuilder.appendWhere(RunningContract.COLUMN_RUN + "="
                        + uri.getLastPathSegment());
                queryBuilder.setTables(RunningContract.TABLE_TOTAL_RUNS);
                break;
            case DETAILS_ID:
                queryBuilder.appendWhere(RunningContract.COLUMN_DETAILS_ID + "="
                        + uri.getLastPathSegment());
                queryBuilder.setTables(RunningContract.TABLE_DETAILS_RUN);
                break;
            case RUNS_DETAILS_ID:
                queryBuilder.appendWhere(RunningContract.COLUMN_RUN_ID + "="
                        + uri.getLastPathSegment());
                queryBuilder.setTables(RunningContract.TABLE_DETAILS_RUN);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;
        String id = "";
        switch (uriType) {
            case RUNS:
                rowsUpdated = sqlDB.update(RunningContract.TABLE_TOTAL_RUNS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case RUNS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(RunningContract.TABLE_TOTAL_RUNS,
                            values,
                            RunningContract.COLUMN_RUN + " = " + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(RunningContract.TABLE_TOTAL_RUNS,
                            values,
                            RunningContract.COLUMN_RUN + " = " + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case DETAILS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(RunningContract.TABLE_DETAILS_RUN,
                            values,
                            RunningContract.COLUMN_DETAILS_ID + " = " + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(RunningContract.TABLE_DETAILS_RUN,
                            values,
                            RunningContract.COLUMN_DETAILS_ID + " = " + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            case RUNS_DETAILS_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(RunningContract.TABLE_DETAILS_RUN,
                            values,
                            RunningContract.COLUMN_RUN_ID + " = " + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(RunningContract.TABLE_DETAILS_RUN,
                            values,
                            RunningContract.COLUMN_RUN_ID + " = " + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


}

