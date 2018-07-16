package interview.com.commentssdk;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


class CommentIntentService extends IntentService {
    private static final String TAG = "CommentIntentService";

    private CommentRequest commentRequest;

    public CommentIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        commentRequest = new CommentRequest(getApplicationContext());
        commentRequest.nextComment();
    }
}
