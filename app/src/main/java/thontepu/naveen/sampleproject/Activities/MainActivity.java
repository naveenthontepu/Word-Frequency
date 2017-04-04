package thontepu.naveen.sampleproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import thontepu.naveen.sampleproject.Utils.ProcessFile;
import thontepu.naveen.sampleproject.R;
import thontepu.naveen.sampleproject.RecyclerViewAdapterViewHolder.WordCountAdapter;
import thontepu.naveen.sampleproject.Pojo.StringCount;
import thontepu.naveen.sampleproject.Utils.Utilities;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.wordList)
    RecyclerView wordList;
    private ProgressDialog progressDialog;
    private WordCountAdapter wordCountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = Utilities.getProgressDialog(this, R.string.pleaseWait);
        wordCountAdapter = new WordCountAdapter(this, Collections.<StringCount>emptyList());
        wordList.setLayoutManager(new LinearLayoutManager(this));
        wordList.setAdapter(wordCountAdapter);
    }

    public void selectDocument(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, 102);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setType("text/*");
//        startActivityForResult(intent,102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 102:
                    Log.i("asdf", "intent = " + data);
                    populateData(data.getData());
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void populateData(Uri data) {
        ProcessFile processFile = new ProcessFile(this);
        Single<List<StringCount>> stringList = processFile.getStringFrequencyObservable(data);
        stringList.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<StringCount>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("asdf", "onSubscribe");
                        Utilities.showProgressDialog(progressDialog);
                        wordCountAdapter.clearList();
                    }

                    @Override
                    public void onSuccess(List<StringCount> value) {

                        Log.i("asdf", "onSuccess length = " + value.size());
                        Utilities.dismissDialog(progressDialog);
                        wordCountAdapter.setData(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("asdf", "onError");
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilities.dismissDialog(progressDialog);
    }
}
