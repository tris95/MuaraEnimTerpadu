package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;

public class SlideViewAdapter extends PagerAdapter {
    private Context context;
    private Integer[] image = {R.drawable.jalan, R.drawable.jlntol};

    public SlideViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = Objects.requireNonNull(layoutInflater).inflate(R.layout.slide, null);
        ImageView imageView = view.findViewById(R.id.imgslide);
        imageView.setImageResource(image[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (position == 0) {
//                    Toast.makeText(context, "slide 1", Toast.LENGTH_SHORT).show();
//                } else if (position == 1) {
//                    Toast.makeText(context, "slide 2", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
