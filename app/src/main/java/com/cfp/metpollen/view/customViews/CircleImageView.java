package com.cfp.metpollen.view.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.cfp.metpollen.R;

/**
 * Created by AhmedAbbas on 2/27/2018.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {

    private static int SQUARE = 0;
    private static int SQUARE_WITH_BORDER = 1;
    private static int SQUARE_WITH_ROUNDED_CORNERS = 2;
    private static int SQUARE_WITH_ROUNDED_CORNERED_BORDER = 3;
    private static int CIRCLE_WITH_BORDER = 4;
    private static int CIRCLE_WITHOUT_BORDER = 5;
    int type = SQUARE;
    int color = Color.TRANSPARENT;
    int borderWidth = 0;
    int cornerRadius = 1;

    public CircleImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);

        TypedArray ta = ctx.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0);
        color = ta.getColor(R.styleable.CircleImageView_border_color, Color.TRANSPARENT);
        borderWidth = ta.getInt(R.styleable.CircleImageView_border_width, 0);
        cornerRadius = ta.getInt(R.styleable.CircleImageView_corner_radius, 1);
        type = ta.getInt(R.styleable.CircleImageView_type, SQUARE);
    }

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius, int color, int borderWidth, int type, int divider) {
        Bitmap finalBitmap;

        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);


        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#D1D1D1"));
        RectF rec = new RectF(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());

        if (type == CIRCLE_WITH_BORDER || type == CIRCLE_WITHOUT_BORDER) {
            canvas.drawCircle(finalBitmap.getWidth() / divider, finalBitmap.getHeight() / divider, finalBitmap.getWidth() / divider, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
        } else if (type == SQUARE_WITH_ROUNDED_CORNERS || type == SQUARE_WITH_ROUNDED_CORNERED_BORDER) {
            canvas.drawRoundRect(rec, finalBitmap.getWidth() / divider, finalBitmap.getWidth() / divider, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
        } else if (type == SQUARE) {
            canvas.drawRect(rec, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
        }

        if (type == SQUARE_WITH_BORDER || type == CIRCLE_WITH_BORDER || type == SQUARE_WITH_ROUNDED_CORNERED_BORDER) {
            Paint p = new Paint();
        /*    if(type == SQUARE_WITH_BORDER)
            {
                p.setStrokeCap(Paint.Cap.SQUARE);
            }
            else*/
            if (type == SQUARE_WITH_ROUNDED_CORNERED_BORDER || type == CIRCLE_WITH_BORDER) {
                p.setStrokeCap(Paint.Cap.ROUND);
            }

            p.setStrokeWidth(borderWidth);
            p.setColor(color);
            p.setStyle(Paint.Style.STROKE);
            // BORDER
            RectF r = new RectF(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawRoundRect(r, finalBitmap.getWidth() / divider, finalBitmap.getWidth() / divider, p);
        }
        return output;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setToCircleWithoutBorder() {
        type = CIRCLE_WITHOUT_BORDER;
        this.cornerRadius = 2;
    }

    public void setToCircleWithBorder(String color, int borderWidth) {
        type = CIRCLE_WITH_BORDER;
        this.color = Color.parseColor(color);
        this.borderWidth = borderWidth;
        this.cornerRadius = 2;
    }

    public void setToCircleWithBorder(int color, int borderWidth) {
        type = CIRCLE_WITH_BORDER;
        this.color = color;
        this.borderWidth = borderWidth;
        this.cornerRadius = 2;
    }

    public void setToSquareWithBorder(int color, int borderWidth) {
        type = SQUARE_WITH_BORDER;
        this.color = color;
        this.borderWidth = borderWidth;
        this.cornerRadius = 1;
    }

    public void setToSquareWithBorder(String color, int borderWidth) {
        type = SQUARE_WITH_BORDER;
        this.color = Color.parseColor(color);
        this.borderWidth = borderWidth;
        this.cornerRadius = 1;
    }

    public void setToSquareWithRoundedCorners() {
        type = SQUARE_WITH_ROUNDED_CORNERS;
        this.cornerRadius = 10;
    }

    public void setToSquareWithRoundedCorneredBorder(String color, int borderWidth) {
        type = SQUARE_WITH_ROUNDED_CORNERED_BORDER;
        this.color = Color.parseColor(color);
        this.borderWidth = borderWidth;
        this.cornerRadius = 10;
    }

    public void setToSquareWithRoundedCorneredBorder(int color, int borderWidth) {
        type = SQUARE_WITH_ROUNDED_CORNERED_BORDER;
        this.color = color;
        this.borderWidth = borderWidth;
        this.cornerRadius = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        Bitmap b = getBitmap();

        if (b != null) {
            try {
                Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

                int radius = getWidth(); //Radius = width

                Bitmap roundBitmap = getRoundedCroppedBitmap(bitmap, radius, color, borderWidth, type, cornerRadius);

                canvas.drawBitmap(roundBitmap, 0, 0, null);
            } catch (OutOfMemoryError error) {
                //error.printStackTrace();
            }
        }

    }

    public Bitmap getBitmap() {
        try {
            Drawable drawable = this.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) this.getDrawable()).getBitmap();
            }
        } catch (Throwable t) {

        }
        return null;
    }


}