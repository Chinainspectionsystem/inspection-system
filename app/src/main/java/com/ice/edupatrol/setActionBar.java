package com.ice.edupatrol;

import android.content.res.Resources;
import android.support.v7.app.ActionBar;

/**
 * Created by acer on 2017/6/27.
 */
public class setActionBar {
    public void setActionBar(ActionBar actionBar, Resources resources)
    {
        actionBar.setTitle("");
        actionBar.setSubtitle("教务巡查");
     //   actionBar.setIcon(R.drawable.logo);
       actionBar.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_actionbar2));


    }
    public void setActionBar2(ActionBar actionBar, Resources resources)
    {
        actionBar.hide();

    }
}
