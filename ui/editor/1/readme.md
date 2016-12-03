1. 画原本的html架子
	1. 能修改原本的html中的dom元素并显示
2. 画render转化后的html架子
	1. 可以替换title中的内容
	1. 可以替换text中的内容
	1. 可以编辑title中的内容
	1. 可以编辑text中的内容
3. 写原本的html架子


# 正确的操作dom
1. 使用javascript操作dom
    1. 尝试parentElement获得父元素, 而不使用parentNode(应该无所谓,dom api中一般说节点主要指的就是element)
    1. 直接对parentElement使用getElement查找子元素中的text、node节点。getElement等操作可以直接对任一元素执行, 表示在它的子元素中查找。
    1. 使用querySelector定义条件获取节点(这是第二种常用的查询dom的方法)
2. 使用javascript保存value
3. 使用jquery操作dom
4. 使用react + nodejs实现
    1. 使用react实现静态版本
    2. 使用nodejs保存到本地文件