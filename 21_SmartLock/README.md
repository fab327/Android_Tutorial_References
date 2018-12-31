## Tutorial on learning Smartlock [Codelab link](https://codelabs.developers.google.com/codelabs/android-smart-lock/index.html?index=../../index#0)

### Note for Android Oreo and up devices: 
because of the Autofill framework, the only relevant portion for those devices would be the [MainActivity#saveCredential](/21_SmartLock/app/src/main/java/com/google/codelab/smartlock/MainActivity.java). This implies that credentials would be saved for future retrieval by the framework
