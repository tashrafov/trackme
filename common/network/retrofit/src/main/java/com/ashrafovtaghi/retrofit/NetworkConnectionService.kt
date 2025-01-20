package com.ashrafovtaghi.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.SocketFactory

@Singleton
class NetworkConnectionService @Inject constructor(context: Context) {

    private val _networkStateFlow = MutableStateFlow(NetworkState.ONLINE)
    val networkStateFlow: StateFlow<NetworkState> = _networkStateFlow

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetworks: MutableSet<Network> = HashSet()

    private val jobs = mutableListOf<Job>()

    init {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        var available = false
        capabilities?.let {
            available = it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || it.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            ) || it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        if (!available) {
            _networkStateFlow.tryEmit(NetworkState.DISCONNECTED)
        }
    }

    fun enableObserver() {
        networkCallback = createNetworkCallback()
        val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun disableObserver() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        jobs.forEach {
            if (it.isActive) {
                it.cancel()
            }
        }
        jobs.clear()
    }

    private fun checkValidNetworks() {
        if (validNetworks.isNotEmpty()) {
            if (_networkStateFlow.value == NetworkState.DISCONNECTED) {
                _networkStateFlow.tryEmit(NetworkState.CONNECTED)
            }
        } else {
            _networkStateFlow.tryEmit(NetworkState.DISCONNECTED)
        }
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            Log.d(TAG, "onAvailable: $network")
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val hasInternetCapability =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            Log.d(TAG, "onAvailable: ${network}, $hasInternetCapability")

            if (hasInternetCapability == true) {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if (hasInternet) {
                        Log.d(TAG, "onAvailable: adding network. $network")
                        validNetworks.add(network)
                        checkValidNetworks()
                    }
                }
                jobs.add(job)
            }
        }

        override fun onLost(network: Network) {
            Log.d(TAG, "onLost: $network")
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }

    object DoesNetworkHaveInternet {
        fun execute(socketFactory: SocketFactory): Boolean {
            return try {
                Log.d(TAG, "PINGING Google...")
                val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                socket.close()
                Log.d(TAG, "PING success.")
                true
            } catch (e: IOException) {
                Log.e(TAG, "No Internet Connection. $e")
                false
            }
        }
    }

    companion object {
        const val TAG = "NetworkConnectionService"
    }
}

enum class NetworkState {
    CONNECTED, DISCONNECTED, ONLINE
}