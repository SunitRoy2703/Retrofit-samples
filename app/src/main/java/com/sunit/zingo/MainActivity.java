package com.sunit.zingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        // getPost();

        //  getComments();

        createPost();

    }

    private void createPost() {

        Post post = new Post(27, "Sunit Roy", "Sunit Roy 2703");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "27");
        fields.put("title", "Sunit Roy");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post post1 = response.body();

                String content = " ";
                content += "Code: " + response.code() + "\n";
                content += post1.getId() + "\n" + post1.getUserId() + "\n" + post1.getTitle() + "\n" + post1.getText() + "\n\n";

                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = " ";
                    content += comment.getId() + "\n" + comment.getPostId() + "\n" + comment.getName() + "\n" + comment.getEmail() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPost() {


        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(4);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = " ";
                    content += post.getUserId() + "\n" + post.getId() + "\n" + post.getTitle() + "\n" + post.getText() + "\n\n";

                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}