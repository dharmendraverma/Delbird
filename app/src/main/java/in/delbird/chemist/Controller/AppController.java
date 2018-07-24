package in.delbird.chemist.Controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import in.delbird.chemist.models.SslHttpStack;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Dharmendra Verma on 4/27/15.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            //mRequestQueue = Volley.newRequestQueue(getApplicationContext(),new SslHttpStack(true));
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}