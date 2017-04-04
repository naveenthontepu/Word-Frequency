package thontepu.naveen.sampleproject.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import thontepu.naveen.sampleproject.Pojo.StringCount;

/**
 * Created by mac on 4/4/17
 */

public class ProcessFile {
    private Context context;

    public ProcessFile(Context context) {
        this.context = context;
    }

    public Single<List<StringCount>> getStringFrequencyObservable(final Uri uri) {
        Observable<StringCount> s = Observable.create(new ObservableOnSubscribe<StringCount>() {
            @Override
            public void subscribe(ObservableEmitter<StringCount> e) throws Exception {
                String string = processData(uri);
                List<StringCount> stringCountList = getStringFrequency(string);
                for (StringCount stringCount : stringCountList) {
                    e.onNext(stringCount);
                }
                e.onComplete();
            }
        });
        Observable<GroupedObservable<Integer, StringCount>> groupedObservable =
                s.sorted(new Comparator<StringCount>() {
                    @Override
                    public int compare(StringCount o1, StringCount o2) {
                        return o1.getCount() - o2.getCount();
                    }
                }).groupBy(new Function<StringCount, Integer>() {
                    @Override
                    public Integer apply(StringCount stringCount) throws Exception {
                        return stringCount.getCount() / 10;
                    }
                });
        return groupedObservable.toList().map(new Function<List<GroupedObservable<Integer, StringCount>>, List<StringCount>>() {
            @Override
            public List<StringCount> apply(List<GroupedObservable<Integer, StringCount>> groupedObservables) throws Exception {
                final List<StringCount> stringCountList = new ArrayList<>();
                for (GroupedObservable<Integer, StringCount> groupedObservable : groupedObservables) {
                    int key = groupedObservable.getKey() * 10;
//                    String s12 = key + " - " + (key + 10);
                    StringCount stringCount = new StringCount(key + " - "+ (key + 10),0);
                    stringCount.setHeader(true);
                    stringCountList.add(stringCount);
                    groupedObservable.toList().subscribe(new Consumer<List<StringCount>>() {
                        @Override
                        public void accept(List<StringCount> stringCounts) throws Exception {
                            stringCountList.addAll(stringCounts);
                        }
                    });
                }
                return stringCountList;
            }
        });
    }

    public List<StringCount> getStringFrequency(String string){
        String[] words = string.split("\\W+");
//        Log.i("asdf", "total words = " + words.length);
        HashMap<String, Integer> wordCountMap = new HashMap<>();
        for (String st : words) {
            if (!st.isEmpty()) {
                if (wordCountMap.get(st) != null) {
                    wordCountMap.put(st, wordCountMap.get(st) + 1);
                } else {
                    wordCountMap.put(st, 1);
                }
            }
        }
        List<StringCount> stringCountList = new ArrayList<>();
        for (String key : wordCountMap.keySet()) {
            stringCountList.add(new StringCount(key, wordCountMap.get(key)));
        }
        return stringCountList;
    }

    private String processData(Uri uri) {
        String st = "";
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().
                    openFileDescriptor(uri, "r");
            assert pfd != null;
            FileInputStream fileInputStream = new FileInputStream(pfd.getFileDescriptor());
            long read = pfd.getStatSize();
            byte[] re = new byte[(int) read];
            int r = fileInputStream.read(re);
            st = new String(re);
            Log.i("asdf", "size = " + r);
            Log.i("asdf", "string =\n " + st);
            fileInputStream.close();
//            FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
//            fileOutputStream.close();
            pfd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }
}
