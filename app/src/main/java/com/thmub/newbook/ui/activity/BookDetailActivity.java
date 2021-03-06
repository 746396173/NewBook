package com.thmub.newbook.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPActivity;
import com.thmub.newbook.bean.BookChapterBean;
import com.thmub.newbook.bean.BookDetailBean;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.ShelfBookBean;
import com.thmub.newbook.model.local.BookShelfRepository;
import com.thmub.newbook.model.local.BookSourceRepository;
import com.thmub.newbook.presenter.BookDetailPresenter;
import com.thmub.newbook.presenter.contract.BookDetailContract;
import com.thmub.newbook.ui.adapter.DetailCatalogAdapter;
import com.thmub.newbook.ui.adapter.DetailFindAdapter;
import com.thmub.newbook.ui.dialog.SourceExchangeDialog;
import com.thmub.newbook.utils.ToastUtils;
import com.thmub.newbook.widget.DashlineItemDivider;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 * <p>
 * 书籍详情activity
 */
public class BookDetailActivity extends BaseMVPActivity<BookDetailContract.Presenter>
        implements BookDetailContract.View {

    /****************************Constant*********************************/
    public static final String RESULT_IS_COLLECTED = "result_is_collected";
    public static final String EXTRA_BOOK = "extra_book";

    public static final int REQUEST_CODE_READ = 0;

    /*****************************View***********************************/
    @BindView(R.id.book_detail_iv_cover)
    ImageView mIvCover;
    @BindView(R.id.book_detail_tv_author)
    TextView mTvAuthor;
    @BindView(R.id.book_detail_tv_type)
    TextView mTvType;
    @BindView(R.id.book_detail_tv_word_count)
    TextView mTvWordCount;

    @BindView(R.id.book_detail_tv_add)
    TextView bookDetailTvAdd;
    @BindView(R.id.book_detail_tv_open)
    TextView bookDetailTvOpen;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.book_detail_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.fl_download_book)
    FrameLayout flDownloadBook;
    @BindView(R.id.fl_add_bookcase)
    FrameLayout flAddBookcase;
    @BindView(R.id.fl_open_book)
    FrameLayout flOpenBook;
    @BindView(R.id.book_detail_rv_catalog)
    RecyclerView bookDetailRvCatalog;
    @BindView(R.id.book_detail_rv_find)
    RecyclerView bookDetailRvFind;

    private SourceExchangeDialog mSourceDialog;
    /****************************Variable*********************************/
    private BookSearchBean mSearchBook;
    private BookDetailBean mDetailBook;
    private ShelfBookBean mShelfBook;

    private DetailCatalogAdapter mCatalogAdapter;
    private DetailFindAdapter mFindAdapter;

    /**************************Initialization******************************/
    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mSearchBook = getIntent().getParcelableExtra(EXTRA_BOOK);
        mDetailBook = new BookDetailBean(mSearchBook);
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(mSearchBook.getTitle());
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        initBookInfo();

        //catalog
        mCatalogAdapter = new DetailCatalogAdapter();
        bookDetailRvCatalog.setLayoutManager(new LinearLayoutManager(mContext));
        bookDetailRvCatalog.setAdapter(mCatalogAdapter);
        bookDetailRvCatalog.addItemDecoration(new DashlineItemDivider());

        //find books
        mFindAdapter = new DetailFindAdapter();
        bookDetailRvFind.setLayoutManager(new GridLayoutManager(mContext, 4));
        bookDetailRvFind.setAdapter(mFindAdapter);

        //Dialog
        mSourceDialog = new SourceExchangeDialog(this, mShelfBook);

    }

    //Book title info
    private void initBookInfo() {

        if (mShelfBook == null)
            return;

        Glide.with(mContext).load(mShelfBook.getCover())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into(mIvCover);
        mTvAuthor.setText(mShelfBook.getAuthor());
        mTvType.setText(mShelfBook.getLink());
        mTvWordCount.setText(mShelfBook.getSourceTag());
        mTvDesc.setText("\t\t\t\t" + mShelfBook.getDesc());

        //Button
        if (mShelfBook.isCollected()) {
            bookDetailTvAdd.setText("移除书架");
            bookDetailTvOpen.setText("继续阅读");
        }
    }

    @Override
    protected void initClick() {
        super.initClick();
        //推荐书籍
        mFindAdapter.setOnItemClickListener((view, pos) -> startActivity(new Intent(mContext, BookDetailActivity.class)
                .putExtra(BookDetailActivity.EXTRA_BOOK, mFindAdapter.getItem(pos))));
        //换源对话框
        mSourceDialog.setOnSourceChangeListener(bean -> {
            startActivity(new Intent(mContext, BookDetailActivity.class)
                    .putExtra(BookDetailActivity.EXTRA_BOOK,bean));
            finish();
        });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mPresenter.loadDetailBook(mSearchBook);
    }

    /**************************Transaction********************************/
    @Override
    protected BookDetailContract.Presenter bindPresenter() {
        return new BookDetailPresenter();
    }

    @Override
    public void finishLoadDetailBook(BookDetailBean item) {

        mDetailBook = item;
        //查找书架，判断是否是已收藏
        mShelfBook = BookShelfRepository.getInstance().getShelfBook(mDetailBook.getTitle(), mDetailBook.getAuthor());
        if (mShelfBook == null) {
            mShelfBook = mDetailBook.getShelfBook();
            mShelfBook.setCollected(false);
        } else {
            mShelfBook.setCollected(true);
        }
        initBookInfo();
        mSourceDialog.setShelfBook(mShelfBook);
        mPresenter.loadCatalogs(mShelfBook);
        mPresenter.loadFindBooks(mDetailBook);
    }

    @Override
    public void finishLoadCatalogs(List<BookChapterBean> items) {
        mCatalogAdapter.addItems(items);
    }

    @Override
    public void finishLoadFindBooks(List<BookSearchBean> items) {
        if (items.size() > 8)
            mFindAdapter.addItems(items.subList(0, 8));
        else
            mFindAdapter.addItems(items);
    }

    @Override
    public void finishRemoveBook(Integer i) {

    }

    @Override
    public void showError(Throwable e) {
        ToastUtils.showError(mContext,e.getMessage());
    }

    @Override
    public void complete() {

    }

    /********************************Event***************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    /**
     * 导航栏菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_source:  //换源
                if (mShelfBook == null)
                    ToastUtils.showInfo(mContext, "正在加载书籍");
                mSourceDialog.show();
                break;
            case R.id.action_edit_source:  //编辑源
                startActivity(new Intent(mContext, SourceEditActivity.class)
                        .putExtra(SourceEditActivity.EXTRA_BOOK_SOURCE
                                , BookSourceRepository.getInstance()
                                        .getBookSourceByName(mShelfBook.getSourceTag())));
                break;
            case R.id.action_reload:  //重新加载
                initWidget();
                processLogic();
                break;
            case R.id.action_open_link:  //打开链接
                Uri uri = Uri.parse(mShelfBook.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 展开简介
     */
    @OnClick(R.id.book_detail_tv_desc)
    protected void showMoreDesc(){
        if (mTvDesc.getMaxLines()==5)
            mTvDesc.setMaxLines(15);
        else
            mTvDesc.setMaxLines(5);
    }

    /**
     * 章节列表
     */
    @OnClick(R.id.book_detail_tv_catalog_more)
    public void goToMoreChapter() {
        startActivity(new Intent(mContext, CatalogActivity.class)
                .putExtra(CatalogActivity.EXTRA_BOOK, new ShelfBookBean(mShelfBook)));
    }

    /**
     * 发现书籍列表
     */
    @OnClick(R.id.book_detail_tv_find_more)
    public void goToMoreBook() {
        startActivity(new Intent(mContext, FindBookActivity.class)
                .putExtra(FindBookActivity.EXTRA_BOOK, mDetailBook));
    }

    /**
     * 加入书架
     */
    @OnClick(R.id.fl_add_bookcase)
    public void addToShelf() {
        if (mShelfBook.isCollected()) {
            //放弃点击
            //因为需要同时删除数据库中的章节和缓存的文件，所以采用异步的方式
            mPresenter.removeShelfBook(mShelfBook);
            bookDetailTvAdd.setText("加入书架");
            bookDetailTvOpen.setText("开始阅读");
            mShelfBook.setCollected(false);
        } else {
            BookShelfRepository.getInstance().saveShelfBook(mShelfBook);
            bookDetailTvAdd.setText("移除书架");
            bookDetailTvOpen.setText("继续阅读");
            mShelfBook.setCollected(true);
        }
    }

    /**
     * 开始阅读
     */
    @OnClick(R.id.fl_open_book)
    public void goToRead() {
        mShelfBook.setCollected(mShelfBook.isCollected());
        startActivityForResult(new Intent(mContext, ReadActivity.class)
                .putExtra(ReadActivity.EXTRA_BOOK, mShelfBook), REQUEST_CODE_READ);
    }

    /**
     * 响应上层返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果进入阅读页面收藏了，就需要返回改变收藏按钮
        if (resultCode == RESULT_OK) {

            if (data == null) return;

            mShelfBook.setCollected(data.getBooleanExtra(RESULT_IS_COLLECTED, false));

            if (mShelfBook.isCollected()) {
                bookDetailTvAdd.setText("移除书架");
                bookDetailTvOpen.setText("继续阅读");
            }
        }
    }

}
