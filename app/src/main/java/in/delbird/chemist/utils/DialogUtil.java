package in.delbird.chemist.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by deepak on 28/1/16.
 */
public class DialogUtil {
    ProgressDialog pdilog;
    public void progressDialog(Context context,String msg)
    {
        pdilog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        pdilog.setMessage(msg);
        pdilog.setIndeterminate(false);
        pdilog.setCancelable(false);
        pdilog.show();
    }
    public void stopProgressDialog()
    {
        pdilog.dismiss();
    }
}
