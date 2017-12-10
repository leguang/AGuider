# AGuider

[![Release](https://jitpack.io/v/leguang/AGuider.svg)](https://jitpack.io/#leguang/AGuider)

AGuider是一个简单易用的构建新手引导的工具，可以让开发者只需要传入xml布局文件即可方便而又灵活地创建属于自己引导界面。（欢迎Star一下）
![](screenshots/gif01.png)![](screenshots/gif02.png)


## 能做什么？([下载 apk](https://github.com/leguang/AGuider/blob/master/app/app-release.apk))
- **只需要通过传入布局ID即可定制出自己的引导界面**
- **有带遮罩+高亮**
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
   		 compile 'com.github.leguang:AGuider:+'
	}
```
此时同步一下，即已完成引入。

### AGuider的简单使用：

```
   new Guider.Builder()
                   .setAnchor(this)
                   .addGuide(new Guide.Builder()
                           .setPoint(textView)
                           .setPosition(Position.bottomLeft())
                           .setView(R.layout.guide_0)
                           .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                               @Override
                               public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                   holder.setText(R.id.tv_des, "骚年，没错，就是这里……");
                               }
                           }).build())
                   .addGuide(new Guide.Builder()
                           .setPoint(imageView)
                           .setPosition(Position.bottomRight())
                           .setView(R.layout.guide_0)
                           .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                               @Override
                               public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                   holder.setText(R.id.tv_des, "对对对，你说的都对……");
                                   ImageView imageView = holder.getView(R.id.iv_arrow);
                                   imageView.setImageResource(R.drawable.arrow_topleft);
                                   LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                                   layoutParams.gravity = Gravity.LEFT;
                                   imageView.setLayoutParams(layoutParams);
                               }
                           }).build())
                   .addGuide(new Guide.Builder()
                           .setPoint(button)
                           .setPosition(Position.top())
                           .setHighlight(Highlight.oval())
                           .setView(R.layout.guide_1)
                           .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                               @Override
                               public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                   holder.setText(R.id.tv_des, "大爷，你终于来了……");
                                   ImageView imageView = holder.getView(R.id.iv_arrow);
                                   imageView.setImageResource(R.drawable.arrow_bottom);
                               }
                           }).build())
                   .show();
```

### 组合用法。
```
  Guider guider0 = new Guider.Builder()
                 .setAnchor(this)
                 .addGuide(new Guide.Builder()
                         .setPoint(textView)
                         .setPosition(Position.bottomLeft())
                         .setView(R.layout.guide_0)
                         .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                             @Override
                             public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                 holder.setText(R.id.tv_des, "骚年，没错，就是这里……");
                             }
                         }).build())
                 .addGuide(new Guide.Builder()
                         .setPoint(imageView)
                         .setPosition(Position.bottomRight())
                         .setView(R.layout.guide_0)
                         .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                             @Override
                             public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                 holder.setText(R.id.tv_des, "对对对，你说的都对……");
                                 ImageView imageView = holder.getView(R.id.iv_arrow);
                                 imageView.setImageResource(R.drawable.arrow_topleft);
                                 LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                                 layoutParams.gravity = Gravity.LEFT;
                                 imageView.setLayoutParams(layoutParams);
                             }
                         }).build())
                 .addGuide(new Guide.Builder()
                         .setPoint(button)
                         .setPosition(Position.top())
                         .setHighlight(Highlight.oval())
                         .setView(R.layout.guide_1)
                         .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                             @Override
                             public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                 holder.setText(R.id.tv_des, "大爷，你终于来了……");
                                 ImageView imageView = holder.getView(R.id.iv_arrow);
                                 imageView.setImageResource(R.drawable.arrow_bottom);
                             }
                         }).build())
                 .build();
 
         Guider guider1 = new Guider.Builder()
                 .setAnchor(this)
                 .setMode(Guider.MODE_TOGETHER)
                 .addGuide(new Guide.Builder()
                         .setPoint(textView)
                         .setPosition(Position.bottomLeft())
                         .setView(R.layout.guide_0)
                         .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                             @Override
                             public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                 holder.setText(R.id.tv_des, "骚年，没错，就是这里……");
                             }
                         }).build())
                 .addGuide(new Guide.Builder()
                         .setPoint(imageView)
                         .setPosition(Position.bottomRight())
                         .setView(R.layout.guide_0)
                         .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                             @Override
                             public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                 holder.setText(R.id.tv_des, "对对对，你说的都对……");
                                 ImageView imageView = holder.getView(R.id.iv_arrow);
                                 imageView.setImageResource(R.drawable.arrow_topleft);
                                 LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                                 layoutParams.gravity = Gravity.LEFT;
                                 imageView.setLayoutParams(layoutParams);
                             }
                         }).build())
                 .addGuide(new Guide.Builder()
                         .setPoint(button)
                         .setPosition(Position.top())
                         .setHighlight(Highlight.oval())
                         .setView(R.layout.guide_1)
                         .setOnConvertListener(new AGuiderListener.OnConvertListener() {
                             @Override
                             public void convert(BaseViewHolder holder, GuiderView guiderView) {
                                 holder.setText(R.id.tv_des, "大爷，你终于来了……");
                                 ImageView imageView = holder.getView(R.id.iv_arrow);
                                 imageView.setImageResource(R.drawable.arrow_bottom);
                             }
                         }).build())
                 .build();
 
         new AGuider.Builder()
                 .addGuiders(guider0, guider1)
                 .show();
```

## 高级用法：
Demo中有更多使用实例。

>**持续更新!，欢迎Issues+Star项目**

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
