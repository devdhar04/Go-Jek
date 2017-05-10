
package com.android.gojek.data;

import com.android.gojek.utils.WebServiceConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsFactory {


    public static ContactApiService createAddService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebServiceConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ContactApiService.class);
    }


}
