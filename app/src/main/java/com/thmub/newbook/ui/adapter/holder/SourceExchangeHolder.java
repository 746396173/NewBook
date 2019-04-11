package com.thmub.newbook.ui.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookSearchBean;


/**
 * Created by Zhouas666 on 2019-04-11
 * Github: https://github.com/zas023
 */
public class SourceExchangeHolder extends ViewHolderImpl<BookSearchBean> {

    TextView itemSourceTvTitle;
    TextView itemSourceTvChapter;
    ImageView itemSourceIv;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_source_change;
    }

    @Override
    public void initView() {
        itemSourceTvTitle = findById(R.id.item_source_tv_title);
        itemSourceTvChapter = findById(R.id.item_source_tv_chapter);
        itemSourceIv = findById(R.id.item_source_iv);
    }

    @Override
    public void onBind(BookSearchBean data, int pos) {
        itemSourceTvTitle.setText(data.getSource());
        itemSourceTvChapter.setText(data.getLink());
        if (data.isSelected())
            itemSourceIv.setVisibility(View.VISIBLE);
        else
            itemSourceIv.setVisibility(View.GONE);
    }
}