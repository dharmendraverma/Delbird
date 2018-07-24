package in.delbird.chemist.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demach.konotor.Konotor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamBoldTextView;
import in.delbird.chemist.CustomViews.GothamLightButtonText;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.CustomViews.GothumMediumTextView;
import in.delbird.chemist.CustomViews.GouthamLightEditText;
import in.delbird.chemist.R;
import in.delbird.chemist.adapters.CartListAdapter;
import in.delbird.chemist.models.CartModel;
import in.delbird.chemist.models.ShopModel;

public class CartActivity extends BaseCartActivity implements View.OnClickListener {

    private final int SELECT_FILE = 1;
    private final int REQUEST_CAMERA = 0;

    @Bind(R.id.emptyCart)
    RelativeLayout emptyCart;
    @Bind(R.id.fillCart)
    LinearLayout fillCart;
    @Bind(R.id.cart_items)
    RecyclerView cartItems;
    @Bind(R.id.image_cart)
    ImageView imageCart;
    @Bind(R.id.cart_count)
    TextView cartCount;
    @Bind(R.id.toolbar_title)
    GothamLightTextView toolbarTitle;
    @Bind(R.id.image_chat)
    ImageView imageChat;
    @Bind(R.id.chat_count)
    TextView chatCount;
    @Bind(R.id.title_txt)
    RelativeLayout titleTxt;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.text_view1)
    GothumMediumTextView textView1;
    @Bind(R.id.text_view2)
    GothumMediumTextView textView2;
    @Bind(R.id.checkout)
    TextView checkout;
    @Bind(R.id.search_icon)
    ImageView searchIcon;
    @Bind(R.id.search_product)
    GouthamLightEditText searchProduct;
    @Bind(R.id.clear)
    ImageView clear;
    @Bind(R.id.search_product_Layout)
    LinearLayout searchProductLayout;
    @Bind(R.id.start_shopping)
    GothamBoldTextView startShopping;
    @Bind(R.id.imagesLinearLayout)
    LinearLayout imagesLinearLayout;
    @Bind(R.id.imagesScrollView)
    ScrollView imagesScrollView;
    private Toolbar toolbar;
    private TextView mTitle;
    private SharedPreferences sharedPreferences;
    TextView mStartShopping;
    private ImageView chat_IV, cart_IV;
    private IntentFilter konotorIntentFilter;
    LocationManager locationManager;
    TextView chatCountTV, emailTV;
    LinearLayout mSearchProductLayout;
    int Count;

    private BroadcastReceiver konotor_broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
            if (unreadMessageCount != 0) {
                chatCountTV.setVisibility(View.VISIBLE);
                chatCountTV.setText(String.valueOf(unreadMessageCount));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        mStartShopping = (TextView) findViewById(R.id.start_shopping);
        mStartShopping.setOnClickListener(this);
        chatCountTV = (TextView) findViewById(R.id.chat_count);
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
        chat_IV = (ImageView) findViewById(R.id.image_chat);
        cart_IV = (ImageView) findViewById(R.id.image_cart);
        cart_IV.setVisibility(View.GONE);
        chat_IV.setVisibility(View.GONE);
        mSearchProductLayout = (LinearLayout) findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);

        //discout_IV=(ImageView)findViewById(R.id.image_discount);
        chat_IV.setOnClickListener(this);
        cart_IV.setOnClickListener(this);
        updateAttachmentView();
    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("My Cart (" + CartModel.getCartModelInstance().getCartItemsCount() + ")");
        // mTitle.setText("My Cart (" + Count + ")");
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);


        imageCart.setVisibility(View.INVISIBLE);


        if (CartModel.getCartModelInstance().getCartItemsCount() > 0) {
            fillCart.setVisibility(View.VISIBLE);
            emptyCart.setVisibility(View.GONE);
            cartCount.setVisibility(View.GONE);
            initializeCartItemsAdapter();
        } else {
            fillCart.setVisibility(View.GONE);
            emptyCart.setVisibility(View.VISIBLE);
            cartCount.setVisibility(View.GONE);
        }
    }

    private void initializeCartItemsAdapter() {
        cartItems.setHasFixedSize(true);
        cartItems.setLayoutManager(new LinearLayoutManager(this));
        cartItems.setAdapter(new CartListAdapter(this));
        updateButtonText();
    }

    public void updateButtonText() {
        checkout.setText("Proceed to Checkout (Rs." + (int) (CartModel.getCartModelInstance().getTotalAmount()) + ")          >");
        mTitle.setText("My Cart (" + CartModel.getCartModelInstance().getCartItemsCount() + ")");
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String getRetailerIdForList() {
        return sharedPreferences.getString(Constants.RETAILER_IDS, "");
    }

    public void launchProductDetailActivity(ShopModel shopModel) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT_DETAIL_MODEL, shopModel);
        // startActivity(intent);
    }


    @OnClick(R.id.checkout)
    public void onClick() {
        if(CartModel.getCartModelInstance().getImages().isEmpty()) {
            selectImage();
        }else {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_shopping:
                startActivity(new Intent(this, StartPageActivity.class));
                break;
            case R.id.image_chat:
                Konotor.getInstance(getApplicationContext())
                        .launchFeedbackScreen(CartActivity.this);
                chatCountTV.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    CartModel.getCartModelInstance().getImages().put("Image " + (CartModel.getCartModelInstance().getImages().size() + 1), Uri.fromFile(destination).toString());
                    updateAttachmentView();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // ivImagePrescription.setImageBitmap(thumbnail);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                CartModel.getCartModelInstance().getImages().put("Image " + (CartModel.getCartModelInstance().getImages().size() + 1), selectedImageUri.toString());
                updateAttachmentView();
            }
        }
    }

    private void updateAttachmentView() {
        if(!CartModel.getCartModelInstance().getImages().isEmpty()){
            imagesScrollView.setVisibility(View.VISIBLE);
            imagesLinearLayout.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);

            Iterator it = CartModel.getCartModelInstance().getImages().entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry pair = (Map.Entry)it.next();
                View view = inflater.inflate(R.layout.image_row,imagesLinearLayout,false);
                TextView textView = (TextView) view.findViewById(R.id.imageName);
                textView.setText((String)pair.getKey());
               ImageView button = (ImageView) view.findViewById(R.id.delete);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CartModel.getCartModelInstance().getImages().remove((String) pair.getKey());
                        updateAttachmentView();
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPhoto(Uri.parse((String) pair.getValue()));
                    }
                });
                imagesLinearLayout.addView(view);


                //it.remove(); // avoids a ConcurrentModificationException
            }
            View view2 = inflater.inflate(R.layout.add,imagesLinearLayout,false);
            GothamLightButtonText button2 = (GothamLightButtonText) view2.findViewById(R.id.add);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImage();
                }
            });
            imagesLinearLayout.addView(view2);
        }else{
            imagesScrollView.setVisibility(View.GONE);
        }
    }

    private void showPhoto(Uri photoUri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(photoUri, "image/*");
        startActivity(intent);
    }
}
