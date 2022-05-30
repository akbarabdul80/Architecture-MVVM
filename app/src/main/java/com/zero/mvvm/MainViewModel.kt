package com.zero.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zero.mvvm.data.state.SimpleState
import com.zero.mvvm.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    val observer: MutableLiveData<SimpleState> by lazy {
        MutableLiveData<SimpleState>()
    }

    val state: LiveData<SimpleState>
        get() = observer

    fun getRandomDog() {
        App.service.getRandomDog()
            .map<SimpleState>(SimpleState::Result)
            .onErrorReturn(SimpleState::Error)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .startWith(SimpleState.Loading)
            .subscribe(observer::postValue)
            .let { return@let CompositeDisposable::add }
    }

}