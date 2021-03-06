package net.loisusong.loisusong.fragment;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import net.loisusong.loisusong.R;
import net.loisusong.loisusong.activity.MainActivity;
import net.loisusong.loisusong.fragment.PostFragments.PostLoisusongFragment;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {
	private MainActivity activity;
	private ActionBarDrawerToggle mToggle = null;

	@Nullable
	public MainActivity getMainActivity() {
		return activity;
	}

	private void setMainActivity(Activity activity) {
		this.activity = (MainActivity) activity;
	}

	protected void setUpCommon(@NonNull View childView) {
		ButterKnife.bind(this, childView);
		setMainActivity(getActivity());
	}

	protected void setUpToolbar(@NonNull Toolbar toolbar) {
		getMainActivity().setSupportActionBar(toolbar);
		getMainActivity().getSupportActionBar().setLogo(R.drawable.ic_lss_white);
		mToggle = getMainActivity().registerDrawerToggle(toolbar);
	}

	@Override
	public void onDestroyView() {
		if (mToggle != null) {
			getMainActivity().unRegisterDrawerToggle(mToggle);
		}
		super.onDestroyView();
	}

	protected void replacePostFragment(int id, String fragmentId) {
		replaceFragment(id, PostLoisusongFragment.newInstance(fragmentId));
	}

	protected void replaceFragment(int id, Fragment fragment) {
		getChildFragmentManager().beginTransaction()
				.replace(id, fragment)
				.commit();
	}
}
