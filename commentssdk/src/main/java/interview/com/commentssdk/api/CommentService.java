package interview.com.commentssdk.api;

import java.util.List;

import interview.com.commentssdk.models.Comment;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommentService {
    @GET("comments/")
    Call<List<Comment>> getCommentById(@Query("id") int id);
}
