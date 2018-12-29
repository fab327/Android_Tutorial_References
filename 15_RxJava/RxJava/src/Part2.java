import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

/*
 * https://blog.danlew.net/2014/09/22/grokking-rxjava-part-2/
 * Observable operators -> https://github.com/ReactiveX/RxJava/wiki/Alphabetical-List-of-Observable-Operators
 * Visual operator helper -> http://rxmarbles.com/
 */
public class Part2 {

    private static String GREETING = "Hello, World!";

    public static void main(String[] args) {

        //We want to search text and display it on the UI

        //1 -> bad because we manipulate the date in onSubscribe
        getStrings(GREETING).subscribe(urls -> {
            for (String url : urls) {
                System.out.println(url);
            }
        });
        /*
        Output:
            Hello, World!
            Test
            Test2
            Test3
            Test4
            Test5
         */
        System.out.println("------------------------------------------------------------------------------------------");

        //2 -> bad because of nested subscriptions
        getStrings(GREETING).subscribe(urls -> {
            Observable.fromIterable(urls)
                    .subscribe(url -> System.out.println(url));
        });
        /*
        Output:
            Hello, World!
            Test
            Test2
            Test3
            Test4
            Test5
         */
        System.out.println("------------------------------------------------------------------------------------------");

        //3
        getStrings(GREETING)
                .flatMap(urls -> Observable.fromIterable(urls))
                .subscribe(url -> System.out.println(url));
        /*
        Output:
            Hello, World!
            Test
            Test2
            Test3
            Test4
            Test5
         */
        System.out.println("------------------------------------------------------------------------------------------");

        //4
        getStrings(GREETING)
                .flatMap(urls -> Observable.fromIterable(urls))
                .flatMap(url -> getTitle(url, false))
                .subscribe(title -> System.out.println(title), error -> { /* do nothing yet */});
        //Notice how the error terminates the flow and won't print the third item in the array
        /*
        Output:
            JustFabCodes
         */
        System.out.println("------------------------------------------------------------------------------------------");

        //5
        //filter -> to filter based on a given criteria
        //take -> if only interested in a given number of items
        //doOnNext -> executes before the logic onSubscribe and adds extra behavior each time an item is emitted
        getStrings(GREETING)
                .flatMap(urls -> Observable.fromIterable(urls))
                .flatMap(url -> getTitle(url, true))
                .filter(title -> title.length() != 0)
                .take(5)
                .doOnNext(s -> System.out.println("I can do additional work on '" + s + "' if was necessary"))
                .subscribe(title -> System.out.println(title));
        /*
        Output:
            I can do additional work on 'JustFabCodes' if was necessary
            JustFabCodes
            I can do additional work on 'JustFabCodes' if was necessary
            JustFabCodes
            I can do additional work on 'JustFabCodes' if was necessary
            JustFabCodes
            I can do additional work on 'JustFabCodes' if was necessary
            JustFabCodes
            I can do additional work on 'JustFabCodes' if was necessary
            JustFabCodes
         */
        System.out.println("------------------------------------------------------------------------------------------");

    }

    ////// Mock server requests //////
    static Observable<List<String>> getStrings(String text) {
        List<String> randomString = new ArrayList();
        randomString.add(text);
        randomString.add("Test");
        randomString.add("Test2");
        randomString.add("Test3");
        randomString.add("Test4");
        randomString.add("Test5");
        return Observable.just(randomString);
    }

    static Observable<String> getTitle(String URL, boolean nullHandling) {
        if (URL.length() < 5) {
            //Simulate a 404
            if (nullHandling) {
                return Observable.just("");
            } else {
                return null;
            }
        } else {
            return Observable.just("JustFabCodes");
        }
    }

}
