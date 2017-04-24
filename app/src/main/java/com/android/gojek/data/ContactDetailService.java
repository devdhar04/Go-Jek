package com.android.gojek.data;

/**
 * Created by dev on 16/04/17.
 */
import com.android.gojek.model.Contact;


import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface ContactDetailService {
    @GET Observable<Contact> fetchContact(@Url String url);
}
