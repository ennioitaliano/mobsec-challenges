# 1. filehasher

Since the challenge explicitly states to define an activity with the intent filter for `com.mobiotsec.intent.action.HASHFILE`, I just added it to the main activity in the manifest, but I was still getting the `ActivityNotFoundException` message. 

By looking with more attention at the error message I saw that I was missing the `data` and `category` elements to proper handle the incoming intent - once added (as per error message), the victim app's activity is displayed correctly instead of crashing right away.

Then, I put the code needed to intercept the intent in the `onCreate` function of my app's `MainActivity`, and got a file URI: 
```content://com.example.victimapp.fileprovider/data/YM3oPnYG.dat```

I hashed its contents and put the hashed result in a result intent, ran the victim app again and correctly saw the flag in the logs.
