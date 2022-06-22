

>"https://api.minerstat.com/v2/coins" servisinden kripto para dataların çekilmesi ile ilgili repo.

# Gerekli Bağımlılıklar

* Navigation

``` 
  implementation "androidx.navigation:navigation-fragment:$nav_version" 
  implementation "androidx.navigation:navigation-ui:$nav_version
```


* Firebase entegration (Email, Facebook, Google)

``` 
    implementation platform('com.google.firebase:firebase-bom:30.0.1')
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.android.gms:play-services-auth:20.2.0'
    implementation 'com.facebook.android:facebook-android-sdk:[8,9)'
```

* Retrofit

``` 
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
```


# Navigation Görünümü 
![navigation_](https://user-images.githubusercontent.com/64421235/175114153-73ef6945-dc5c-4f36-b6f3-bd3424488c82.png)
