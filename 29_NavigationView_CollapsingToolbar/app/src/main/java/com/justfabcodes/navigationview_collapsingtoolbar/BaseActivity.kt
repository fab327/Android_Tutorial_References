package com.justfabcodes.navigationview_collapsingtoolbar

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    internal lateinit var navigationView: NavigationView

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setSupportActionBar(toolbar)

        drawerLayout = drawer_layout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        navigationView = nav_view

        setupDrawerListeners()
        actionBarDrawerToggle.syncState()
    }

    ///region the navView menu can also be used in the toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        handleMenuNavigation(item.itemId)

        return super.onOptionsItemSelected(item)
    }
    ///endregion

    private fun setupDrawerListeners() {
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.setChecked(true)

            handleMenuNavigation(menuItem.itemId)

            drawer_layout.closeDrawers()
            true
        }
    }

    private fun handleMenuNavigation(menuId: Int) {
        when (menuId) {
            R.id.nav_home -> startActivity(
                Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            )
            R.id.nav_tools -> startActivity(
                Intent(this, ToolsActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            )
        }
    }

}
