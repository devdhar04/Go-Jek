package com.android.gojek.data;

import com.android.gojek.model.Contact;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by dev on 26/04/17.
 */


public interface ContactApiService {

    @GET
    Observable<Contact[]> fetchContacts(@Url String url);

    @GET
    Observable<Contact> fetchContact(@Url String url);

    @PUT
    @Headers("Content-Type: application/json")
    Observable<Contact> updateContact(@Url String url, @Body Contact body);

    @POST
    @Headers("Content-Type: application/json")
    Observable<Contact> addContact(@Url String url, @Body Contact body);

    @DELETE
    Observable<String> deleteContact(@Url String url);
}
