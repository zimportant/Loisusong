package net.loisusong.loisusong.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.loisusong.loisusong.R;
import net.loisusong.loisusong.service.utils.LogUtils;
import net.loisusong.loisusong.service.wrapper.PostIntentWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostLoisusongActivity extends AppCompatActivity {
	@BindView(R.id.tl_post_loisusong)
	Toolbar toolbar;
	@BindView(R.id.iv_post_loisusong)
	ImageView imageView;
	@BindView(R.id.tv_title)
	TextView titleTextView;
	@BindView(R.id.tv_date)
	TextView dateTextView;
	@BindView(R.id.wv_post_loisusong)
	WebView webView;

	private String title, date, img, content;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_loisusong);

		ButterKnife.bind(this);
		setUpIntentData();
		setUpToolbar();
		setUpContent();
		setUpLayout();
	}

	private void setUpToolbar() {
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(Html.fromHtml(title));
	}

	private void setUpIntentData() {
		Intent intent = getIntent();
		PostIntentWrapper intentWrapper = new PostIntentWrapper(intent);
		title = intentWrapper.getTitle();
		date = intentWrapper.getDate();
		img = intentWrapper.getUrl();
		content = intentWrapper.getContent();
	}

	private void setUpContent() {
		content = reformat(content);
		content = "<!DOCTYPE html>" +
				"<html><head></head><body>" +
				"<div style='width: \'100vw\'; " +
				"word-break: \'break-all\'; " +
				"word-wrap: \'break-word\';'>" +
				content + "</div></body></html>";
	}

	private String reformat(String s) {
		s = s.replaceAll("<p><iframe[^>]*></iframe></p>", "");
		s = s.replaceAll("Premiera Book", "'-apple-system', 'HelveticaNeue'");
		s = s.replaceAll("Times-u", "'-apple-system', 'HelveticaNeue'");
		s = s.replaceAll("font-size: 20px;", "font-size: 15px;");
		s = s.replaceAll("font-size: 22px;", "font-size: 15px;");
		s = s.replaceAll("class=\"wp-caption-text\"", "style=\"font-family: Helvetica; " +
				"font-size: 13px; font-style: italic; " +
				"text-align: left; line-height: 1.8em; " +
				"max-width: 96%; padding: 0 4px 5px; " +
				"background: #fff; margin: 0; box-sizing: border-box; " +
				"word-wrap: break-word;\"");
		s = s.replaceAll("width: [0-9]+px", "width: 100%");
		s = s.replaceAll("<img", "<img style=\"width: 100%; height: auto;\"");
		s = s.replaceAll("<p>(?i)y(?i)o(?i)u(?i)t(?i)u(?i)b(?i)e.+</p>", "");
		s = s.replaceAll("<div class=\"pdf24Plugin-cp\">.+</div>", "");
		return s;
	}

	private void setUpLayout() {
		setUpCommonView();
		setUpWebView();
	}

	private void setUpCommonView() {
		titleTextView.setText(Html.fromHtml(title));
		dateTextView.setText(date);
		Picasso.get()
				.load(img)
				.placeholder(R.drawable.image_placeholder)
				.into(imageView);
	}

	private void setUpWebView() {
		WebSettings webSettings = webView.getSettings();
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setAppCacheEnabled(false);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.setWebViewClient(new PostWebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}
		});
		webView.loadData(content, "text/html; charset=utf-8", null);
	}

	public class PostWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
				Uri uri = request.getUrl();
				CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
				builder.setToolbarColor(ContextCompat.getColor(
						PostLoisusongActivity.this,
						R.color.colorPrimary
				));
				builder.setSecondaryToolbarColor(ContextCompat.getColor(
						PostLoisusongActivity.this,
						R.color.colorPrimaryDark
				));
				CustomTabsIntent customTabsIntent = builder.build();
				customTabsIntent.launchUrl(PostLoisusongActivity.this, uri);
			}
			return true;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
