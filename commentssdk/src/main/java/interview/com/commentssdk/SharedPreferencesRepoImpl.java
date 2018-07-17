package interview.com.commentssdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesRepoImpl implements SharedPreferencesRepo {
    private static final String SP_CURRENT_COMMENT_ID = "currentCommentId";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesRepoImpl(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public int getCommentId() {
        return sharedPreferences.getInt("currentCommentId", 1);
    }

    @Override
    public void incrementCommentId() {
        int nextId = getCommentId()+ 1;
        if (nextId > 500) {
            nextId = 1;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SP_CURRENT_COMMENT_ID, nextId);
        editor.commit();
    }
}
