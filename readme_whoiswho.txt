wrapper-alias   ---- capacitor wrapper game - renders:   https://meetqonline.com/meetqwords/        name MeetqWords

check for package com.meetqwords.mobile;

npx cap sync android
cd android
gradlew clean
gradlew assembleDebug

adb shell am start -n com.meetqwords.mobile/com.meetqwords.mobile.MainActivity


or 


npm run build && npx cap sync && npx cap open android


run in Android Studio


to create a release apk in C:\Users\User\Desktop\my_cursor_projects\wrapper-alias\android\app\release    - gradlew assembleRelease 

_______________________________________________________________________________________________________________________


wrapper-app1 ----- capacitor wrapper game renders: "https://meetqonline.com/meetqlingo/?from_app=1";    name MeetqLingo


word_match_native   Android Studio - mix (Webview) game  -  multiple wordmatch    name WordMatch



***********************************************************************************************


usefull commands:

find string, in Powershell as Admin

Select-String -Path ".\**\*" -Pattern "currently unavailable" -SimpleMatch -ErrorAction SilentlyContinue

