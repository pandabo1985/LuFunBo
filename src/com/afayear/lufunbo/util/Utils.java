package com.afayear.lufunbo.util;

import java.io.File;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.afayear.lufunbo.Constants;


public final class Utils implements Constants {
	
	public static Bitmap getTabIconFromFile(final File file, final Resources res) {
		if (file == null || !file.exists()) return null;
		final String path = file.getPath();
		final BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, o);
		if (o.outHeight <= 0 || o.outWidth <= 0) return null;
		o.inSampleSize = (int) (Math.max(o.outWidth, o.outHeight) / (48 * res.getDisplayMetrics().density));
		o.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, o);
	}
	
}
