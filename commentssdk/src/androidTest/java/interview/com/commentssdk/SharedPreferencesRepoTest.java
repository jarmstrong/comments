package interview.com.commentssdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesRepoTest {

    @Before
    public void clearSharedPreferences() {
        Context context = InstrumentationRegistry.getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences
                .edit()
                .clear()
                .commit();
    }

    @Test
    public void incrementCommentId_ShouldIncrement() {
        SharedPreferencesRepo sharedPreferencesRepo = new SharedPreferencesRepoImpl(InstrumentationRegistry.getContext());
        int commentId = sharedPreferencesRepo.getCommentId();

        Assert.assertEquals(commentId, 1);

        sharedPreferencesRepo.incrementCommentId();

        commentId = sharedPreferencesRepo.getCommentId();
        Assert.assertEquals(commentId, 2);
    }

    @Test
    public void incrementCommentId_ShouldResetOver500() {
        SharedPreferencesRepo sharedPreferencesRepo = new SharedPreferencesRepoImpl(InstrumentationRegistry.getContext());
        int commentId = sharedPreferencesRepo.getCommentId();

        Assert.assertEquals(1, commentId);

        for (int i = 0; i < 500; ++i) {
            sharedPreferencesRepo.incrementCommentId();
        }

        commentId = sharedPreferencesRepo.getCommentId();
        Assert.assertEquals(1, commentId);
    }
}
