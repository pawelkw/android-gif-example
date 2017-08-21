package burrows.apps.example.gif.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import burrows.apps.example.gif.App
import burrows.apps.example.gif.R
import burrows.apps.example.gif.data.rest.repository.RiffsyApiClient
import burrows.apps.example.gif.presentation.SchedulerProvider
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main activity that will load our Fragments via the Support Fragment Manager.
 *
 * @author [Jared Burrows](mailto:jaredsburrows@gmail.com)
 */
class MainActivity : AppCompatActivity() {
  @Inject lateinit var repository: RiffsyApiClient
  @Inject lateinit var schedulerProvider: SchedulerProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Injection dependencies
    (application as App).activityComponent.inject(this)

    // Setup Toolbar
    toolBar.setNavigationIcon(R.mipmap.ic_launcher)
    toolBar.setTitle(R.string.main_screen_title)
    setSupportActionBar(toolBar)

    // Use Fragments
    var fragment: MainFragment? = supportFragmentManager.findFragmentById(R.id.contentFrame) as MainFragment?
    if (fragment == null) fragment = MainFragment()
    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.contentFrame, fragment, MainFragment::class.java.simpleName)
        .commit()
    }

    // Create presenter
    MainPresenter(fragment, repository, schedulerProvider)
  }
}
