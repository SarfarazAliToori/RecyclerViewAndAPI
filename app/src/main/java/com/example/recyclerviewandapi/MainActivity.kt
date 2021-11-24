package com.example.recyclerviewandapi

import android.Manifest
import android.app.DownloadManager
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyOnClickItemViewListener {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.INTERNET)
    }

    lateinit var myAddapter : MyAddapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (allPermissionsGranted()) {
            loadRecyclerView()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                loadRecyclerView()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    } // end of on RequestPermissionsResult()

    private fun loadRecyclerView() {
        // Loading Data From API.
        loadDataFromApi()
        recycler_view.layoutManager = LinearLayoutManager(this)
        myAddapter = MyAddapter(this)
        recycler_view.adapter = myAddapter
    }

    private fun loadDataFromApi() {
        //val queue = Volley.newRequestQueue(this)
        val url = "http://universities.hipolabs.com/search?country=pakistan"

        var gsonArray = JsonArrayRequest(Request.Method.GET, url,null, {
                var myArrayListOfDataClass = ArrayList<MyDataClass>()
            for (i in 0 until it.length()) {
                val myJsonObj = it.getJSONObject(i)
                val myDataClass = MyDataClass(
                    myJsonObj.getString("name"),
                    myJsonObj.getString("country"),
                    myJsonObj.getString("state-province"),
                    myJsonObj.getString("alpha_two_code"),
                    myJsonObj.getString("web_pages")
                )
                myArrayListOfDataClass.add(myDataClass)
            }
            myAddapter.updateData(myArrayListOfDataClass)

        },{
            Log.d("error", it.localizedMessage)
        })

        //queue.add(gsonArray)
        MySingleton.getInstance(this).addToRequestQueue(gsonArray)
    }

    override fun onClickedListener(items: MyDataClass) {
        Toast.makeText(this, "${items.uniTitle}", Toast.LENGTH_SHORT).show()
    }

}