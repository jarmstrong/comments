package interview.com.commentssdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentRequestTest {
    @Mock
    CommentService commentService;

    @Mock
    Call<List<Comment>> call;

    @Spy
    List<Comment> spyComments = new ArrayList<>();

    @InjectMocks
    CommentRequest commentRequest;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("interview.com.commentssdk.test", appContext.getPackageName());
    }

    @Test
    public void whenNextComment_GetsFirstElement() {
        Mockito.when(commentService.getCommentById(1)).thenReturn(call);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Callback<List<Comment>> callback = invocation.getArgument(0);

                spyComments.add(new Comment(0, 1, "James", "itsjarm@gmail.com", "test"));
                callback.onResponse(call, Response.success(spyComments));

                return null;
            }
        }).when(call).enqueue(any(Callback.class));

        commentRequest.nextComment();

        Mockito.verify(spyComments, times(1)).get(0);
    }
}
