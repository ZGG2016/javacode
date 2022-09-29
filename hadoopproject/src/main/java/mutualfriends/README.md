1、数据描述

每行数据：第一个字母是本人，后面的字母是它的好友

2、需求

求出哪些人两两之间有共同好友，及他俩的共同好友都有谁？

3、方法

对于A的朋友是B\C\D\E\F\，那么BC的共同朋友就是A。所以将BC作为key，将A作为value，在map端输出即可！其他的朋友循环处理。


4、输出

    AB      E:C:D
    AC      E:B
    AD      B:E
    AE      C:B:D
    BC      A:E
    BD      A:E
    BE      C:D:A
    BF      A
    CD      E:A:B
    CE      A:B
    CF      A
    DE      B:A
    DF      A
    EF      A