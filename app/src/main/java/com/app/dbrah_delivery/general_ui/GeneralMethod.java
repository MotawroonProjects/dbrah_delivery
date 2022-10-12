package com.app.dbrah_delivery.general_ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.app.dbrah_delivery.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("image")
    public static void image(View view, String imageUrl) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                if (view instanceof CircleImageView) {
                    CircleImageView imageView = (CircleImageView) view;
                    if (imageUrl != null) {
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                } else if (view instanceof RoundedImageView) {
                    RoundedImageView imageView = (RoundedImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);

                    }
                } else if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                }

            }
        });


    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {


        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);
            }
        }

    }

    @BindingAdapter("qr_image")
    public static void qr_image(View view, String imageUrl) {

        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {
                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        }


    }

    @BindingAdapter("departmentImage")
    public static void department_image(View view, String imageUrl) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }

        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }
        }

    }


    @BindingAdapter("createAt")
    public static void dateCreateAt(TextView textView, String s) {
        if (s != null) {
            try {
                String[] dates = s.split("T");
                textView.setText(dates[0]);
            } catch (Exception e) {

            }

        }

    }

    @BindingAdapter({"offerDate"})
    public static void displayOfferDate(TextView textView, String offerDate) {
        if (offerDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }

    @BindingAdapter({"offertime"})
    public static void displayOffertime(TextView textView, String offerDate) {
        if (offerDate != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }

    @BindingAdapter({"offertypetime"})
    public static void displayOfferTypetime(TextView textView, String offerDate) {
        if (offerDate != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("aa", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }

    @BindingAdapter("orderStatus")
    public static void orderStatus(ProgressBar progressBar, String status) {
        if (status != null) {
            if (status.equals("accepted")) {
                progressBar.setProgress(20);
            } else if (status.equals("picked_up")) {
                progressBar.setProgress(50);
            } else if (status.equals("on_the_way")) {
                progressBar.setProgress(80);
            } else if (status.equals("ended")) {
                progressBar.setProgress(100);
            }
        }
    }

    @BindingAdapter("orderStatus")
    public static void orderStatus(TextView btnStatus, String status) {
        if (status != null) {
            if (status.equals("accepted")) {
                btnStatus.setText(R.string.pick_up);
            } else if (status.equals("picked_up")) {
                btnStatus.setText(R.string.on_way);
            } else if (status.equals("on_the_way")) {
                btnStatus.setText(R.string.end);

            } else if (status.equals("ended")) {
                btnStatus.setText(R.string.finish_delivery);
            }
        }
    }
    @BindingAdapter("orderRowStatus")
    public static void orderRowStatus(TextView btnStatus, String status) {
        if (status != null) {
            if (status.equals("accepted")) {
                btnStatus.setText(R.string.accepted);
            } else if (status.equals("picked_up")) {
                btnStatus.setText(R.string.pick_up);
            } else if (status.equals("on_the_way")) {
                btnStatus.setText(R.string.on_way);

            } else if (status.equals("ended")) {
                btnStatus.setText(R.string.finish_delivery);
            }
        }
    }
    @BindingAdapter("orderRowStatus")
    public static void orderRowStatus(ProgressBar progressBar, String status) {
        if (status != null) {
            if (status.equals("accepted")) {
                progressBar.setProgress(20);
                progressBar.setProgressDrawable(progressBar.getContext().getResources().getDrawable(R.drawable.progress1_shape));
            } else if (status.equals("picked_up")) {
                progressBar.setProgress(50);
                progressBar.setProgressDrawable(progressBar.getContext().getResources().getDrawable(R.drawable.progress2_shape));

            } else if (status.equals("on_the_way")) {
                progressBar.setProgress(80);
                progressBar.setProgressDrawable(progressBar.getContext().getResources().getDrawable(R.drawable.progress3_shape));

            }
        }
    }
    @BindingAdapter("createTime")
    public static void createAtTime(TextView textView, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (date != null) {

            try {
                Date parse = dateFormat.parse(date);

                SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                format.setTimeZone(TimeZone.getDefault());
                String time = format.format(parse);
                textView.setText(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {

        }

    }
}










