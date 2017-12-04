# AGuider

[![Release](https://jitpack.io/v/leguang/AGuider.svg)](https://jitpack.io/#leguang/AGuider)

AGuider是一个简单易用的构建新手引导的工具，可以让开发者只需要传入xml布局文件即可方便而又灵活地创建属于自己引导界面。（欢迎Star一下）
## 能做什么？([下载 apk](https://github.com/leguang/AGuider/blob/master/app/app-release.apk))
- **只需要通过传入布局ID即可定制出自己的引导界面**
- **有带遮罩+高亮和Popwindow两种**
- **默认提供一些动画**
- **简洁的API，简单的配置**

## 如何使用它？

1. 先在项目目录下的的build.gradle 的 repositories 添加:
```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

2. 然后在App目录下的dependencies添加:
```
	dependencies {
	     //新手引导工具，有带遮罩的和Popwindow两种。
   		 compile 'com.github.leguang:AGuider:0.0.1'
	}
```
此时同步一下，即已完成引入。

### AGuider的简单使用：

```
   new BaseDialog(this)
        .setLayoutId(R.layout.share_layout)//传入你的xml布局。
        .setConvertListener(new ADialogListener.OnDialogConvertListener() {
            @Override
            public void convert(BaseViewHolder holder, final Dialog dialog) {
                //通过ViewHolder对View进行一些定制化。
                holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击关闭");
                        dialog.dismiss();
                    }
                });
            }
        })
        .setDimAmount(0.3f)//设置window的暗度。
        .setGravity(Gravity.TOP)//位置有四种选择。
        .setAnimStyle(R.style.SlideAnimation)//进入和退出动画。
        .show();//显示。
```

### DialogFragment的简单使用：

```
    new BaseDialogFragment()
           .setLayoutId(R.layout.share_layout)//传入你的xml布局。
           .setConvertListener(new ADialogListener.OnDialogFragmentConvertListener() {
               //通过ViewHolder对View进行一些定制化。
               @Override
               public void convert(BaseViewHolder holder, DialogFragment dialog) {
                   holder.setOnClickListener(R.id.wechat, new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           showToast("点了微信");
                       }
                   });
               }
           })
           .setDimAmount(0.3f)//设置window的暗度。
           .setGravity(Gravity.BOTTOM)//位置有多种选择。
           .setAnimStyle(R.style.SlideAnimation)//进入和退出动画。
           .show(getSupportFragmentManager(), "MyBaseDialogFragment");//显示。
```

### 用DialogFragment显示Dialog。
```
 AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("注意：")
                        .setMessage("是否退出应用？")
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .create();

                new BaseDialogFragment()
                        .setDialog(alertDialog)
                        .setGravity(Gravity.TOP)
                        .show(getSupportFragmentManager());
```
Demo中有更多使用实例。

## 高级用法：
当然你也可以通过继承Dialog或者BaseDialogFragment来改造属于自己的对话框。

>**持续更新!，欢迎Issues+Star项目**

## 感谢
[Alex-Cin/Dialog](https://github.com/Alex-Cin/Dialog)

[CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)

## License

```
Copyright 2016 李勇

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
