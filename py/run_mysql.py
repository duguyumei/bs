"""
日期：2022.04.03
"""
from public_mysql import connect   #调用public_mysql文件的connect函数

class MySQL():
    def implement(self):
        '''执行SQL语句'''
        db = connect()
        cursor = db.cursor()
        for i in range(1):
            sql = """SELECT * FROM `evaluate`"""
            try:
                cursor.execute(sql)
                result = cursor.fetchone()
                db.commit()
                return result
                print('查询结果：', result)
            except Exception:
                db.rollback()
                print("查询失败")

        cursor.close()
        db.close()

# if __name__ == '__main__':
#     a = MySQL()
#     a.implement()

def getData() :
    a = MySQL()
    a.implement()