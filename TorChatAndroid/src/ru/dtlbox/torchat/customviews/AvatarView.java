package ru.dtlbox.torchat.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import android.widget.ImageView;
import android.widget.LinearLayout;

public class AvatarView extends ImageView {
	
	Bitmap frameImage = null;
	Context context;
	boolean isReady = false;
	
	public AvatarView(Context context) {
		super(context);
		this.context = context;
	}

	public AvatarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public void setFrameImage(Bitmap frameImage) {
		this.frameImage = frameImage;
		this.setLayoutParams(new LinearLayout.LayoutParams(frameImage.getWidth(),frameImage.getHeight()));
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
	    
	    Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
	    Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

	    int w = getWidth(), h = getHeight();


	    Bitmap roundBitmap =  getCroppedBitmap(bitmap, w, frameImage);
	    canvas.drawBitmap(roundBitmap, 0,0, null);

	}

	public static Bitmap getCroppedBitmap(Bitmap bmp, int radius, Bitmap frame) {
	    Bitmap sbmp;
	    if(bmp.getWidth() != radius || bmp.getHeight() != radius)
	        sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
	    else
	        sbmp = bmp;
	    Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
	            sbmp.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xffa19774;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

	    paint.setAntiAlias(true);
	    paint.setFilterBitmap(true);
	    paint.setDither(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(Color.parseColor("#BAB399"));
	    canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
	            sbmp.getWidth() / 2+0.1f, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(sbmp, rect, rect, paint);
	    
	    Paint paint2 = new Paint();
	    paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
	    Matrix matrix = new Matrix();
	    matrix.setScale((float)sbmp.getWidth()/frame.getWidth(), (float)sbmp.getHeight()/frame.getHeight());
	    if(frame!=null) {
	    	canvas.drawBitmap(frame, matrix, paint2);
	    }
	    return output;
	}
	

}
