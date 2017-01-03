# 基本目的: 
实现mongo document 到 java domain object的映射

参考: 
1. [jongo - query in java as in mongo shell](https://github.com/bguerout/jongo)
2. [MJORM - mongodb java orm, both xml-based & annotation-based](https://code.google.com/archive/p/mongo-java-orm/)

# 主要功能点:
1. 定义映射关系(xml或annotation)
2. document和pojo的相互映射和转换

# tasks:
## 使用xml定义映射关系，并实现document和pojo的相互转换
1. 可以定义映射关系
	2. 可以在映射表（objectMapper）中注册并获取相应的对象映射描述（objectDescriptor）－15: 11
	3. 可以在对象映射描述（objectDescriptor）中添加并获取相应的属性映射描述（PropertyDescriptor）－15: 6
	4. 可以从xml文件中读取所有的对象映射描述
		5. 可以从xml document解析并得到相应的对象映射描述
			6. 对象描述支持class －15: 12
			4. 属性描述支持property、field －15: 23
			5. 属性描述支持isId －10: 6
		6. 可以在映射表中注册多个xml文件 －15: 22
3. 对象转换
	4. 类型一致，直接转换 －10: 5
	5. 类型不一致：
		6. document 和 pojo
			5. document －> pojo －15: 60
			    1. 转换继承属性 -15 : 29
			    1. convert to a collection of corresponding pojo when the document field is a collection -15: 22
			    1. convert to a collection of corresponding pojo when the document field is an array -15:
			    1. String to enum -10:
			    1. objectId to String -15:
			6. pojo -> document －15: 15
				7. 如果没有指定_id，自动生成ObjectId作为_id －10: 4
				1. 转换继承属性 -10: 6
				1. convert to a collection of corresponding document object when the pojo property is an collection -15:
				1. convert to a collection of corresponding document object when the pojo property is an array -15:
				1. enum to String -5:
				1. String to objectId -15:
		<!--<!--1. collection 和 mongo相应类型(BsonArray)-->-->
			<!--<!--8. collection -> BsonArray -15:-->-->
			<!--<!--9. BsonArray -> collection －15:-->-->
		<!--10. string 和 enum-->
			<!--11. string -> enum -10:-->
			<!--12. enum －> string -5:-->
		<!--13. string 和 ObjectId-->
			<!--14. string -> objectId -15:-->
			<!--15. ObjectId -> string -5:-->