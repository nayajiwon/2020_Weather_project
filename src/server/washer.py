import pymysql

scsc_db = pymysql.connect(
    user='scsc',
    passwd='scsc',
    host='127.0.0.1',
    db='scsc',
    charset='utf8'
)
cursor = scsc_db.cursor(pymysql.cursors.DictCursor)
def parse_input(target):
    wash_dict = {'자동세차':'1', '손세차':'2', '디테일링':'3', '출장세차':'4', '실내세차':'5', '출장세차(손세차)':'4'}
    name, address, phone, location, wash_type = target.split('	')
    lat, lon = location.split(',')
    lat.strip()
    lon.strip()
    wash_list = wash_type.split('/')
    city = input('시:').strip()
    district = input('구:').strip()
    dong = input('동:').strip()
    sql = '''INSERT INTO `car_wash_washer` (`name`, `lat`, `lon`, `address`, `phone`, `city`, `district`, `dong`)
    VALUES ("{}","{}","{}","{}","{}","{}","{}","{}");'''.format(name,lat,lon,address,phone,city,district,dong)
    print(sql)
    cursor.execute(sql)
    scsc_db.commit()

    wid = cursor.lastrowid

    for item in wash_list:
        wash_id = wash_dict.get(item)
        sql = '''INSERT INTO `car_wash_washer_wash_type` (washer_id, washtype_id)
        VALUES ("{}","{}");'''.format(wid, wash_id)
        cursor.execute(sql)
        print(sql)
    scsc_db.commit()
 

inp = input('세차장 정보 입력: ')
parse_input(inp)
print('입력 완료')
