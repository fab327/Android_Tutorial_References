package com.justfabcodes.navigationview_collapsingtoolbar

import android.os.Bundle

class ToolsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools)

        //Preselect the matching item in the menu
        navigationView.setCheckedItem(R.id.nav_tools)
    }

}
