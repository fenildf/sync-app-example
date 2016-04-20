package jonatas.com.br.syncappexample.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import jonatas.com.br.syncappexample.data.Post;
import jonatas.com.br.syncappexample.data.source.local.PostsPersistenceContract.PostEntry;
import jonatas.com.br.syncappexample.data.source.PostsDataSource;
import jonatas.com.br.syncappexample.data.wrappers.Posts;
/**
 * Created by jonatas on 20/04/16.
 */
public class PostsLocalDataSource implements PostsDataSource {

    private static PostsLocalDataSource INSTANCE;

    private PostsDbHelper mDbHelper;

    private SQLiteDatabase mDb;

    private PostsLocalDataSource(@NonNull Context context) {
        if (context != null) {
            mDbHelper = new PostsDbHelper(context);
            mDb = mDbHelper.getWritableDatabase();
        }
    }

    public static PostsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PostsLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Nullable
    @Override
    public Posts getPosts() {
        Posts posts = new Posts();

        try {
            String[] projection = {
                    PostEntry.COLUMN_NAME_ENTRY_ID,
                    PostEntry.COLUMN_NAME_TITLE,
                    PostEntry.COLUMN_NAME_CONTENT,
            };

            Cursor c = mDb.query(PostEntry.TABLE_NAME, projection, null, null, null, null, null);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String id = c
                            .getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_ENTRY_ID));
                    String title = c
                            .getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_TITLE));
                    String content =
                            c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_CONTENT));
                    Post post = new Post(id, title, content);
                    posts.add(post);
                }
            }
            if (c != null) {
                c.close();
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return posts;
    }

    /**
     * Note: {@link GetPostCallback#onDataNotAvailable()} is fired if the {@link Post} isn't
     * found.
     */
    @Override
    public Post getPost(@NonNull String postId) {
        try {
            String[] projection = {
                    PostEntry.COLUMN_NAME_ENTRY_ID,
                    PostEntry.COLUMN_NAME_TITLE,
                    PostEntry.COLUMN_NAME_CONTENT,
            };

            String selection = PostEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] selectionArgs = {postId};

            Cursor c = mDb.query(PostEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            Post post = null;

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    String id = c
                            .getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_ENTRY_ID));
                    String title = c
                            .getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_TITLE));
                    String content =
                            c.getString(c.getColumnIndexOrThrow(PostEntry.COLUMN_NAME_CONTENT));
                    post = new Post(id, title, content);
                }
            }

            if (c != null) {
                c.close();
            }

            return post;

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void refreshPosts() {
        // Not required because the {@link PostsRepository} handles the logic of refreshing the
        // posts from all the available data sources.
    }
}
