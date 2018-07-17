package interview.com.commentssdk;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface CommentService {
    @GET("comments/")
    Observable<List<Comment>> getCommentsById(@Query("id") int id);
}
