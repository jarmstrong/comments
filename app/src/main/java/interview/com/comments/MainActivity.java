package interview.com.comments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import interview.com.commentssdk.CommentClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommentClient commentClient = new CommentClient(this);
        commentClient.start();
    }
}
