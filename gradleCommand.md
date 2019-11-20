
### gradle的常用命令

1.依赖树结果保存到工程根目录dependencies.txt文件中
```
./gradlew :app:dependencies --configuration releaseRuntimeClasspath > dependencies.txt
```

2.查看指定库的依赖的关系，如下展示lifecycle-livedata的被依赖关系，并将结果保存到dependenciesSingle.txt中
```
./gradlew :app:dependencyInsight --dependency lifecycle-livedata --configuration releaseRuntimeClasspath > dependenciesSingle.txt
```
