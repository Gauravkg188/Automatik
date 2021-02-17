/**
 * By Gaurav Kumar
 * application perform many tasks like image labelling,text from image,bar code scanning and translation to english using Android Machine Leaning tools
 */

package  com.kg.automatik

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.util.*


open class MainActivity : AppCompatActivity() {
    lateinit var adapter:Adapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val RECORD_REQUEST_CODE:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout=findViewById(R.id.tab_layout)
        viewPager=findViewById(R.id.view_pager)
        adapter=Adapter(supportFragmentManager, ArrayList(), ArrayList())
        adapter.addFragment(FragmentImageLabeling(),"Image Label")
        adapter.addFragment(FragmentImageToText(),"Image-Text")
        adapter.addFragment(FragmentBarCode(),"BarCode")
        adapter.addFragment(FragmentTranslation(),"Translate")

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )



           if (permission != PackageManager.PERMISSION_GRANTED) {

               makeRequest()
           }
        else {


               viewPager.adapter = adapter
               tabLayout.setupWithViewPager(viewPager)

           }




    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty()
                    || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                   Toast.makeText(this,"please provide permission to use features",Toast.LENGTH_LONG).show()
                } else {

                    viewPager.adapter=adapter
                    tabLayout.setupWithViewPager(viewPager)
                }
                return
            }
        }
    }
}