"""
日期：2022.04.05
"""
import pymysql

class DoMysql:
    # 这里可以通过配置文件或者传参的方式来封装，但是我们用配置文件比较好管理
    def __init__(self):
        self.mysql = pymysql.connect (host='127.0.0.1', user='root', password='Gepoint', port=3306, db='springboot-vue')
        self.cursor = self.mysql.cursor ()

    # 返回单条数据
    def fetch_one(self, sql):
        self.cursor.execute (sql)
        return self.cursor.fetchone ()

    # 返回多条数据
    def fetch_chall(self, sql):
        self.cursor.execute (sql)
        return self.cursor.fetchall ()

    def fetch_code(self):
        self.cursor.close ()
        self.mysql.close ()
