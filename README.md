# Android Utils


Some self-used Android utils.

- AboutMsgRender
- ChineseTraditionalFestivals
- DialogRootView
- GlobalToast
- IconGridView
- SP
- UriAnalyser


### How to use?

Firstly, download and copy `globaltoast-1.1.2.aar` to `libs` directory in your project.

Then add code below to `build.gradle`:
```
repositories {
    flatDir { dirs 'libs' }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    compile(name: 'globaltoast-1.1.2', ext: 'aar')
}
```


### License

    Copyright 2016-2017 By_syk

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


*Copyright &#169; 2017 By_syk. All rights reserved.*