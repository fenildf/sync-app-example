package jonatas.com.br.syncappexample.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import jonatas.com.br.syncappexample.data.Post;
import jonatas.com.br.syncappexample.data.wrappers.Posts;

/**
 * Created by jonatas on 20/04/16.
 */

public interface PostsDataSource {
    interface GetPostCallback {

        void onPostLoaded(Post post);

        void onDataNotAvailable();
    }

    @Nullable
    Posts getPosts();

    @Nullable
    Post getPost(@NonNull String postId);

    void refreshPosts();

}
