# 基本目的: 
实现mongo document 到 java domain object的映射

参考: 
1. [jongo - query in java as in mongo shell](https://github.com/bguerout/jongo)
2. [MJORM - mongodb java orm, both xml-based & annotation-based](https://code.google.com/archive/p/mongo-java-orm/)

# 主要功能点:
1. 定义映射关系(xml或annotation)
2. document和pojo的相互映射
    1. 基本数据类型映射
    2. 嵌套数据类型映射
    