package com.tutorial.tvapp.player

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class PlaybackActivity : FragmentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState ==null)
        {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content,MyVideoFragment())
                .commit()
        }
    }
}