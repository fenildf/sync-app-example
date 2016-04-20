package jonatas.com.br.syncappexample.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jonatas on 20/04/16.
 */
public class PostsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Posts.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PostsPersistenceContract.PostEntry.TABLE_NAME + " (" +
                    PostsPersistenceContract.PostEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    PostsPersistenceContract.PostEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                    " )";

    public PostsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
