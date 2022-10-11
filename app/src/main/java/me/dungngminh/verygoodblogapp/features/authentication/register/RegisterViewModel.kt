package me.dungngminh.verygoodblogapp.features.authentication.register

import com.jakewharton.rxrelay3.PublishRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val interactor: Interactor) : BaseViewModel() {

    private val stateS = BehaviorSubject.create<ViewState>()
    private val eventS = PublishRelay.create<SingleEvent>()

    private val initialState = ViewState.initial()
    private val intentS = PublishRelay.create<ViewIntent>()

    val stateObservable: Observable<ViewState> = stateS.distinctUntilChanged()
    // For trigger navigation or show snack-bar ...
    val eventObservable: Observable<SingleEvent> = eventS.hide()

    // for bind view and restore when view created and maybe created again
    val state get() = stateS.value!!;
}