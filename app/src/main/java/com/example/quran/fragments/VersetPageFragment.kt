package com.example.quran.fragments

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.media.AudioManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.quran.R
import com.example.quran.Entities.Verset
import com.example.quran.api.verset.VersetIndexResponse
import com.example.quran.api.verset.VersetService
import com.example.quran.database.ServiceDB
import kotlinx.android.synthetic.main.recycler_verset_fragment.*
import kotlinx.android.synthetic.main.verset_fragment.*
import kotlinx.android.synthetic.main.verset_item.*
import kotlinx.android.synthetic.main.verset_item.tvIdSourat
import kotlinx.android.synthetic.main.verset_item.tvIdVerset
import kotlinx.android.synthetic.main.verset_item.tvNameSourat
import kotlinx.android.synthetic.main.verset_item.tvTextAn
import kotlinx.android.synthetic.main.verset_item.tvTextAn2
import kotlinx.android.synthetic.main.verset_item.tvTextAr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import android.media.MediaPlayer
import android.provider.ContactsContract
import android.util.Log
import android.widget.*
import com.example.quran.Entities.Mofasir
import com.example.quran.Entities.VersetFav
import com.example.quran.Entities.VersetFire
import com.example.quran.api.tafsir.TafsirResponse
import com.example.quran.api.tafsir.TafsirService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class VersetPageFragment(var verset: Verset) : Fragment(R.layout.verset_fragment){


    private val mediaPlayer =  MediaPlayer();

    private lateinit var mAuth : FirebaseAuth

    private lateinit var googleSignInClient : GoogleSignInClient

    val db = FirebaseFirestore.getInstance()

    companion object{
        private const val RC_SIGN_IN=120
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext() , gso)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser



        tvIdSourat.text = ""+verset.sourat_id ;
        tvIdVerset.text = ""+verset.verset_sourat_id ;

        var sourat = ServiceDB.database.souratDao().getSouratById(verset.sourat_id) ;

        tvNameSourat.text = sourat[0]?.nom ;
        tvTextAr.text = verset.text_ar ;
        tvTextAn.text = verset.text_an ;
        tvTextAn2.text = sourat[0]?.location ;

        tvNbrMots.text = ""+verset.nbr_mots ;


        var played = false
        var preapred = false


        val url = "http://salamquran.com/en/api/v6/"

        val retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val versetApi = retrofit.create(VersetService::class.java)
        val responseIndex = versetApi.getAyaIndex(verset.sourat_id,verset.verset_sourat_id,1)

        responseIndex.enqueue(object: Callback<VersetIndexResponse> {
            override fun onResponse(call: Call<VersetIndexResponse>, responseIndex: Response<VersetIndexResponse>) {
                val resIndex = responseIndex.body()

                print("------------------------------------------------------")
                print(responseIndex.body().toString())
                print("--------------------------------------------------------")

                //handel moshaf link

                btn_audio.setOnClickListener {
                    if(!preapred){
                        val uri =  "https://cdn.islamic.network/quran/audio/128/ar.alafasy/"+resIndex?.result!!.index.toString()+".mp3"
                        prepareAudio(uri)
                        preapred = true
                    }
                    if (played) {
                        mediaPlayer.pause()
                        btn_audio.setImageResource(R.drawable.ic_play)
                        played = false

                    }else{
                        mediaPlayer.start()
                        btn_audio.setImageResource(R.drawable.ic_pause)
                        played = true

                    }


                }

                btnImage.setOnClickListener {
                    val activity = it.context as AppCompatActivity

                    val versetImageFragment = VersetImageFragment(resIndex?.result!!.page)


                    activity.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment,  versetImageFragment)
                        addToBackStack(null)
                        commit()
                    }
                }

            }

            override fun onFailure(call: Call<VersetIndexResponse>, t: Throwable) {



            }



        })




        val mofasirin = arrayListOf<Mofasir>(
            Mofasir(1, "التفسير الميسر"),
            Mofasir(2, "تفسير الجلالين"),
            Mofasir(3, "تفسير السعدي"),
            Mofasir(4, "تفسير ابن كثير"),
            Mofasir(5, "تفسير الوسيط لطنطاوي"),
            Mofasir(6, "تفسير البغوي"),
            Mofasir(7, "تفسير القرطبي"),
            Mofasir(8, "تفسير الطبري"),
            Mofasir(9, "Arberry"),
            Mofasir(10, "Yusuf Ali"),
            Mofasir(11, "Keyzer"),
            Mofasir(12, "Leemhuis"),
            Mofasir(13, "Siregar")
        )
        val test = view.findViewById<TextView>(R.id.tafsir)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,mofasirin)
        val tafsirUrl = "http://api.quran-tafseer.com/"
        val retrofitTafsir = Retrofit.Builder().baseUrl(tafsirUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val tafsirApi = retrofitTafsir.create(TafsirService::class.java)

        //get mofassir from drop list
        val dropList = view.findViewById<Spinner>(R.id.ListContact)
        val tafsirSection = view.findViewById<TextView>(R.id.tafsirSection)

        dropList.adapter = arrayAdapter
        dropList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedObject = dropList.selectedItem as Mofasir

                val responseTafsir = tafsirApi.getTafsirByMofasirId(selectedObject.id,verset.sourat_id,verset.verset_sourat_id)

                responseTafsir.enqueue(object: Callback<TafsirResponse> {
                    override fun onResponse(call: Call<TafsirResponse>,responseTafsir: Response<TafsirResponse>) {
                        tafsirSection.text = responseTafsir.body()?.text

                    }

                    override fun onFailure(call: Call<TafsirResponse>, t: Throwable) {

                    }
                } )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val fav_items = ServiceDB.database.versetFavDeo().getVersetFavById(verset.id)
        var faved = false

        if(fav_items?.size > 0 ) {
            faved = true
            btn_fav.setImageResource(R.drawable.ic_bookmark_checked)
        }else {
            btn_fav.setImageResource(R.drawable.ic_bookmark)
        }

        btn_fav.setOnClickListener {
            if(faved) {
                btn_fav.setImageResource(R.drawable.ic_bookmark)
                ServiceDB.database.versetFavDeo().deleteVersetFav(verset.id)
                faved = false
            }else {
                btn_fav.setImageResource(R.drawable.ic_bookmark_checked)
                ServiceDB.database.versetFavDeo().insertVersetFav(VersetFav(verset.id , verset.id))
                faved = true
            }
        }

        var stored = false ;

        if(user != null) {
            db.collection("fav").whereEqualTo("userId", user.uid).get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document!!.isEmpty) {
                        val users = document.toObjects(VersetFire::class.java)

                        users.forEach {
                            if(it.id.toInt() == verset.id) {
                                firebase_btn.setImageResource(R.drawable.ic_star_checked)
                                stored = true
                            }

                        }
                    }
                } else {
                    println(task.exception)
                }
            }
        }

        firebase_btn.setOnClickListener {

            if (user != null) {
                if(!stored) {

                    Toast.makeText(requireContext(), user.uid, Toast.LENGTH_LONG).show()
                    db.collection("fav").add(VersetFire(verset.id.toString(), verset.text_ar ,user.uid)).addOnCompleteListener { task: Task<DocumentReference> ->
                        if (task.isSuccessful) {
                            println("success !!!!")
                            //*****
                            db.collection("fav").whereEqualTo("userId", user.uid).get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                                if (task.isSuccessful) {
                                    val document = task.result
                                    if (!document!!.isEmpty) {
                                        val users = document.toObjects(VersetFire::class.java)

                                        firebase_btn.setImageResource(R.drawable.ic_star_checked)
                                        users.forEach {
                                            Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                } else {
                                    println(task.exception)
                                }
                            }



                        } else {
                            println(task.exception)
                        }
                    }

                }

            } else {

                signIn()


            }

        }


        shareBtn.setOnClickListener {
            var dialog = SendMailFragment() ;
            val activity = it.context as AppCompatActivity
            dialog.show(activity.supportFragmentManager , "sendMailDialog" )
        }

    }



    private fun prepareAudio(audioUrl: String) {


        // initializing media player


        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
        } catch ( e : IOException) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(requireContext(),"بدأ", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.stop()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, VersetPageFragment.RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == VersetPageFragment.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception=task.exception
            if(task.isSuccessful)
            {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    // Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    print( "COMPTE :" + account.id.toString())

                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    // Log.w("SignInActivity", "Google sign in failed", e)
                    print("SignInActivity "+ "GOOGLE MAFIHAACH"+ e.toString())
                }}else{
                //Log.w("SignInActivity", exception.toString())
                print("SignInActivity "+ exception.toString())

            }

        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    print("SignInActivity "+ "NAJA7NAA")

                } else {
                    // If sign in fails, display a message to the user.
                    // Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                    print("SignInActivity "+ "faaaaailllllerrr "+ task.exception.toString())

                }
            }
    }




}