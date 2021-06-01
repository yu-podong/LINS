package com.yupodong.lins.Scheduler;

import android.text.style.LineBackgroundSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private static final float DEFAULT_DOT_RADIUS = 4;
    //Note that negative values indicate a relative offset to the LEFT
    private static final int[] xOffsets = new int[]{0,-10,10,-20};
    private int color;
    private HashSet<CalendarDay> dates;
    private float dotRadius =5;
    private int spanType;


    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    public EventDecorator(int color, float dotRadius, int spanType) {
        this.color = color;
        this.dotRadius = dotRadius;
        this.dates = new HashSet<>();
        this.spanType = spanType;
    }

    /*Note! I added this method so that I can add dates after object creation!*/
    public boolean addDate(CalendarDay day){
        return dates.add(day);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return dates.contains(day);
    }

    @Override
        public void decorate(DayViewFacade view) {

            LineBackgroundSpan span = new CustomSpan(color, xOffsets[spanType]);
        view.addSpan(span);
        //view.addSpan(new DotSpan(5,color));
    }
}
