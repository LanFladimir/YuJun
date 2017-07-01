
package android.databinding;
import com.fladimir.yujungong.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 19;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.fladimir.yujungong.R.layout.activity_main:
                    return com.fladimir.yujungong.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case com.fladimir.yujungong.R.layout.activity_splash:
                    return com.fladimir.yujungong.databinding.ActivitySplashBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return com.fladimir.yujungong.R.layout.activity_main;
                }
                break;
            }
            case 1573928931: {
                if(tag.equals("layout/activity_splash_0")) {
                    return com.fladimir.yujungong.R.layout.activity_splash;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}