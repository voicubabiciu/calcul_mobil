package com.voicubabiciu.proiectandroid.features.home.viewToDos


import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.voicubabiciu.proiectandroid.common.Resource


class TodosQueryLiveData(private val query: Query) :
    LiveData<Resource<QuerySnapshot>>(),
    EventListener<QuerySnapshot> {

    private var registration: ListenerRegistration? = null

    override fun onEvent(snapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
        Resource(snapshots?:e!!)
    }

    override fun onActive() {
        super.onActive()
        registration = query.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        registration?.also {
            it.remove()
            registration = null
        }
    }
}