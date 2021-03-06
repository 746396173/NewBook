package com.thmub.newbook.presenter;

import android.util.Log;

import com.thmub.newbook.base.RxPresenter;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.model.SourceModel;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.presenter.contract.BookDetailContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 */
public class BookDetailPresenter extends RxPresenter<BookDetailContract.View>
        implements BookDetailContract.Presenter {

    private static final String TAG = "BookDetailPresenter";

    @Override
    public void removeShelfBook(ShelfBookBean bookBean) {
        BookShelfRepository.getInstance()
                .removeShelfBookInRx(bookBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Integer i) {
                        mView.finishRemoveBook(i);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadDetailBook(BookSearchBean bookBean) {

        SourceModel.getInstance(bookBean.getSourceTag())
                .parseBook(bookBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BookDetailBean bookDetailBean) {
                        mView.finishLoadDetailBook(bookDetailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadCatalogs(ShelfBookBean bookBean) {
        //加载最新10章
        SourceModel.getInstance(bookBean.getSourceTag())
                .parseCatalog(bookBean, -10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookChapterBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookChapterBean> bookChapterBeans) {
                        mView.finishLoadCatalogs(bookChapterBeans);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadFindBooks(BookDetailBean bookBean) {
        if (bookBean.getFindLink() == null || isEmpty(bookBean.getFindLink())) {
//            mView.finishLoadFindBooks(null);
            return;
        }
        SourceModel.getInstance(bookBean.getSourceTag())
                .findBook(bookBean.getFindLink())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BookSearchBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(List<BookSearchBean> bookChapterBeans) {
                        mView.finishLoadFindBooks(bookChapterBeans);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
