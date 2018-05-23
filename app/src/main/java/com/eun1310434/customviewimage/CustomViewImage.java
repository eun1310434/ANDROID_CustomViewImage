/*=====================================================================
□ INFORMATION
  ○ Data : 23.05.2018
  ○ Mail : eun1310434@naver.com
  ○ Blog : https://blog.naver.com/eun1310434
  ○ Reference : Do it android app Programming

□ FUNCTION
  ○ 뷰를 상속하여 새로운 뷰를 만들기
     - XML레이아웃으로 만든 것이 아닌 직접 만든 뷰 설정
  ○ 터치 이벤트
     - 터치한 위치에 그림그리기
     - 처음 터치한 좌표, 터치 중인 좌표, 마지막 터치한 좌표를 출력

□ STUDY
  ○ View
     - This class represents the basic building block for user interface components.
       A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
       View is the base class for widgets, which are used to create interactive UI components (buttons, text fields, etc.).
       The ViewGroup subclass is the base class for layouts, which are invisible containers that hold other Views (or other ViewGroups) and define their layout properties.
=====================================================================*/

package com.eun1310434.customviewimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;


public class CustomViewImage extends View {

	// cache bitmap
	private Bitmap cacheBitmap; //메모리에 뷰의 객체와 동일한 Bitmap을 만들어 화면에 바로 전시

	// cache canvas
	private Canvas cacheCanvas;

	private Paint mPaint;

	public CustomViewImage(Context context) {
		super(context);

		// create a new paint object
		mPaint = new Paint();

	}

	//To be called when the size is changed.
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		createCacheBitmap(w, h);
		testDrawing();
	}

	/**
	 * @param w width
	 * @param h height
	 */
	// Create the cache bitmap
	private void createCacheBitmap(int w, int h) {
		// 더블 버퍼링 - 메모리에서 비트맵을 또 만들어서
		// ARGB_8888(RGB 색상으로 된 이미지 만듬)
		cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		cacheCanvas = new Canvas();
		cacheCanvas.setBitmap(cacheBitmap);
	}

	private void testDrawing() {

		cacheCanvas.drawColor(Color.WHITE);

		mPaint.setColor(Color.BLACK);
		cacheCanvas.drawRect(100, 100, 400, 400, mPaint);

		// create and draw images
		Bitmap srcImg = BitmapFactory.decodeResource(getResources(), R.drawable.waterdrop);
		cacheCanvas.drawBitmap(srcImg, 10, 200, mPaint);

		Matrix horInverseMatrix = new Matrix();
		horInverseMatrix.setScale(-1, 1);
		Bitmap horInverseImg = Bitmap.createBitmap(srcImg, 0, 0,
				srcImg.getWidth(), srcImg.getHeight(), horInverseMatrix, false);
		cacheCanvas.drawBitmap(horInverseImg, 30, 130, mPaint);

		Matrix verInverseMatrix = new Matrix();
		verInverseMatrix.setScale(1, -1);
		Bitmap verInverseImg = Bitmap.createBitmap(srcImg, 0, 0,
				srcImg.getWidth(), srcImg.getHeight(), verInverseMatrix, false);
		cacheCanvas.drawBitmap(verInverseImg, 30, 230, mPaint);

		// mask
		Bitmap srcImg2 = BitmapFactory.decodeResource(getResources(), R.drawable.face);
		mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
		Bitmap scaledImg = Bitmap.createScaledBitmap(srcImg2,
				srcImg2.getWidth()*2, srcImg2.getHeight()*2, false);
		cacheCanvas.drawBitmap(scaledImg, 400, 400, mPaint);
		
	}

	//Draw the bitmap
	protected void onDraw(Canvas canvas) {
		if (cacheBitmap != null) {
			canvas.drawBitmap(cacheBitmap, 0, 0, null);
		}
	}

}
