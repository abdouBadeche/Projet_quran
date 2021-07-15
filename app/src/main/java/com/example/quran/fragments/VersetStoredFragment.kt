package com.example.quran.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quran.Adapter.RacineSearchedAdapter
import com.example.quran.Adapter.VersetFavAdapter
import com.example.quran.Adapter.VersetStoredAdapter
import com.example.quran.Entities.VersetFire
import com.example.quran.R
import com.example.quran.database.ServiceDB
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

class VersetStoredFragment:Fragment(R.layout.recycler_verset_stored_fragment){

    private lateinit var mAuth : FirebaseAuth
     var msg : List<VersetFire> = listOf<VersetFire>()

    private lateinit var googleSignInClient : GoogleSignInClient

    val db = FirebaseFirestore.getInstance()

    companion object{
        private const val RC_SIGN_IN=120
    }



    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<VersetStoredAdapter.ViewHolder>? = null

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


        if (user != null) {

            Toast.makeText(requireContext(), user.uid, Toast.LENGTH_LONG).show()

            println("success !!!!")
            //*****
            db.collection("fav").whereEqualTo("userId", user.uid).get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (!document!!.isEmpty) {
                        msg = document.toObjects(VersetFire::class.java)

                        msg.forEach {
                            Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show()

                        }


                        layoutManager = LinearLayoutManager(requireContext())

                        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_verset_stored)

                        recyclerView?.layoutManager = this.layoutManager

                        adapter = VersetStoredAdapter(msg)


                        recyclerView?.adapter = this.adapter

                    }
                } else {
                    println(task.exception)
                }
            }

        } else {

            signIn()


        }


    }



    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, VersetStoredFragment.RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == VersetStoredFragment.RC_SIGN_IN) {
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