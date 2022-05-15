package com.apps.dbrah_delivery.notifications;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.model.ChatUserModel;
import com.apps.dbrah_delivery.model.ClientModel;
import com.apps.dbrah_delivery.model.MessageModel;
import com.apps.dbrah_delivery.model.NotiFire;
import com.apps.dbrah_delivery.model.ProviderModel;
import com.apps.dbrah_delivery.model.StatusResponse;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.preferences.Preferences;
import com.apps.dbrah_delivery.remote.Api;
import com.apps.dbrah_delivery.share.App;
import com.apps.dbrah_delivery.tags.Tags;
import com.apps.dbrah_delivery.uis.activity_chat.ChatActivity;
import com.apps.dbrah_delivery.uis.activity_home.HomeActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class FireBaseNotifications extends FirebaseMessagingService {
    private CompositeDisposable disposable = new CompositeDisposable();
    private Map<String, String> map;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        map = remoteMessage.getData();

        for (String key : map.keySet()) {
            Log.e("Key=", key + "_value=" + map.get(key));
        }

        manageNotification(map);


    }

    private void manageNotification(Map<String, String> map) {
        String title = map.get("title");
        String body = map.get("body");
        String notification_type = map.get("notification_type");
        String order_id = map.get("order_id");
        String user_id = map.get("user_id");
        String provider_id = map.get("provider_id");

        String order_status = map.get("status");


        String sound_Path = "";
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        sound_Path = uri.toString();
        Intent cancelIntent = new Intent(this, BroadcastCancelNotification.class);
        PendingIntent cancelPending = PendingIntent.getBroadcast(this, 0, cancelIntent, 0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setAutoCancel(true)
                .setOngoing(false)
                .setChannelId(App.CHANNEL_ID)
                .setDeleteIntent(cancelPending)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(Uri.parse(sound_Path), AudioManager.STREAM_NOTIFICATION);


        if (notification_type.equals("chat")) {

            String image = map.get("file");

            if (image != null && !image.isEmpty() && !map.get("type").equals("text")) {
                body = getString(R.string.attach_sent);
            } else {
                body = map.get("message");
            }

            notificationCompat.setContentTitle(title);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("data", getChatUserModel(map));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));

            if (user_id == null) {
                user_id = "";
            }
            if (provider_id == null) {
                provider_id = "";
            }
            if (getRoomId() != null && (order_id.equals(getRoomId().getOrder_id())
                    && (provider_id.equals(getRoomId().getProvider_id())
                    || user_id.equals(getRoomId().getUser_id())))) {
                ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                String className = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                if (className.equals("com.apps.dbrah_delivery.uis.activity_chat.ChatActivity")) {

                    EventBus.getDefault().post(getMessageModel(map));

                } else {


                    if (getChatUserModel(map).getUser_image() != null && !getChatUserModel(map).getUser_image().isEmpty()) {

                        Glide.with(this)
                                .asBitmap()
                                .load(Uri.parse(getChatUserModel(map).getUser_image()))
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        notificationCompat.setLargeIcon(resource);
                                        manager.notify(Tags.not_id, notificationCompat.build());

                                    }
                                });
                    } else {
                        // Log.e("djhdhdh","fkkfkfk");
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                        notificationCompat.setLargeIcon(bitmap);
                        manager.notify(Tags.not_id, notificationCompat.build());
                    }


                }
            } else {
                if (getChatUserModel(map).getUser_image() != null && !getChatUserModel(map).getUser_image().isEmpty()) {
                    Glide.with(this)
                            .asBitmap()
                            .load(Uri.parse(getChatUserModel(map).getUser_image()))
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    notificationCompat.setLargeIcon(resource);
                                    manager.notify(Tags.not_id, notificationCompat.build());

                                }
                            });
                } else {
                    Log.e("djhdhdh", "fkkfkfk");

                    // Log.e("dlldldl", "lldldl");
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                    notificationCompat.setLargeIcon(bitmap);
                    manager.notify(Tags.not_id, notificationCompat.build());
                }

            }

        } else if (notification_type.equals("basic")) {
            if (order_status.equals("preparing")) {
                title = getString(R.string.new_order);
                body = getString(R.string.new_order) + "-" + getString(R.string.new_client) + "\n" + getString(R.string.order_num) + " #" + order_id;
            }
//            else if (order_status.equals("accepted")) {
//                title = getString(R.string.your_offer_has_been_accepted);
//
//                body = getString(R.string.your_offer_has_been_accepted) + " " + getChatUserModel(map).getUser_name() + "\n" + getString(R.string.order_num) + " #" + order_id;
//
//            } else if (order_status.equals("rejected")) {
//                title = getString(R.string.your_offer_has_been_rejected);
//                body = getString(R.string.your_offer_has_been_rejected) + " " + getChatUserModel(map).getUser_name() + "\n" + getString(R.string.order_num) + " #" + order_id;
//
//            }
            notificationCompat.setContentTitle(title);
            notificationCompat.setContentText(body);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("order_id", order_id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            notificationCompat.setLargeIcon(bitmap);
            manager.notify(Tags.not_id, notificationCompat.build());
            EventBus.getDefault().post(new NotiFire(order_status));

        } else {

            notificationCompat.setContentTitle(title);
            notificationCompat.setContentText(body);
            notificationCompat.setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("order_id", "");

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
            taskStackBuilder.addNextIntent(intent);
            notificationCompat.setContentIntent(taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
            notificationCompat.setLargeIcon(bitmap);
            manager.notify(Tags.not_id, notificationCompat.build());
            EventBus.getDefault().post(new NotiFire(order_status));

        }

    }


    private MessageModel getMessageModel(Map<String, String> map) {
        String order_id = map.get("order_id");
        String id = map.get("id");
        String provider_id = map.get("provider_id");
        String user_id = map.get("user_id");
        String from_type = map.get("from_type");
        String message = map.get("message");
        String file = map.get("file");
        String type = map.get("type");
        String created_at = map.get("created_at");
        String updated_at = map.get("updated_at");

        if (file == null) {
            file = "";

        }


        MessageModel messageModel = new MessageModel(id, provider_id, user_id, order_id, from_type, message, file, type, created_at, updated_at);

        return messageModel;
    }

    private ChatUserModel getChatUserModel(Map<String, String> map) {
        String order_id = map.get("order_id");
        ClientModel userModel;
        ChatUserModel model;
        ProviderModel providerModel;
        if (map.get("user") != null) {
            userModel = new Gson().fromJson(map.get("user"), ClientModel.class);
            String user_id = userModel.getId();
            //  Log.e("llkkk",user_id);

            String user_name = userModel.getName();
            String user_phone = userModel.getPhone_code() + userModel.getPhone();
            String user_image = userModel.getImage();

            model = new ChatUserModel("", user_id, map.get("representative_id"), user_name, user_phone, user_image, order_id);

        } else {
            providerModel = new Gson().fromJson(map.get("provider"), ProviderModel.class);
            String user_id = providerModel.getId();
            String user_name = providerModel.getName();
            String user_phone = providerModel.getPhone_code() + providerModel.getPhone();
            String user_image = providerModel.getImage();

            model = new ChatUserModel(user_id, "", map.get("representative_id"), user_name, user_phone, user_image, order_id);

        }


        return model;

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (getUserModel() == null) {
            return;
        }

        Api.getService(Tags.base_url)
                .updateFirebasetoken(getUserModel().getData().getId(), s, "android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatus() == 200) {
                                    UserModel userModel = getUserModel();
                                    userModel.getData().setFirebase_token(s);
                                    setUserModel(userModel);

                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("token", e.toString());

                    }
                });

    }

    public UserModel getUserModel() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getUserData(this);
    }


    public void setUserModel(UserModel userModel) {
        Preferences preferences = Preferences.getInstance();
        preferences.createUpdateUserData(this, userModel);

    }


    public ChatUserModel getRoomId() {
        Preferences preferences = Preferences.getInstance();
        return preferences.getRoomId(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
