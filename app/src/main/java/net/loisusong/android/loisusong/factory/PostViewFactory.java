package net.loisusong.android.loisusong.factory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import net.loisusong.android.loisusong.activity.PostLoisusongActivity;
import net.loisusong.android.loisusong.activity.PostVideoActivity;
import net.loisusong.android.loisusong.activity.PostYoutubeActivity;
import net.loisusong.android.loisusong.service.constant.Constant;

public class PostViewFactory {
	public static Intent createActivityIntent(Context context, String typePost) {
		Intent i;
		switch (typePost) {
			case Constant.POST_ALL:
				i = new Intent(context, PostLoisusongActivity.class);
				break;
			case Constant.POST_INTERNATIONAL:
				i = new Intent(context, PostLoisusongActivity.class);
				break;
			case Constant.POST_VIETNAM:
				i = new Intent(context, PostLoisusongActivity.class);
				break;
			case Constant.POST_ABOUT_US:
				i = new Intent(context, PostLoisusongActivity.class);
				break;
			case Constant.POST_MEDIA:
				i = new Intent(context, PostYoutubeActivity.class);
				break;
			case Constant.POST_COFFEE:
				i = new Intent(context, PostVideoActivity.class);
				break;
			default:
				i = new Intent(context, PostLoisusongActivity.class);
				break;
		}
		return i;
	}
}
