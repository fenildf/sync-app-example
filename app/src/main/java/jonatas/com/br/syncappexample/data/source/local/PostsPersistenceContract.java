package jonatas.com.br.syncappexample.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by jonatas on 20/04/16.
 */
public class PostsPersistenceContract {

    public PostsPersistenceContract() {}

    public static abstract class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        public static final String COLUMN_NAME_ENTRY_ID = "post_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "description";
    }
}
