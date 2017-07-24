package com.abozaid.cityguide.presentation.dagger.modules;

import com.abozaid.cityguide.BuildConfig;
import com.abozaid.cityguide.data.network.DataCallsImpl;
import com.abozaid.cityguide.domain.calls.DataCalls;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aliabozaid on 5/6/16.
 */
@Module public class ApiModule {

  public ApiModule() {
  }

  @Provides @Singleton DataCalls provideNetworkCalls(DataCallsImpl dataCallsImpl) {
    return dataCallsImpl;
  }

  @Provides @Singleton Retrofit provideRetrofitBuilder(OkHttpClient okHttpClient) {

    return new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient() {

    return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Customize the request
        Request request = original.newBuilder().header("Content-Type", "application/json").build();

        Response response = chain.proceed(request);
        response.cacheResponse();
        // Customize or return the response
        return response;
      }
    })

        .build();
  }
}
