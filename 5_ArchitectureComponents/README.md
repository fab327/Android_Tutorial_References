Based off of this codelab https://codelabs.developers.google.com/codelabs/build-app-with-arch-components/index.html?index=..%2F..%2Findex#0

Relevant concepts:

- Room (TypeConverter, Entities, Dao) -> com.justfabcodes.android.sunshine.data.database
- LiveData & ViewModels -> MainActivity & DetailActivity / DetailActivityViewModel & MainActivityViewModel
- ViewModelFactory -> MainViewModelFactory & DetailViewModelFactory
- Repository concept (Network and Db) -> SunshineRepository

- Executors -> AppExecutors
- Manual dependency injection -> InjectorUtils
- JSON manual parsing -> OpenWeatherJsonParser
- Firebase JobService -> SunshineFirebaseJobService
- Intent service -> SunshineSyncIntentService
- DiffUtil -> ForecastAdapter
- Databinding -> DetailActivity
- Date conversions -> SunshineDateUtils
