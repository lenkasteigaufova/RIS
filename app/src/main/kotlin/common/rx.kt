package common

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject


interface RxView {
    val visible  : Observable<Unit>
    val gone     : Observable<Unit>
    val attached : Observable<Unit>
    val detached : Observable<Unit>
}
fun <A> RxView.bind(source: Observable<A>, action: (A) -> Unit): Subscription = source
        .takeUntil(detached)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(action)

open class RxLayout: CoordinatorLayout, RxView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override val visible = event<Unit>()
    override val gone = event<Unit>()
    override val attached = event<Unit>()
    override val detached = event<Unit>()

    override fun onWindowVisibilityChanged(visibility: Int) {
        when (visibility) {
            View.GONE       -> gone()
            View.VISIBLE    -> visible()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        detached()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attached()
    }
}

open class RxFrame: FrameLayout, RxView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override val visible = event<Unit>()
    override val gone = event<Unit>()
    override val attached = event<Unit>()
    override val detached = event<Unit>()

    override fun onWindowVisibilityChanged(visibility: Int) {
        when (visibility) {
            View.GONE       -> gone()
            View.VISIBLE    -> visible()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        detached()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attached()
    }
}



inline fun <reified A> event(): PublishSubject<A> = PublishSubject.create<A>()
inline fun <reified A> cache(): BehaviorSubject<A> = BehaviorSubject.create<A>()
inline fun <reified A> cache(init: A): BehaviorSubject<A> = BehaviorSubject.create<A>(init)
inline fun <reified A> safeCache(init: A): SerializedSubject<A, A> = BehaviorSubject.create<A>(init).toSerialized()
operator fun <A> Observer<A>.invoke(value: A) = onNext(value)
operator fun Observer<Unit>.invoke() = onNext(Unit)
fun <A> BehaviorSubject<A>.update(step: (A) -> A): Subscription = take(1).subscribe { onNext(step(it)) }
fun <A> Subject<A, A>.value(f: (A) -> Unit): Subscription = take(1).subscribe(f)
fun <A> Subject<A?, A?>.updateValue(f: (A) -> A) = take(1).subscribe { it?.let { onNext(f(it)) } }
fun <T> observable(action: () -> T): Observable<T> = Observable.fromCallable(action)

