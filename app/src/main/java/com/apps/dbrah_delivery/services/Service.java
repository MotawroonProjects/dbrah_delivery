package com.apps.dbrah_delivery.services;


import com.apps.dbrah_delivery.model.NationalitiesModel;
import com.apps.dbrah_delivery.model.NotificationDataModel;
import com.apps.dbrah_delivery.model.PlaceGeocodeData;
import com.apps.dbrah_delivery.model.StatusResponse;
import com.apps.dbrah_delivery.model.UserModel;

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
    @POST("api/logout")
    Single<Response<StatusResponse>> logout(@Header("AUTHORIZATION") String token,
                                            @Field("api_key") String api_key,
                                            @Field("phone_token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/firebase-tokens")
    Single<Response<StatusResponse>> updateFirebasetoken(@Header("AUTHORIZATION") String token,
                                                         @Field("api_key") String api_key,
                                                         @Field("phone_token") String phone_token,
                                                         @Field("user_id") String user_id,
                                                         @Field("software_type") String software_type


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
}