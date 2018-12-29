import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/*
 * https://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/
 *
 * Observable can be a db query/network call/click on screen
 * Observer would be the element displaying or reacting to it
 */
public class Part1 {

    private static String GREETING = "Hello, World!";

    public static void main(String[] args) {

        //1
        Observable<String> myObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext(GREETING);
                observableEmitter.onComplete();
            }
        });

        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("Subscription happened");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };
        myObservable.subscribe(myObserver);
        System.out.println("------------------------------------------------------------------------------------------");

        //2
        Observable<String> myObservable_2 = Observable.just(GREETING);
        Consumer<String> onNextAction = new Consumer<String>() {    //s -> System.out.println(s);
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
                System.out.println(s);
            }
        };
        myObservable_2.subscribe(onNextAction);
        System.out.println("------------------------------------------------------------------------------------------");

        //3
        Observable.just(GREETING)
                .subscribe(s -> System.out.println(s));
        System.out.println("------------------------------------------------------------------------------------------");

        //4
        //We may not always have access to the observable nor want a generic modification of its output.
        //Therefore, the observer is one option for customized modifications (operators are the best choice)
        Observable.just(GREETING)
                .subscribe(s -> System.out.println(s + " -Fab"));
        System.out.println("------------------------------------------------------------------------------------------");

        //5
        Observable.just(GREETING)
                .map(s -> s + " -Fab")
                .subscribe(s -> System.out.println(s));
        System.out.println("------------------------------------------------------------------------------------------");

        //6
        Observable.just(GREETING)
                .map(s -> s + " -Fab")
                .map(q -> q.hashCode())
                .map(r -> Integer.toString(r))
                .subscribe(s -> System.out.println(s));
        System.out.println("------------------------------------------------------------------------------------------");

    }
}
