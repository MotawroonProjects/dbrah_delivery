package com.app.dbrah_delivery.services;


import com.app.dbrah_delivery.model.MessagesDataModel;
import com.app.dbrah_delivery.model.NationalitiesModel;
import com.app.dbrah_delivery.model.NotificationDataModel;
import com.app.dbrah_delivery.model.OrdersModel;
import com.app.dbrah_delivery.model.PlaceGeocodeData;
import com.app.dbrah_delivery.model.SingleMessageModel;
import com.app.dbrah_delivery.model.SingleOrderDataModel;
import com.app.dbrah_delivery.model.StatusResponse;
import com.app.dbrah_delivery.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("geocode/json")
    Single<Response<PlaceGeocodeData>> getGeoData(@Query(value = "latlng") String latlng,
                                                  @Query(value = "language") String language,
                                                  @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/representative/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone);


    @GET("api/representative/nationalities")
    Single<Response<NationalitiesModel>> getNationalities();

    @Multipart
    @POST("api/representative/register")
    Single<Response<UserModel>> signUp(@Part("phone") RequestBody phone,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("name") RequestBody name,
                                       @Part("provider_code") RequestBody provider_code,
                                       @Part("nationality_id") RequestBody nationality_id,
                                       @Part("town_id") RequestBody town_id,
                                       @Part("residence_number") RequestBody residence_number,
                                       @Part("delivery_range") RequestBody delivery_range,
                                       @Part MultipartBody.Part image);

    @Multipart
    @POST("api/representative/update_profile")
    Single<Response<UserModel>> update(@Part("representative_id") RequestBody representative_id,
                                       @Part("phone") RequestBody phone,
                                       @Part("phone_code") RequestBody phone_code,
                                       @Part("name") RequestBody name,
                                       @Part("delivery_range") RequestBody delivery_range,
                                       @Part("provider_code") RequestBody provider_code,
                                       @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("api/contact_us")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String phone,
                                               @Field("message") String message);

    @FormUrlEncoded
    @POST("api/representative/logout")
    Single<Response<StatusResponse>> logout(@Field("representative_id") String representative_id,
                                            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("api/storeToken")
    Single<Response<StatusResponse>> updateFirebasetoken(@Field("representative_id") String representative_id,
                                                         @Field("token") String token,
                                                         @Field("type") String type
    );

    @FormUrlEncoded
    @POST("api/contact-us")
    Single<Response<StatusResponse>> contactUs(@Field("api_key") String api_key,
                                               @Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String phone,
                                               @Field("message") String message


    );


    @GET("api/notifications")
    Single<Response<NotificationDataModel>> getNotifications(@Header("AUTHORIZATION") String token,
                                                             @Query(value = "api_key") String api_key,
                                                             @Query(value = "user_id") String user_id
    );

    @FormUrlEncoded
    @POST("api/representative/changeStatus")
    Single<Response<UserModel>> updateStatus(@Field("representative_id") String representative_id
    );

    @GET("api/representative/current_orders")
    Single<Response<OrdersModel>> getcurrentOrders(@Query(value = "representative_id") String representative_id);

    @GET("api/representative/new_orders")
    Single<Response<OrdersModel>> getnewOrders(@Query(value = "representative_id") String representative_id);

    @GET("api/representative/last_orders")
    Single<Response<OrdersModel>> getPreviousOrders(@Query(value = "representative_id") String representative_id,
                                                    @Query(value = "time") String time);


    @GET("api/representative/order_details")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("order_id") String order_id,
                                                           @Query("representative_id") String representative_id);

    @FormUrlEncoded
    @POST("api/representative/updateOrderStatus")
    Single<Response<SingleOrderDataModel>> changeOrderStatus(@Field("order_id") String order_id,
                                                             @Field("representative_id") String representative_id,
                                                             @Field("status") String status);

    @GET("api/getChat")
    Single<Response<MessagesDataModel>> getChatMessages(@Query("order_id") String order_id,
                                                        @Query("representative_id") String representative_id,
                                                        @Query("user_id") String user_id,
                                                        @Query("provider_id") String provider_id);

    @Multipart
    @POST("api/storeMessage")
    Single<Response<SingleMessageModel>> sendMessages(@Part("order_id") RequestBody order_id,
                                                      @Part("type") RequestBody type,
                                                      @Part("from_type") RequestBody from,
                                                      @Part("message") RequestBody message,
                                                      @Part("representative_id") RequestBody representative_id,
                                                      @Part("user_id") RequestBody user_id,
                                                      @Part("provider_id") RequestBody provider_id,
                                                      @Part MultipartBody.Part image
    );
}