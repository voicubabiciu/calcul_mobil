package voicu.babiciu.lab07

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import voicu.babiciu.lab07.databinding.ActivityMainBinding

import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import voicu.babiciu.lab07.SocketService.LocalBinder
import android.text.method.ScrollingMovementMethod





class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mServiceBound: Boolean = false
    private var mBoundService: SocketService? = null
    private var mIsBound: Boolean = false
    private lateinit var socketReceiver: SocketBroadcastReceiver

    companion object {
        const val FILTER_ACTION_KEY = "100"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtmsg.movementMethod = ScrollingMovementMethod()
        initEvents()
    }

    private fun initEvents() {
        binding.bind.setOnClickListener {
            doBindService()
        }
        binding.stopService.setOnClickListener {
            if (mServiceBound) {
                unbindService(mConnection)
                mServiceBound = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setReceiver()
    }

    private fun setReceiver() {
        socketReceiver =
            SocketBroadcastReceiver(binding.txtmsg)
        val intentFilter = IntentFilter()
        intentFilter.addAction(FILTER_ACTION_KEY)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(socketReceiver, intentFilter)
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            mBoundService = (service as LocalBinder).getService()
            mServiceBound = true
        }


        override fun onServiceDisconnected(className: ComponentName) {
            mBoundService = null
        }
    }

    private fun doBindService() {
        bindService(
            Intent(this@MainActivity, SocketService::class.java),
            mConnection,
            BIND_AUTO_CREATE
        )
        mIsBound = true
    }

    private fun doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection)
            mIsBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        doUnbindService()
    }
}