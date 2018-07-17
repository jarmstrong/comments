package interview.com.commentssdk;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import rx.schedulers.Schedulers;


class CommentIntentService extends IntentService {
    private static final String TAG = "CommentIntentService";

    public CommentIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        CommentService commentService = new RemoteCommentService();
        final SharedPreferencesRepo sharedPreferencesRepo = new SharedPreferencesRepoImpl(getApplicationContext());

        final CommentPresenter presenter = new CommentPresenter(
                commentService,
                sharedPreferencesRepo,
                Schedulers.immediate(),
                Schedulers.immediate(),
                new CommentContract.View() {
                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        if (comments == null || comments.isEmpty()) {
                            Log.e(TAG, "No comments in response body");
                            onError( new Exception("No comments in response body"));
                            return;
                        }

                        Comment comment = comments.get(0);
                        Log.i(TAG, comment.toString());
                        sharedPreferencesRepo.incrementCommentId();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                    }
                });

        presenter.nextComment();
    }
}
