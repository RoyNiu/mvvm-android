# mvvm-android https://github.com/RoyNiu/mvvm-android

**Tech**
- hilt,Coroutine,retrofit,Gson,Glide,mvvm

**MVVM Best Practice:**
- Avoid references to Views in ViewModels.
- Instead of pushing data to the UI, let the UI observe changes to it.
- Distribute responsibilities, add a domain layer if needed.
- Add a data repository as the single-point entry to your data.
- Expose information about the state of your data using a wrapper or another LiveData.
- Consider edge cases, leaks and how long-running operations can affect the instances in your
  architecture.
- Don’t put logic in the ViewModel that is critical to saving clean state or related to data. Any
  call you make from a ViewModel can be the last one.

**simplified explanation**
- Simple page make a network request directly use the EasyApi.Complex page should inherit from
  RemoteBase
- You can define your own business error type.If you need show errorMsg,plz override
  the baseViewModel's method getFeatureErrorMap, it will add your error type into the errorManager
- There are two type of dataRepository in the example,I know it looks like a bit redundancy, but it
  is really necessary in some complex feature.
- Actually, I have already gap 1 year,and maybe develop this library is my "rehabilitation",hahaha. If
I have time later on,I'll enhance the "localRepository" by integrating Room and offer server caching
mechanism to choose from.I plan to encapsulate these caching functionalities in the domain layer to
make it easier to use in upper layer.However for the foreseeable future, I'll focus on finding a job
abroad.Wish me luck.
- At last, sorry for my poor English ,even though I ve been learned it for 1 year.