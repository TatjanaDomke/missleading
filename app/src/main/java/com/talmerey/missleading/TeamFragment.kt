package com.talmerey.missleading

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

class TeamFragment : Fragment(), TeamMemberListAdapter.ItemClickListener {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: TeamMemberListAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private val allItemList: List<TeamMember>
        get() {

            val allItems = ArrayList<TeamMember>()
            allItems.add(TeamMember("Taherul Kahn", "SWAT Android", R.drawable.taherul))
            allItems.add(TeamMember("Emma Burton", "SWAT Android", R.drawable.emma))
            allItems.add(TeamMember("Aga Ziemba", "SWAT Android", R.drawable.aga))
            allItems.add(TeamMember("Zeeshan Naseer", "Gold iOS", R.drawable.zeeshan))
            allItems.add(TeamMember("Umair Naru", "Gold iOS", R.drawable.umair))
            allItems.add(TeamMember("Victor Ireri", "A Android", R.drawable.victor))
            allItems.add(TeamMember("Joe McGuiness", "A Android", R.drawable.joe))
            allItems.add(TeamMember("Ana Padinha", "A iOS", R.drawable.ana))
            allItems.add(TeamMember("Ben Marsh", "A iOS", R.drawable.ben))
            allItems.add(TeamMember("King Chan", "SWAT iOS", R.drawable.king))
            allItems.add(TeamMember("Sam Ogunwe", "SWAT iOS", R.drawable.sam))
            allItems.add(TeamMember("Karan Saimbhi", "Gold Android", R.drawable.karan))
            allItems.add(TeamMember("Paul MacDonald", "Gold Android", R.drawable.paul))
            return allItems
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)

        mRecyclerView = view.findViewById<View>(R.id.team_recycler_view) as RecyclerView

        val teamListItem = allItemList
        mLayoutManager = LinearLayoutManager(activity)
        Log.d("debugMode", "The application stopped after this")
        mRecyclerView!!.layoutManager = mLayoutManager

         mAdapter = TeamMemberListAdapter(activity!!, teamListItem)
         mRecyclerView!!.adapter = mAdapter
         mAdapter!!.setClickListener(this)

        return view

    }

    override fun itemClick(view: View, position: Int) {


    }

    companion object {
        fun newInstance(): TeamFragment = TeamFragment()
    }
}