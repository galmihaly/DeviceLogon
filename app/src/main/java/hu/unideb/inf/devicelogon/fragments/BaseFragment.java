package hu.unideb.inf.devicelogon.fragments;

import androidx.fragment.app.Fragment;

import hu.unideb.inf.devicelogon.interfaces.IFragmentNavigationPresenter;
import hu.unideb.inf.devicelogon.interfaces.IFragmentNavigationView;

public abstract class BaseFragment extends Fragment implements IFragmentNavigationView {

    protected IFragmentNavigationPresenter navigationPresenter;
    @Override
    public void atachPresenter(IFragmentNavigationPresenter presenter) {
        navigationPresenter = presenter;
    }
}
