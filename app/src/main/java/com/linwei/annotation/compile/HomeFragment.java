package com.linwei.annotation.compile;

import com.linwei.annotation.ObjectFactory;
import com.linwei.annotation.R;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 首页Fragment
 */
@ObjectFactory(type = BaseFragment.class, key = "Home")
public class HomeFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
}
