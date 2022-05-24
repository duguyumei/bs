"""
日期：2022.04.03
"""
import pymysql

def connect():
    '''连接MySQL数据库'''
    try:
        db = pymysql.connect(
            host='127.0.0.1',
            port=3306,
            user='root',
            passwd='Gepoint',
            db='springboot-vue',
            charset='utf8'
            )
        return db
    except Exception:
        raise Exception("数据库连接失败")