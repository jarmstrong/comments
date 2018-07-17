package interview.com.commentssdk;

import java.util.List;

public interface CommentContract {
    interface View {

        void onStarted();

        void onCompleted();

        void onNext(List<Comment> commentResponse);

        void onError(Throwable e);
    }

    interface Presenter {

        void nextComment();

        void onDestroy();
    }
}
