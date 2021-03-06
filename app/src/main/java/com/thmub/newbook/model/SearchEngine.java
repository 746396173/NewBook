package com.thmub.newbook.model;


import com.thmub.newbook.R;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.BookSourceBean;
import com.thmub.newbook.utils.SharedPreUtils;
import com.thmub.newbook.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhouas666 on 2019-03-27
 * Github: https://github.com/zas023
 */
public class SearchEngine {

    private static final String TAG = "SearchEngine";

    //线程池
    private ExecutorService executorService;

    private Scheduler scheduler;
    private CompositeDisposable compositeDisposable;

    private List<BookSourceBean> mSourceList = new ArrayList<>();

    private int threadsNum;
    private int searchSiteIndex;
    private int searchSuccessNum;

    private OnSearchListener searchListener;

    public SearchEngine() {
        threadsNum = SharedPreUtils.getInstance().getInt(UiUtils.getString(R.string.pref_thread_num), 6);
    }

    public void setOnSearchListener(OnSearchListener searchListener) {
        this.searchListener = searchListener;
    }

    /**
     * 搜索引擎初始化
     */
    public void initSearchEngine(@NonNull List<BookSourceBean> sourceList) {
        mSourceList.addAll(sourceList);
        executorService = Executors.newFixedThreadPool(threadsNum);
        scheduler = Schedulers.from(executorService);
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 刷新引擎
     *
     * @param sourceList
     */
    public void refreshSearchEngine(@NonNull List<BookSourceBean> sourceList) {
        mSourceList.clear();
        mSourceList.addAll(sourceList);
    }


    /**
     * 关闭引擎
     */
    public void closeSearchEngine() {
        executorService.shutdown();
        if (!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
        compositeDisposable = null;
    }

    /**
     * 搜索关键字
     *
     * @param keyword
     */
    public void search(String keyword) {
        if (mSourceList.size() == 0) {
            System.out.println("没有选中任何书源");
            searchListener.loadMoreFinish(true);
            return;
        }
        searchSuccessNum = 0;
        searchSiteIndex = -1;
        for (int i = 0; i < threadsNum; i++) {
            searchOnEngine(keyword);
        }
    }

    /**
     * 根据书名和作者搜索书籍
     *
     * @param title
     * @param author
     */
    public void search(String title, String author) {
        if (mSourceList.size() == 0) {
            System.out.println("没有选中任何书源");
            searchListener.loadMoreFinish(true);
            return;
        }
        searchSuccessNum = 0;
        searchSiteIndex = -1;
        for (int i = 0; i < threadsNum; i++) {
            searchOnEngine(title, author);
        }
    }

    private synchronized void searchOnEngine(final String keyword) {
        searchSiteIndex++;
        if (searchSiteIndex < mSourceList.size()) {
            BookSourceBean source = mSourceList.get(searchSiteIndex);
            SourceModel.getInstance(source.getSourceName())
                    .searchBook(keyword)
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<BookSearchBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(List<BookSearchBean> bookSearchBeans) {
                            searchSuccessNum++;
                            if (bookSearchBeans != null)
                                searchListener.loadMoreSearchBook(bookSearchBeans);
                            searchOnEngine(keyword);
                        }

                        @Override
                        public void onError(Throwable e) {
                            searchOnEngine(keyword);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            searchListener.loadMoreFinish(true);
        }

    }

    private synchronized void searchOnEngine(final String title, final String author) {
        searchSiteIndex++;
        if (searchSiteIndex < mSourceList.size()) {
            BookSourceBean source = mSourceList.get(searchSiteIndex);
            SourceModel.getInstance(source.getSourceName())
                    .searchBook(title)
                    .subscribeOn(scheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<BookSearchBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onNext(List<BookSearchBean> bookSearchBeans) {
                            searchSuccessNum++;
                            if (bookSearchBeans != null) {
                                List<BookSearchBean> list = new ArrayList<>(1);
                                for (BookSearchBean bean : bookSearchBeans) {
                                    if (bean.getTitle().equals(title)
                                            && bean.getAuthor().equals(author)) {
                                        list.add(bean);
                                        searchListener.loadMoreSearchBook(list);
                                        break;
                                    }
                                }
                            }
                            searchOnEngine(title, author);
                        }

                        @Override
                        public void onError(Throwable e) {
                            searchOnEngine(title, author);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            searchListener.loadMoreFinish(true);

        }

    }


    /************************************************************************/
    public interface OnSearchListener {

        void loadMoreFinish(Boolean isAll);

        void loadMoreSearchBook(List<BookSearchBean> items);

    }
}
