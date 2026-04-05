package com.example.sgsitsnavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ArrowOverlayView extends View {

    public static class ArrowData {
        public float angleDegrees;
        public boolean visible;
        public String label;
        public float verticalOffset;

        public ArrowData(float angleDegrees, boolean visible, String label, float verticalOffset) {
            this.angleDegrees = angleDegrees;
            this.visible = visible;
            this.label = label;
            this.verticalOffset = verticalOffset;
        }
    }

    private final Paint arrowPaint = new Paint();
    private final Paint textPaint = new Paint();

    private final List<ArrowData> arrows = new ArrayList<>();

    public ArrowOverlayView(Context context) {
        super(context);
        init();
    }

    public ArrowOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrowOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        arrowPaint.setAntiAlias(true);
        arrowPaint.setColor(Color.GREEN);
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(34f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void updateArrows(List<ArrowData> newArrows) {
        arrows.clear();
        if (newArrows != null) {
            arrows.addAll(newArrows);
        }
        invalidate();
    }

    public void clearArrows() {
        arrows.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (arrows.isEmpty()) return;

        float cx = getWidth() / 2f;
        float baseY = getHeight() * 0.72f;
        float arrowSize = Math.min(getWidth(), getHeight()) * 0.09f;

        for (ArrowData arrow : arrows) {
            if (!arrow.visible) continue;

            float cy = baseY - arrow.verticalOffset;

            canvas.save();
            canvas.translate(cx, cy);
            canvas.rotate(arrow.angleDegrees);

            Path path = new Path();
            path.moveTo(0, -arrowSize);
            path.lineTo(arrowSize * 0.55f, arrowSize * 0.4f);
            path.lineTo(arrowSize * 0.18f, arrowSize * 0.4f);
            path.lineTo(arrowSize * 0.18f, arrowSize);
            path.lineTo(-arrowSize * 0.18f, arrowSize);
            path.lineTo(-arrowSize * 0.18f, arrowSize * 0.4f);
            path.lineTo(-arrowSize * 0.55f, arrowSize * 0.4f);
            path.close();

            canvas.drawPath(path, arrowPaint);
            canvas.restore();

            if (arrow.label != null && !arrow.label.isEmpty()) {
                canvas.drawText(arrow.label, cx, cy + arrowSize + 45f, textPaint);
            }
        }
    }
}

//package com.example.sgsitsnavigation;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.util.AttributeSet;
//import android.view.View;
//
//public class ArrowOverlayView extends View {
//
//    private final Paint arrowPaint = new Paint();
//    private final Paint textPaint = new Paint();
//
//    private float arrowAngleDegrees = 0f;
//    private boolean showArrow = false;
//    private String label = "";
//
//    public ArrowOverlayView(Context context) {
//        super(context);
//        init();
//    }
//
//    public ArrowOverlayView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public ArrowOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        arrowPaint.setAntiAlias(true);
//        arrowPaint.setColor(Color.GREEN);
//        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//
//        textPaint.setAntiAlias(true);
//        textPaint.setColor(Color.WHITE);
//        textPaint.setTextSize(42f);
//        textPaint.setTextAlign(Paint.Align.CENTER);
//    }
//
//    public void updateArrow(float angleDegrees, boolean visible, String text) {
//        this.arrowAngleDegrees = angleDegrees;
//        this.showArrow = visible;
//        this.label = text;
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        if (!showArrow) return;
//
//        float cx = getWidth() / 2f;
//        float cy = getHeight() * 0.72f;
//
//        float arrowSize = Math.min(getWidth(), getHeight()) * 0.12f;
//
//        canvas.save();
//        canvas.translate(cx, cy);
//        canvas.rotate(arrowAngleDegrees);
//
//        Path path = new Path();
//        path.moveTo(0, -arrowSize);
//        path.lineTo(arrowSize * 0.55f, arrowSize * 0.4f);
//        path.lineTo(arrowSize * 0.18f, arrowSize * 0.4f);
//        path.lineTo(arrowSize * 0.18f, arrowSize);
//        path.lineTo(-arrowSize * 0.18f, arrowSize);
//        path.lineTo(-arrowSize * 0.18f, arrowSize * 0.4f);
//        path.lineTo(-arrowSize * 0.55f, arrowSize * 0.4f);
//        path.close();
//
//        canvas.drawPath(path, arrowPaint);
//        canvas.restore();
//
//        if (label != null && !label.isEmpty()) {
//            canvas.drawText(label, cx, cy + arrowSize + 70f, textPaint);
//        }
//    }
//}
