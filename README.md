# BusLinesApp

## 介绍
哦，上帝，这该死的小学期，我要狠狠踢他的屁股。

## 重点

**在`push`自己的代码前，请一定一定一定先`pull`，避免版本冲突**

**如果发生冲突，请和组员确认代码，切勿自己强行合并**

## 软件架构
```bash
app
|__ java
|     |__ com.cuc.constellationapp
|            |__ utils		# 基础工具层，可以放运算、字符串检查等工具
|            |__ dao	    # dao层，实现数据库交互
|            |__ model		# 放数据
|			|	 |__ api	# API数据
|			|	 |__ data	# 类图设计的类
|            |__ httprequest  # 接口请求
|            |__ view		# 视图
|		          |__ activity
|		          |__ fragment
|		          |__ adapter	# 绑定数据
|		          |__ holder	# 获取RecycleView的item项
|__ res
     |__ layout	# 布局文件
     |__ drawable	# 图片
     |__ menu	# 导航栏多选项
     |__ values	# 所有字号、颜色、文字
```


