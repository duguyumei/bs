"""
日期：2022.04.03
"""
# coding=utf-8
from mysqlUtil import DoMysql
from math import *

import sys

openid = sys.argv[1]

# 连接mysql
mysql = DoMysql();
# 执行语句
sql = ''' select openid,marchant,source from evaluate order by stime'''
content = mysql.fetch_chall(sql)
# 格式化数据
data = {}
for line in content:
    user = line[0]
    marchant = line[1]
    sorce = line[2]
    # 如果之前不存在该用户,增加
    if not user in data.keys():
        data[user] = {marchant: sorce}
    # 如果存在多个评价,新评价占50%,之前的所有评分占50%
    elif marchant in data[user].keys():
        data[user][marchant] = (sorce + data[user][marchant]) / 2
    # 如果不存在该用户与该商家之间的评价,新增
    else:
        data[user][marchant] = sorce
# 关闭连接
mysql.fetch_code()

# 计算用户相似度
def Euclid(user1,user2):
    # 取出两个用户的购买数据
    user1Data = data[user1]
    user2Data = data[user2]
    a = []
    #设置默认距离,最相似
    distance =  0;

    #遍历,寻找相同店铺
    for key in user1Data.keys():
        if key in user2Data.keys():
            a.append(user1Data[key])
    if len(a) == 0:
        return 0;

    for key in user1Data.keys():
        if key in user2Data.keys():
            # 相似,距离相加,欧几里得公式
            distance += (pow(float(user1Data[key]) - float(user2Data[key]),2))
    #取倒数
    return 1 / (1 + sqrt(distance))


# 计算用户与其他用户的相似度
def top_simliar(user):
    res = []
    for userid in data.keys():
        # 排除当前用户
        if not userid == user:
            simliar = Euclid(user,userid)
            res.append((userid, simliar))
    #根据相似度排序,寻找最相似的用户
    res.sort(key=lambda val:val[1],reverse=True)
    return res

# 构建推荐方法
def recommend(user):
    for e in top_simliar(user):
        top_user = e[0]
        if(e[1] != 0):
            items = data[top_user]
            recommend_list = []
            for item in items.keys():
                # 如果存在用户未购买过的商家
                if item not in data[user].keys():
                    recommend_list.append((item, items[item]))
            if len(recommend_list) != 0:
                # 按评分排序
                recommend_list.sort(key=lambda val: val[1], reverse=True)
                return recommend_list;
    # # 取相似度最高的用户
    # top_user = top_simliar(user)[0][0]
    # # 取相似度最高的用户的购买信息
    # items = data[top_user]
    # #推荐列表
    # recommend_list = []
    # for item in items.keys():
    #     # 如果存在用户未购买过的商家
    #     if item not in data[user].keys():
    #         recommend_list.append((item,items[item]))
    # #按评分排序
    # recommend_list.sort(key=lambda val:val[1],reverse=True)
    # return recommend_list;

# 推荐
print(recommend(openid)[0])




