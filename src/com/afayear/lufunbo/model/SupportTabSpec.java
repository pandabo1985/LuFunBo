package com.afayear.lufunbo.model;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import static com.afayear.lufunbo.util.CompareUtils.bundleEquals;
import static com.afayear.lufunbo.util.CompareUtils.objectEquals;
import static com.afayear.lufunbo.util.CompareUtils.classEquals;

public class SupportTabSpec implements Comparable<SupportTabSpec>{

	public final String name;
	public final Object icon;
	public final Class<? extends Fragment> cls;
	public final Bundle args;
	public final int position;

	public SupportTabSpec(final String name, final Object icon, final Class<? extends Fragment> cls, final Bundle args,
			final int position) {
		if (cls == null) throw new IllegalArgumentException("Fragment cannot be null!");
		if (name == null && icon == null)
			throw new IllegalArgumentException("You must specify a name or icon for this tab!");
		this.name = name;
		this.icon = icon;
		this.cls = cls;
		this.args = args;
		this.position = position;

	}

	@Override
	public int compareTo(final SupportTabSpec another) {
		return position - another.position;
	}

	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof SupportTabSpec)) return false;
		final SupportTabSpec spec = (SupportTabSpec) o;
		return objectEquals(name, spec.name) && objectEquals(icon, spec.icon) && classEquals(cls, spec.cls)
				&& bundleEquals(args, spec.args) && position == spec.position;
	}

	@Override
	public String toString() {
		return "TabSpec{name=" + name + ", icon=" + icon + ", cls=" + cls + ", args=" + args + ", position=" + position
				+ "}";
	}

}
