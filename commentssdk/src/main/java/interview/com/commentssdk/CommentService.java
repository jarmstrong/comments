package interview.com.commentssdk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface CommentService {
    @GET("comments/")
    Call<List<Comment>> getCommentById(@Query("id") int id);
}
