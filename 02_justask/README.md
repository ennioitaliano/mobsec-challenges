# 2. justask
- The victim app has 4 activities, and each one of them contains a different part of the flag
- The first one has no implicit intents available, so an explicit one should be used, but simply opening it does not print anything useful in the console.
- The key is to look for activity results, particularly for extras, in which we found the first flag.
- The second flag is obtained by just executing the action of the implicit intent.
- The process for the third one is the same as the first one.
- The fourth flag extraction process started like the second one, but extras contained bundles instead of strings, so the result launcher had to be modified a little. Also, a recursive functin is needed until a string is obtained. 

## Victim app's security issues
Practically overlapping with [filehasher's ones](https://github.com/ennioitaliano/mobsec-challenges-solutions/tree/main/01_filehasher#victim-apps-security-issues).
