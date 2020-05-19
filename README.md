
## 一、依赖EasyAnnotation

审核中
## 二、功能介绍

| 注解             | 描述                                                   |
| ---------------- | ------------------------------------------------------ |
| @BindView        | 指定控件绑定                                           |
| @OnClick         | 指定事件绑定                                           |
| @IntentField     | 指定Activity跳转时，Binder数据传输参数(范围：成员变量) |
| @IntentParameter | 指定Activity跳转时，Binder数据传输参数(范围：方法形参) |
| @IntentMethod    | 指定Activity跳转目标点标识                             |
| @ObjectFactory   | 指定生成对象工厂类                                     |

### 2.1、使用数据绑定功能

1. 初始化注解，需要在Activity初始化方法onCreate，调用AnnotationUtils.bind(this)进行注入。

   ```
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_three);
           AnnotationUtils.bind(this);
       }
   ```

2. View控件绑定使用@BindView(id)注入。

   ```
    @BindView(R.id.mTvTitle)
    public TextView mTvTitle;
   ```

3. View控件事件绑定使用@OnClick([id])注入。

   ```
      @OnClick({R.id.mBtSubmit})
       public void onClick(View view){
           if(view.getId()==R.id.mBtSubmit){
               Toast.makeText(this,"点击了",Toast.LENGTH_SHORT).show();
           }
       }
   ```
### 2.2、使用Activity之间跳转功能

#### 2.2.1、使用成员变量设置Activity中binder参数，@IntentField(目标Activity)、@IntentMethod(目标Activity)

1. Activity传递数据，并进行跳转。

   ```
   public class OneActivity extends AppCompatActivity {
       @IntentField("TwoActivity")
        String title = "OneActivity";
   
       @IntentField("TwoActivity")
        UserInfo userInfo;
       
        @IntentMethod("TwoActivity")
        public void jumpOneToTwoActivity() {
             userInfo = new UserInfo("username", "password");
             new TwoActivity$Jump().jumpActivity(OneActivity.this, title, userInfo);
        }
   
   }
   ```

2. Activity接收数据。

   ```
   public class TwoActivity extends AppCompatActivity {
   
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_two);
           initData();
           initView();
       }
   
       /**
        * 获取数据
        */
       private void initData() {
           TwoActivity$Init activity = (TwoActivity$Init)
                   new TwoActivity$Init().initActivity(this);
           String title = activity.title;
           UserInfo userInfo = activity.userInfo;
           Toast.makeText(this, "title=" + title + ";username=" +
                           userInfo.username + ";password" + userInfo.password,
                   Toast.LENGTH_LONG).show();
       }
    }
   ```

#### 2.2.2、使用方法形参设置Activity中binder参数，@IntentParameter(目标Activity)、@IntentMethod(目标Activity)

1. Activity传递数据，并进行跳转。

   ```
     @IntentMethod("MainActivity")
       public void jumpOneToMainActivity(@IntentParameter("MainActivity") String name,
                                         @IntentParameter("MainActivity") int count,
                                         String title) {
           new MainActivity$Jump().jumpActivity(OneActivity.this, name, count);
       }
   ```

2. Activity接收数据。

   ```
   private void initData() {
           MainActivity$Init activity = (MainActivity$Init)
                   new MainActivity$Init().initActivity(this, 0);
           int count = activity.count;
           String name = activity.name;
           Toast.makeText(this,
                   "count=" + count + ";name=" + name, Toast.LENGTH_SHORT).show();
       }
   ```
### 2.3、使用生成对象工厂类

1. @ObjectFactory(type=父类.class,key="类标识")，生成实例化对象。

   ```
   public abstract class BaseFragment extends Fragment {
   
       @Nullable
       @Override
       public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           View rootView = inflater.inflate(getLayoutId(), null);
           return rootView;
       }
   
       public abstract int getLayoutId();
   }
   ```

   ```
   @ObjectFactory(type = BaseFragment.class, key = "Home")
   public class HomeFragment extends BaseFragment {
       @Override
       public int getLayoutId() {
           return R.layout.fragment_home;
       }
   }
   ```

2. 通过标识获取实例化对象。

   ```
    private void initView() {
           HomeFragment main = (HomeFragment)
                   new BaseFragment$Factory().objectFactory("Home");
   }
   ```

   

## 赞赏

如果Open Coder对您有很大帮助，您愿意扫描下面的二维码，只需要支付0.01，表达您对我认可和鼓励。
> 非常感谢您的捐赠。谢谢!

<div align="center">
<img src="https://github.com/WeiSmart/tablayout/blob/master/screenshots/weixin_pay.jpg" width=20%>
<img src="https://github.com/WeiSmart/tablayout/blob/master/screenshots/zifubao_pay.jpg" width=20%>
</div>

---

## About me
- #### Email:linwei9605@gmail.com   
- #### Blog: [https://offer.github.io/](https://offer.github.io/)
- #### 掘金: [https://juejin.im/user/59091b030ce46300618529e0](https://juejin.im/user/59091b030ce46300618529e0)

## License
```
   Copyright 2020 offer

      Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.```

```
