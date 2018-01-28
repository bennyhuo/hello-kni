Hello KNI
=========

修改 Google 官方 Jni 例子，加入对 Kotlin Native 生成的动态库的调用。目前 Jni 与 Kotlin Native 互通仍然需要 C wrap。实际上目前 Kotlin Native 只能通过 C 来与其他语言交互，这一点也可以参照 Kotlin Native 官方的 Python 例子。

### 构建步骤

1. 安装 Kotlin Native 编译器，当然你也可以自己编译。把编译器的路径（包含 konan 命令的目录）添加到 $PATH。
2. 运行以下命令得到 Kotlin Native 编译好的依赖：

	```sh
	cd prebuiltLibrary
	./build.sh
	```

3. 运行 app。

### 运行结果

运行截图：

![](images/screen-shot.png)

日志输出：

![](images/log.png)