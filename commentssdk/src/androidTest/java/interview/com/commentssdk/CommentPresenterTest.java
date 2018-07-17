package interview.com.commentssdk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentPresenterTest {
    @Mock
    private CommentService commentService;

    @Mock
    private CommentContract.View view;

    // TODO: Don't need?
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchValidDataShouldLoadIntoView() {
        List<Comment> commentResponse = Arrays.asList(new Comment(0, 1, "", "", ""));

        when(commentService.getCommentsById(1)).thenReturn(Observable.just(commentResponse));

        CommentPresenter commentPresenter = new CommentPresenter(
                commentService,
                new MockSharedPreferencesRepoImpl(),
                Schedulers.immediate(),
                Schedulers.immediate(),
                view);

        commentPresenter.nextComment();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onStarted();
        inOrder.verify(view, times(1)).onNext(commentResponse);
        inOrder.verify(view, times(1)).onCompleted();
    }

    @Test
    public void fetchErrorShouldReturnErrorToView() {
        Exception exception = new Exception();

        when(commentService.getCommentsById(1)).thenReturn(Observable.<List<Comment>>error(exception));

        CommentPresenter commentPresenter = new CommentPresenter(
                commentService,
                new MockSharedPreferencesRepoImpl(),
                Schedulers.immediate(),
                Schedulers.immediate(),
                view);

        commentPresenter.nextComment();

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onStarted();
        inOrder.verify(view, times(1)).onError(exception);
        verify(view, never()).onCompleted();
    }
}
