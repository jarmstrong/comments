package interview.com.commentssdk;

import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CommentPresenter implements CommentContract.Presenter {
    private CommentService commentService;
    private SharedPreferencesRepo sharedPreferencesRepo;
    private Scheduler backgroundScheduler;
    private Scheduler mainScheduler;
    private CommentContract.View view;
    private CompositeSubscription compositeSubscription;

    public CommentPresenter(
            CommentService commentService,
            SharedPreferencesRepo sharedPreferencesRepo,
            Scheduler backgroundScheduler,
            Scheduler mainScheduler,
            CommentContract.View view) {
        this.commentService = commentService;
        this.sharedPreferencesRepo = sharedPreferencesRepo;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void nextComment() {
        view.onStarted();
        compositeSubscription.clear();

        int currentCommentId = sharedPreferencesRepo.getCommentId();

        Subscription subscription = commentService
                .getCommentsById(currentCommentId)
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                        view.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e);
                    }

                    @Override
                    public void onNext(List<Comment> commentResponse) {
                        view.onNext(commentResponse);
                    }
                });

        compositeSubscription.add(subscription);
    }


    @Override
    public void onDestroy() {
        compositeSubscription.clear();
        view = null;
    }
}
