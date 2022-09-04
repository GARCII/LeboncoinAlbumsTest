<p>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
</p>

# Architecture description

The chosen architecture to build this project was "Clean architecture" . Why ?

Basically we have 3 layers, i'll explain each of these layer later, but
using this architecture will lead to a separation of concerns and will help us
test this layers in isolation in a way that doesn't really affect each others.


- **Domain**: the domain layer, contains our business logic, defining 
  repositories, entities and use cases that can be also called interactors as well.
  Finally this layer shouldn't have any dependency on any other layers or third part libraries.

- **Data**: the data layer will implement what is already defined in the domain such as repositories.
and here you can have multiple data source implementation like caching in local database or getting data remotely from
an API. This layer also the responsibility to map data between domain and data models.

- **Presentation**: the presentation layer contains viewModels and uses MVVM design pattern (created by Microsoft btw). This is a little bit particular because
we still can move viewModels to the application but I prefer to keep it separate. It's just a personal choice. The main purpose of viewModels
here is to just maintain and keep our state when configuration changes.

- app: the app module contains basically Android Framework components (fragments, activities, etc..) that we need to display data.

**For more information** : 
- Great [book](https://www.amazon.fr/Clean-Architecture-Craftsmans-Software-Structure-ebook/dp/B075LRM681/ref=tmm_kin_swatch_0?_encoding=UTF8&qid=&sr=) written by Robert C. Martin (Uncle Bob)

# libraries

Exhaustive list of libraries helped me to achieve this project.

### Network
Retrofit was an obvious choice for the simplicity that it can offer to perform API calls. in addition 
the API call has no custom requirements in terms of caching, request prioritization and retries.

### Network response deserialization
 [Moshi](https://github.com/square/moshi) was introduced as a small, efficient and safer alternative to Gson. Using moshi codegen will create compile time
 adapters to model classes, which will remove the usage of reflection in runtime. In terms of size, Moshi+Okio combined is more light-weight than Gson.


### Dependency injection with Hilt
I used [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) library backed by Google, It was built on top of ```Dagger``` and inherit many benefits like 
runtime performance, compile time validation and faster build. Hilt provides a standardized way to manage dependency injection in particular Android with scoping component, extension for viewModels and tooling support.


### Display Images
[Glide](https://github.com/bumptech/glide) is used to load and cache images asynchronously and works fine with [okhttp](https://square.github.io/okhttp/)

### Caching
Data persistence was done with the help of [Room](https://developer.android.com/jetpack/androidx/releases/room?gclsrc=ds&gclsrc=ds), which is JetPack compatible, easy to test, and check queries at compile time (expect ```RawQuery```). 

### Asynchronous task
Kotlin coroutines are used to perform asynchronous tasks. They are described as "light-weight threads" and added to Kotlin in version ```1.3```.
It helps to manage long-running tasks using suspend functions and offer the possibility to run these tasks on different optimized threads.

Coroutines work perfectly with Flows which are used to handle streams of data.

# Technical choices

### Data transfer object

Mapping data between layers allowing to create other representations of the same domain without affecting it models

### Repository pattern

Repository defines how to manage data operation in domain layer, in our case ```AlbumRepository``` and implementation in data layer
providing multiple data sources (remote or local)

### MVVM

As mentioned earlier, the presentation layer using MVVM design pattern, the ```ViewModel``` is aware of the Android lifecycle supporting Coroutines, LiveData and Flows.
the most important that views and viewModels are not tightly coupled compared to other architectures like MVP for example.

### Loading data progressively

I was intended to use [JetPack paging library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) to keep in memory a limited amount of albums, but i've noticed that
it was not clean architecture compatible in my opinion. Why ?

I didn't find a proper way to map ```PagingSource<Int, Album>``` type coming from ```AlbumDao``` to ```Album``` domain model. I found an [issue](https://issuetracker.google.com/issues/206697857) describing my point of view. Once released I'll be happy to test it.

In the meantime I used classic infinite scroll to solve this issue.

# Testing

- Junit: for assertions
- It was my first time using [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) library to perform API tests

# Perspective

- I was intended to add more features but i didn't has enough time to maximize and insure a best user experience like : 
  - The ability to sort albums by ```index``` or ```title```
  - Create album details screen
  
- I had many questions while working on this test, basically it was about how to design and structure data, i thought about :
    - Create 2 separate tables ```Album``` and ```Title```
    - One ```Album``` could have one or many ```Titles```
    - But noticing that I was creating a whole ```Album``` table finally for a single attribute which is ```albumId```, so I went for simplicity  thinking that if we had more Album data in the future then I'll reconsider my choice.
