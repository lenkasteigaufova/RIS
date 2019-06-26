package app

import android.app.Activity
import android.app.Application
import android.view.View
import io.MockRepository
import io.Repository
import io.RepositoryIml
import io.RisApi
import model.Order
import model.UserProfile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.schedulers.Schedulers


class App : Application(){

    lateinit var repository: Repository

    lateinit var orderChanges: Observable<Order>

    override fun onCreate() {
        super.onCreate()

        val client = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }.let { interceptor ->
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("http://158.193.144.172:8000/ris/stolyservice/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(RisApi::class.java)

//        repository = RepositoryIml(api)
        repository = MockRepository()

        orderChanges = repository.getUserProfile().map(UserProfile::actualOrder).distinctUntilChanged()

    }

}

val View.repository        : Repository get() = (context.applicationContext as App).repository
val Activity.repository    : Repository get() = (applicationContext as App).repository
val View.orderChanges      : Observable<Order> get() = (context.applicationContext as App).orderChanges