package omz.pluralsight.nasa.fragments;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

import omz.pluralsight.nasa.R;


/**
 * Created by omrierez on 27/02/16.
 */
public class BaseFragment extends android.support.v4.app.Fragment {


    private final static String TAG = BaseFragment.class.getSimpleName();


    protected void showInternetConnectionErrorSnackbar() {
        Snackbar snackbar = Snackbar.make(getView(), getString(R.string.error_no_internet), Snackbar.LENGTH_INDEFINITE);
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(Color.RED);
        }
        snackbar.show();
    }
}


