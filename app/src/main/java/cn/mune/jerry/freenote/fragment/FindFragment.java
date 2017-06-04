package cn.mune.jerry.freenote.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.mune.jerry.freenote.R;

/**
 * Created by lijie on 16/12/25.
 */

public class FindFragment extends TabBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);

        return view;
    }
}
