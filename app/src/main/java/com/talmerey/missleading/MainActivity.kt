package com.talmerey.missleading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //UI elements
    private var firstName: String? = null
    private var lastName: String? = null
    private var txtWelcomeMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLoginButtonClicked(view:View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        initialise()
    }

    fun onLogoutButtonClicked(view:View) {
        (if (mAuth != null) mAuth else throw NullPointerException("Expression 'mAuth' must not be null"))?.signOut()
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        txtWelcomeMessage = findViewById(R.id.txtWelcomeMessage);
    }

    override fun onStart() {
        super.onStart()
        initialise()
        displayWelcomeMessage()
    }

    private fun displayWelcomeMessage() {
            if (mAuth != null) {
                if (mAuth!!.currentUser != null) {
                    val mUser = mAuth!!.currentUser
                    val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

                    mUserReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            firstName = snapshot.child("firstName").value as String
                            lastName = snapshot.child("lastName").value as String

                            txtWelcomeMessage!!.text = "Welcome to MissLead ${firstName} ${lastName}!"
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }
            }
    }


}
