package com.linwei.annotation.compile;
import com.linwei.annotation.ObjectFactory;
import com.linwei.annotation.R;

/**
 * @Author: WS
 * @Time: 2020/5/12
 * @Description: 用户Fragment
 */
@ObjectFactory(type = BaseFragment.class, key = "Mine")
public class MineFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
