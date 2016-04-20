package jonatas.com.br.syncappexample.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import jonatas.com.br.syncappexample.data.Post;
import jonatas.com.br.syncappexample.data.source.PostsDataSource;
import jonatas.com.br.syncappexample.data.wrappers.Posts;

/**
 * Created by jonatas on 20/04/16.
 */
public class PostsRemoteDataSource implements PostsDataSource {

    private static PostsRemoteDataSource INSTANCE;

    public static PostsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostsRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private PostsRemoteDataSource() {}

    @Nullable
    @Override
    public Posts getPosts() {

        // TODO make request here!

        return new Posts();
    }

    @Nullable
    @Override
    public Post getPost(@NonNull String postId) {

        // TODO make request here!

        return null;
    }

    @Override
    public void refreshPosts() {
        // Not required because the {@link PostsRepository} handles the logic of refreshing the
        // posts from all the available data sources.
    }
}
