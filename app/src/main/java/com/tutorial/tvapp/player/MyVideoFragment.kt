package com.tutorial.tvapp.player

import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PagingIndicator
import androidx.leanback.widget.PlaybackControlsRow.FastForwardAction
import androidx.leanback.widget.PlaybackSeekDataProvider
import com.tutorial.tvapp.R

class MyVideoFragment : VideoSupportFragment()
{
    private lateinit var transportGlue: CustomTransportControlGlue

    private lateinit var fastForwadIndicatorView: View
    private  lateinit var rewindIndicatorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//       val playerGlue  = PlaybackTransportControlGlue(activity,MediaPlayerAdapter(activity))
//        playerGlue.host=VideoSupportFragmentGlueHost(this)
//        playerGlue.addPlayerCallback(object :PlaybackGlue.PlayerCallback()
//        {
//            override fun onPreparedStateChanged(glue: PlaybackGlue?) {
//               if(glue?.isPrepared == true)
//               {
//                    playerGlue.seekProvider= PlaybackSeekDataProvider()
//                   playerGlue.play()
//               }
//            }
//        })
//        playerGlue.subtitle="My Demo Subtitle"
//        playerGlue.title="MY Android App"
//
//        val uriPath="https://upload.wikimedia.org/wikipedia/commons/transcoded/a/a7/How_to_make_video.webm/How_to_make_video.webm.720p.vp9.webm"
//        playerGlue.playerAdapter.setDataSource(Uri.parse(uriPath))

        transportGlue=CustomTransportControlGlue(
            context=requireContext(),
            playerAdapter =BasicMediaPlayerAdapter(requireContext())

        )
        transportGlue.host=VideoSupportFragmentGlueHost(this)
        transportGlue.subtitle="My Demo Subtitle"
        transportGlue.title="MY Android App"

        val uriPath="https://upload.wikimedia.org/wikipedia/commons/transcoded/a/a7/How_to_make_video.webm/How_to_make_video.webm.720p.vp9.webm"
       transportGlue.playerAdapter.setDataSource(Uri.parse(uriPath))

        setOnKeyInterceptListener { view, keyCode, event ->
            if(isControlsOverlayVisible || event.repeatCount >0) {
                isControlsOverlayAutoHideEnabled=true
            }else when(keyCode){
                KeyEvent.KEYCODE_DPAD_RIGHT->
                {
                    isControlsOverlayAutoHideEnabled=event.action!=KeyEvent.ACTION_DOWN
                    if(event.action==KeyEvent.ACTION_DOWN)
                    {
                        animateIndicator(fastForwadIndicatorView)
                    }
                }
                KeyEvent.KEYCODE_DPAD_LEFT->
                {
                    isControlsOverlayAutoHideEnabled=event.action!=KeyEvent.ACTION_DOWN
                    if(event.action==KeyEvent.ACTION_DOWN)
                    {
                        animateIndicator(rewindIndicatorView)
                    }
                    }
                }




            transportGlue.onKey(view,keyCode,event)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         val view=super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
           fastForwadIndicatorView=inflater.inflate(R.layout.view_forward,view,false)
        view.addView(fastForwadIndicatorView )
        rewindIndicatorView=inflater.inflate(R.layout.view_rewind,view,false)
        view.addView(rewindIndicatorView )

         return view
    }
    fun animateIndicator(indicator: View) {
        indicator.animate()
            .withEndAction {
                indicator.isVisible = false
                indicator.alpha = 1F
                indicator.scaleX = 1F
                indicator.scaleY = 1F
            }
            .withStartAction {
                indicator.isVisible = true

            }
            .alpha(0.2F)
            .scaleX(2f)
            .scaleY(2f)
            .setDuration(400)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
}