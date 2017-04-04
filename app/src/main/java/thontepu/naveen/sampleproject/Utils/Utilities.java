package thontepu.naveen.sampleproject.Utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by mac on 4/4/17
 */

public class Utilities {
    public static ProgressDialog getProgressDialog(Activity activity, int msg) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(activity.getResources().getString(msg));
        return progressDialog;
    }

    public static void showProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
    public static void dismissDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
