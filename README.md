comments
========

Simple Android module to pull comments from an API at a set interval.

*commentssdk*

SDK Entry Point
* `Client` Entry point for the SDK end-user

Scheduling / Intents
* `AlarmBroadcastReceiver` Triggered by AlarmManager, starts CommentIntentService
* `CommentIntentService` Instantiates the presenter, provides implementation API call
* `Comment` Model for the REST API's comment json
* `CommantContract` MVP contract for interacting with the comment API / easier unit testing
* `CommentService` Retrofit API service interface
* `RemoteCommentService` Implementation of live data API service for comments
* `SharedPreferencesRepo` Keeps Context out of the Presenter, makes it easy to test, provides API to shared preferences specific to comments.
* `SharedPreferencesRepoImpl` Actual implementation of shared preferences repo.
* `MockSharedPreferencesRepoImpl` Example of mocking this to avoid using shared preferences directly in test.

Tests
* `androidTest/CommentPresenterTest` Tests flow of comment presenter
* `androidTest/SharedPreferencesRepoTest` Tests shared preferences implementation

*app*
- Includes `commentssdk` as library module, starts on app open to demo functionality

Issues / features would resolve in a real project:
* Wakelock can be lost before the API call completes in the service if it runs very slowly. A solution is to use WakefulBroadcastReceiver, however this is deprecated in API 26.1.0+. Google docs suggest this is an old pattern, but need to investigate any new solution. Alternatives that work through dozemode might include GCM?
* Add simple methods for the SDK end-user to restart, or cancel the comment log service.
* If user quits app, re-opens, alarm may be queued twice. A solution is modify the user's SDK 'start' method call to check if an alarm exists.
* More tests