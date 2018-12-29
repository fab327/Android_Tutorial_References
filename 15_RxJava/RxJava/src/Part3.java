import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Part3 {

    public static void main(String[] args) {

        //1 - Error handling
        Observable.just("Hello, world!")
                .map(s -> potentialException(s))
                .map(s -> anotherPotentialException(s))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Ouch!");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Completed!");
                    }
                });
    }

    private static String potentialException(String s) {
        if (s == null) {
            throw new NullPointerException("NPE");
        }
        return s;
    }

    private static String anotherPotentialException(String s) {
        if (!(s.length() > 0)) {
            throw new RuntimeException("RTE");
        }
        return s;
    }

}
