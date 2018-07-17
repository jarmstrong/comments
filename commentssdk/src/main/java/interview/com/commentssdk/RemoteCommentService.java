package interview.com.commentssdk;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RemoteCommentService implements CommentService {
    private CommentService api;

    public RemoteCommentService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(CommentService.class);
    }

    @Override
    public Observable<List<Comment>> getCommentsById(int id) {
        return api.getCommentsById(id);
    }
}
