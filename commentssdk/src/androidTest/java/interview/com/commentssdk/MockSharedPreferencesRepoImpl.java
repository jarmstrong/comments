package interview.com.commentssdk;

public class MockSharedPreferencesRepoImpl implements SharedPreferencesRepo {
    private int commentId = 1;

    @Override
    public int getCommentId() {
        return commentId;
    }

    @Override
    public void incrementCommentId() {
        commentId += 1;
    }
}
