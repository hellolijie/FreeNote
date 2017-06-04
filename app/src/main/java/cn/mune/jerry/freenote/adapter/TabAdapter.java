package cn.mune.jerry.freenote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.mune.jerry.freenote.activity.MainActivity;
import cn.mune.jerry.freenote.fragment.FindFragment;
import cn.mune.jerry.freenote.fragment.MarkFragment;
import cn.mune.jerry.freenote.fragment.RecentFragment;

/**
 * Created by lijie on 16/12/25.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private static final String[] TITLE = {"发现", "最近", "书签"};

    private MainActivity mainActivity;

    private FindFragment findFragment;
    private MarkFragment markFragment;
    private RecentFragment recentFragment;

    public TabAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);

        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0:
                if (findFragment == null)
                    findFragment = new FindFragment();
                fragment = findFragment;
                break;
            case 1:
                if (recentFragment == null)
                    recentFragment = new RecentFragment();
                fragment = recentFragment;
                break;
            case 2:
                if (markFragment == null)
                    markFragment = new MarkFragment();
                fragment = markFragment;
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }
}
