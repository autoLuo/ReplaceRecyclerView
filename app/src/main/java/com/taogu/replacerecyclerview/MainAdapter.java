package com.taogu.replacerecyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import java.util.HashMap;
import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;
    private List<ShopBean> beans;
    private  int itemWidth=0;
    private int mixHeight=0;
    private HashMap<Integer, Float> indexMap = new HashMap<Integer, Float>();
    public  MainAdapter(Context mContext, List<ShopBean> beans){
        this.mContext=mContext;
        this.beans=beans;
        mixHeight=ScreenUtils.getScreenHeight(mContext)/2;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(itemWidth==0){
            holder.mLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    itemWidth=holder.mLayout.getWidth();
                }
            });
        }

        if(!indexMap.containsKey(position)){
            Glide.with(mContext)
                    .asBitmap()
                    .load(beans.get(position).getImage().get(0))//图片地址
                    .placeholder(R.drawable.ic_load)//加载中等待图片
                    .error(R.drawable.ic_load)
                    .skipMemoryCache(true)//true:跳过缓存
                    .override(itemWidth,mixHeight)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            loadImage(resource,holder.mImageView,position);
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override//加载失败的图片显示
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        }
                    });
        }else{
            loadImage(beans.get(position).getImage().get(0),holder.mImageView,indexMap.get(position));
        }

        holder.mTitleView.setText(beans.get(position).getTitle());
        holder.mPriceView.setText(beans.get(position).getPrice()+" 积分");
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.itemClickListener(v,position);
                }
            }
        });
    }

    private void loadImage(Bitmap bitmap, ImageView imageView,int position){
        double ratio = (itemWidth * 1.0) / bitmap.getWidth();//计算要展示图片宽度比例
        int height = (int) (bitmap.getHeight() * ratio);//计算图片高度
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.height = height;//限制图片的最高高度
        indexMap.put(position, (float) height);
        Glide.with(mContext)
                .asBitmap()
                .load(bitmap)
                .transform(new CenterCrop(),new RoundedCornersTransform(DisplayUtils.dp2px(mContext,8),RoundedCornersTransform.TOP))
                .transition(BitmapTransitionOptions.withCrossFade(500))//加载过渡动画
                .into(imageView);
    }
    private void loadImage(String  url,ImageView imageView,float bitmapHeight){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.height = (int) bitmapHeight;//限制图片的最高高度

        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .transform(new CenterCrop(),new RoundedCornersTransform(DisplayUtils.dp2px(mContext,8),RoundedCornersTransform.TOP))
                .transition(BitmapTransitionOptions.withCrossFade(500))//加载过渡动画
                .into(imageView);
    }
    @Override
    public int getItemCount() {
        return beans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTitleView;
        TextView mPriceView;
        LinearLayout mLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.shop_image);
            mTitleView=itemView.findViewById(R.id.shop_title);
            mPriceView=itemView.findViewById(R.id.shop_price);
            mLayout=itemView.findViewById(R.id.shop_layout);

        }
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void itemClickListener(View parent,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}

