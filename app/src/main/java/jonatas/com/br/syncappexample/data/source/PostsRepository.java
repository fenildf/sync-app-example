package jonatas.com.br.syncappexample.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jonatas.com.br.syncappexample.data.Post;
import jonatas.com.br.syncappexample.data.wrappers.Posts;

/**
 * Created by jonatas on 20/04/16.
 */
public class PostsRepository implements PostsDataSource {

    private static PostsRepository INSTANCE = null;

    private final PostsDataSource mPostsRemoteDataSource;

    private final PostsDataSource mPostsLocalDataSource;

    private List<PostsRepositoryObserver> mObservers = new ArrayList<PostsRepositoryObserver>();

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Post> mCachedPosts;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty;

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param postsRemoteDataSource the backend data source
     * @param postsLocalDataSource  the device storage data source
     * @return the {@link PostsRepository} instance
     */
    public static PostsRepository getInstance(PostsDataSource postsRemoteDataSource,
                                              PostsDataSource postsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PostsRepository(postsRemoteDataSource, postsLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(PostsDataSource, PostsDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private PostsRepository(@NonNull PostsDataSource postsRemoteDataSource,
                            @NonNull PostsDataSource postsLocalDataSource) {
        mPostsRemoteDataSource = postsRemoteDataSource;
        mPostsLocalDataSource = postsLocalDataSource;
    }

    public void addContentObserver(PostsRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(PostsRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (PostsRepositoryObserver observer : mObservers) {
            observer.onPostsChanged();
        }
    }

    @Nullable
    @Override
    public Posts getPosts() {

        Posts posts = null;

        if (!mCacheIsDirty) {
            // Respond immediately with cache if available and not dirty
            if (mCachedPosts != null) {
                posts = getCachedPosts();
                return posts;
            } else {
                // Query the local storage if available.
                posts = mPostsLocalDataSource.getPosts();
            }
        }
        // To simplify, we'll consider the local data source fresh when it has data.
        if (posts == null || posts.isEmpty()) {
            posts = mPostsRemoteDataSource.getPosts();
            saveTasksInLocalDataSource(posts);
        }

        processLoadedTasks(posts);
        return getCachedPosts();
    }


    @Nullable
    @Override
    public Post getPost(@NonNull String postId) {
        Post cachedPost = getTaskWithId(postId);

        if (cachedPost != null) {
            return cachedPost;
        }

        Post post = mPostsLocalDataSource.getPost(postId);

        if (post == null) {
            post = mPostsRemoteDataSource.getPost(postId);
        }

        return post;
    }

    @Override
    public void refreshPosts() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    public Posts getCachedPosts() {
        Posts posts = new Posts();
        posts.addAll(mCachedPosts.values());

        return !posts.isEmpty() ? posts : null;
    }

    private void saveTasksInLocalDataSource(Posts posts) {
        if (posts != null) {
            for (Post post : posts) {
                // TODO save post
            }
        }
    }

    private void processLoadedTasks(Posts posts) {
        if (posts == null) {
            mCachedPosts = null;
            mCacheIsDirty = false;
            return;
        }

        if (mCachedPosts == null) {
            mCachedPosts = new LinkedHashMap<>();
        }
        mCachedPosts.clear();

        for (Post task : posts) {
            mCachedPosts.put(task.getId(), task);
        }

        mCacheIsDirty = false;
    }

    @Nullable
    private Post getTaskWithId(@NonNull String id) {
        if (mCachedPosts == null || mCachedPosts.isEmpty()) {
            return null;
        } else {
            return mCachedPosts.get(id);
        }
    }
}
