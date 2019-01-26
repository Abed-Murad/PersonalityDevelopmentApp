# Personality Development App Replica

Description

Personality Development App Replica is a Replica of The [Personality Development App](https://play.google.com/store/apps/details?id=com.mayur.personalitydevelopment&amp;hl=en_US) in google play store , with the same designs and different implementation,it is a also a temple for article based app that can be used to show simmler Master/details content ,along with an inspiring quotes section.

The App provides Licenses Content Copied with the permission of their creators, that content can be articles , youtube videos, images and more.

The user can interact with the content by liking or disliking the content, adding comments, saving bookmarks, reporting content and sending suggestions

The Finial App will have two flavors free , and paid (which will have no ads and some exclusive content)



## Intended User

This App is for people who would like to improve their personality

## Screenshots

![Screenshots](https://github.com/Abed-Murad/PersonalityDevelopmentApp/blob/master/Google%20Play%20Res/Store/MockUp%20Cover%201.2.png?raw=true "Title")


## Features

Version 1

- Push notifications
- Launch Screen
- Show Articles and Videos cards
- Show Full Article Details
- Share Content
- On Cloud Search
- All the contents is tagged
- Search all content with a single tag
- RateUs, About , Feedback

Version 2

- Ads
- Authentication With Facebook &amp; Google
- Bookmark content
- Like Content
- Comment Content
- Images Quotes Section
- Download, Like, Share Quotes

Version 3

- Create, Like, Report   Posts
- Paid , Free Flavors
- Settings(Night Mode, Font Size , Notification )
- Widget
- Unit Testing
- Dependency Injection
- Arabic Support

Technologies

- Crashlytics
- Cloud Firestore
- Firebase Auth
- AAC (Room , ViewModel , LiveData , Respiratory )
- Admob Ads
- Android app Bundles

## Key Considerations

- All The Strings, dimensions, colors, are based on their corresponding files.

How will your app handle data persistence?

The App will use Android Architecture Components ( Room , ModelView , Life Data ), along with the Repository class which will insure the the implementation of the Single source of truth principle

Describe your UI/UX Guidelines.

The App will use the Google Material Design Guidelines and any corner cases will be describe below

## Describe any libraries you&#39;ll be using and share your reasoning for including them.

Retrofit : Https Requests
Glide: Image Loading

Firebase Auth: Authentication

Firebase Messaging: Push Notification

Binding Library: Data Binding

Gson: converting json to pojo

Google Material Icons

Font Awesome

Espresso: Testing

Room: data persistence

Describe how you will implement Google Play Services or other external services.

The App will use Google Sign-in, Google Analytics, Throw Firebase

|   |
| --- |

## Required Tasks

Version 1

Task 1: UI/UX implementation

- The Launch Screen with A placeholder Logo [logo\_placehoder\_gray.png](https://photos.app.goo.gl/9riHspyw5ok23QMh8)
- Side Navigation Drawer layout
- Tags Header Layout
- Search icon in The Toolbar
- Two types content cards (Article or youtube video)
  - Article (Image, Title, Time Stamp)
  - Video (Image, title, Time Stamp)
- Content details layout contitnes:
  - Image Header
  - Title
  - Time Created
  - Body (Plain Text , Links , Images )
  - Share Floating Button
- Hide the Toolbar when Scrolling down

Task 2: Data Models Creating and pulling

- Create Firestore Models For The Content (Articles and youtube Videos)
  - Article ( id, Title, Body, Image, Url, Time Stamp,source name, Source Url, List\&lt;Tag\&gt; )
  - Video ( id, Title, Image, Url, Time Stamp, List\&lt;Tag\&gt; )
- Create The Room Database
  - ViewModels
  - LiveData
  - The Repository Class
  - Single source of truth principle
- Connect The Firestore Models to the Room Entities

Task 3: User Interaction

- Implement Share Floating Action Button to share article body or youtube video url with a text to the app in the end of each shared text
- Implement Firebase Notification
- Implement Firebase Database For the Favorite Articles
- Add RateUs, About , Feedback in the Navigation Drawer

Task 4: Search

- Implement Search Functionality based on [android developer site guidelines](https://developer.android.com/guide/topics/search/)
- Add Voice Search
- Add Offline Search if the wifi is down, otherwise always search on Firestore



Version 2

Task 1: UI/UX implementation

Task 2: Data Models Creating and pulling

Task 3: User Interaction

Version 3







User Interface Mocks
