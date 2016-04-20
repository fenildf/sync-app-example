package jonatas.com.br.syncappexample.data;

/**
 * Created by jonatas on 20/04/16.
 */

/**
 * Immutable model class for a Post.
 */
public final class Post {

    private final String mId;
    private final String mTitle;
    private final String mContent;

    /**
     * Use this constructor to create a new Post.
     *
     * @param mId
     * @param mTitle
     * @param mContent
     */
    public Post(String mId, String mTitle, String mContent) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
