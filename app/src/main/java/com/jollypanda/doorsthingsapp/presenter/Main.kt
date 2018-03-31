package com.jollypanda.doorsthingsapp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * @author Yamushev Igor
 * @since  31.03.18
 */
@StateStrategyType(SkipStrategy::class)
interface MainView : MvpView {

}

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    
    var endPointId: String? = null
    
}