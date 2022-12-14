# Hello Architecture
I create this project to show what's for me the best way to build an Android app on 2022.
## Architecture
![Architecture Diagram](https://i.imgur.com/Ks7AhZE.png)  
I use this diagram as and idea to build this app because it approaches to famous Clean Architecture (that's what I try to follow). It has 3 layers:
- Presentation
- Domain
- Data  

Every layer is complete independent from another, that allow us to test every layer more easy.    

I tried to follow this layer independency with package structure too, it looks like this:  
![Package structure](https://i.imgur.com/xuUn8O5.png)
- DI: dependency injection, this time I use [Koin](https://insert-koin.io/) because everyone was talking about it and I need to try it. Conclusion: I LOVE IT!
- Data: to get, save and show every call that user ask. Where repository implementations are.
- Domain: every request that UI needs it's translated to an usecase (for example, fetch every user from backend) and every usecase interface
- Presentation: for all UI classes, as Fragments, ViewModels, Adapters, Activities...
    
This is a more detailed view of package structure.  
![More detailed package structure](https://i.imgur.com/qyQMNOS.png)
### Presentation layer
Starting with the UI part, I tried the ["Single Activity Architecture"](https://www.youtube.com/watch?v=2k8x8V77CrU) because I want try it with the new [Navigation Component](https://developer.android.com/guide/navigation) which makes build the app more easy. (You only have to have one Activity and the rest is all Fragments)  

I use [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) for avoid NPE and inconsistency with [Animations](https://developer.android.com/guide/navigation/navigation-animate-transitions). It's a big jump from the old way.

I use the famous MVVM pattern to build the app, UI layer has his own model, LiveData on ViewModel which are observerd from fragments. I tried to have light fragments which only needs to observe data from ViewModel.  

Every time user wants can refresh the user list with a simple swipe ([SwipeRefreshLayout](https://developer.android.com/reference/androidx/swiperefreshlayout/widget/SwipeRefreshLayout)) and always show user when something it has been updated, removed, created... (and of course, if something fail, it's displayed to user)

And to prove how easy is to have an adaptable layout and did it in this way.
Creating a values integer files different depends on weight if it's more than 600dp:  
![Values integer files](https://i.imgur.com/cKIchfo.png)
![Contain](https://i.imgur.com/IIDgMHY.png)  
If it's more thant 600 it has 2 gallery per column, if's not, only 1.
And with this code it allows you to show more or less columns:  
![Code](https://i.imgur.com/SHRoXWa.png)
![Portrait mode](https://i.imgur.com/T0ezdKL.png)
![Landscape mode](https://i.imgur.com/U2c2kUX.png)

### Domain layer
Where every UseCase live.  
I use again Flow to comunicate with viewmodels.  
I always create and interface for every usecase to make me testing more easy.

### Data
I use Room to save user data (using own model which saves remoteId from backend) with Flow too.
I use Retrofit to call backend with suspend functions.

### Repository data flow
![Repository data flow](https://i.imgur.com/BOFmL5d.png)
1. Use case ask repository to fecth user data
2. Repository ask backend to get new data
3. Remote gives this data to the repo
4. The repo save this data in local
5. When data is save in local it's notified repo
6. Repo inform usecase with new data

## Testing
I tested one view model to show how it's possible to test coroutines and I tested every mapper to show how simple is to unit test in Android.  

For ViewModel is easy to test the behaviour because you can implement an usecase interface and do whatever you want. 