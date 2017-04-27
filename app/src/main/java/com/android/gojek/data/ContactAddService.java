package com.android.gojek.data;

import com.android.gojek.model.Contact;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by dev on 26/04/17.
 */


public interface ContactAddService {

    @PUT
    @FormUrlEncoded
    Observable<String> addContact(@Field("firstName") String firstName,
                       @Field("lastName") String lastName,
                       @Field("mobile") String mobile,
                        @Field("email") String email);
}
