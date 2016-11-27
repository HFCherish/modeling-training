class GameHelp
  def self.helpAsString
    res = "roll           掷骰子命令，行走1~6步。步数由随即算法产生。
block n     玩家拥有路障后，可将路障放置到离当前位置前后10步的距离，任一玩家经过路障，都将被拦截。该道具一次有效。n 前后的相对距离，负数表示后方。
bomb n    可将路障放置到离当前位置前后10步的距离，任一玩家j 经过在该位置，将被炸伤，送往医院，住院三天。n 前后的相对距离，负数表示后方。
robot        使用该道具，可清扫前方路面上10步以内的其它道具，如炸弹、路障。
sell x        出售自己的房产，x 地图上的绝对位置，即地产的编号。
sellTool x  出售道具，x 道具编号
query        显示自家资产信息
help          查看命令帮助
quit           强制退出"
    puts res.to_s
    return res.to_s
  end
end