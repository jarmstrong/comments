package interview.com.commentssdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class CommentIntentService extends IntentService implements Callback<List<Comment>> {
    private static final String TAG = "CommentIntentService";

    public CommentIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CommentService service = retrofit.create(CommentService.class);

        // TODO: Move strings to strings.xml
        // Using SharedPref's to persist current id through device restarts
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("prefs_key", Context.MODE_PRIVATE);
        int currentId = sharedPreferences.getInt("currentCommentId", 1);

        Call<List<Comment>> call = service.getCommentById(currentId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
        if (response.isSuccessful()) {
            List<Comment> comments = response.body();

            if (comments == null || comments.isEmpty()) {
                Log.e(TAG, "No comments in response body");
                return;
            }

            Comment comment = comments.get(0);
            Log.i(TAG, comment.toString());

            updateCurrentId();
        } else {
            Log.e(TAG, "Failed to retrieve comment");
        }
    }

    private void updateCurrentId() {
        // TODO: Move strings to strings.xml
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("prefs_key", Context.MODE_PRIVATE);
        int currentId = sharedPreferences.getInt("currentCommentId", 1) + 1;
        if (currentId > 500) {
            currentId = 1;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("currentCommentId", currentId);
        editor.commit();
    }

    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        Log.e(TAG, "onFailure", t);
    }
}
