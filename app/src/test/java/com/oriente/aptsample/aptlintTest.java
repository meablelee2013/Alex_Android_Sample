package com.oriente.aptsample;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class aptlintTest {

    @Deprecated
    enum WeekDay {
        Monday, Sunday
    }

    public static final int Monday = 1;
    public static final int Sunday = 2;

    @IntDef({Monday, Sunday})
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    @interface WekDay {

    }

    @WekDay
    public static int mCurrentIntWeekDay;

    @Deprecated
    public static WeekDay mCurrentWeekDay;

    @Deprecated
    public static void setCurrentDay(WeekDay weekDay) {
        mCurrentWeekDay = weekDay;
    }

    public static void setCurrentDay(@WekDay int weekDay) {
        mCurrentIntWeekDay = weekDay;
    }

    public static void main(String[] args) {
        setCurrentDay(WeekDay.Monday);

        setCurrentDay(Monday);
    }
}
