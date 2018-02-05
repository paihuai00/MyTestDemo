package com.csx.mytestdemo.rxjava_test;

import android.os.Environment;
import android.util.Log;

import com.csx.mlibrary.BaseActivity;
import com.csx.mlibrary.widget.Utils;
import com.csx.mytestdemo.R;

import java.io.File;
import java.sql.Time;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Created by cuishuxiang
 * @date 2018/1/25.
 *
 * RxJava
 */

public class RxJavaActivity extends BaseActivity {
    private static final String TAG = "RxJavaActivity";
    @Override
    public int getLayoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    public void initView() {

        //最基本的RxJava2 订阅
//        testMethod_1();

        //线程控制
//        testScheduler();

        //操作符
        testOperate();


        //存储目录
//        testDirectory();
    }


    private void testOperate() {
        Observable.just("1", "2")
                .flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {

                        int num_1 = Integer.valueOf(s);

                        return Observable.just(num_1);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: 接收到 = " + integer);
            }
        });

    }

    private void testScheduler() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                Log.d(TAG, "被观察者当前线程: " + Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "观察者当前线程: " + Thread.currentThread().getName());
                    }
                });

        //多次切换线程，看是否有效
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "Observable 被观察者 当前线程为：" + Thread.currentThread().getName());
            }
        }).subscribeOn(AndroidSchedulers.mainThread())          // 第一次，将 被观察者  指定在main线程
                .subscribeOn(Schedulers.io())                   // 第二次，将 被观察者   指定在io线程

                .observeOn(Schedulers.io())                     // 第一次，将 观察者  指定在io线程
                .observeOn(AndroidSchedulers.mainThread())      // 第二次，将 观察者  指定在main线程
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "Observer观察者当前线程: " + Thread.currentThread().getName());
                    }
                });


    }

    private void testMethod_1() {
        //1,创建一个被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("这是最基本RxJava 2 ");
                emitter.onComplete();
//                emitter.onError();
            }
        });

        //创建一个被观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: " + d);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
        //被观察者 -- > 订阅 观察者
//        observable.subscribe(observer);

        observable.subscribe();
        /**
         * subscribe 两个参数的方法。
         *
         * 无论上面有几个complete方法，我只关心 onNext  onError 方法
         */
//        observable.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d(TAG, "accept: onNext" + s);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.d(TAG, "accept: throwable");
//            }
//        });


//        ----------------------------链式调用----------------------------------
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Rxjava2 链式调用");
                Log.d(TAG, "被观察者当前线程为：" + Thread.currentThread().getName());
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

                /**
                 * onSubscribe 方法先于 onNext 执行
                 *
                 * 如果调用 d.dispose();
                 * 该方法就会截断事件的传递(只影响Observer观察者)。onNext  方法将不会执行！
                 *
                 * 但是，Observable 的事件还会继续发送
                 */
                Log.d(TAG, "观察者Observer当前线程为：" + Thread.currentThread().getName());
                Log.d(TAG, "onSubscribe: Rxjava2 链式调用 " + d.isDisposed());
                d.dispose();
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });



    }

    @Override
    public void initData() {

    }

    private void testDirectory() {
        //手机内存
        Environment.getDataDirectory().getParentFile();
        Log.d(TAG, "手机内存: " + Environment.getDataDirectory().getParentFile());

        //SD卡
        Environment.getExternalStorageDirectory();
        Log.d(TAG, "手机SD卡 : " + Environment.getExternalStorageDirectory());

        //私有存储
        Log.d(TAG, "缓存路径: " + this.getExternalCacheDir());

        Log.d(TAG, "图片存储路径: " + this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));

        Log.d(TAG, "外置SD卡的路径: " + Utils.getExtendedMemoryPath(this));
    }
}
