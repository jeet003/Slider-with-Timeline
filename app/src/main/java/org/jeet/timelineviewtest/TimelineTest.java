package org.jeet.timelineviewtest;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

//import com.viewpagerindicator.CirclePageIndicator;

import org.jeet.ctimelineview.TimelineRow;
import org.jeet.ctimelineview.TimelineViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import ch.halcyon.squareprogressbar.SquareProgressBar;

import static org.jeet.ctimelineview.TimelineViewAdapter.No;

public class TimelineTest extends AppCompatActivity {

    //Create Timeline Rows List
     ArrayList<TimelineRow> TimelineRowsList = new ArrayList<>();
    List<SliderRow> sliderRows = new ArrayList<>();
    public  static ArrayAdapter<TimelineRow> myAdapter;
    public  static ListView myListView;
   // public static RecyclerView cardRecyclerView;
    // CardAdapter cardAdapter;
    public LinearLayoutManager layoutManager;


    public  static SquareProgressBar squareProgressBar;
    public static ProgressBarAnimation anim;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_test);
        getSupportActionBar().hide();


        // Add Random Rows to the List
        for (int i=0; i<10; i++) {
            Date d=getRandomDate();
            TimelineRowsList.add(
                    new TimelineRow(
                            //Row Id
                            i
                            //Row Date
                            ,d
                            //Row Title or null
                            ,"Title "+i
                            //Row Description or null
                            ,"Description " +i
                            //Row bitmap Image or null
                            , BitmapFactory.decodeResource(getResources(), R.drawable.img_0 + getRandomNumber(0,10))
                            //Row Bellow Line Color
                            , getRandomColor()
                            //Row Bellow Line Size in dp
                            , 7
                            //Row Image Size in dp
                            , 35
                            //Row image Background color or -1
                            , -1
                            //Row Background Size in dp or -1
                            , 35
                    )
            );
            sliderRows.add(
                    new SliderRow(
                    i
                    //Row Date
                    ,d
                    //Row Title or null
                    ,"Title "+i
                    //Row Description or null
                    ,"Description " +i
                    )

            );


        }

        //Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(this, 0, TimelineRowsList,
                //if true, list will be arranged by date
                true);




        //Get the ListView and Bind it with the Timeline Adapter
         myListView = (ListView) findViewById(R.id.timelineListView);
        //ListView myList= (ListView) findViewById(R.id.mylist);
        myListView.setAdapter(myAdapter);
        //myList.setAdapter(myAdapter);




        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(this,0,sliderRows,true));
        PageListener pageListener = new PageListener();
        mPager.setOnPageChangeListener(pageListener);

        //  CirclePageIndicator indicator = (CirclePageIndicator)
        //        findViewById(R.id.indicator);

        //  indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        // indicator.setRadius(5 * density);

        NUM_PAGES =10;


        squareProgressBar = (SquareProgressBar) findViewById(R.id.sprogressbar);
        //squareProgressBar.setProgress(50.0);
        squareProgressBar.setImage(R.drawable.sample);


        //squareProgressBar.drawStartline(true);

        anim = new ProgressBarAnimation(squareProgressBar, 0.0f, 10.0f);
        anim.setDuration(1000);
        squareProgressBar.startAnimation(anim);

        // Auto start of viewpager

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);

            }
        }, 3000, 3000);









        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                           private int currentVisibleItemCount;
                                           private int currentScrollState;
                                           private int currentFirstVisibleItem;
                                           private int totalItem;
                                           private LinearLayout lBelow;


                                           @Override
                                           public void onScrollStateChanged(AbsListView view, int scrollState) {
                                               // TODO Auto-generated method stub
                                               this.currentScrollState = scrollState;
                                               this.isScrollCompleted();
                                           }

                                           @Override
                                           public void onScroll(AbsListView view, int firstVisibleItem,
                                                                int visibleItemCount, int totalItemCount) {
                                               // TODO Auto-generated method stub
                                               this.currentFirstVisibleItem = firstVisibleItem;
                                               this.currentVisibleItemCount = visibleItemCount;
                                               this.totalItem = totalItemCount;


                                           }

                                           private void isScrollCompleted() {
                                               if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                                                       && this.currentScrollState == SCROLL_STATE_IDLE) {
                                                   /** To do code here*/
                                               /*

                                                   for (int i=0; i<15; i++) {
                                                       myAdapter.add(
                                                               new TimelineRow(
                                                                       //Row Id
                                                                       i
                                                                       //Row Date
                                                                       ,getRandomDate()
                                                                       //Row Title or null
                                                                       ,"Title "+i
                                                                       //Row Description or null
                                                                       ,"Description " +i
                                                                       //Row bitmap Image or null
                                                                       ,BitmapFactory.decodeResource(getResources(), R.drawable.img_0 + getRandomNumber(0,10))
                                                                       //Row Bellow Line Color
                                                                       , getRandomColor()
                                                                       //Row Bellow Line Size in dp
                                                                       , getRandomNumber(2,25)
                                                                       //Row Image Size in dp
                                                                       , getRandomNumber(25,40)
                                                                       //Row Background color or -1
                                                                       , getRandomColor()
                                                                       //Row Background Size in dp or -1
                                                                       , getRandomNumber(25,40)
                                                               )
                                                       );
                                                   }
                                                   */

                                               }
                                           }


                                       });

        //if you wish to handle the clicks on the rows
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = TimelineRowsList.get(position);
                Toast.makeText(TimelineTest.this, row.getTitle()+" pos"+position, Toast.LENGTH_SHORT).show();
                //cardRecyclerView.smoothScrollToPosition(position);
                /*
                myAdapter.insert(new TimelineRow(
                        //Row Id
                        TimelineRowsList.size()
                        //Row Date
                        ,new Date()
                        //Row Title or null
                        ,"Title "+TimelineRowsList.size()
                        //Row Description or null
                        ,"Description " +TimelineRowsList.size()
                        //Row Image
                        ,null
                        //Row Bellow Line Color
                        , getRandomColor()
                        //Row Bellow Line Size in dp
                        , getRandomNumber(2,25)
                        //Row Image Size in dp
                        , getRandomNumber(25,40)
                        //Row Background color or -1
                        , getRandomColor()
                        //Row Background Size in dp or -1
                        , getRandomNumber(25,40)
                )
                        //insert position
                        ,0)
                ;
                */
            }
        };
        myListView.setOnItemClickListener(adapterListener);
        //Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);




        //squareProgressBar.setWidth(20);


        //squareProgressBar.setIndeterminate(true);




    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            android.os.Process.killProcess(android.os.Process.myPid());// Kill the app on click of back button.
        }
        return super.onKeyDown(keyCode, event);
    }


    //Random Methods
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public int getRandomNumber(int min, int max){
        return  min + (int)(Math.random() * max);
    }



    public Date getRandomDate () {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        Date endDate = new Date();
        try {
            startDate = sdf.parse("02/09/2015");
            long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            endDate = new Date(random);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }


/*
    private void init() {


        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(TimelineTest.this,0,sliderRows,true));


      //  CirclePageIndicator indicator = (CirclePageIndicator)
        //        findViewById(R.id.indicator);

      //  indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
       // indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        /*
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }
    */

    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            anim = new ProgressBarAnimation(squareProgressBar, No*10+10, position*10+10);
            anim.setDuration(500);
            squareProgressBar.startAnimation(anim);
            currentPage=position;
            No = position;
            TimelineTest.myAdapter.notifyDataSetChanged();
            myListView.smoothScrollToPosition(No);
        }
    }
}


