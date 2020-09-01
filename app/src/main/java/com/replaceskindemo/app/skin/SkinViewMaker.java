package com.replaceskindemo.app.skin;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


public class SkinViewMaker {

	private static final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new HashMap<String, Constructor<? extends View>>();
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
	
	public static View make(View parent, String name, Context context,AttributeSet attrs){
		View view = null;
		
		if ("TextView".equals(name)) {
			view = new TextView(context, attrs);
		} else if ("ImageView".equals(name)) {
			view = new ImageView(context, attrs);
		} else if ("Button".equals(name)) {
			view = new Button(context, attrs);
		} else if ("EditText".equals(name)) {
			view = new EditText(context, attrs);
		} else if ("Spinner".equals(name)) {
			view = new Spinner(context, attrs);
		} else if ("ImageButton".equals(name)) {
			view = new ImageButton(context, attrs);
		} else if ("CheckBox".equals(name)) {
			view = new CheckBox(context, attrs);
		} else if ("RadioButton".equals(name)) {
			view = new RadioButton(context, attrs);
            } else if ("ListView".equals(name)) {
                view = new ListView(context, attrs);
            } else if ("CheckedTextView".equals(name)) {
			view = new CheckedTextView(context, attrs);
		} else if ("AutoCompleteTextView".equals(name)) {
			view = new AutoCompleteTextView(context, attrs);
		} else if ("MultiAutoCompleteTextView".equals(name)) {
			view = new MultiAutoCompleteTextView(context, attrs);
		} else if ("RatingBar".equals(name)) {
			view = new RatingBar(context, attrs);
		} else if ("SeekBar".equals(name)) {
			view = new SeekBar(context, attrs);
		} else if(LinearLayout.class.getSimpleName().equals(name)) {
			view = new LinearLayout(context, attrs);
		} else if(RelativeLayout.class.getSimpleName().equals(name)) {
			view = new RelativeLayout(context, attrs);
		} 
		
		if(view == null){
			view = createViewFromTag(parent.getContext(),name,attrs);
		}
		
		return view;
	}
	
	
	static View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private static View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }
}
