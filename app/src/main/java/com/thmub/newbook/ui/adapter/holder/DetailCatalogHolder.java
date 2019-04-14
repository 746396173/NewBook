package com.thmub.newbook.ui.adapter.holder;

import android.widget.TextView;

import com.thmub.newbook.R;
import com.thmub.newbook.base.adapter.ViewHolderImpl;
import com.thmub.newbook.bean.BookChapterBean;

/**
 * Created by Zhouas666 on 2019-04-02
 * Github: https://github.com/zas023
 * <p>
 * 详情页目录holder
 */
public class DetailCatalogHolder extends ViewHolderImpl<BookChapterBean> {

    private TextView itemTvBookInfo;

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_book_detail_info;
    }

    @Override
    public void initView() {
        itemTvBookInfo = findById(R.id.item_book_detail_info);
    }

    @Override
    public void onBind(BookChapterBean data, int pos) {
        itemTvBookInfo.setText("第" + (data.getChapterIndex() + 1) + "章  " + data.getChapterTitle());
        //第一项为书籍简介
//        if (pos == 0) {
//            //注意父层是RecyclerView，所以使用RecyclerView.LayoutParams
//            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemLlBookInfo.getLayoutParams();
//            lp.setMargins(0, 8, 0, 8);
//            //两个字符缩进
//            itemTvBookInfo.setText("\t\t\t\t"+data.getChapterTitle());
//        }else {
//            //itemTvBookInfo.setText("第 "+data.getChapterIndex()+1+" 章  "+data.getChapterTitle());
//            //data.getChapterIndex()+1会被jvm优化成字符串
//            itemTvBookInfo.setText("第 "+(data.getChapterIndex()+1)+" 章  "+data.getChapterTitle());
//        }
    }
}
