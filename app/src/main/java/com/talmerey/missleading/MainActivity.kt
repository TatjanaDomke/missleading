package com.talmerey.missleading

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.opengl.Visibility
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.talmerey.missleading.R.id.toolbar


class MainActivity : AppCompatActivity() {

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //UI elements
    private var firstName: String? = null
    private var lastName: String? = null
    private var txtWelcomeMessage: TextView? = null
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_tasks -> {
                toolbar.title = "Tasks"
                val tasksFragment = TasksFragment.newInstance()
                openFragment(tasksFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
               toolbar.title = "Team"
                val teamFragment = TeamFragment.newInstance()
                openFragment(teamFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_skills -> {
                toolbar.title = "Skills"
                val skillsFragment = SkillsFragment.newInstance()
                openFragment(skillsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        txtWelcomeMessage!!.visibility = View.GONE
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
