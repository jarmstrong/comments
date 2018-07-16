package interview.com.commentssdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentRequest implements Callback<List<Comment>> {
    private static final String TAG = "CommentRequest";
    private CommentService commentService;
    private Context context;

    public CommentRequest(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        commentService = retrofit.create(CommentService.class);
    }

    public void nextComment() {
        // TODO: Move strings to strings.xml
        // Using SharedPref's to persist current id through device restarts
        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs_key", Context.MODE_PRIVATE);
        int currentId = sharedPreferences.getInt("currentCommentId", 1);
        Call<List<Comment>> call = commentService.getCommentById(currentId);
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

            updateCurrentId(comment);
        } else {
            Log.e(TAG, "Failed to retrieve comment");
        }
    }

    private void updateCurrentId(Comment comment) {
        // TODO: Move strings to strings.xml / constants
        int nextId = comment.getId() + 1;
        if (nextId > 500) {
            nextId = 1;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("prefs_key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("currentCommentId", nextId);
        editor.commit();
    }

    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        Log.e(TAG, "onFailure", t);
    }
}
