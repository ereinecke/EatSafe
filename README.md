# EatSafe
EatSafe is an Android app, currently in alpha, aimed at travelers with dietary restrictions that need help understanding ingredient lists in foreign languages. Based on the Open Food Facts API (openfoodfacts.org), EatSafe will allow a user to scan a product’s barcode and get all available nutritional information. The user will be able to set preferences for specific allergies or sensitivities that they are managing, and those will be highlighted if found in the database.

The user will also also be able to upload new products to the Open Food Facts database, along with ingredient data and any warnings they may have about it. A key feature to be implemented in a future release will be the ability to translate ingredient lists from labels.

![OpenFoodFacts logo](https://upload.wikimedia.org/wikipedia/commons/7/75/Open_Food_Facts_logo.svg)

EatSafe is built with thanks to the following libraries:

  - Jackson JSON https://github.com/FasterXML/jackson
  - StreamProvider https://github.com/commonsguy/cwac-provider
  - Material bottom toolbar https://github.com/aurelhubert/ahbottomnavigation
  - Sugar ORM  http://satyan.github.io/sugar/
  - ZXing barcode https://github.com/zxing/zxing
  - Loyal Slider  https://github.com/jjhesk/LoyalNativeSlider
  - About Library  https://github.com/mikepenz/AboutLibraries
  
Android components used in this app:
  
  - Material Design, including tab pager and bottom navigation
  - Barcode scanning
  - Widget to launch scan
  - AdMob ads via firebase-ads
  - IntentService which pulls searched-for item from content resolver or REST call to openfoodfacts.org.
  - ContentProvider, ContentResolver, Cursor, CursorAdapter to display previously recalled data.
