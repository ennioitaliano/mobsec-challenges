# 3. serialintent
- By checking out the victim app's manifest file, we can see that there is an exported SerialActivity
- The challenge's instructions say "Start the SerialActivity, it will give you back the flag. Kinda.", so we should start it with a result builder and check for any extras sent back
- Apparently there exists a way to execute another app's code, given some information about it
- So, in order, we:
    - get the path of the victim APK
    - create a custom class loader that is capable of loading classes directly from the "victim" application's APK
    - retrieve the Bundle object containing all the extra data that was passed with the Intent
    - retrieve a Java Serializable object from the Bundle associated with the key "flag". Because of the previous step, when the system deserializes this object, it will use the victimLoader. This means it can create an instance of a class that is defined in the "victim" app, even if the current app doesn't know about that class.
- Then, we:
    - use Java Reflection to look for any method named "getFlag" declared directly within the class
    - suppress Java's default access control checks, to call the method even if it is private
    - invoke the `getFlag()` method on the previously retrieved `flagContainer`
- Finally, we print the captured flag
- Note: since this is a sensitive operation, we had to declare it in the app manifest file (via the `queries` tag)


## Victim app's security issues
- custom signature-level permission could be used as countermeasure (only applications signed with the same cryptographic key can interact with each other)
- sharing a serializable object with other apps pretending it is private is a security issue
