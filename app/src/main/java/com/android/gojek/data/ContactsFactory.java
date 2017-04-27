
package com.android.gojek.data;

import com.android.gojek.utils.WebServiceConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsFactory {




  public static ContactListService create() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(WebServiceConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(ContactListService.class);
  }

  public static ContactDetailService createDetailService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(WebServiceConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    return retrofit.create(ContactDetailService.class);
  }

  public static ContactAddService createAddService(String url) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    return retrofit.create(ContactAddService.class);
  }
}
