# Slick
Slick is  an Android library to create applications following the principles of Clean Architecture.

# Article

For more details check this [article.](https://medium.com/@abozaid/slick-for-clean-architecture-727a0b5fc015)

# Gradle

and this in dependencies
```
compile 'com.github.aliabozaid.slick:Slick:0.2’
```

# Testing
Just run this command
```
./gradlew assembleDebug
```


# Usage
At first, your fragment or activity should extend **`BaseFragment`** or **`BaseActivity`** and implements the callback interface. 

The callback should contain your custom response. 

_**i.e:**_ In our sample,

**`IPlacesFragment`** extends **`IBaseFragment`** _(You can also use **`IBaseActivity`** in case of activity)._ 

#### Details
**`IPlacesFragment`** has the following methods:
```
void updateView(ListOfPlacesModel data);
 
void showError(Throwable t);
```

 **`getpresenter()`** should return an **`IPresenter`** object.
 
```
@Override public IPresenter getPresenter() {
    return presenter;
  }
 ```

Your presenter interface should extends **`IPresenter`**. Then, inject the presenter object inside Fragement or Activity.

```
@Inject IPlacesPresenter presenter;
```

Finally, use the interactor to handle the request from the data the server.

## License

```
Copyright 2017 Abozaid

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```





