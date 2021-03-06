package edu.byu.cs.tweeter.Client.view.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.Client.view.main.PlaceholderFragment;
import edu.byu.cs.tweeter.Client.view.main.login.LoginFragment;
import edu.byu.cs.tweeter.Client.view.main.login.RegisterFragment;

public class LoginSectionsPagerAdapter extends FragmentPagerAdapter {

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages
     * of the Main Activity when logging in.
     */
    private static final int LOGIN_FRAGMENT_POSITION = 0;
    private static final int REGISTER_FRAGMENT_POSITION = 1;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.login_title, R.string.register_title};
    private final Context mContext;

    public LoginSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == LOGIN_FRAGMENT_POSITION) {
            return new LoginFragment();
        }
        else if (position == REGISTER_FRAGMENT_POSITION) {
            return new RegisterFragment();
        }
        //else if (position == REGISTER_FRAGMENT_POSITION) {
        //    return new RegisterFragment(); }
        else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

}
