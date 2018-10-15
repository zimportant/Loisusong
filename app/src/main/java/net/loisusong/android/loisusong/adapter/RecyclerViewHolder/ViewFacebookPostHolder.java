package net.loisusong.android.loisusong.adapter.RecyclerViewHolder;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.squareup.picasso.Picasso;

import io.realm.RealmObject;

import net.loisusong.android.loisusong.factory.PostViewFactory;
import net.loisusong.android.loisusong.service.model.PostsDataFacebook;
import net.loisusong.android.loisusong.R;
import net.loisusong.android.loisusong.service.utils.text.TextModifier;
import net.loisusong.android.loisusong.service.wrapper.PostIntentWrapper;

import android.text.Html;

public class ViewFacebookPostHolder extends ViewPostHolder {
	private String typePost;
	private PostsDataFacebook postsDataFacebook;

	public ViewFacebookPostHolder(View itemView, String typePost) {
		super(itemView);
		this.typePost = typePost;
	}

	@Override
	public void setPost(RealmObject realmObject) {
		postsDataFacebook = (PostsDataFacebook) realmObject;
		Picasso.get()
				.load(postsDataFacebook.format.last().url)
				.placeholder(R.drawable.image_placeholder)
				.into(imageView);

		titleTextView.setText(Html.fromHtml(postsDataFacebook.videoDescription));
		dateTextView.setText(TextModifier.beautifyDate(postsDataFacebook.updatedTime));
	}

	@Override
	public void onClick(View v) {
		Intent intent = PostViewFactory.createActivityIntent(v.getContext(), typePost);
		PostIntentWrapper intentWrapper = new PostIntentWrapper(intent);
		intentWrapper.addTitle(postsDataFacebook.videoDescription);
		intentWrapper.addDate(postsDataFacebook.updatedTime);
		intentWrapper.addContent(postsDataFacebook.videoDescription);
		intentWrapper.addUrl(postsDataFacebook.sourceUrl);

		intent = intentWrapper.getIntent();
		String transition = v.getContext().getString(R.string.transition_header_post);
		ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
				.makeSceneTransitionAnimation(
						(AppCompatActivity) v.getContext(),
						imageView,
						transition
				);
		v.getContext().startActivity(intent, optionsCompat.toBundle());
	}
}
