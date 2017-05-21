package org.jeet.timelineviewtest;

/**
 * Created by jeet on 21/3/17.
 */

import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
        import android.content.Context;
        import android.os.Parcelable;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ch.halcyon.squareprogressbar.SquareProgressBar;


public class SlidingImage_Adapter extends PagerAdapter {


    private List<SliderRow> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    private TextView txtDate;
    private TextView txtTitle;
    private TextView txtDesc;

    private Resources res;

    private String AND;




    public SlidingImage_Adapter(Context context, int resource, List<SliderRow> objects, boolean orderTheList) {
        this.context = context;

        inflater = LayoutInflater.from(context);
        res = context.getResources();
        AND = res.getString(org.jeet.ctimelineview.R.string.AND);
        if (orderTheList)
            this.IMAGES = rearrangeByDate(objects);
        else
            this.IMAGES = objects;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        txtDate = (TextView)imageLayout.findViewById(R.id.crowDate);
        txtTitle = (TextView)imageLayout.findViewById(R.id.crowTitle);
        txtDesc = (TextView)imageLayout.findViewById(R.id.crowDesc);
        //imageView.setImageResource(IMAGES.get(position));
        SliderRow sr=IMAGES.get(position);
        txtTitle.setText(sr.getTitle());
        txtDate.setText(getPastTime(sr.getDate()));
        txtDesc.setText(sr.getDescription());



        view.addView(imageLayout, 0);



        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private String getPastTime(Date date) {

        if (date == null) return "";
        StringBuilder dateText = new StringBuilder();
        Date today = new Date ();
        long diff = (today.getTime() - date.getTime()) / 1000;

        long years = diff / (60*60*24*30*12);
        long months = (diff / (60*60*24*30)) % 12;
        long days = (diff / (60*60*24)) %30;
        long hours = (diff / (60*60)) %24;
        long minutes = (diff / 60) % 60;
        long seconds = diff % 60;

        if (years > 0) {
            appendPastTime(dateText, years, org.jeet.ctimelineview.R.plurals.years, months, org.jeet.ctimelineview.R.plurals.months);
        }

        else if (months > 0) {
            appendPastTime(dateText, months, org.jeet.ctimelineview.R.plurals.months, days, org.jeet.ctimelineview.R.plurals.days);
        }

        else if (days > 0) {
            appendPastTime(dateText, days, org.jeet.ctimelineview.R.plurals.days, hours, org.jeet.ctimelineview.R.plurals.hours);
        }

        else if (hours > 0) {
            appendPastTime(dateText, hours, org.jeet.ctimelineview.R.plurals.hours, minutes, org.jeet.ctimelineview.R.plurals.minutes);
        }

        else if (minutes > 0) {
            appendPastTime(dateText, minutes, org.jeet.ctimelineview.R.plurals.minutes, seconds, org.jeet.ctimelineview.R.plurals.seconds);
        }

        else if (seconds >= 0) {
            dateText.append(res.getQuantityString(org.jeet.ctimelineview.R.plurals.seconds, (int) seconds, (int) seconds));
        }

        return dateText.toString();
    }

    private void appendPastTime(StringBuilder s,
                                long timespan, int nameId,
                                long timespanNext, int nameNextId) {

        s.append(res.getQuantityString(nameId, (int) timespan, timespan ));
        if (timespanNext > 0) {
            s.append(' ').append(AND).append(' ');
            s.append(res.getQuantityString(nameNextId, (int) timespanNext, timespanNext));
        }
    }
    private List<SliderRow> rearrangeByDate (List<SliderRow> objects) {
        if(objects.get(0) == null || objects.get(0).getDate() == null ) return objects;
        int size = objects.size();
        for (int i = 0; i< size-1; i++) {
            for (int j = i+1; j < size ; j++) {
                if (objects.get(i).getDate().compareTo(objects.get(j).getDate()) <= 0)
                    Collections.swap(objects, i, j);
            }

        }
        return objects;
    }


}