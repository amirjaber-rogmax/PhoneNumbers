package amirjaber.rogmax.phonenumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import amirjaber.rogmax.phonenumbers.adapters.RecyclerViewAdapter;
import amirjaber.rogmax.phonenumbers.facades.Facade;
import amirjaber.rogmax.phonenumbers.models.Model;
import amirjaber.rogmax.phonenumbers.models.ModelValue;
import amirjaber.rogmax.phonenumbers.retrofit.APIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Model> list;
    private RecyclerViewAdapter viewAdapter;
    Facade userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        viewAdapter = new RecyclerViewAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        userController = APIUtils.getFacadeService();

        loadList();


    }

    private void loadList() {
        Call<ModelValue> call = userController.view();
        call.enqueue(new Callback<ModelValue>() {
            @Override
            public void onResponse(Call<ModelValue> call, Response<ModelValue> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                progressBar.setVisibility(View.GONE);

                if (value.equals("1")) {
                    list = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter(ViewActivity.this, list);
                    recyclerView.setAdapter(viewAdapter);
                    Toast.makeText(ViewActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelValue> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
