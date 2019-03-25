package test.accenture

/**
 * @author Satyam
 */
import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.opengl.Visibility
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import test.accenture.R.attr.layoutManager
import test.accenture.R.id.lv_data
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import android.support.v7.widget.DividerItemDecoration
import android.view.View


class MainActivity : AppCompatActivity() {

    private var myPreferences = "myPrefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if(verifyAvailableNetwork(this as AppCompatActivity)){
            MyAssyn(this).execute("","")
        }
        else{
            if(!AppPreference.saveData?.equals("")) {
                setDataRecyclerView(AppPreference.saveData, MainActivity())
            }
            else{
                Toast.makeText(this,"Internet Connection not available",Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo= connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    data class Albums (val userId: Long,val id: Long, val title: String)

    @SuppressLint("NewApi")
    class  MyAssyn internal constructor(context: MainActivity) : AsyncTask<Any, Any, Any>()
    {

        private val activityReference: WeakReference<MainActivity> = WeakReference(context)
        val activity = activityReference.get()

        override fun onPreExecute() {
            super.onPreExecute()
            activity?.progressBar?.visibility= View.VISIBLE
            activity?.txt_load?.visibility= View.VISIBLE

        }

        override fun doInBackground(vararg params: Any?): String? {
            //To change body of created functions use File | Settings | File Templates.

            val url:String= "https://jsonplaceholder.typicode.com/albums"

            var response: String? = activity?.NetworkCall(url)
            return response
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            activity?.progressBar?.visibility= View.GONE
            activity?.txt_load?.visibility= View.GONE
            activity?.setDataRecyclerView(result.toString(),activity)



        }
    }

    fun NetworkCall(urlToHit:String): String {

        val url = URL(urlToHit)

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode ; $responseMessage")
            val response = StringBuffer()

            BufferedReader(InputStreamReader(inputStream)).use {

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
            }
            return response.toString()

        }
    }

    fun setDataRecyclerView(result:String,context: MainActivity){
        val activity =context
        val gson = GsonBuilder().create()
        val Model= gson.fromJson(result.toString(),Array<Albums>::class.java).toList()
        AppPreference.saveData= result.toString()
        //adding a layoutmanager
        val adapter = AlbumAdapter(Model)
        lv_data?.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(lv_data.getContext(),
                DividerItemDecoration.VERTICAL)
        lv_data.addItemDecoration(dividerItemDecoration)
        lv_data?.adapter=adapter
    }

}
