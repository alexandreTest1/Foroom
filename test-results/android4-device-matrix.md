# Android 4 — Device Matrix

Test class: `com.example.foroom.tests.ConversationTests` (app `Foroom Training`, `debug` build).
Each configuration ran the full test class and every scenario separately; runs were repeated on top of earlier messages and accounts.

| # | Device / AVD | Type | Android / API | Screen resolution | Scenario 1 — johnWeek message | Scenario 2 — question in own chat | Scenario 3 — conversation with another account |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | Pixel_7 (AVD) | Emulator | Android 13 / API 33 | 1080 x 2400 | Pass | Pass | Pass |
| 2 | Samsung Galaxy S22 (SM-S901E) | Physical device | Android 14 / API 34 | 1080 x 2340 | Pass | Pass | Pass |
| 3 | realme C53 (RMX3760) | Physical device via Android Device Streaming | Android 15 / API 35 | 720 x 1600 | Pass | Pass | Pass |

Screenshots of the Android Studio test results: `screenshots/android4/`

- `pixel7_emulator_android13_api33.png`
- `samsung_galaxy_s22_android14_api34.png`
- `realme_c53_android15_api35.png`
