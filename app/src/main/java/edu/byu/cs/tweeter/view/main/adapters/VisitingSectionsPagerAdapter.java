package edu.byu.cs.tweeter.view.main.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.main.PlaceholderFragment;
import edu.byu.cs.tweeter.view.main.followers.FollowersFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;

public class VisitingSectionsPagerAdapter extends FragmentStatePagerAdapter {
    private static final int STORY_FRAGMENT_POSITION = 0;
    private static final int FOLLOWING_FRAGMENT_POSITION = 1;
    private static final int FOLLOWERS_FRAGMENT_POSITION = 2;
    private User user;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;

    public VisitingSectionsPagerAdapter(Context context, FragmentManager fm, User user) {
        super(fm);
        mContext = context;
        this.user = user;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == STORY_FRAGMENT_POSITION) {
            Fragment fragment = new StoryFragment();
            fragment.setArguments(makeBundle());
            return fragment;
        }
        else if (position == FOLLOWING_FRAGMENT_POSITION) {
            Fragment fragment = new FollowingFragment();
            fragment.setArguments(makeBundle());
            return fragment;
        }
        else if (position == FOLLOWERS_FRAGMENT_POSITION) {
            Fragment fragment = new FollowersFragment();
            fragment.setArguments(makeBundle());
            return fragment;
        }
        else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    private Bundle makeBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("alias", user.getAlias());
        bundle.putString("firstName", user.getFirstName());
        bundle.putString("lastName", user.getLastName());
        bundle.putString("imageURL", user.getImageUrl());
        if (user.getImageUri() == null) {
            bundle.putString("imageUri", null);
        }
        else {
            bundle.putString("imageUri", user.getImageUri().toString());
        }
        return bundle;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}