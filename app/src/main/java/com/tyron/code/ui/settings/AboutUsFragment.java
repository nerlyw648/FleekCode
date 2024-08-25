package com.tyron.code.ui.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.google.android.material.transition.MaterialSharedAxis;
import org.codeassist.unofficial.R;

public class AboutUsFragment extends MaterialAboutFragment {

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
  }

  @Override
  protected MaterialAboutList getMaterialAboutList(Context context) {
    MaterialAboutCard appCard =
        new MaterialAboutCard.Builder()
            .addItem(ConvenienceBuilder.createAppTitleItem(context))
            .addItem(
                new MaterialAboutActionItem.Builder().subText(R.string.app_description).build())
            .addItem(
                ConvenienceBuilder.createVersionActionItem(
                    context,
                    getDrawable(R.drawable.ic_round_info_24),
                    getString(R.string.app_version),
                    true))
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_baseline_open_in_new_24),
                    getString(R.string.app_source_title),
                    false,
                    Uri.parse("https://github.com/nerlyw648/FleekCode")))
            .build();

    MaterialAboutCard communityCard =
        new MaterialAboutCard.Builder()
            .title(R.string.community)
            .addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                    context,
                    getDrawable(R.drawable.ic_icons8_telegram_app),
                    "Telegram",
                    false,
                    Uri.parse("https://t.me/fleekcode_chat")))
            .build();

    MaterialAboutCard licenseCard =
        ConvenienceBuilder.createLicenseCard(
            context,
            getDrawable(R.drawable.ic_baseline_menu_book_24),
            getString(R.string.app_name),
            "2024",
            "nerlyw648, Tyron, Deenu",
            OpenSourceLicense.GNU_GPL_3);

    return new MaterialAboutList.Builder()
        .addCard(appCard)
        .addCard(communityCard)
        .addCard(licenseCard)
        .build();
  }

  private Drawable getDrawable(@DrawableRes int drawable) {
    return ResourcesCompat.getDrawable(
        requireContext().getResources(), drawable, requireContext().getTheme());
  }
}
