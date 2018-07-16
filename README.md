comments
========

Simple Android module to pull comments from an API at a set interval.

*commentssdk*
* `CommentClient` Entry point for the SDK end-user
* `CommentService` Retrofit API service interface
* `Comment` Model for the REST API's comment json
* `CommentBroadcastReceiver` Triggers by AlarmManager, starts CommentIntentService
* `CommentIntentService` Performs API call, logs. Handles errors.

*app*
- Includes `commentssdk` as library module, starts on app open to demo functionality

Issues / features would resolve with more time:
* Wakelock can be lost before the API call completes in the service if it runs very slowly. A solution is to use WakefulBroadcastReceiver, however this is deprecated in API 26.1.0+. Google docs suggest this is an old pattern, but need to investigate any new solution. Alternatives that work through dozemode might include GCM?
* Add simple methods for the SDK end-user to restart, or cancel the comment log service.
* If user quits app, re-opens, alarm may be queued twice. Just need to modify the user's SDK 'start' method call to check if an alarm exists.
* Unit tests