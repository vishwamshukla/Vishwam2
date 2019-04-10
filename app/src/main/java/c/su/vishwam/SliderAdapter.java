package c.su.vishwam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slider_images = {
            R.drawable.opd,
            R.drawable.ipd,
            R.drawable.emergency
    };

    public String[] slider_heading = {
            "OPD",
            "IPD",
            "Emergency"
    };

    public String[] slider_description ={
            "Manage your Opd patient in a very better way",
            "Take Intensive care of your IPD patients",
            "Save lives with handling emergencies quickly"
    };

    @Override
    public int getCount() {
        return slider_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slideimageView);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_description);

        slideImageView.setImageResource(slider_images[position]);
        slideHeading.setText(slider_heading[position]);
        slideDescription.setText(slider_description[position]);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
