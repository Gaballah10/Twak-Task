package com.example.tawktask.useCases

import com.example.tawktask.network.model.GithubUser
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteCallUseCases @Inject constructor(private val networkUseCase: NetworkUseCase) {

    var since: Int = 0
    private var isLoadingData = false
    private var isLoadingFooter = false

    fun load(
        listener: OnLoadListener
    ) {

        if (isLoadingData) {
            listener.loading()
            return
        }
        if (isLoadingFooter) {
            listener.footerLoading()
        }

        since = since
        isLoadingFooter = since != 0

        networkUseCase.callBackGithubUsersData(since)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<GithubUser>> {
                override fun onSubscribe(d: Disposable) {
                    if (isLoadingData) {
                        listener.loading()
                    }
                    if (isLoadingFooter) {
                        listener.footerLoading()
                    }
                }

                override fun onSuccess(response: List<GithubUser>) {
                    if (response.isNotEmpty()) {
                        since = response[response.size-1].id!!
                    }
                    isLoadingData = false
                    isLoadingFooter = false
                    listener.loaded(response)
                }

                override fun onError(ex: Throwable) {
                    isLoadingData = false
                    isLoadingFooter = false
                    listener.error(ex)
                }
            })
    }

    /**
     * Reset current pagination to 0
     */
    fun refresh() {
        since = 0
    }

    interface OnLoadListener {
        fun loading()
        fun footerLoading()
        fun loaded(data: List<GithubUser>)
        fun doNothing()
        fun error(ex: Throwable)
    }
}