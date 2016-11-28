## rich game tasks
Feature: use polymorphism to implement, especially for ui comparing to rich-java-mechanism and conducted tdd for ui

### PLAYER TASKS
1. 玩家状态多态（多态行为：不同状态执行命令时，有不同的操作）
	1. 玩家在wait for command状态，如果执行了需要回复的命令，应该记录需要回复的命令, wait for response － 10 : 14
	1. 玩家在wait for response状态，如果回复了上一条待回复命令后，不接受进一步的response，end turn － 10 : 9
1. 玩家命令多态（多态行为：不同命令执行不同的操作）
	1. 玩家输入roll命令（place多态（多态行为：走到不同的place，有不同的操作））
		1. estate多态（不同的estate，到达后有不同的操作）
			1. 玩家走到empty estate，
				1. 如果钱不够，end turn －5 : 10
				1. 如果钱够，wait for response to buy estate － 5 : 
				1. 如果response yes，买房; response no,不买. end turn －5 : 17
			1. 走到own estate
				1. 钱不够，end turn －5 :
				1. 最高级，end turn －5 : 16
				1. 如果钱够，wait for response to upgrade estate －5 :
				1. response yes， upgrade; response no，不upgrade. end turn －5 : 5
			1. 走到others estate
				1. 钱不够，交过路费，bankrupt －5 : 4
				1. 钱够，交过路费，end turn －10 : 8
				1. 如果有福神，end turn －5 : 4 
				1. 如果房主在监狱或医院，end turn －5 : 2
            1. 走到toolhouse
                1. 点数不够，end turn －5 ：6
                2. 如果点数够，wait for response to select tool －5 :  
                3. 选择工具后，买工具，继续留在toolhouse(回到a) －5 : 18
                1. F后，end turn －5 :  5
            1. 走到gifthouse
                1. wait for response to select gift  10 : 
                1. 输入正确，获得gift; 输入错误，不获得gift. end turn. (gift多态（不同gift有不同行为）) －10 : 
                    1. 资金卡：加钱，end turn : 12
                    1. point卡：加点数，end turn : 1
                    1. 福神：获得福神，end turn : 1
                        1. 福神5轮内有效 －10 : 5
            1. 走到prison，进入prison，end turn －5 : 3
                1. 在prison待两天 －2 : 1
            1. 走到hospital，end turn －3 : 1
            1. 走到magic house，end turn －3 : 1  
            1. 走到mineral estate，加点数，end turn －5 : 2  
            1. tool多态：（经过不同的tool有不同的行为）
                1. 经过炸弹，送进医院，end turn －3 : 
                    1. 医院待三天 －3 - 8
                1. 经过block，停止，进到block后的place －3 : 5 
    1. block n. wait for command. 如果有block，在地图上放block －10 ：
    1. bomb n. wait for command. 如果有bomb，在地图上放bomb，wait for command －10
    1. robot. wait for command. 如果有robot，在地图上使用robot，wait for command －3 -
    1. sell x. wait for command. 如果有房产，卖房产，置为空地 －10
    1. sellTool x. wait for command. 如果有tool，卖tool -10
    1. query，打印player信息，wait for command －10 :  3
    1. help，打印help信息，wait for command －5 : 3

### MAP TASKS
1. 可以从map的一个点移动到另一个点
    1. 移动到目标  －15 : 
    1. 经过炸弹或block就停 －10 : 
1. 可以在map上放block，bomb
    1. 能在指定地点放 －5 :  
    1. 只能在前后十步放; 如果有人不能放; 如果有道具不能放 －10 
1. 可以在map上使用robot清扫前后10步的道具 －5 : 

### GAME TASKS
1. 游戏开始后，初始化玩家和地图. 默认第一个玩家是当前玩家,其余玩家都在等待. 所有玩家都被放到地图的开始位置 －15 : 
1. 游戏开始后，可以接受玩家输入命令并指定当前玩家执行命令 - 10 : 
1. 游戏开始后，可以切换玩家
    1. 可以切换到下一玩家 －5 : 
    1. 如果当前玩家end turn，应该切换到下一玩家 － 10 :  
    1. 如果下一玩家bankrupt后，切换到下一玩家 －5 :
    1. 如果下一玩家停留在医院或监狱，切换到下一玩家 －5 : 
1. 游戏开始（game_start）后，可以结束游戏
    1. 如果手动结束游戏，game_end －5 : 
    1. player bankrupt后，如果只有一个player没有bankrupt，game_end －10 :

### IO TASKS
1. 初始化game,依据输入的初始资金和玩家. -15
2. 显示map(多态行为: 不同类型的place显示不同的符号)
    1. 显示地标 -15
    1. 如果有炸弹,显示*; 如果有block,显示@ -5
    1. 如果有玩家,显示玩家颜色 - 15
    1. 不同级别的地产显示不同的数字 -5
1. 将用户输入转化为命令 (多态行为: 不同的用户输入转为不同的command) -15
1. 如果用户有输入, 就可以拿到输入的命令(迭代行为:用户不断有输入)  -15
1. 提示信息
    1. 当前用户输入命令roll
        1. 并且在own estate房前，提示是否升级
        1. 在empty estate房前，提示是否购买
        1. 在toolhouse房前，提示选择购买工具
        1. 在gifthouse房前，提示选择礼品
        1. 在other estate前，
            1. 如果需要交过路费，提示应该交过路费
            1. 如果破产,提示破产
            1. 如果有福神，提示“福神附身，免过路费”
            1. 如果房主在医院或监狱，提示“。。。免过路费”
        1. 在prison，提示“监狱待2天”
        1. 在block前, 提示“遇到block停止”
        1. 在bomb, 提示“遇到bomb，进医院3天“
        1. 在矿地, 提示”获得点数“
    1. 当前用户输入命令query，显示当前玩家的资产信息
    1. 当前用户输入命令help，显示help信息
    1. 如果切换玩家时被跳过:
        1. 待在监狱, 提示"还要在监狱待xx天, 本轮跳过"
        1. 待在医院, 提示"还要在医院待xx天, 本轮跳过"
    1. 如果游戏结束, 有胜者, 提示"恭喜xxx获胜"
    
