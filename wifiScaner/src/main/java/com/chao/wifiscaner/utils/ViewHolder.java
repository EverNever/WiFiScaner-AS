/**
 * 简洁ViewHolder写法，在getView里面直接调用ViewHolder.get即可。
 * 详情：http://www.eoeandroid.com/thread-321547-1-1.html
 */
package com.chao.wifiscaner.utils;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
