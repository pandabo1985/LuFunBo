package com.afayear.lufunbo.util;


import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.afayear.lufunbo.Constants;
import com.afayear.lufunbo.R;

public class ThemeUtils implements Constants{
	
	private static final String THEME_NAME_TWIDERE = "twidere";
	private static final String THEME_NAME_DARK = "dark";
	private static final String THEME_NAME_LIGHT = "light";
	private static final String THEME_NAME_LIGHT_DARKACTIONBAR = "light_darkactionbar";

	private static final HashMap<String, Integer> THEMES = new HashMap<String, Integer>();
	private static final HashMap<String, Integer> THEMES_SOLIDBG = new HashMap<String, Integer>();
	
	static{
		THEMES.put(THEME_NAME_TWIDERE, R.style.Theme_Twidere);
		THEMES.put(THEME_NAME_DARK, R.style.Theme_Twidere_Dark);
		THEMES.put(THEME_NAME_LIGHT, R.style.Theme_Twidere_Light);
		THEMES.put(THEME_NAME_LIGHT_DARKACTIONBAR, R.style.Theme_Twidere_Light_DarkActionBar);
		THEMES_SOLIDBG.put(THEME_NAME_TWIDERE, R.style.Theme_Twidere_SolidBackground);
		THEMES_SOLIDBG.put(THEME_NAME_DARK, R.style.Theme_Twidere_Dark_SolidBackground);
		THEMES_SOLIDBG.put(THEME_NAME_LIGHT, R.style.Theme_Twidere_Light_SolidBackground);
		THEMES_SOLIDBG.put(THEME_NAME_LIGHT_DARKACTIONBAR, R.style.Theme_Twidere_Light_DarkActionBar_SolidBackground);
	}
	
	
	private ThemeUtils(){
		throw new AssertionError();
	}
	public static void applyBackground(final View view, final int color) {
		if (view == null) return;
		try {
			final Drawable bg = view.getBackground();
			if (bg == null) return;
			final Drawable mutated = bg.mutate();
			if (mutated == null) return;
			mutated.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
			view.invalidate();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getThemeColor(final Context context) {
		if (context == null) return Color.TRANSPARENT;
		final int def = context.getResources().getColor(android.R.color.holo_blue_light);
		try {
			final TypedArray a = context.obtainStyledAttributes(new int[] { android.R.attr.colorActivatedHighlight });
			final int color = a.getColor(0, def);
			a.recycle();
			return color;
		} catch (final Exception e) {
			return def;
		}
	}
	
	public static boolean shouldApplyColorFilter(final Context context) {
		return shouldApplyColorFilter(getThemeResource(context));
	}
	
	public static boolean shouldApplyColorFilter(final int res) {
		switch (res) {
			case R.style.Theme_Twidere:
			case R.style.Theme_Twidere_SwipeBack:
			case R.style.Theme_Twidere_SolidBackground:
			case R.style.Theme_Twidere_SwipeBack_SolidBackground:
			case R.style.Theme_Twidere_Compose:
				return false;
		}
		return true;
	}
	public static boolean shouldApplyColorFilterToTabIcons(final Context context) {
		return shouldApplyColorFilterToTabIcons(getThemeResource(context));
	}
	
	public static boolean shouldApplyColorFilterToTabIcons(final int res) {
		return isLightActionBar(res);
	}
	public static boolean isLightActionBar(final int res) {
		switch (res) {
			case R.style.Theme_Twidere_Light:
			case R.style.Theme_Twidere_Light_SwipeBack:
			case R.style.Theme_Twidere_Light_SolidBackground:
			case R.style.Theme_Twidere_Light_SwipeBack_SolidBackground:
			case R.style.Theme_Twidere_Light_Compose:
				return true;
		}
		return false;
	}
	
	public static int getThemeResource(final Context context) {
		return getThemeResource(getThemeName(context), isSolidBackground(context));
	}
	public static int getThemeResource(final String name, final boolean solid_background) {
		final Integer res = (solid_background ? THEMES_SOLIDBG : THEMES).get(name);
		return res != null ? res : R.style.Theme_Twidere;
	}
	public static String getThemeName(final Context context) {
		if (context == null) return THEME_NAME_TWIDERE;
		final SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref != null ? pref.getString(PREFERENCE_KEY_THEME, THEME_NAME_TWIDERE) : THEME_NAME_TWIDERE;
	}
	
	public static boolean isSolidBackground(final Context context) {
		if (context == null) return false;
		final SharedPreferences pref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return pref != null ? pref.getBoolean(PREFERENCE_KEY_SOLID_COLOR_BACKGROUND, false) : false;
	}

}
